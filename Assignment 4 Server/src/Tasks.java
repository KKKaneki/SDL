import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class Tasks implements Runnable {
    public static Socket socket;
    public static DataInputStream dataInputStream;
    public static MainServer mainServer;
    public static Code code = new Code();

    
    public Tasks(Socket socket) {
        try {
            this.socket = socket; 
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            System.out.println("Thread : " + Thread.currentThread().getName());
            Integer inputCode = dataInputStream.readInt();
            System.out.println(inputCode);
            MainServer.handleTheUserChoice(inputCode);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    
}
