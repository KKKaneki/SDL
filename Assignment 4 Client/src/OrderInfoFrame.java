import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class OrderInfoFrame extends JFrame {
    OrderInfoFrame(){
        setTitle("Order Info");
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
        
        addOrder.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(final MenuEvent e) {
                dispose();
                new AddOrderFrame();
            }
            @Override
            public void menuDeselected(final MenuEvent e) {
            }
            @Override
            public void menuCanceled(final MenuEvent e) {
            }
        });

        orderHistory.addMenuListener(new MenuListener(){
            @Override
            public void menuSelected(final MenuEvent e) {
                dispose();
                new OrderHistoryFrame();
            }
            @Override
            public void menuDeselected(final MenuEvent e) {
            }
            @Override
            public void menuCanceled(final MenuEvent e) {
            }
        });
        order.addMenuListener(new MenuListener(){
            @Override
            public void menuSelected(final MenuEvent e) {
                dispose();
                new OrderInfoFrame();
            }
            @Override
            public void menuDeselected(final MenuEvent e) {
            }
            @Override
            public void menuCanceled(final MenuEvent e) {
            }
        });

        chat.addMenuListener(new MenuListener(){
            @Override
            public void menuSelected(final MenuEvent e) {
                dispose();
                new ChatFrame();
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


        // MAIN FRAME INFO
         // HEADING
         JLabel heading = new JLabel("Order Info");
         heading.setFont(heading.getFont().deriveFont(20.0f));
         heading.setBounds(220, 10, 100, 30);
         add(heading);
        // END OF INFO FRAME
        JLabel enterText = new JLabel("Enter Order ID : ");
        enterText.setBounds(160,50,120,40);
        add(enterText);
        JTextField input = new JTextField();
        input.setBounds(280,50,120,40);
        add(input);
        JButton findBtn = new JButton("Find");
        findBtn.setBounds(400,50,100,40);
        add(findBtn);
        JLabel jl = new JLabel("Order not found.");
        jl.setBounds(10, 90, 560, 30);
        jl.setVisible(false);
        add(jl);

        DefaultTableModel dfm = new DefaultTableModel();
        dfm.addColumn("ID");
        dfm.addColumn("Name");
        dfm.addColumn("Quantity");
        dfm.addColumn("Price");
        JTable tb = new JTable();
        tb.setModel(dfm);
        JScrollPane js = new JScrollPane(tb);
        js.setBounds(10, 100, 560, 350);

        // EDIT BUTTON
        JButton edit = new JButton("Update");
        edit.setVisible(false);
        edit.setBounds(250, 450,100 , 20);
        add(edit);

        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                dispose();
                new EditOrderFrame();
            }
        });

        findBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String id = (String) input.getText();
                if(Options.getOrder(id) == 1) {
                    // SHOW CURRENT ORDER NOW
                    String[][] data = new String[Options.currentOrder.dishItems.size()][4];

                    for(int i = 0 ; i < Options.currentOrder.dishItems.size(); i++) {
                        data[i][0] = String.valueOf(Options.currentOrder.orderID);
                        data[i][1] = Options.currentOrder.dishItems.get(i).nameOfItem;
                        data[i][2] = String.valueOf(Options.currentOrder.qty.get(i));
                        data[i][3] = String.valueOf(Options.currentOrder.dishItems.get(i).price);
                        dfm.addRow(new Object[]{data[i][0],data[i][1],data[i][2],data[i][3]});
                    }
                    tb.setEnabled(false);
                    edit.setVisible(true);

                    js.setVisible(true);

                    jl.setVisible(false);

                    add(js);
                } else {
                    js.setVisible(false);
                    jl.setVisible(true);

                }
               
            }
        });


        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void logoutMenuFrame() {
        System.out.println("Log out btn clicked...");
        new LoginFrame();
    }
}
