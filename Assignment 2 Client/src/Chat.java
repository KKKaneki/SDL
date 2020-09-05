import java.io.*;
import java.net.*;
import java.util.*;


class Message implements Serializable{
    public String msg;
}

public class Chat {
    

    public static void chatWithServer(){
        try {
            Code code = new Code();
            Scanner scan = new Scanner(System.in);
            Socket socket = new Socket("localhost",8080);
            DataOutputStream sendChoice = new DataOutputStream(socket.getOutputStream());

            sendChoice.writeInt(code.chat);

            ObjectOutputStream dataOutputStream = new ObjectOutputStream(socket.getOutputStream());

        

            ObjectInputStream dataInputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("Chat With Server (Use 'EXIT' to close)");
            while(true){
                Message message = new Message();
                System.out.print("[Client] Your Message :" );
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
        } catch(Exception e){
            e.printStackTrace();
        }  
    }
}
