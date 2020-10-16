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
                String cat = (String)cb.getSelectedItem();
                String name = (String) nameField.getText();
                Float price = Float.parseFloat(priceField.getText());
                if(name == "") {
                    nameError.setText("Name should not be empty");
                    nameError.setForeground(COLOR.RED);
                    wait(3000);
                    nameError.setText("");
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
