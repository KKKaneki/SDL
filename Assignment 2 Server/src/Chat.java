import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.*;



class Message implements Serializable{
    public String msg;
}

public class Chat {
    

    public static void chatWithClient(ObjectOutputStream objectOutputStream,ObjectInputStream objectInputStream){
        try {
            Scanner scan = new Scanner(System.in);
            while(true){
                Message message = new Message();
                
                Message clientMessage = (Message) objectInputStream.readObject();
                System.out.println("[Client] Message : " + clientMessage.msg);
                if(clientMessage.msg.toUpperCase().equals("EXIT")) {
                    System.out.println("Client has closed connection ...");
                    break;
                } 
                System.out.print("[Server] Your Message : ");
                message.msg = scan.nextLine();
                System.out.println("Waiting for client response..");
                objectOutputStream.writeObject(message);
                if(message.msg.toUpperCase().equals("EXIT")) {
                    System.out.println("You have closed connection ...");
                    break;
                } 
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
