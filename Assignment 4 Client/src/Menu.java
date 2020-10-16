import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.event.*;
import java.io.File;
import java.awt.*;
import java.awt.image.*;
public class Menu extends JFrame {
    public static LoginUser currentUser;
    public static JFrame frame ;
    public static String[] dishMenuItems = {"SIZZLER", "SOUP","ROTI","MAIN COURSE","RICE","FRUITSALAD", "DESSERT","BEVERAGE"};
    

    Menu(final LoginUser user) {
        try {
            setTitle("Home");
            this.currentUser = user;
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
            profile = new JMenu(this.currentUser.USERNAME);
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
            menuBar.add(orderHistory); 
            menuBar.add(order);
            menuBar.add(chat);
        
            profile.add(logout);
            menuBar.add(profile);



            setJMenuBar(menuBar);

            // HEADING
            JLabel heading = new JLabel("About This Plateform");
            heading.setFont(heading.getFont().deriveFont(20.0f));
            heading.setBounds(180, 50, 230, 30);
            add(heading);
            // ADD IMAGE
            BufferedImage pImage = ImageIO.read(new File("plateform.png"));
            Image img = pImage.getScaledInstance(282, 210, Image.SCALE_SMOOTH);
            JLabel imgl = new JLabel(new ImageIcon(img));
            imgl.setBounds(140,80,282,210);
            add(imgl);

            // INFORMATION ABOUT 
            JTextArea info = new JTextArea("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
            info.setBounds(20,300,540,180);
            info.setWrapStyleWord(true);
            info.setLineWrap(true);
            info.setEditable(false);
            info.setFont(info.getFont().deriveFont(16.0f));
            info.setMargin(new Insets(5,5,5,5));
            add(info);


       
            setLayout(null);
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void logoutMenuFrame() {
        System.out.println("Log out btn clicked...");
        new LoginFrame();
    }

}
