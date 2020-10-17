import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class OrderInfoFrame extends JFrame {
    OrderInfoFrame(){
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


        
    }
    public static void logoutMenuFrame() {
        System.out.println("Log out btn clicked...");
        new LoginFrame();
    }
}
