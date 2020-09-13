import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



class Message implements Serializable{
    public String msg;
    public String name;
}

public class Chat {
    public  ObjectOutputStream objectOutputStream;
    public  ObjectInputStream objectInputStream;
    ExecutorService executorService;


    private static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    
    Chat(ObjectOutputStream objectOutputStream,ObjectInputStream objectInputStream) {
         this.objectOutputStream = objectOutputStream;
         this.objectInputStream = objectInputStream;
         executorService = Executors.newFixedThreadPool(5);
    }

 

    public void chatWithClient(Socket so,ServerSocket ss){
        try {
            Scanner scan = new Scanner(System.in);

            while(true){
                Message message = new Message();
                
                Message clientMessage = (Message) objectInputStream.readObject();
                System.out.println("[" + clientMessage.name  + "] Message : " + clientMessage.msg);
                if(clientMessage.msg.toUpperCase().equals("EXIT")) {
                    System.out.println(clientMessage.name + " has closed connection ...");
                    break;
                } 
                System.out.print("[Server] Your Message to" + clientMessage.name + ": ");
                message.msg = scan.nextLine();
               // message.msg = sendMessageToAll();

                objectOutputStream.writeObject(message);

                if(message.msg.toUpperCase().equals("EXIT")) {
                    System.out.println("You have closed connection ...");
                    break;
                } 
                System.out.println("Waiting for client response..");  
            }
                      
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
