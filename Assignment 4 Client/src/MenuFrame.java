import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuFrame extends JFrame {
    public static String[] dishMenuItems = {"SIZZLER", "SOUP","ROTI","MAINCOURSE","RICE","FRUITSALAD", "DESSERT","BEVERAGE"};

    MenuFrame() {
        setTitle("Menu Card");
        setSize(1600,800);
        Options.getMenu(0);
        
         // HEADING
         JLabel heading = new JLabel();
         heading.setText("Menu");
         heading.setFont(heading.getFont().deriveFont(40.0f));
         heading.setBounds(700, 10, 200,30);
         add(heading);

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




         // -------------------------------------------------SIZZLER---------------------------------------
        // ALL THE TABLES CONTAINING MENU
        JLabel sizzHeading = new JLabel("Sizzler");
        sizzHeading.setFont(sizzHeading.getFont().deriveFont(20.0f));
        sizzHeading.setBounds(200, 50, 100, 30);
        add(sizzHeading);
         String[] sizzCol = {"ID", "Dish Name", "Price"};
         String[][] sizzData = new String[Options.restraurentMenu.Dishes.get(dishMenuItems[0]).size()][3];

         for(int i = 0 ; i < Options.restraurentMenu.Dishes.get(dishMenuItems[0]).size(); i++ ) {
             sizzData[i][0] = String.valueOf(Options.restraurentMenu.Dishes.get(dishMenuItems[0]).get(i).dishID);
             sizzData[i][1] = String.valueOf(Options.restraurentMenu.Dishes.get(dishMenuItems[0]).get(i).nameOfItem);
             sizzData[i][2] = String.valueOf("Rs " + Options.restraurentMenu.Dishes.get(dishMenuItems[0]).get(i).price);
         }

        JTable sizzTable = new JTable(sizzData,sizzCol);
        sizzTable.setEnabled(false);
        sizzTable.setFont(sizzTable.getFont().deriveFont(12.0f));
        JScrollPane sizzScroll = new JScrollPane(sizzTable);
        sizzScroll.setBounds(20, 80, 360, 250);
        add(sizzScroll);
         // -------------------------------------------------SIZZLER---------------------------------------

          // -------------------------------------------------SOUP---------------------------------------
        // ALL THE TABLES CONTAINING MENU
        JLabel soupHeading = new JLabel("Soup");
        soupHeading.setFont(soupHeading.getFont().deriveFont(20.0f));
        soupHeading.setBounds(560, 50, 100, 30);
        add(soupHeading);
         String[] soupCol = {"ID", "Dish Name", "Price"};
         String[][] soupData = new String[Options.restraurentMenu.Dishes.get(dishMenuItems[1]).size()][3];

         for(int i = 0 ; i < Options.restraurentMenu.Dishes.get(dishMenuItems[1]).size(); i++ ) {
             soupData[i][0] = String.valueOf(Options.restraurentMenu.Dishes.get(dishMenuItems[1]).get(i).dishID);
             soupData[i][1] = String.valueOf(Options.restraurentMenu.Dishes.get(dishMenuItems[1]).get(i).nameOfItem);
             soupData[i][2] = String.valueOf("Rs " + Options.restraurentMenu.Dishes.get(dishMenuItems[1]).get(i).price);
         }

        JTable soupTable = new JTable(soupData,soupCol);
        soupTable.setEnabled(false);
        soupTable.setFont(soupTable.getFont().deriveFont(12.0f));
        JScrollPane soupScroll = new JScrollPane(soupTable);
        soupScroll.setBounds(400, 80, 360, 250);
        add(soupScroll);
         // -------------------------------------------------SOUP---------------------------------------


         // -------------------------------------------------ROTI---------------------------------------
        // ALL THE TABLES CONTAINING MENU
        JLabel rotiHeading = new JLabel("Roti");
        rotiHeading.setFont(rotiHeading.getFont().deriveFont(20.0f));
        rotiHeading.setBounds(920, 50, 100, 30);
        add(rotiHeading);
         String[] rotiCol = {"ID", "Dish Name", "Price"};
         String[][] rotiData = new String[Options.restraurentMenu.Dishes.get(dishMenuItems[2]).size()][3];

         for(int i = 0 ; i < Options.restraurentMenu.Dishes.get(dishMenuItems[2]).size(); i++ ) {
             rotiData[i][0] = String.valueOf(Options.restraurentMenu.Dishes.get(dishMenuItems[2]).get(i).dishID);
             rotiData[i][1] = String.valueOf(Options.restraurentMenu.Dishes.get(dishMenuItems[2]).get(i).nameOfItem);
             rotiData[i][2] = String.valueOf("Rs " + Options.restraurentMenu.Dishes.get(dishMenuItems[2]).get(i).price);
         }

        JTable rotiTable = new JTable(rotiData,rotiCol);
        rotiTable.setEnabled(false);
        rotiTable.setFont(rotiTable.getFont().deriveFont(12.0f));
        JScrollPane rotiScroll = new JScrollPane(rotiTable);
        rotiScroll.setBounds(780, 80, 360, 250);
        add(rotiScroll);
         // -------------------------------------------------ROTI---------------------------------------


         // -------------------------------------------------MAIN COURSE---------------------------------------
        // ALL THE TABLES CONTAINING MENU
        JLabel mainCourseHeading = new JLabel("Main Course");
        mainCourseHeading.setFont(mainCourseHeading.getFont().deriveFont(20.0f));
        mainCourseHeading.setBounds(1280, 50, 200, 30);
        add(mainCourseHeading);
         String[] mainCourseCol = {"ID", "Dish Name", "Price"};
         String[][] mainCourseData = new String[Options.restraurentMenu.Dishes.get(dishMenuItems[3]).size()][3];

         for(int i = 0 ; i < Options.restraurentMenu.Dishes.get(dishMenuItems[3]).size(); i++ ) {
            mainCourseData[i][0] = String.valueOf(Options.restraurentMenu.Dishes.get(dishMenuItems[3]).get(i).dishID);
            mainCourseData[i][1] = String.valueOf(Options.restraurentMenu.Dishes.get(dishMenuItems[3]).get(i).nameOfItem);
            mainCourseData[i][2] = String.valueOf("Rs " + Options.restraurentMenu.Dishes.get(dishMenuItems[3]).get(i).price);
         }

        JTable mainCourseTable = new JTable(mainCourseData,mainCourseCol);
        mainCourseTable.setEnabled(false);
        mainCourseTable.setFont(mainCourseTable.getFont().deriveFont(12.0f));
        JScrollPane mainCourseScroll = new JScrollPane(mainCourseTable);
        mainCourseScroll.setBounds(1160, 80, 360, 250);
        add(mainCourseScroll);
         // -------------------------------------------------MAIN COURSE---------------------------------------



           // -------------------------------------------------RICE---------------------------------------
        // ALL THE TABLES CONTAINING MENU
        JLabel riceHeading = new JLabel("Rice");
        riceHeading.setFont(sizzHeading.getFont().deriveFont(20.0f));
        riceHeading.setBounds(200, 330, 100, 30);
        add(riceHeading);
         String[] riceCol = {"ID", "Dish Name", "Price"};
         String[][] riceData = new String[Options.restraurentMenu.Dishes.get(dishMenuItems[4]).size()][3];

         for(int i = 0 ; i < Options.restraurentMenu.Dishes.get(dishMenuItems[4]).size(); i++ ) {
            riceData[i][0] = String.valueOf(Options.restraurentMenu.Dishes.get(dishMenuItems[4]).get(i).dishID);
            riceData[i][1] = String.valueOf(Options.restraurentMenu.Dishes.get(dishMenuItems[4]).get(i).nameOfItem);
            riceData[i][2] = String.valueOf("Rs " + Options.restraurentMenu.Dishes.get(dishMenuItems[4]).get(i).price);
         }

        JTable riceTable = new JTable(riceData,riceCol);
        riceTable.setEnabled(false);
        riceTable.setFont(riceTable.getFont().deriveFont(12.0f));
        JScrollPane riceScroll = new JScrollPane(riceTable);
        riceScroll.setBounds(20, 360, 360, 250);
        add(riceScroll);
         // -------------------------------------------------RICE---------------------------------------

          // -------------------------------------------------FRUIT SALAD---------------------------------------
        // ALL THE TABLES CONTAINING MENU
        JLabel fsHeading = new JLabel("Soup");
        fsHeading.setFont(fsHeading.getFont().deriveFont(20.0f));
        fsHeading.setBounds(560, 330, 100, 30);
        add(fsHeading);
         String[] fsCol = {"ID", "Dish Name", "Price"};
         String[][] fsData = new String[Options.restraurentMenu.Dishes.get(dishMenuItems[5]).size()][3];

         for(int i = 0 ; i < Options.restraurentMenu.Dishes.get(dishMenuItems[5]).size(); i++ ) {
             fsData[i][0] = String.valueOf(Options.restraurentMenu.Dishes.get(dishMenuItems[5]).get(i).dishID);
             fsData[i][1] = String.valueOf(Options.restraurentMenu.Dishes.get(dishMenuItems[5]).get(i).nameOfItem);
             fsData[i][2] = String.valueOf("Rs " + Options.restraurentMenu.Dishes.get(dishMenuItems[5]).get(i).price);
         }

        JTable fsTable = new JTable(fsData,fsCol);
        fsTable.setEnabled(false);
        fsTable.setFont(fsTable.getFont().deriveFont(12.0f));
        JScrollPane fsScroll = new JScrollPane(fsTable);
        fsScroll.setBounds(400, 360, 360, 250);
        add(fsScroll);
         // -------------------------------------------------FRUIT SALAD---------------------------------------


         // -------------------------------------------------DESSERT---------------------------------------
        // ALL THE TABLES CONTAINING MENU
        JLabel dessertHeading = new JLabel("Dessert");
        dessertHeading.setFont(dessertHeading.getFont().deriveFont(20.0f));
        dessertHeading.setBounds(920, 330, 100, 30);
        add(dessertHeading);
         String[] dessertCol = {"ID", "Dish Name", "Price"};
         String[][] dessertData = new String[Options.restraurentMenu.Dishes.get(dishMenuItems[6]).size()][3];

         for(int i = 0 ; i < Options.restraurentMenu.Dishes.get(dishMenuItems[6]).size(); i++ ) {
            dessertData[i][0] = String.valueOf(Options.restraurentMenu.Dishes.get(dishMenuItems[6]).get(i).dishID);
            dessertData[i][1] = String.valueOf(Options.restraurentMenu.Dishes.get(dishMenuItems[6]).get(i).nameOfItem);
            dessertData[i][2] = String.valueOf("Rs " + Options.restraurentMenu.Dishes.get(dishMenuItems[6]).get(i).price);
         }

        JTable dessertTable = new JTable(dessertData,dessertCol);
        dessertTable.setEnabled(false);
        dessertTable.setFont(dessertTable.getFont().deriveFont(12.0f));
        JScrollPane dessertScroll = new JScrollPane(dessertTable);
        dessertScroll.setBounds(780, 360, 360, 250);
        add(dessertScroll);
         // -------------------------------------------------ROTI---------------------------------------


         // -------------------------------------------------Beverage---------------------------------------
        // ALL THE TABLES CONTAINING MENU
        JLabel beverageHeading = new JLabel("Beverage");
        beverageHeading.setFont(beverageHeading.getFont().deriveFont(20.0f));
        beverageHeading.setBounds(1280, 330, 100, 30);
        add(beverageHeading);
         String[] beverageCol = {"ID", "Dish Name", "Price"};
         String[][] beverageData = new String[Options.restraurentMenu.Dishes.get(dishMenuItems[7]).size()][3];

         for(int i = 0 ; i < Options.restraurentMenu.Dishes.get(dishMenuItems[7]).size(); i++ ) {
            beverageData[i][0] = String.valueOf(Options.restraurentMenu.Dishes.get(dishMenuItems[7]).get(i).dishID);
            beverageData[i][1] = String.valueOf(Options.restraurentMenu.Dishes.get(dishMenuItems[7]).get(i).nameOfItem);
            beverageData[i][2] = String.valueOf("Rs " + Options.restraurentMenu.Dishes.get(dishMenuItems[7]).get(i).price);
         }

        JTable beverageTable = new JTable(beverageData,beverageCol);
        beverageTable.setEnabled(false);
        beverageTable.setFont(beverageTable.getFont().deriveFont(12.0f));
        JScrollPane beverageScroll = new JScrollPane(beverageTable);
        beverageScroll.setBounds(1160, 360, 360, 250);
        add(beverageScroll);
         // -------------------------------------------------Beverage---------------------------------------



        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        


    }
    public static void logoutMenuFrame() {
        System.out.println("Log out btn clicked...");
        new LoginFrame();
    }
}
