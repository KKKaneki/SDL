import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.*;



class ServerResponse implements Serializable {
    public String msg;
    public Boolean success;
}

public class Login {
	public static boolean isLoggedIn,notFirstTimeFailed;
	public static Scanner scan;
    public static ObjectOutputStream userObjectOutputStream;
    public static ObjectInputStream userObjectInputStream;
    public static Socket socket;
    public static Code code = new Code();
    
    
    // CHECK IF ADMIN
    public static BitSet whoIsLogged = new BitSet(2);

    // GET THE LOGIN MENU
	public static void getLoginMenu(LoginUser user) {
        try {

            // RUNS TILL THE USER IS NOT LOGGED IN
               socket = new Socket("localhost",8080);

               DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
               dataOutputStream.writeInt(code.login);
               if(notFirstTimeFailed){
                   System.out.println("INCORRECT USERNAME/PASSWORD TRY AGAIN...\n");
               }
               
               userObjectOutputStream = new ObjectOutputStream(socket.getOutputStream());
               userObjectOutputStream.writeObject(user);
               
   
               userObjectInputStream = new ObjectInputStream(socket.getInputStream());
               ServerResponse response = (ServerResponse) userObjectInputStream.readObject();
            
               
            //    INITIALIZE ISADMIN
               System.out.print(response.success);
    
               if(response.success == true && response.msg.equals("ADMIN")) whoIsLogged.set(0);
               else if(response.success) whoIsLogged.set(1);

               if(response.success) {
                   isLoggedIn = true;
                   System.out.println("Logged in successfully\n");
               }

               //WHEN THE USERNAME AND THE PASSWORD EXISTS
               userObjectInputStream.close();


        } catch(Exception e){
            e.printStackTrace();
        }
    }
    

     // LOGOUT OF THE SYSTEM
     public static void logout(){
        isLoggedIn = false;
        whoIsLogged.clear();
        //getLoginMenu();
        System.out.print("Logout");
    }
	
}
