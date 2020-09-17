import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.io.*;
import java.util.*;

import java.awt.event.*;
import java.util.BitSet;

public class App implements ActionListener {
    public static JFrame frame;
    JLabel welcomeLabel,usernameLabel,passwordLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginBtn;

    LoginUser loggedInUser = new LoginUser();

    public static void main(String[] args) throws Exception {
       new App();


       try {
//			INITIALIZE THE SCANNER
            Scanner scan = new Scanner(System.in);     
            Login.isLoggedIn = false;
            Login.whoIsLogged = new BitSet(2);
            Login.whoIsLogged.clear();
         
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    App(){
         // CREATE THE MAIN FRAME
         frame = new JFrame();
         frame.setSize(600,500);
         // INITIALIZE THE LOGIN SCREEN
  
        welcomeLabel = new JLabel();
        welcomeLabel.setText("Welcome to the ABC Restraurent App");
        welcomeLabel.setBounds(175, 10, 600, 200);
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
        loginBtn.setBounds(260,260,100,30);
        loginBtn.addActionListener(this);
        frame.add(loginBtn);

        while(!Login.isLoggedIn) {
            frame.setLayout(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
        loggedInUser.USERNAME = usernameField.getText();
        loggedInUser.PASSWORD = String.valueOf(passwordField.getPassword());

        // DISPOSE THIS FRAME WHEN LOGGED IN AND MAKE ANOTHER FRAME
        frame.dispose();
        new Menu(loggedInUser);
        
    }

    public void actionPerformed(ActionEvent e){
            
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            LoginClass login = new LoginClass(username,password);
            login.attemptLogin();
    
    }
}
