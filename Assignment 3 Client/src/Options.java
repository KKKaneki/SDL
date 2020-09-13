import java.util.*;
import java.util.concurrent.ExecutorService;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;

class RegisteredUser implements Serializable {
    Properties users;
}

class RestraurentMenu implements Serializable {
    public Hashtable<String,Vector<Dishes>> Dishes = new Hashtable<String,Vector<Dishes>>();
}

class OrdersHistory implements Serializable {
    public Vector<Order> orders = new Vector<Order>();
}


public class Options {
    
    public static Code code = new Code();
    public static Socket socket;
    public static String[] dishItems = {"SIZZLER", "SOUP","ROTI","MAINCOURSE","RICE","FRUITSALAD", "DESSERT","BEVERAGE"};
    public static RestraurentMenu restraurentMenu;
    public static Order currentOrder;
    public static OrdersHistory orders;
    public static Integer ID;


    public void getAdminMenu(){

        try {
            
            getMenu(0);
            orderHistory(0);
            System.out.println(restraurentMenu.Dishes.get("ROTI").size());

            while(true){
                final Scanner scan = new Scanner(System.in);

                System.out.println("MENU");
                System.out.println(" 1. VIEW USERS");
                System.out.println(" 2. VIEW MENU");
                System.out.println(" 3. ADD NEW DISH");
                System.out.println(" 4. ADD ORDER");
                System.out.println(" 5. ORDERS HISTORY");
                System.out.println(" 6. VIEW ORDER");
                System.out.println(" 7. Logout");
                System.out.println(" 8. Chat With Server\n");
                System.out.print(" Enter your choice : ");
                final Integer choice = Integer.parseInt(scan.nextLine());
               //PERFORM A PARTICULAR ACTION
                switch(choice) {
                    case 1: showAllUsers();
                            break;
                    case 2: getMenu(1);
                            break;
                    case 3: addNewDish();
                            break;
                    case 4: makeCurrentOrder();
                            break;
                    case 5: orderHistory(1);
                            break;
                    case 6: getOrder();
                            break;
                    case 7: Login.logout();
                            break;
                    case 8: Chat.chatWithServer();
                            break;
                    default: System.out.println("ENTER A VALID INPUT\n");
                            break;
                }
            }
        
        } catch(final Exception e){
            e.printStackTrace();
        }
    }

    public void getUserMenu(){
        try {
            final Scanner scan = new Scanner(System.in);    
            getMenu(0);
            orderHistory(0);
            
            while(true){
                System.out.println("MENU                             ");
                System.out.println(" 1. MENU");
                System.out.println(" 2. ADD ORDER");
                System.out.println(" 3. ORDERS HISTORY");
                System.out.println(" 4. VIEW ORDER");
                System.out.println(" 5. Logout");
                System.out.println(" 6. Chat With Server\n");
                System.out.println(" Enter your choice : ");
                final Integer choice = Integer.parseInt(scan.nextLine().toString());
                
            //        PERFORM A PARTICULAR ACTION
                switch(choice) {
                    case 1: getMenu(1);
                            break;
                    case 2: makeCurrentOrder();
                            break;
                    case 3: orderHistory(1);
                            break;
                    case 4: getOrder();
                            break;
                    case 5: Login.logout();
                            break;
                    case 6: Chat.chatWithServer();
                            break;
                    default: System.out.println("ENTER A VALID INPUT\n");
                            break;
                }
            }
    
        } catch(final Exception e){
            e.printStackTrace();
        }
       
    }

    public static void showAllUsers(){
        final Scanner scan = new Scanner(System.in);

        try {
            socket = new Socket("localhost",8080);

            final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            final ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            dataOutputStream.writeInt(code.getRegisteredUsers);

            final RegisteredUser registeredUsers = (RegisteredUser) objectInputStream.readObject();
            System.out.println(registeredUsers.users.size());
            final Enumeration<?> user = registeredUsers.users.propertyNames();
            System.out.println("\nUsers");
            Integer i = 1;
            while(user.hasMoreElements()){
                System.out.println(i++ + ". " + user.nextElement());
            }
            System.out.println("\n");
            socket.close();
        } catch(final Exception e){
            e.printStackTrace();
        }
    }

    public static void getMenu(final Integer x){
        final Scanner scan = new Scanner(System.in);

        try{
            socket = new Socket("localhost",8080);
            final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeInt(code.getMenu);

            final ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            restraurentMenu = (RestraurentMenu) objectInputStream.readObject();

            if(x == 1){
                // GET THE MENU OF THE ITEMS
                System.out.println("#######################################################################\n");
                System.out.println("                               MENU\n");
                // PRINT ALL THE SUB MENU ITEMS
                for(int i = 0 ; i < dishItems.length ; i++){
                    printTheSingleItemTable(dishItems[i]);
                    System.out.println("\n\n");
                }
                System.out.println("#######################################################################\n");
            }
            socket.close();
        } catch(final Exception e){
            e.printStackTrace();
        }      
    }

    // PRINTING THE TABLE FOR SPECIFIC ITEMS FROM THE MENU ITEM
    private static void printTheSingleItemTable(final String itemName){
    
        final Enumeration<?> allItems = restraurentMenu.Dishes.get(itemName).elements();
        System.out.println("******************************************************************\n");
        System.out.println("                          " + itemName  + "                       \n");
        System.out.println("******************************************************************\n");
        System.out.println("ID \tNAME \t\t\t\t\t\tPRICE\n");
            if(restraurentMenu.Dishes.get(itemName).size() == 0){
            System.out.println("No items were found in " + itemName + ".\n");
            }
            final int i = 1;
            while(allItems.hasMoreElements()){
                final Dishes dishes = (Dishes) allItems.nextElement();
            System.out.println(String.format("%s %-45s- %s",dishes.dishID,dishes.nameOfItem, "Rs. " + dishes.price + "\n"));
            }
        System.out.println("******************************************************************\n");
    }

    //    ADDING A NEW DISH TO THE DATABASE;
    private static void addNewDish() {
        final Scanner scan = new Scanner(System.in);
        Boolean flag = true;
        // MENU OR ADDING DISH ITEMS 
        try {
        while(flag){
            socket = new Socket("localhost",8080);

                System.out.println("                         ADD ITEM                              \n");
                System.out.println(" 1. SIZZLERS");
                System.out.println(" 2. SOUPS");
                System.out.println(" 3. ROTI");
                System.out.println(" 4. MAIN COURSE");
                System.out.println(" 5. RICE");
                System.out.println(" 6. FRUIT SALAD");
                System.out.println(" 7. DESSERTS");
                System.out.println(" 8. BEVERAGES");
                System.out.println(" 9. Exit");
                System.out.print(" Enter your choice : ");
                // INTEGER VALUE STORING THE CHOICE SELECTED BY THE USER
                 Integer choice = Integer.parseInt(scan.nextLine());
                 DataOutputStream addItemChoice = new DataOutputStream(socket.getOutputStream());

    
                if(choice == 1) {
                    addItemChoice.writeInt(code.addSizzler);
                } else if(choice == 2) {
                    addItemChoice.writeInt(code.addSoup);
                } else if(choice == 3) {
                    addItemChoice.writeInt(code.addRoti);
                } else if(choice == 4) {
                    addItemChoice.writeInt(code.addMainCourse);
                } else if(choice == 5) {
                    addItemChoice.writeInt(code.addRice);
                } else if(choice == 6) {
                    addItemChoice.writeInt(code.addFruitSalad);
                } else if(choice == 7) {
                    addItemChoice.writeInt(code.addDessert);
                } else if(choice == 8) {
                    addItemChoice.writeInt(code.addBeverage);
                }  else {
                    addItemChoice.writeInt(100);
                }
			     ObjectOutputStream newDish = new ObjectOutputStream(socket.getOutputStream());
                switch(choice) {
                    // ADDING A SIZZLER TO THE MENU
                    case 1: final Sizzlers sizzler = new Sizzlers();
                            sizzler.addSizzlers();
                            System.out.println(sizzler.nameOfItem);
                            newDish.writeObject((Dishes)sizzler);

                            getMenu(0);
                            printTheSingleItemTable(dishItems[0]);
                            System.out.println("ITEM HAS BEEN ADDED.\n");
                            break;
                    // ADDING ANOTHER SOUP TO THE MENU
                    case 2: final Soup soup = new Soup();

                            soup.addSoup();
                            newDish.writeObject(soup);
                            getMenu(0);
                            printTheSingleItemTable(dishItems[1]);
                            System.out.println("ITEM HAS BEEN ADDED.\n");
                            break;
                    // ADDING ROTI TO THE MENU
                    case 3: final Roti roti = new Roti();
                            roti.addRoti();
                            newDish.writeObject(roti);
                            getMenu(0);
                            printTheSingleItemTable(dishItems[2]); 
                            System.out.println("ITEM HAS BEEN ADDED.\n");         
                            break;
                    // ADDING THE MAIN COURSE
                    case 4: final MainCourse mainCourse = new MainCourse();
                            mainCourse.addMainCourse();
                            newDish.writeObject(mainCourse);
                            getMenu(0);
                            printTheSingleItemTable(dishItems[3]);
                            System.out.println("ITEM HAS BEEN ADDED.\n");
                            break;
                    // ADDING THE RICE TO THE MENU 
                    case 5: final Rice rice = new Rice();
                            rice.addRice();
                            newDish.writeObject(rice);
                            getMenu(0);
                            printTheSingleItemTable(dishItems[4]);
                            System.out.println("ITEM HAS BEEN ADDED.\n");
                            break;
                    //  ADDING THE FRUIT SALAD TO THE MENU
                    case 6: final FruitSalad fruitSalad = new FruitSalad();
                            fruitSalad.addFruitSalad();
                            newDish.writeObject(fruitSalad);
                            getMenu(0);
                            printTheSingleItemTable(dishItems[5]);
                            System.out.println("ITEM HAS BEEN ADDED.\n");
                            break;
                    //  ADDING THE DESSERT TO THE MENU
                    case 7: final Dessert dessert = new Dessert();
                            dessert.addDessert();
                            newDish.writeObject(dessert);
                            getMenu(0);
                            printTheSingleItemTable(dishItems[6]);
                            System.out.println("ITEM HAS BEEN ADDED.\n");
                            break;
                    //  ADDING BEVERAGES TO THE MENU
                    case 8: final Beverage beverage = new Beverage();
                            beverage.addBeverage();
                            newDish.writeObject(beverage);
                            getMenu(0);
                            printTheSingleItemTable(dishItems[7]);
                            System.out.println("ITEM HAS BEEN ADDED.\n");
                            break;
                    // EXITING THE ADD DISH MENU
                    case 9: System.out.println("Exiting the add dish menu...\n");
                            flag = false;
                            break;
                    // WRONG CHOICE SELECTED MESSAGE
                    default: System.out.println("Enter a valid choice\n");
                            break;
                }
                socket.close();
            } 
        }
        catch (final Exception e){
            e.printStackTrace();
        }
          
    	
    }

    // MAKE A CURRENT ORDER
    private static void makeCurrentOrder(){
        final Scanner scan = new Scanner(System.in);
        Boolean flag = true;
        int choice;
        currentOrder = new Order();
        do {
            System.out.println(" MAKE ORDER");
            System.out.println(" 1. Add Item");
            System.out.println(" 2. Remove Item");
            System.out.println(" 3. See the Order");
            System.out.println(" 4. Place Order");
            System.out.println(" 5. Exit");
            System.out.print("Enter your choice : ");
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
                default: System.out.println("ENTER A VALID OPTION\n");
                        break;
            }
        }while(flag);
    }

    // SEE THE CURRENT ORDER
    public static void seeCurrentOrder(){
        final Scanner scan = new Scanner(System.in);
        System.out.println("");
        System.out.println(" Order ID: "+ currentOrder.orderID  +"                 \n");
        if(currentOrder.dishItems.size() == 0){
            System.out.println("NO ITEMS AVAILABLE IN THE ORDER\n");
        }
        for(int i=0;i<currentOrder.dishItems.size();i++){
            final Dishes item = currentOrder.dishItems.get(i);
            final Integer qty = currentOrder.qty.get(i);
            System.out.println(String.format("%s %-45s %d x Rs %f\n",item.dishID, item.nameOfItem,qty,item.price));
        }
        System.out.println(String.format("%-53s = Rs %f\n", "Total Amount",currentOrder.orderPrice));
    }

    // ADD A ITEM TO THE CURRENT ORDER
    public static void addOrderItem(){
        final Scanner scan = new Scanner(System.in);

        currentOrder.orderID = (int)Math.ceil(Math.random()*1000000);
        String ID;
        int flag = 0;
        System.out.println("ENTER THE ID OF THE ITEM : ");
        ID = scan.nextLine();
        for(int i=0;i<dishItems.length;i++){
            final Vector<Dishes> item = restraurentMenu.Dishes.get(dishItems[i]);
            for(int j=0;j<item.size();j++){
                if(item.elementAt(j).dishID.equals(ID)){

                    System.out.println("ENTER THE QUANTITY : ");
                    final Integer x = Integer.parseInt(scan.nextLine().toString());
                    currentOrder.qty.add(x);

                    currentOrder.dishItems.add(item.elementAt(j));
                    currentOrder.orderPrice += x*item.elementAt(j).price;
                    flag = 1;
                    break;
                }
            }
            if(flag == 1) break;
        }

        if(flag == 1) System.out.println("ITEM HAS BEEN ADDED TO THE MENU\n");
        else{
            System.out.println("ITEM NOT FOUND. CHOOSE AGAIN\n");
        }
    }

    // REMOVE A ITEM FROM THE CURRENT ORDER
    public static void removeOrderItem(){
        final Scanner scan = new Scanner(System.in);

        String ID;
        System.out.println("ENTER THE ID OF ITEM TO REMOVE :");
        ID = scan.nextLine();
        int flag = 0;

        for(int i=0;i<currentOrder.dishItems.size();i++){
            if(currentOrder.dishItems.get(i).dishID.equals(ID)){
                currentOrder.orderPrice -= currentOrder.qty.get(i)*currentOrder.dishItems.get(i).price;
                currentOrder.dishItems.remove(i);
                currentOrder.qty.remove(i);
                flag = 1;
                break;
            }
        }
        if(flag == 1){
            System.out.println("ITEM HAS BEEN REMOVED\n");
        } else {
            System.out.println("ITEM NOT FOUND\n");
        }
    }

    // PLACE THE CURRENT ORDER
    public static void placeTheOrder(){
        final Scanner scan = new Scanner(System.in);

        if(currentOrder.dishItems.size() == 0){
            System.out.println("Please enter at least one order item...");
        } else {
            try {
                System.out.print("ENTER YOUR NAME :");
                final String name = scan.nextLine();
                System.out.print("ENTER YOUR PHONE NUMBER : ");
                final String phone = scan.nextLine();
                currentOrder.phone = phone;
                currentOrder.name = name;
                System.out.println("ARE YOU SURE YOU WANT TO PLACE THE ORDER? (Y/N) ");
                final String ch = scan.nextLine();
                if(ch.equals("Y")){
                    socket = new Socket("localhost",8080);
                    // SEND THIS TO SERVER
                    final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeInt(code.placeOrder);
                    final ObjectOutputStream placeTheOrdeStream = new ObjectOutputStream(socket.getOutputStream());
                    placeTheOrdeStream.writeObject(currentOrder);
                    
                    // orders.add(currentOrder);
                    System.out.println("TOTAL AMOUNT OF YOUR ORDER IS " + currentOrder.orderPrice  + "\n");
                    System.out.println("YOUR ORDER ID IS " + currentOrder.orderID + "\n");
                    System.out.println("ORDER HAS BEEN PLACED.\n");
                    currentOrder = new Order();
                   
                } else {
                    System.out.println("MAKE THE CHANGES YOU WANT...\n");
                }
            } catch(final Exception e){
                e.printStackTrace();
            }
        }
       

    }


    public static void orderHistory(final Integer x){
        try {
            socket = new Socket("localhost",8080);
            final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeInt(code.orderHistory);

            final ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            
            orders = (OrdersHistory) objectInputStream.readObject();
            if(x == 1){
                printOrderHistory();
            }

        } catch(final Exception e){
            e.printStackTrace();
        }

    }

      // SEE ALL THE ORDERS IN THE RECENT TO OLD ORDER
    private static void printOrderHistory(){
        System.out.println("#######################################################################\n");
        System.out.println("                           ORDER HISTORY\n");
        for(int i=orders.orders.size()-1;i>=0;i--){
            System.out.println("ORDER ID: " + orders.orders.get(i).orderID + " (" + orders.orders.get(i).name + ")");
            final ArrayList<Dishes> items = orders.orders.get(i).dishItems;
            final ArrayList<Integer> qty = orders.orders.get(i).qty;
            for(int j=0;j<items.size();j++){
                System.out.println(String.format("%-45s %d - Rs %f\n", items.get(j).nameOfItem,qty.get(j),items.get(j).price));
            }
            System.out.println(String.format("%-47s = Rs %f\n\n", "Total Amount",orders.orders.get(i).orderPrice));
        }
        System.out.println("#######################################################################\n");
    }

     // GET THE PARTICULAR ORDER 
     private static void getOrder(){
         try {
            final Scanner scan = new Scanner(System.in);
            orderHistory(0);
            socket = new Socket("localhost",8080);

            final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeInt(code.editOrder);

            System.out.println("ENTER THE ORDER ID :");
            int flag = 0;
            ID = Integer.parseInt(scan.nextLine().toString());
            for(int i=0;i<orders.orders.size();i++){
                if(orders.orders.elementAt(i).orderID.equals(ID)){
                    currentOrder = orders.orders.elementAt(i);
                    seeCurrentOrder();
                    flag = 1;
                    break;
                }
            }
            System.out.println(currentOrder.orderID);
            if(flag == 1){
                flag = 1;
                int choice;
                do{
                    System.out.println("         EDIT ORDER\n");
                    System.out.println(" 1. ADD ITEM");
                    System.out.println(" 2. REMOVE ITEM");
                    System.out.println(" 3. SEE THE ORDER");
                    System.out.println(" 4. SAVE ORDER");
                    System.out.println(" 5. Exit");
                    System.out.print("Enter your choice : ");
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
                        default: System.out.println("ENTER A VALID OPTION\n");
                                break;
                    }
                }while(flag == 1);
            } else {
                System.out.println("NO ORDER WITH THIS ID IS FOUND.\n");
            }
         } catch(final Exception e){
            e.printStackTrace();
         }
    }

    // SAVE THIS ORDER AND DELETE THE OTHER
    public static void saveTheOrder(){
        try {

            final ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream());
            currentOrder.orderID = ID;
            System.out.println(currentOrder.orderID);
            ous.writeObject(currentOrder);
            currentOrder = new Order();

            System.out.println("Order has been updated");
        } catch(final Exception e){
            e.printStackTrace();
        }
    }


   


}