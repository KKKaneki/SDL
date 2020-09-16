import java.net.*;
import java.io.*;
import java.util.*;

public class ClientThread extends Thread {
    public static Socket socket;    
    public static ObjectOutputStream dataOutputStream;
    public static ObjectInputStream dataInputStream;
    
    public ClientThread(Socket socket) throws Exception{
        this.socket = socket;
        dataOutputStream = new ObjectOutputStream(socket.getOutputStream());
        dataInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void run(){
        try {
            Scanner scan = new Scanner(System.in);

            System.out.print("Enter your name : ");
            String name = scan.nextLine();
           
            System.out.println("Chat With Server (Use 'EXIT' to close)");
            while(true){
                Message message = new Message();
                System.out.print("[" + name + "] Your Message :" );
                message.name = name;
                message.msg = scan.nextLine();

                dataOutputStream.writeObject(message);
                if(message.msg.toUpperCase().equals("EXIT")) {
                    System.out.println("You have closed connection ...");
                    break;
                } 
                System.out.println("Waiting for server response..");
                Message serverMessage = (Message) dataInputStream.readObject();
                System.out.println("[Server] Message : " + serverMessage.msg);
                if(serverMessage.msg.toUpperCase().equals("EXIT")) {
                    System.out.println("Server have closed connection ...");
                    break;
                } 
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
