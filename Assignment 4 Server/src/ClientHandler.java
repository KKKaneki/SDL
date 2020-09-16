import java.util.*;
import java.io.*;



public class ClientHandler extends Thread {
    private static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();    
    public static  ObjectOutputStream objectOutputStream;
    public static ObjectInputStream objectInputStream;


    public ClientHandler(ObjectInputStream objectInputStream,ObjectOutputStream objectOutputStream) throws Exception {
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
        // this.clients = clients;
   }


    public void run() {
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
                message.msg = sendMessageToAll();

                objectOutputStream.writeObject(message);

                if(message.msg.toUpperCase().equals("EXIT")) {
                    System.out.println("You have closed connection ...");
                    break;
                } 
                System.out.println("Waiting for client response..");
               
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String sendMessageToAll(){
        Scanner scan = new Scanner(System.in);
        System.out.print("[Server] Your Message : ");
        String s = scan.nextLine();
        return s;        
    }
}
