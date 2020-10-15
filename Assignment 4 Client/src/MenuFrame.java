import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuFrame extends JFrame {
    public static String[] dishMenuItems = {"SIZZLER", "SOUP","ROTI","MAIN COURSE","RICE","FRUITSALAD", "DESSERT","BEVERAGE"};

    MenuFrame() {
        setTitle("Menu Card");
        setSize(1600,800);
        Options.getMenu(0);
        
         // HEADING
         JLabel heading = new JLabel();
         heading.setText("Menu");
         heading.setFont(heading.getFont().deriveFont(25.0f));
         heading.setBounds(750, 20, 100,30);
         add(heading);

         // MENU BAR
         JMenuBar menuBar = new JMenuBar();
         JMenu userMenu, getMenu, addMenu, addOrder, orderHistory, order, chat,profile;
         userMenu = new JMenu("Users");
         getMenu = new JMenu("Menu Card");
         addMenu = new JMenu("Add Menu Item");
         // ADD ALL THE ITEMS TO BE ADDED
         for(String item: dishMenuItems) {
             JMenuItem dishItem = new JMenuItem(item);
             addMenu.add(dishItem);
         }
 
 
         addOrder = new JMenu("Add Order");
         orderHistory = new JMenu("Order History");
         order = new JMenu("Order Info");
         chat = new JMenu("Chat Server");
         profile = new JMenu(Menu.currentUser.USERNAME);
         JMenuItem logout = new JMenuItem("Logout");
         profile.add(logout);
 
         
         menuBar.add(userMenu);
         menuBar.add(getMenu);
         menuBar.add(addMenu);
         menuBar.add(orderHistory); 
         menuBar.add(order);
         menuBar.add(chat);
         menuBar.add(profile);
     
 
         // menuBar.setLayout(new GridBagLayout());
         
 
         setJMenuBar(menuBar);
 
         logout.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(final ActionEvent event) {
                 Login.logout();
                 dispose();
                 logoutMenuFrame();
             }    
         });

         setJMenuBar(menuBar);


        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        


    }
    public static void logoutMenuFrame() {
        System.out.println("Log out btn clicked...");
        new LoginFrame();
    }
}
