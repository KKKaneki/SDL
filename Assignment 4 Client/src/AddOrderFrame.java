import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class AddOrderFrame extends JFrame {
    public static String[] dishMenuItems = {"SIZZLER", "SOUP","ROTI","MAINCOURSE","RICE","FRUITSALAD", "DESSERT","BEVERAGE"};

    AddOrderFrame(){
        setTitle("Place Order");
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

        // LABEL
         // FRAME
        JLabel heading = new JLabel("Place Order");
        heading.setBounds(220, 10, 200, 40);
        heading.setFont(heading.getFont().deriveFont(20.0f));
        add(heading);

        String col[] = {"Type","Name","Quantity","Price"};
        String[][] data = new String[100][2];
        JTable tb = new JTable();
        tb.setEnabled(false);
        DefaultTableModel model = new DefaultTableModel();
                    tb.setModel(model);
                    model.addColumn("ID");
                    model.addColumn("Name");
                    model.addColumn("Quantity");
                    model.addColumn("Price");
       
        JScrollPane js = new JScrollPane(tb);
        js.setBounds(10, 50, 560, 350);
        add(js);



        JPanel jp = new JPanel();
        jp.setBounds(10, 420, 580, 40);
        // SELECT TYPE 
        JComboBox type = new JComboBox(dishMenuItems);
        JComboBox name = new JComboBox();
        String t = (String)type.getSelectedItem();
        Options.getMenu(0);
        Vector<Dishes> d = Options.restraurentMenu.Dishes.get(t);
        String[] arr = new String[d.size()];
        int i = 0;
        for(Dishes x : d) {
            arr[i] =  x.nameOfItem + " - Rs " + x.price;;
            i++;
        }
        DefaultComboBoxModel dcmn = new DefaultComboBoxModel(arr);
        name.setModel(dcmn);

        // QUANTITY
        JTextField qty = new JTextField("1");
        qty.setSize(100, 40);
        qty.setFont(qty.getFont().deriveFont(15.0f));
        
        JLabel qtyLabel = new JLabel("Quantity: ");
        
        // BUTTON
        JButton addBtn = new JButton("Add");
       
        jp.add(type);
        jp.add(name);
        jp.add(qtyLabel);
        jp.add(qty);
        jp.add(addBtn);
        Options.currentOrder = new Order();
        JLabel totalPrice = new JLabel("Total - Rs. " + Options.currentOrder.orderPrice);
        totalPrice.setBounds(450, 400, 100, 20);
        add(totalPrice);

        // ADD BUTTON LISTENER
        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int itemQty = Integer.parseInt(qty.getText());
                Dishes di = Options.restraurentMenu.Dishes.get((String)type.getSelectedItem()).get(name.getSelectedIndex());
                Options.currentOrder.dishItems.add(di);
                Options.currentOrder.qty.add(itemQty);
                Options.currentOrder.orderPrice += di.price*itemQty;
                totalPrice.setText("Total - Rs. " + Options.currentOrder.orderPrice);

                model.addRow(new Object[]{di.dishID,di.nameOfItem,itemQty,di.price*itemQty});
            }
        });

        type.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Options.getMenu(0);
                String t = (String)type.getSelectedItem();
                Vector<Dishes> d = Options.restraurentMenu.Dishes.get(t);

                String[] arr = new String[d.size()];
                int i = 0;
                for(Dishes x : d) {
                    arr[i] = x.nameOfItem + " - Rs " + x.price;
                    i++;
                }
                DefaultComboBoxModel dcmn = new DefaultComboBoxModel(arr);
                name.setModel(dcmn);
            }
        });

        add(jp);

        // PLACE ORDER 

        JLabel custName = new JLabel("Name : ");
        custName.setBounds(100,460,50,20);
        add(custName);
        JTextField customerName = new JTextField();
        customerName.setBounds(150,460,120,20);
        add(customerName);
        JLabel custPhone = new JLabel("Phone : ");
        custPhone.setBounds(300,460,50,20);
        add(custPhone);
        JTextField customerPhone = new JTextField();
        customerPhone.setBounds(350,460,150,20);
        add(customerPhone);
        
        JPanel orderBtnPanel = new JPanel();
        orderBtnPanel.setBounds(250, 480, 100, 50);
        JButton orderBtn = new JButton("Place Order");
        orderBtn.setBackground(Color.BLACK);
        orderBtn.setForeground(Color.WHITE);
        orderBtnPanel.add(orderBtn);

        orderBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String nameOfCustomer = (String) customerName.getText();
                String phoneOfCustomer = (String) customerPhone.getText();
                if(nameOfCustomer.equals("") || phoneOfCustomer.equals("") || Options.currentOrder.dishItems.size() == 0) {
                    JOptionPane.showMessageDialog(null, "Fill Name, Phone and One Dish at least","Error",JOptionPane.ERROR_MESSAGE);
                } else {
                    // VALID
                    Options.placeTheOrder(nameOfCustomer,phoneOfCustomer);
                    JOptionPane.showMessageDialog(null, "Order Placed.","Success",JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new OrderHistoryFrame();
                }
            }
        });

        add(orderBtnPanel);




        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    }

    public static void logoutMenuFrame() {
        System.out.println("Log out btn clicked...");
        new LoginFrame();
    }


}
