import javax.swing.*;
import java.awt.event.*;
import java.util.EventListener;
import java.awt.*;
import javax.swing.event.*;


public class AddItemFrame extends JFrame {
    public static String[] dishMenuItems = {"SIZZLER", "SOUP","ROTI","MAINCOURSE","RICE","FRUITSALAD", "DESSERT","BEVERAGE"};

    AddItemFrame(){
        setTitle("Add Item");
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


        // FRAME
        JLabel heading = new JLabel("Add Dish");
        heading.setBounds(200, 20, 100, 50);
        heading.setFont(heading.getFont().deriveFont(20.0f));
        add(heading);


        JLabel subCat = new JLabel("Category : ");
        subCat.setBounds(40, 80, 100, 20);
        add(subCat);

        JComboBox cb = new JComboBox(dishMenuItems);
        cb.setBounds(40,100,100,30);
        add(cb);

        JLabel name = new JLabel("Dish Name : ");
        name.setBounds(180, 80, 100, 20);
        add(name);

        JTextField nameField = new JTextField();
        nameField.setBounds(180,100,150,30);
        add(nameField);
        // ERROR MESSAGE
        JLabel nameError = new JLabel("");
        nameError.setBounds(180,130,150,20);
        add(nameError);

        JLabel price = new JLabel("Price (Rs): ");
        price.setBounds(350, 80, 100, 20);
        add(price);
        JTextField priceField = new JTextField();
        priceField.setBounds(350,100,150,30);
        add(priceField);
        // ERROR MESSAGE
        JLabel priceError = new JLabel("");
        priceError.setBounds(350,130,150,20);
        add(priceError);

        // ADD BUTTON
        JButton addButton = new JButton("Add");
        addButton.setBounds(200,160,100,30);
        add(addButton);

        // EVENT LISTENER
        addButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    String cat = (String)cb.getSelectedItem();
                    String name = (String) nameField.getText();
                    Float price = Float.parseFloat(priceField.getText());
                    System.out.println("Cat : " + cat);
                    System.out.println("Name : " + name);
                    System.out.println("Price : " + price);

                        int flag = 1;
                        if(name.equals("")) {
                            nameError.setText("Name should not be empty");
                            nameError.setForeground(new Color(255,0,0));
                            wait(3000);
                            flag = 0;
                            nameError.setText("");
                        }
                        if(String.valueOf(price).equals("")) {
                            priceError.setText("Price should not be empty");
                            priceError.setForeground(new Color(255,0,0));
                            wait(3000);
                            flag = 0;
                            priceError.setText("");
                        }
                        if(flag == 1) {
                            if(cat.equals(dishMenuItems[0])){
                                Options.addNewDish(1,name,price);
                            } else if(cat.equals(dishMenuItems[1])){
                                Options.addNewDish(2,name,price);
                            } else if(cat.equals(dishMenuItems[2])){
                                Options.addNewDish(3,name,price);
                            } else if(cat.equals(dishMenuItems[3])){
                                Options.addNewDish(4,name,price);
                            } else if(cat.equals(dishMenuItems[4])){
                                Options.addNewDish(5,name,price);
                            } else if(cat.equals(dishMenuItems[5])){
                                Options.addNewDish(6,name,price);
                            } else if(cat.equals(dishMenuItems[6])){
                                Options.addNewDish(7,name,price);
                            } if(cat.equals(dishMenuItems[7])){
                                Options.addNewDish(8,name,price);
                            } 

                            JOptionPane.showMessageDialog(null, "Item Added Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new MenuFrame();
                        }
                
                } catch(Exception ex) {
                        JOptionPane.showMessageDialog(null,"Name should not be empty and Price should be a number\n", "Error",JOptionPane.ERROR_MESSAGE);
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
