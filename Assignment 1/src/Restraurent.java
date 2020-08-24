import java.util.*;



public class Restraurent {
    
    // Contains the usernames and password
    private static Properties users;
    private static Scanner scan;
    private static String USERNAME;
    private static String PASSWORD;
    private static boolean isLoggedIn;
    private static boolean notFirstTimeFailed;
    private static Hashtable<String,Vector<Dishes>> Dishes = new Hashtable<String,Vector<Dishes>>();
    // CHECK IF THE USER LOGGED IN IS ADMIN OR NOT ( 0 - Admin & 1 - Staff)
    private static BitSet whoIsLogged = new BitSet(2);
    // CURRENT ORDER
    private static Order currentOrder;
    //  LIST OF ALL THE ORDERS
    private static Vector<Order> orders = new Vector<Order>();

    public static String[] dishItems = {"SIZZLER", "SOUP","ROTI","MAINCOURSE","RICE","FRUITSALAD", "DESSERT","BEVERAGE"};
   

    public static void main(String[] args) {
        users = new Properties();
        // ADD A ADMIN USER TO THE USERS PROPERTY
        users.setProperty("admin", "password");
        users.setProperty("user", "password");

        notFirstTimeFailed = false;
        isLoggedIn = false;
        


        scan = new Scanner(System.in);


        // ASSIGNING THE DEFAULT VALUES TO THE DISHES
        for(int i=0;i<dishItems.length;i++){
            Dishes.put(dishItems[i], new Vector<Dishes>());
        }


        LoginMenu();

        // TESTING PURPOSES ONLY
        // isAdminOperations();
    }   

    public static void LoginMenu(){
        // RUNS TILL THE USER IS NOT LOGGED IN
        while(!isLoggedIn){
            if(notFirstTimeFailed){
                print("INCORRECT USERNAME/PASSWORD TRY AGAIN...\n");
                printLine();
            }
            print("WE NEED TO CONFIRM THAT YOU ARE A PART OF RESTAURENT\n");
            print("                         LOGIN                      \n");
            // ASK THE USER FOR HIS USERNAME
            print("Enter USERNAME: ");
            USERNAME = scan.nextLine();
            // ASK THE USER FOR HIS PASSWORD
            print("Enter PASSWORD: ");
            PASSWORD = scan.nextLine();
            printLine();

            // WHEN THE USERNAME AND THE PASSWORD EXISTS
            if(users.getProperty(USERNAME) != null && users.getProperty(USERNAME).equals(PASSWORD)){
                isLoggedIn = true;
                if (USERNAME.equals("admin")){
                    whoIsLogged.set(0);
                } else {
                    whoIsLogged.set(1);
                }
                // USER IS LOGGED IN
                print("CONGRATULATIONS YOUR ARE LOGGED IN AS " + USERNAME + "\n");
                printLine();
            }
            // GIVE THE INCORRECT MESSAGE
            notFirstTimeFailed = true;
            
        }

        while(true){
            if(whoIsLogged.get(0)){
                isAdminOperations();          
            } else {
                isNotAdminOperations();
            }
        }
    }

    public static void isNotAdminOperations(){
        while(true){
            printLine();
            print("                         MENU                              \n");
            print("-----------------------------------------------------------\n");
            print(" 1. MENU\n");
            print(" 2. ADD ORDER\n");
            print(" 3. ORDERS HISTORY\n");
            print(" 4. VIEW ORDER\n");
            print(" 5. Logout\n");
            printLine();
            print(" Enter your choice : ");
            Integer choice = Integer.parseInt(scan.nextLine().toString());

        //        PERFORM A PARTICULAR ACTION
            switch(choice) {
                case 1: getMenu();
                        break;
                case 2: makeCurrentOrder();
                        break;
                case 3: orderHistory();
                        break;
                case 4: getOrder();
                        break;
                case 5: logout();
                        break;
                default: print("ENTER A VALID INPUT\n");
                        break;
            }
        }
    }

//   ADMIN MENU TO RESTRICT OTHER USERS FRON ACCESSING THE INFORMATION
    public static void isAdminOperations(){
        while(true){
            printLine();
            print("                         MENU                              \n");
            printLine();
            print(" 1. VIEW USERS\n");
            print(" 2. VIEW MENU\n");
            print(" 3. ADD NEW DISH\n");
            print(" 4. ADD ORDER\n");
            print(" 5. ORDERS HISTORY\n");
            print(" 6. VIEW ORDER\n");
            print(" 7. Logout\n");
            printLine();
            print(" Enter your choice : ");
            Integer choice = Integer.parseInt(scan.nextLine().toString());
        
    //        PERFORM A PARTICULAR ACTION
            switch(choice) {
                case 1: showAllUsers();
                        break;
                case 2: getMenu();
                        break;
                case 3: addNewDish();
                        break;
                case 4: makeCurrentOrder();
                        break;
                case 5: orderHistory();
                        break;
                case 6: getOrder();
                        break;
                case 7: logout();
                        break;
                default: print("ENTER A VALID INPUT\n");
                        break;
            }
        }
    }

    // LOGOUT THE CURRENT USER
    public static void logout(){
        notFirstTimeFailed = false;
        isLoggedIn = false;
        whoIsLogged.clear();
        LoginMenu();
    }

    // SEE ALL THE ORDERS IN THE RECENT TO OLD ORDER
    private static void orderHistory(){
        print("#######################################################################\n");
        print("                           ORDER HISTORY\n");
        for(int i=orders.size()-1;i>=0;i--){
            printLine();
            print("ORDER ID: " + orders.get(i).orderID + "\n");
            ArrayList<Dishes> items = orders.get(i).dishItems;
            ArrayList<Integer> qty = orders.get(i).qty;
            for(int j=0;j<items.size();j++){
                print(String.format("%-45s %d - Rs %f\n", items.get(j).nameOfItem,qty.get(j),items.get(j).price));
            }
            printLine();
            print(String.format("%-47s = Rs %f\n", "Total Amount",orders.get(i).orderPrice));
            printLine();
        }
        print("#######################################################################\n");

    }

    // GET THE PARTICULAR ORDER 
    private static void getOrder(){
        print("ENTER THE ORDER ID :");
        int flag = 0;
        Integer ID = Integer.parseInt(scan.nextLine().toString());
        for(int i=0;i<orders.size();i++){
            if(orders.elementAt(i).orderID.equals(ID)){
                currentOrder = orders.elementAt(i);
                seeCurrentOrder();
                flag = 1;
                break;
            }
        }
        if(flag == 1){
            flag = 1;
            int choice;
            do{
                printLine();
                print("         EDIT ORDER\n");
                printLine();
                print(" 1. ADD ITEM\n");
                print(" 2. REMOVE ITEM\n");
                print(" 3. SEE THE ORDER\n");
                print(" 4. SAVE ORDER\n");
                print(" 5. Exit\n");
                printLine();
                print("Enter your choice : ");
                choice = Integer.parseInt(scan.nextLine().toString());
                // SWTICH THE CHOICES
                switch(choice){
                    case 1: addOrderItem();
                            break;
                    case 2: removeOrderItem();
                            break;
                    case 3: seeCurrentOrder();
                            break;
                    case 4: saveTheOrder();
                            break;
                    case 5: flag = 0;
                            break;
                    default: print("ENTER A VALID OPTION\n");
                            break;
                }
            }while(flag == 1);
        } else {
            print("NO ORDER WITH THIS ID IS FOUND.\n");
        }
    }

    private static void saveTheOrder(){
        for(int i=0;i<orders.size();i++){
            if(orders.elementAt(i).orderID.equals(currentOrder.orderID)){
                orders.removeElementAt(i);
                orders.add(i, currentOrder);
                print("TOTAL AMOUNT OF YOUR ORDER IS " + currentOrder.orderPrice  + "\n");
                print("YOUR ORDER ID IS " + currentOrder.orderID + "\n");
                print("ORDER HAS BEEN PLACED.\n");
                currentOrder = new Order();
                break;
            }
        }
    }


    // SHOWING ALL THE USERS TO THE ADMIN
    private static void showAllUsers(){
//    	GETTING THE PROPERTY KEYS FOR THE USERS
        print("#######################################################################\n");
        print("                               USERS\n");
        printLine();
        Enumeration<?> enumUsers = users.propertyNames();
        Character ch = 'A';
        while(enumUsers.hasMoreElements()){
            print(" " + ch + ". " + enumUsers.nextElement() + "\n");
            ch++;
        }
        printLine();
        print("#######################################################################\n");
    }
    
    // GET THE MENU OF THE ITEMS
    private static void getMenu(){
        print("#######################################################################\n");
        print("                               MENU\n");
        printLine();
        // PRINT ALL THE SUB MENU ITEMS
        for(int i = 0 ; i < dishItems.length ; i++){
            printTheSingleItemTable(dishItems[i]);
            print("\n\n");
        }
        printLine();
        print("#######################################################################\n");
    }

//    ADDING A NEW DISH TO THE DATABASE;
    private static void addNewDish() {
        Boolean flag = true;
        // MENU OR ADDING DISH ITEMS 
        while(flag){
            printLine();
            print("                         ADD ITEM                              \n");
            print(" 1. SIZZLERS\n");
            print(" 2. SOUPS\n");
            print(" 3. ROTI\n");
            print(" 4. MAIN COURSE\n");
            print(" 5. RICE\n");
            print(" 6. FRUIT SALAD\n");
            print(" 7. DESSERTS\n");
            print(" 8. BEVERAGES\n");
            print(" 9. Exit\n");
            printLine();
            print(" Enter your choice : ");
            // INTEGER VALUE STORING THE CHOICE SELECTED BY THE USER
            Integer choice = Integer.parseInt(scan.nextLine().toString());
            switch(choice) {
                // ADDING A SIZZLER TO THE MENU
                case 1: Sizzlers sizzler = new Sizzlers();
                        sizzler.addSizzlers();
                        Dishes.get(dishItems[0]).add(sizzler);
                        printTheSingleItemTable(dishItems[0]);
                        print("ITEM HAS BEEN ADDED.\n");
                        break;
                // ADDING ANOTHER SOUP TO THE MENU
                case 2: Soup soup = new Soup();
                        soup.addSoup();
                        Dishes.get(dishItems[1]).add(soup);
                        printTheSingleItemTable(dishItems[1]);
                        print("ITEM HAS BEEN ADDED.\n");
                        break;
                // ADDING ROTI TO THE MENU
                case 3: Roti roti = new Roti();
                        roti.addRoti();
                        Dishes.get(dishItems[2]).add(roti);
                        printTheSingleItemTable(dishItems[2]); 
                        print("ITEM HAS BEEN ADDED.\n");         
                        break;
                // ADDING THE MAIN COURSE
                case 4: MainCourse mainCourse = new MainCourse();
                        mainCourse.addMainCourse();
                        Dishes.get(dishItems[3]).add(mainCourse);
                        printTheSingleItemTable(dishItems[3]);
                        print("ITEM HAS BEEN ADDED.\n");
                        break;
                // ADDING THE RICE TO THE MENU 
                case 5: Rice rice = new Rice();
                        rice.addRice();
                        Dishes.get(dishItems[4]).add(rice);
                        printTheSingleItemTable(dishItems[4]);
                        print("ITEM HAS BEEN ADDED.\n");
                        break;
                //  ADDING THE FRUIT SALAD TO THE MENU
                case 6: FruitSalad fruitSalad = new FruitSalad();
                        fruitSalad.addFruitSalad();
                        Dishes.get(dishItems[5]).add(fruitSalad);
                        printTheSingleItemTable(dishItems[5]);
                        print("ITEM HAS BEEN ADDED.\n");
                        break;
                //  ADDING THE DESSERT TO THE MENU
                case 7: Dessert dessert = new Dessert();
                        dessert.addDessert();
                        Dishes.get(dishItems[6]).add(dessert);
                        printTheSingleItemTable(dishItems[6]);
                        print("ITEM HAS BEEN ADDED.\n");
                        break;
                //  ADDING BEVERAGES TO THE MENU
                case 8: Beverage beverage = new Beverage();
                        beverage.addBeverage();
                        Dishes.get(dishItems[7]).add(beverage);
                        printTheSingleItemTable(dishItems[7]);
                        print("ITEM HAS BEEN ADDED.\n");
                        break;
                // EXITING THE ADD DISH MENU
                case 9: print("Exiting the add dish menu...\n");
                        flag = false;
                        break;
                // WRONG CHOICE SELECTED MESSAGE
                default: print("Enter a valid choice\n");
                         break;
            }
        }
    	
    }

    // PRINTING THE TABLE FOR SPECIFIC ITEMS FROM THE MENU ITEM
    private static void printTheSingleItemTable(String itemName){
        Enumeration<?> allItems = Dishes.get(itemName).elements();
        print("******************************************************************\n");
        print("                          " + itemName  + "                       \n");
        print("******************************************************************\n");
        print("ID \tNAME \t\t\t\t\t\tPRICE\n");
        if(Dishes.get(itemName).size() == 0){
            print("No items were found in " + itemName + ".\n");
        }
        int i = 1;
        while(allItems.hasMoreElements()){
            Dishes dishes = (Dishes) allItems.nextElement();
            print(String.format("%s %-45s- %s",dishes.dishID,dishes.nameOfItem, "Rs. " + dishes.price + "\n"));
        }
        print("******************************************************************\n");
    }
    
    // MAKE A CURRENT ORDER
    private static void makeCurrentOrder(){
        Boolean flag = true;
        int choice;
        currentOrder = new Order();
        do {
            printLine();
            print("             MAKE ORDER\n");
            printLine();
            print(" 1. Add Item\n");
            print(" 2. Remove Item\n");
            print(" 3. See the Order\n");
            print(" 4. Place Order\n");
            print(" 5. Exit\n");
            printLine();
            print("Enter your choice : ");
            choice = Integer.parseInt(scan.nextLine().toString());
            // SWTICH THE CHOICES
            switch(choice){
                case 1: addOrderItem();
                        break;
                case 2: removeOrderItem();
                        break;
                case 3: seeCurrentOrder();
                        break;
                case 4: placeTheOrder();
                        break;
                case 5: flag = false;
                        break;
                default: print("ENTER A VALID OPTION\n");
                        break;
            }
        }while(flag);
    }

    // SEE THE CURRENT ORDER
    public static void seeCurrentOrder(){
        printLine();
        print("          Order ID: "+ currentOrder.orderID  +"                 \n");
        printLine();
        if(currentOrder.dishItems.size() == 0){
            print("NO ITEMS AVAILABLE IN THE ORDER\n");
        }
        for(int i=0;i<currentOrder.dishItems.size();i++){
            Dishes item = currentOrder.dishItems.get(i);
            Integer qty = currentOrder.qty.get(i);
            print(String.format("%s %-45s %d - Rs %f\n",item.dishID, item.nameOfItem,qty,item.price));
        }
        printLine();
        print(String.format("%-47s = Rs %f\n", "Total Amount",currentOrder.orderPrice));
    }

    // ADD A ITEM TO THE CURRENT ORDER
    public static void addOrderItem(){
        currentOrder.orderID = (int)Math.ceil(Math.random()*1000000);
        String ID;
        int flag = 0;
        print("ENTER THE ID OF THE ITEM : ");
        ID = scan.nextLine();
        for(int i=0;i<dishItems.length;i++){
            Vector<Dishes> item = Dishes.get(dishItems[i]);
            for(int j=0;j<item.size();j++){
                if(item.elementAt(j).dishID.equals(ID)){

                    print("ENTER THE QUANTITY : ");
                    Integer x = Integer.parseInt(scan.nextLine().toString());
                    currentOrder.qty.add(x);

                    currentOrder.dishItems.add(item.elementAt(j));
                    currentOrder.orderPrice += x*item.elementAt(j).price;
                    flag = 1;
                    break;
                }
            }
            if(flag == 1) break;
        }
        print("\n");
        printLine();
        if(flag == 1) print("ITEM HAS BEEN ADDED TO THE MENU\n");
        else{
            print("ITEM NOT FOUND. CHOOSE AGAIN\n");
        }
        printLine();
    }


    // REMOVE A ITEM FROM THE CURRENT ORDER
    public static void removeOrderItem(){
        String ID;
        print("ENTER THE ID OF ITEM TO REMOVE :");
        ID = scan.nextLine();
        int flag = 0;

        for(int i=0;i<currentOrder.dishItems.size();i++){
            if(currentOrder.dishItems.get(i).dishID.equals(ID)){
                currentOrder.orderPrice -= currentOrder.qty.get(i)*currentOrder.dishItems.get(i).price;
                currentOrder.dishItems.remove(i);
                flag = 1;
                break;
            }
        }
        if(flag == 1){
            print("ITEM HAS BEEN REMOVED\n");
        } else {
            print("ITEM NOT FOUND\n");
        }
    }

    // PLACE THE CURRENT ORDER
    public static void placeTheOrder(){
        print("ENTER YOUR NAME :");
        String name = scan.nextLine();
        print("ENTER YOUR PHONE NUMBER : ");
        String phone = scan.nextLine();
        currentOrder.phone = phone;
        currentOrder.name = name;
        print("ARE YOU SURE YOU WANT TO PLACE THE ORDER? (Y/N) ");
        String ch = scan.nextLine();
        if(ch.equals("Y")){
            orders.add(currentOrder);
            print("TOTAL AMOUNT OF YOUR ORDER IS " + currentOrder.orderPrice  + "\n");
            print("YOUR ORDER ID IS " + currentOrder.orderID + "\n");
            print("ORDER HAS BEEN PLACED.\n");
            currentOrder = new Order();
        } else {
            print("MAKE THE CHANGES YOU WANT...\n");
        }
    }


//    PRINTING A COMPLETE LINE
    public static void printLine() {
        print("-----------------------------------------------------------\n");
    }

    // Just a short form to make print statements easy
    public static void print(final Object msg) {
        System.out.print(msg);
    }

}
