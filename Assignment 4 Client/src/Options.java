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



    public static RegisteredUser showAllUsers(){
        final Scanner scan = new Scanner(System.in);
        RegisteredUser registeredUsers = new RegisteredUser();
        try {
            socket = new Socket("localhost",8080);

            final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            final ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            dataOutputStream.writeInt(code.getRegisteredUsers);

            registeredUsers = (RegisteredUser) objectInputStream.readObject();
    
            socket.close();
        } catch(final Exception e){
            e.printStackTrace();
        }
        return registeredUsers;
    }

    public static void getMenu(final Integer x){
        final Scanner scan = new Scanner(System.in);
        try{
            socket = new Socket("localhost",8080);
            final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeInt(code.getMenu);

            final ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            restraurentMenu = (RestraurentMenu) objectInputStream.readObject();

            socket.close();
        } catch(final Exception e){
            e.printStackTrace();
        }      
    }


    //    ADDING A NEW DISH TO THE DATABASE;
    public static void addNewDish(int choice,String d,Float p) {
        final Scanner scan = new Scanner(System.in);
        Boolean flag = true;
        // MENU OR ADDING DISH ITEMS 
        try {
            socket = new Socket("localhost",8080);

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
                System.out.println(choice);
			    ObjectOutputStream newDish = new ObjectOutputStream(socket.getOutputStream());
                switch(choice) {
                    // ADDING A SIZZLER TO THE MENU
                    case 1: final Sizzlers sizzler = new Sizzlers();
                            sizzler.addSizzlers(d,p);
                            System.out.println("Name : " + sizzler.nameOfItem + " Price: " + sizzler.price);
                            newDish.writeObject((Dishes)sizzler);
                            System.out.println("ITEM HAS BEEN ADDED.\n");
                            break;
                    // ADDING ANOTHER SOUP TO THE MENU
                    case 2: final Soup soup = new Soup();
                            soup.addSoup(d,p);
                            newDish.writeObject(soup);
                            System.out.println("ITEM HAS BEEN ADDED.\n");
                            break;
                    // ADDING ROTI TO THE MENU
                    case 3: final Roti roti = new Roti();
                            roti.addRoti(d,p);
                            newDish.writeObject(roti);
                            System.out.println("ITEM HAS BEEN ADDED.\n");         
                            break;
                    // ADDING THE MAIN COURSE
                    case 4: final MainCourse mainCourse = new MainCourse();
                            mainCourse.addMainCourse(d,p);
                            newDish.writeObject(mainCourse);
                            System.out.println("ITEM HAS BEEN ADDED.\n");
                            break;
                    // ADDING THE RICE TO THE MENU 
                    case 5: final Rice rice = new Rice();
                            rice.addRice(d,p);
                            newDish.writeObject(rice);
                            System.out.println("ITEM HAS BEEN ADDED.\n");
                            break;
                    //  ADDING THE FRUIT SALAD TO THE MENU
                    case 6: final FruitSalad fruitSalad = new FruitSalad();
                            fruitSalad.addFruitSalad(d,p);
                            newDish.writeObject(fruitSalad);
                            System.out.println("ITEM HAS BEEN ADDED.\n");
                            break;
                    //  ADDING THE DESSERT TO THE MENU
                    case 7: final Dessert dessert = new Dessert();
                            dessert.addDessert(d,p);
                            newDish.writeObject(dessert);
                            System.out.println("ITEM HAS BEEN ADDED.\n");
                            break;
                    //  ADDING BEVERAGES TO THE MENU
                    case 8: final Beverage beverage = new Beverage();
                            beverage.addBeverage(d,p);
                            newDish.writeObject(beverage);
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
        catch (final Exception e){
            e.printStackTrace();
        }
          
    	
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
    public static void placeTheOrder(String name,String phone){
        final Scanner scan = new Scanner(System.in);

        if(currentOrder.dishItems.size() == 0){
            System.out.println("Please enter at least one order item...");
        } else {
            try {
                currentOrder.phone = phone;
                currentOrder.name = name;
                    socket = new Socket("localhost",8080);
                    // SEND THIS TO SERVER
                    final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeInt(code.placeOrder);
                    final ObjectOutputStream placeTheOrdeStream = new ObjectOutputStream(socket.getOutputStream());
                    placeTheOrdeStream.writeObject(currentOrder);

                    System.out.println("ORDER HAS BEEN PLACED.\n");
                    currentOrder = new Order();
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

        } catch(final Exception e){
            e.printStackTrace();
        }

    }


     // GET THE PARTICULAR ORDER 
     public static int getOrder(String id){
        int flag = 0;
         try {
            final Scanner scan = new Scanner(System.in);
            orderHistory(0);


            System.out.println(orders.orders.size());

            for(int i=0;i<orders.orders.size();i++){
                if((orders.orders.elementAt(i).orderID == Integer.parseInt(id))){
                    currentOrder = orders.orders.elementAt(i);
                    System.out.println(currentOrder.name);
                    flag = 1;
                    break;
                }
            }
         } catch(final Exception e){
            e.printStackTrace();
         }
         return flag;
    }
    public static void editOrder(String id){
         try {
            final Scanner scan = new Scanner(System.in);
            orderHistory(0);
            socket = new Socket("localhost",8080);

            final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeInt(code.editOrder);

            final ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream());
            currentOrder.orderID = Integer.parseInt(id);
            ous.writeObject(currentOrder);
            currentOrder = new Order();

            System.out.println("Order has been updated");            
         } catch(final Exception e){
            e.printStackTrace();
         }
    }

    // SAVE THIS ORDER AND DELETE THE OTHER
    public static void saveTheOrder(){
        try {

            final ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream());
            currentOrder.orderID = ID;
            ous.writeObject(currentOrder);
            currentOrder = new Order();

            System.out.println("Order has been updated");
        } catch(final Exception e){
            e.printStackTrace();
        }
    }

}