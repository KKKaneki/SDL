import javax.swing.*;
import java.awt.event.*;

public class Menu {
    public static LoginUser currentUser;
    public static JFrame frame ;

    Menu(){
    }


    public static void createMenuFrame(LoginUser user){
        currentUser = user;
        frame = new JFrame();
        frame.setSize(600,600);

        // LABEL
        JLabel userLabel = new JLabel("USER : " + currentUser.USERNAME);
        userLabel.setBounds(400,10,100,40);
        frame.add(userLabel);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(500, 10, 80, 40);
        logoutBtn.addActionListener(new ActionListener(){
            @Override  
            public void actionPerformed(ActionEvent event){
            
                Login.logout();
                // Menu.frame.dispose();
                Menu.frame.setVisible(false);
                // App.frame.setVisible(true);
                App.loginFrameVisible();
            }

        });
        frame.add(logoutBtn);
        
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void menuFrameVisible(){
       
        frame.setVisible(true);
        App.frame.setVisible(false);
    }



}
