import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.*;

public class App implements ActionListener {
    public static JFrame frame;
    JLabel welcomeLabel,usernameLabel,passwordLabel;
    JTextField usernameField,passwordField;
    JButton loginBtn;

    public static void main(String[] args) throws Exception {
       new App();
    }

    App(){
         // CREATE THE MAIN FRAME
         frame = new JFrame();
         frame.setSize(1980,1000);
         // INITIALIZE THE LOGIN SCREEN
  
        welcomeLabel = new JLabel();
        welcomeLabel.setText("Welcome to the ABC Restraurent App");
        welcomeLabel.setBounds(795, 100, 600, 400);
        frame.add(welcomeLabel);
   

        // INITIALIZE THE USERNAME AND PASSWORD TEST FIELDS
        usernameLabel = new JLabel("Username :");
        usernameLabel.setBounds(750,330,400,19);
        usernameField = new JTextField();
        usernameField.setBounds(750, 350, 400, 35);

        passwordLabel = new JLabel("Password :");
        passwordLabel.setBounds(750,390,400,20);
        passwordField = new JTextField();
        passwordField.setBounds(750, 410, 400, 35);

        frame.add(usernameLabel);
        frame.add(passwordLabel);
        frame.add(usernameField);
        frame.add(passwordField);


        // LOGIN BTN
        loginBtn = new JButton("Login");
        loginBtn.setBounds(900,470,100,30);
        loginBtn.addActionListener(this);
        frame.add(loginBtn);

        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){

        String username = usernameField.getText();
        String password = passwordField.getText();

        if(username.equals("admin") && password.equals("password")) {
            System.out.println("Congrats you have logged in.\n");
        } else {
            System.out.println("Login Unsuccessfull\n");
        }
        
    }
}
