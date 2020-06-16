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

public class UdpServer extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];
    private static int portNumber = 9000;
    Controller controller;

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
            sendUpdatedData(received);
        }
        socket.close();
    }

    private void sendUpdatedData(String received) {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                updateData(parseText(received));
                logInfo(received);
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
                //String toSplit = fields[0];
                String id = fields[0];//toSplit.split("\\.")[0];
                return new String[]{id, fields[1], fields[2], fields[3]};
            } catch (Exception e){
                System.out.println("Problem with data: "+ text);
                return null;
            }
        } else return null;
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