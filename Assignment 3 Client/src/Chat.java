import java.io.*;
import java.net.*;
import java.util.*;


class Message implements Serializable{
    public String msg;
    public String name;
}

public class Chat {

    public static void chatWithServer(){
        try {
            Code code = new Code();
            Scanner scan = new Scanner(System.in);
            Socket socket = new Socket("localhost",8080);
            DataOutputStream sendChoice = new DataOutputStream(socket.getOutputStream());

            sendChoice.writeInt(code.chat);

            ClientThread ct = new ClientThread(socket);
           
            Thread chatThread = new Thread(ct);
            chatThread.start();

            chatThread.join();

            chatThread.interrupt();
           
        } catch(Exception e){
            e.printStackTrace();
        }  
    }
}
