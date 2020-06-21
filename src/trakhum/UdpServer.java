package trakhum;

import javafx.application.Platform;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Vector;

public class UdpServer extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];
    private static int portNumber = 9000;
    Controller controller;
    Vector dataIn = new Vector<String>();

    public UdpServer(Controller controller) throws SocketException {
        this.controller = controller;
        socket = new DatagramSocket(portNumber);
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void run() {
        running = true;
        logInfo("Starting UDP Server "+getLocalIpAddress()+" on port "+portNumber);
        while (running) {
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
                logInfo("Error: "+e.getLocalizedMessage());
            }
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            String received
                    = new String(packet.getData(), 0, packet.getLength());
            dataIn.add(received);
            sendUpdatedDataToUI();
        }
        socket.close();
    }

    private void sendUpdatedDataToUI() {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                String lastReceived = (String)dataIn.lastElement();
                String [] fields = parseText(lastReceived);
                updateData(fields);
                logInfo(lastReceived);
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    synchronized private void logInfo(String text){
        controller.updateLog(text);
    }

    synchronized private void updateData(String [] dataFields){
        controller.updateDataOnView(dataFields);
    }

    private String[] parseText(String text) {
        String fields[] = text.split(",");
        if(fields!=null && fields.length>=4) {
            try {
                String toSplit = fields[0];
                String id = toSplit.split("\\.")[0];
                if(id!=null && id.length()>=13) {
                    id = id.substring(0,12);
                    return new String[]{id, fields[7], fields[8], fields[9]};
                }
            } catch (Exception e){
                System.out.println("Problem with data: "+ text);
                return null;
            }
        }
        return null;
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return "Error while getting local IP address";
    }
}