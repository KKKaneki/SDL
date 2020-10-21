import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class EditOrderFrame extends JFrame {
    public static String[] dishMenuItems = {"SIZZLER", "SOUP","ROTI","MAINCOURSE","RICE","FRUITSALAD", "DESSERT","BEVERAGE"};

    EditOrderFrame(){
        setTitle("Update Order");
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
        JLabel heading = new JLabel("Edit Order");
        heading.setBounds(220, 10, 200, 40);
        heading.setFont(heading.getFont().deriveFont(20.0f));
        add(heading);

        String col[] = {"Type","Name","Quantity","Price"};
        JTable tb = new JTable();
        tb.setEnabled(false);
        DefaultTableModel model = new DefaultTableModel();
                    tb.setModel(model);
                    model.addColumn("ID");
                    model.addColumn("Name");
                    model.addColumn("Quantity");
                    model.addColumn("Price");

        String[][] data = new String[Options.currentOrder.dishItems.size()][4];

        for(int i = 0 ; i < Options.currentOrder.dishItems.size(); i++) {
            data[i][0] = Options.currentOrder.dishItems.get(i).dishID;
            data[i][1] = Options.currentOrder.dishItems.get(i).nameOfItem;
            data[i][2] = String.valueOf(Options.currentOrder.qty.get(i));
            data[i][3] = String.valueOf(Options.currentOrder.dishItems.get(i).price);
            model.addRow(new Object[]{data[i][0],data[i][1],data[i][2],data[i][3]});
        }
       
        JScrollPane js = new JScrollPane(tb);
        js.setBounds(10, 50, 560, 300);
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
        JLabel totalPrice = new JLabel("Total - Rs. " + Options.currentOrder.orderPrice);
        totalPrice.setBounds(450, 400, 100, 20);
        add(totalPrice);


        // DELETE ITEMS
        JPanel existingPanel = new JPanel();
        existingPanel.setBounds(10,350,560,50);
        String[] existing = new String[Options.currentOrder.dishItems.size()];
        for( i = 0 ; i < Options.currentOrder.dishItems.size() ; i++) {
            existing[i] = Options.currentOrder.dishItems.get(i).nameOfItem + "(" + Options.currentOrder.qty.get(i) + ")";
        }
        DefaultComboBoxModel edcb = new DefaultComboBoxModel(existing);

        JComboBox existingItems = new JComboBox();
        existingItems.setModel(edcb);
        existingPanel.add(existingItems);
        JButton remove = new JButton("Remove Selected");

        remove.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
                int index = existingItems.getSelectedIndex();
                Options.currentOrder.orderPrice -= Options.currentOrder.dishItems.get(index).price*Options.currentOrder.qty.get(index);;

                Options.currentOrder.dishItems.remove(index);
                Options.currentOrder.qty.remove(index);
                model.removeRow(index);
                totalPrice.setText("Total - Rs. " + Options.currentOrder.orderPrice);
                edcb.removeElementAt(index);
            }
        });
        existingPanel.add(remove);
        add(existingPanel);


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
            
                int len = Options.currentOrder.dishItems.size();
                edcb.addElement(Options.currentOrder.dishItems.get(len-1).nameOfItem + "(" + Options.currentOrder.qty.get(len-1) + ")");
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
        JTextField customerName = new JTextField(Options.currentOrder.name);
        customerName.setBounds(150,460,120,20);
        customerName.setText(Options.currentOrder.name);
        add(customerName);
        JLabel custPhone = new JLabel("Phone : ");
        custPhone.setBounds(300,460,50,20);
        add(custPhone);
        JTextField customerPhone = new JTextField();
        System.out.println(Options.currentOrder.phone);
        customerPhone.setText(Options.currentOrder.phone);
        customerPhone.setBounds(350,460,150,20);
        add(customerPhone);
        
        JPanel orderBtnPanel = new JPanel();
        orderBtnPanel.setBounds(250, 480, 100, 50);
        JButton orderBtn = new JButton("Update Order");
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
                    Options.editOrder(String.valueOf(Options.currentOrder.orderID));
                    JOptionPane.showMessageDialog(null, "Order Updated.","Success",JOptionPane.INFORMATION_MESSAGE);
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
