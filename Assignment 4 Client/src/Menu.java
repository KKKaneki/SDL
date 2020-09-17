import javax.swing.*;
import java.awt.event.*;

public class Menu implements ActionListener {
    LoginUser currentUser;
    JFrame frame;

    Menu(LoginUser user){
        this.currentUser = user;
        frame = new JFrame();
        frame.setSize(600,600);

        // LABEL
        JLabel userLabel = new JLabel("USER : " + this.currentUser.USERNAME);
        userLabel.setBounds(400,10,100,40);
        frame.add(userLabel);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(500, 10, 80, 40);
        logoutBtn.addActionListener(this);
        frame.add(logoutBtn);
        
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event){
        Login.logout();
        frame.dispose();
        new App();
    }


}
