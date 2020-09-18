import java.io.Serializable;
import javax.swing.*;

class LoginUser implements Serializable {
    String USERNAME;
    String PASSWORD; 
    LoginUser(){}
}

public class LoginClass {
    public LoginUser user;

    LoginClass(String username,String password) {
        user = new LoginUser();
        user.USERNAME = username;
        user.PASSWORD = password;
    }

    public void attemptLogin(JFrame frame){
        JOptionPane jPane = new JOptionPane();
            // GET THE LOGIN
            Login.getLoginMenu(user);
            System.out.println(user.PASSWORD + " " + user.USERNAME);
            if(Login.isLoggedIn) {
                if(Login.whoIsLogged.get(0)) {
                    jPane.showMessageDialog(frame, "ADMIN logged in successfully!!", "Correct Credentials", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("ADMIN has logged in.\n");
                } else {
                    jPane.showMessageDialog(frame, "Logged in successfully!!", "Correct Credentials", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Staff has logged in.\n");
                }
            } else {
                jPane.showMessageDialog(frame, "Unsuccessfully Login !!", "Incorrect Credentials", JOptionPane.ERROR_MESSAGE);
                System.out.println("Login Unsuccessfull\n");
            }
    }
}
