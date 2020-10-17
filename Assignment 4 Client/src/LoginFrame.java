
import java.io.Serializable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

class LoginUser implements Serializable {
    String USERNAME;
    String PASSWORD; 
}

public class LoginFrame extends JFrame  {

    public static JFrame frame;
    public static JLabel welcomeLabel,usernameLabel,passwordLabel;
    public static JTextField usernameField;
    public static JPasswordField passwordField;
    public static JButton loginBtn;

    public static LoginUser loggedInUser = new LoginUser();


    public LoginFrame(){
         // CREATE THE MAIN FRAME
         frame = new JFrame("Login");
         frame.setSize(600,500);
         // INITIALIZE THE LOGIN SCREEN
  
        welcomeLabel = new JLabel();
        welcomeLabel.setText("Welcome to the ABC Restraurent App");
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(20.0f));
        welcomeLabel.setBounds(150, 10, 600, 180);
        frame.add(welcomeLabel);
   
 
        // INITIALIZE THE USERNAME AND PASSWORD TEST FIELDS
        usernameLabel = new JLabel("Username :");
        usernameLabel.setBounds(120,130,400,19);
        usernameField = new JTextField();
        usernameField.setBounds(120, 150, 400, 35);
 
        passwordLabel = new JLabel("Password :");
        passwordLabel.setBounds(120,190,400,20);
        passwordField = new JPasswordField();
        passwordField.setBounds(120, 210, 400, 35);
 
        frame.add(usernameLabel);
        frame.add(passwordLabel);
        frame.add(usernameField);
        frame.add(passwordField);
 
 
        // LOGIN BTN
        loginBtn = new JButton("Login");
        loginBtn.setFont(loginBtn.getFont().deriveFont(14.0f));
        loginBtn.setBounds(260,260,100,30);
        loginBtn.setBackground(Color.BLACK);
        loginBtn.setForeground(Color.WHITE);
        
        frame.add(loginBtn);
 
        loggedInUser.USERNAME = usernameField.getText();
        loggedInUser.PASSWORD = String.valueOf(passwordField.getPassword());
 
        loginBtn.addActionListener(new ActionListener(){
            @Override
             public void actionPerformed(ActionEvent e){ 
                 LoginFrame.loggedInUser.USERNAME = usernameField.getText();
                 LoginFrame.loggedInUser.PASSWORD= String.valueOf(passwordField.getPassword());
 
                 attemptLogin();
             }
        });
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void attemptLogin(){
        // GET THE LOGIN
        Login.getLoginMenu(loggedInUser);

        if(Login.isLoggedIn) {
            if(Login.whoIsLogged.get(0)) {
                JOptionPane.showMessageDialog(frame, "ADMIN logged in successfully!!", "Correct Credentials", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("ADMIN has logged in.\n");
            } else {
                JOptionPane.showMessageDialog(frame, "Logged in successfully!!", "Correct Credentials", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Staff has logged in.\n");
            }
            
            frame.dispose();

            new Menu(loggedInUser);
        } else {
            JOptionPane.showMessageDialog(frame, "Unsuccessfully Login !!", "Incorrect Credentials",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println("Login Unsuccessfull\n");
        }
    }

    
}
