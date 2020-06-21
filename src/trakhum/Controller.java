package trakhum;

import java.net.SocketException;
import java.io.FileWriter;
import java.io.IOException;


public class Controller {
    MainView mainView;
    int msgCount = 0;
    String id1 = null;
    String id2 = null;
    FileWriter myWriter;

    public void startProcessing(MainView mainView){
        this.mainView = mainView;
        UdpServer udp = null;
        try {
            udp = new UdpServer(this);
            udp.start();
        } catch (SocketException e) {
            e.printStackTrace();
            mainView.updateLog("Error: "+e.getMessage());
        }
        try {
            myWriter = new FileWriter("/Users/antoniopaes/Documents/log/javalog.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateDataOnView(String[] data){
        if(data!=null){
            String id = data[0];
            if (id1 == null ) {
                id1 = id;
            } else if (id2 == null && !id1.equals(id)) {
                id2 = id;
            }
            if (id.equals(id1)) {
                mainView.updateDataSet1(data);
                //System.out.println("Updating data: "+data[0]);
            } else if (id.equals(id2)) {
                mainView.updateDataSet2(data);
                //System.out.println("Updating data: "+data[0]);
            }
        } else {
            System.out.println("Null data, can't update data");
        }
    }

    public void updateLog(String str){
        mainView.updateLog(msgCount+ ":::"+str);
        //writeToFile(msgCount+ ":::"+str);
        msgCount++;
    }


    private void writeToFile(String data) {
        try {
            myWriter.write(data+"\n");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
