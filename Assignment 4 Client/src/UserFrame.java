import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class UserFrame extends JFrame {
    public static String[] dishMenuItems = {"SIZZLER", "SOUP","ROTI","MAIN COURSE","RICE","FRUITSALAD", "DESSERT","BEVERAGE"};
    public RegisteredUser registeredUser;
    UserFrame() {
        setTitle("Users");
            setSize(600, 600);
    
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

            registeredUser = Options.showAllUsers();

            // DISPLAY THE USERS ON THE FRAME
            displayUser();

            setLayout(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
    }
    public static void logoutMenuFrame() {
        System.out.println("Log out btn clicked...");
        new LoginFrame();
    }

    public void displayUser() {
        // HEADING
        JLabel heading = new JLabel();
        heading.setText("Users");
        heading.setFont(heading.getFont().deriveFont(20.0f));
        heading.setBounds(250, 40, 100,30);
        add(heading);

        final Enumeration<?> user = registeredUser.users.propertyNames();
        String[] columns = {"S.No","Name"};

        String[][] data = new String[registeredUser.users.size()][2];
        Integer i = 1;
        while(user.hasMoreElements()){
            data[i-1][0] = String.valueOf(i);
            data[i-1][1] = String.valueOf(user.nextElement());
            i++;
        }

        JTable table = new JTable(data,columns);
        table.setEnabled(false);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(80,80,400,400);

        add(scroll);
    }
}