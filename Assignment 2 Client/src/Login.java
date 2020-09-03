import java.io.*;
import java.util.*;
import java.net.*;

class LoginUser implements Serializable {
    String USERNAME;
    String PASSWORD; 
    LoginUser(){}
}

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
    public static BitSet whoIsLogged;
	public static void main(String[] args) {
		try {
//			INITIALIZE THE SCANNER
			scan = new Scanner(System.in);
			
			isLoggedIn = false;
            whoIsLogged = new BitSet(2);
            whoIsLogged.clear();
            
            // GET THE LOGIN
            getLoginMenu();
        
            
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
    
    // GET THE LOGIN MENU
	public static void getLoginMenu() {
        try {

           LoginUser user = new LoginUser();
            // RUNS TILL THE USER IS NOT LOGGED IN
           while(!isLoggedIn){
               socket = new Socket("localhost",8080);

               DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
               dataOutputStream.writeInt(code.login);
               if(notFirstTimeFailed){
                   System.out.println("INCORRECT USERNAME/PASSWORD TRY AGAIN...\n");
               }
               System.out.println("WE NEED TO CONFIRM THAT YOU ARE A PART OF RESTAURENT\n");
               System.out.println("LOGIN");
               // ASK THE USER FOR HIS USERNAME
               System.out.print("Enter username : ");
               user.USERNAME = scan.nextLine();
               // ASK THE USER FOR HIS PASSWORD
               System.out.print("Enter password: ");
               user.PASSWORD = scan.nextLine();
               System.out.println("");
               
               
               userObjectOutputStream = new ObjectOutputStream(socket.getOutputStream());
               userObjectOutputStream.writeObject(user);
               
   
               userObjectInputStream = new ObjectInputStream(socket.getInputStream());
               ServerResponse response = (ServerResponse) userObjectInputStream.readObject();
            
               
            //    INITIALIZE ISADMIN
               if(response.success == true && response.msg.equals("ADMIN")) whoIsLogged.set(0);
               else if(response.success) whoIsLogged.set(1);

               if(response.success) {
                   isLoggedIn = true;
                   System.out.println("Logged in successfully\n");


                   Options option = new Options();
                   // CHECK IF ADMIN
                   if(isLoggedIn && whoIsLogged.get(0)) option.getAdminMenu();
                   else option.getUserMenu(); 
               }

               //WHEN THE USERNAME AND THE PASSWORD EXISTS
               userObjectInputStream.close();
            }


        } catch(Exception e){
            e.printStackTrace();
        }
    }
    

     // LOGOUT OF THE SYSTEM
     public static void logout(){
        isLoggedIn = false;
        whoIsLogged.clear();
        getLoginMenu();
        System.out.print("Logout");
    }
	
}
