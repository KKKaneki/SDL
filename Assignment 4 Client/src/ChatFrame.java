import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.io.*;
import java.net.*;
import java.security.cert.Extension;


public class ChatFrame extends JFrame {
    public static String[] dishMenuItems = {"SIZZLER", "SOUP","ROTI","MAINCOURSE","RICE","FRUITSALAD", "DESSERT","BEVERAGE"};
    ChatFrame(){
        setTitle("Chat Server");
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

        JLabel lb = new JLabel("Name :");
        lb.setBounds(200, 40, 50,20);
        add(lb);

        JTextField name = new JTextField();
        name.setBounds(250, 40, 100, 30);
        add(name);

        JButton btn = new JButton("Enter");
        btn.setBounds(360, 40, 80, 30);
        add(btn);

        // MESSAGE
        JTextField mes = new JTextField();
        mes.setBounds(150, 500, 200, 30);
        mes.setVisible(false);
        add(mes);


        JButton send = new JButton("Send");
        send.setBounds(360, 500, 80, 30);
        send.setVisible(false);
        
        JButton exit = new JButton("Exit");
        exit.setBounds(450, 500, 80, 30);
        exit.setVisible(false);
        add(exit);
        add(send);
        DefaultTableModel jd = new DefaultTableModel();
        jd.addColumn("Server Chat");
        JTable chatTable = new JTable();
        chatTable.setFont(chatTable.getFont().deriveFont(14.0f));
       
        chatTable.setEnabled(false);
        chatTable.setModel(jd);
        JScrollPane jsp = new JScrollPane(chatTable);
        jsp.setBounds(10, 80, 560, 420);
        jsp.setVisible(false);

        btn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    mes.setVisible(true);
                    send.setVisible(true);
                    exit.setVisible(true);

                    Code code = new Code();
                    Scanner scan = new Scanner(System.in);
                    Socket socket = new Socket("localhost",8080);
                    DataOutputStream sendChoice = new DataOutputStream(socket.getOutputStream());
        
                    sendChoice.writeInt(code.chat);
                    String nm = name.getText();
                    
                    ObjectOutputStream dataOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream dataInputStream = new ObjectInputStream(socket.getInputStream());
                    jsp.setVisible(true);
                       
                        send.addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent e) {
                                    try {               

                                        Message message = new Message();
                                        message.name = nm;                         
                                        message.msg = mes.getText();
                                        jd.addRow(new Object[]{String.valueOf("[" + name.getText() + "] :" + mes.getText())});

                                        dataOutputStream.writeObject(message);


                                        System.out.println("Waiting for server response.."); 
                                        Message serverMessage = (Message) dataInputStream.readObject();
                                        jd.addRow(new Object[]{"[Server] :" + serverMessage.msg}); 

                                    } catch(Exception ex) {
                                        ex.printStackTrace();
                                    }                       
                            }
                        });


                        exit.addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent e) {
                                    try {               
              
                                        Message message = new Message();
                                        message.name = nm;                         
                                        message.msg = "EXIT";

                                        dataOutputStream.writeObject(message);

                                        socket.close();
                                        dispose();
                                        new Menu(Menu.currentUser);
                                    } catch(Exception ex) {
                                        ex.printStackTrace();
                                    }                       
                            }
                        });
                 
                
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
                
            }
        });


        
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
