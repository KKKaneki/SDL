import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;


public class OrderHistoryFrame extends JFrame {
    OrderHistoryFrame(){
        setTitle("Order History");
        setSize(600, 600);
        // MENU BAR
        JMenuBar menuBar = new JMenuBar();
        JMenu userMenu, getMenu, addMenu, addOrder, orderHistory, order, chat,profile;
        userMenu = new JMenu("Users");
        getMenu = new JMenu("Menu Card");
        addMenu = new JMenu("Add Menu Item");
    
        addOrder = new JMenu("Add Order");
        orderHistory = new JMenu("Order History");
        order = new JMenu("Order Info");
        chat = new JMenu("Chat Server");
        profile = new JMenu(Menu.currentUser.USERNAME);
        JMenuItem logout = new JMenuItem("Logout");



        // menuBar.setLayout(new GridBagLayout());
        

        userMenu.addMenuListener(new MenuListener(){
            @Override
            public void menuSelected(final MenuEvent e) {
                dispose();
                new UserFrame();
            }
            @Override
            public void menuDeselected(final MenuEvent e) {
            }
            @Override
            public void menuCanceled(final MenuEvent e) {
            }
        });

        getMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(final MenuEvent e) {
                dispose();
                new MenuFrame();
            }
            @Override
            public void menuDeselected(final MenuEvent e) {
            }
            @Override
            public void menuCanceled(final MenuEvent e) {
            }
        });
        addMenu.addMenuListener(new MenuListener(){
            @Override
            public void menuSelected(final MenuEvent e) {
                dispose();
                new AddItemFrame();
            }
            @Override
            public void menuDeselected(final MenuEvent e) {
            }
            @Override
            public void menuCanceled(final MenuEvent e) {
            }
        });



        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                Login.logout();
                dispose();
                logoutMenuFrame();
            }

        });
                    
        menuBar.add(userMenu);
        menuBar.add(getMenu);
        menuBar.add(addMenu);
        menuBar.add(addOrder);
        menuBar.add(orderHistory); 
        menuBar.add(order);
        menuBar.add(chat);

        profile.add(logout);
        menuBar.add(profile);
        setJMenuBar(menuBar);


        // LABEL
         // FRAME
        JLabel heading = new JLabel("Order History");
        heading.setBounds(200, 10, 200, 40);
        heading.setFont(heading.getFont().deriveFont(20.0f));
        add(heading);
    

        Options.orderHistory(0);
        String[][] data = new String[Options.orders.orders.size()][4];
        int j = 0;
        for(int i = Options.orders.orders.size() - 1 ; i >= 0 ; i--,j++ ) {
            System.out.println(i);
            data[j][0] = String.valueOf(Options.orders.orders.get(i).orderID);
            data[j][1] = String.valueOf(Options.orders.orders.get(i).name);
            data[j][2] = String.valueOf(Options.orders.orders.get(i).phone);
            data[j][3] = String.valueOf("Rs. " + Options.orders.orders.get(i).orderPrice);    
        }
        
        JTable tb = new JTable(data,new Object[]{"Order No.","Name","Phone","Amount"});
        JScrollPane jsp = new JScrollPane(tb);
        jsp.setBounds(10,50,570,400);
        add(jsp);


        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    }

    public static void logoutMenuFrame() {
        System.out.println("Log out btn clicked...");
        new LoginFrame();
    }

}
