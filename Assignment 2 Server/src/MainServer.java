import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;



class LoginUser implements Serializable {
    String USERNAME;
    String PASSWORD; 
}

class ServerResponse implements Serializable {
    public String msg;
    public Boolean success;
}

class RegisteredUser implements Serializable {
    Properties users;
}

class RestraurentMenu implements Serializable {
    public Hashtable<String,Vector<Dishes>> Dishes = new Hashtable<String,Vector<Dishes>>();
}

class OrdersHistory implements Serializable {
    public Vector<Order> orders = new Vector<Order>();
}


public class MainServer {
    public static ServerSocket serverSocket;
    public static Socket socket;
    public static ObjectInputStream userObjectInputStream;
    public static ObjectOutputStream userObjectOutputStream;
    public static RegisteredUser registeredUsers = new RegisteredUser();
    public static Boolean isLogged;
    public static Code code = new Code();

    public static ObjectOutputStream objectOutputStream;
    public static DataInputStream dataInputStream;

    public static OrdersHistory ordersHistory;

    public static RestraurentMenu restraurentMenu = new RestraurentMenu();
    public static String[] dishItems = {"SIZZLER", "SOUP","ROTI","MAINCOURSE","RICE","FRUITSALAD", "DESSERT","BEVERAGE"};




    public static void main(String[] args){
        try {
            isLogged = false;
            registeredUsers.users = new Properties();

            ordersHistory = new OrdersHistory();

            addStaticUser();
            addMenuItems();
            

            // ESTABLISH A SERVER SOCKET PROGRAM
            serverSocket = new ServerSocket(8080);
    
                
                while(true){
                    getActiviyToPerform();
                }
              

            } catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void loginFunc(){
        try{
            // ACCEPT ANY REQUEST THAT COMES
            userObjectInputStream = new ObjectInputStream(socket.getInputStream());

            LoginUser user = (LoginUser) userObjectInputStream.readObject();

            ServerResponse serverResponse = new ServerResponse();

            // WHEN THE USERNAME AND THE PASSWORD EXISTS
            if(registeredUsers.users.getProperty(user.USERNAME) != null && registeredUsers.users.getProperty(user.USERNAME).equals(user.PASSWORD)){
                if (user.USERNAME.equals("admin")){
                    serverResponse.msg = "ADMIN";
                } else {
                    serverResponse.msg = "USER";
                }
                serverResponse.success = true;
                isLogged = true;

                System.out.println("Login Successful\n");
            } else {
                serverResponse.msg = "INCORRECT CREDENTIALS";
                serverResponse.success = false;
                System.out.println("Login Failed\n");
            }
            objectOutputStream.writeObject(serverResponse);



            socket.close();
        } catch(Exception e){
            e.printStackTrace();
        }  
    }


    public static void getActiviyToPerform(){
        try {
            System.out.println("Waiting For Client connection for Menu ...\n");
            socket = serverSocket.accept();
            System.out.println("Connection between Client And Server established for Menu...\n");

            dataInputStream = new DataInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());


            Integer inputCode = dataInputStream.readInt();
            handleTheUserChoice(inputCode);


        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void handleTheUserChoice(Integer choice){
        try {

            if(choice.equals(code.login)){
                System.out.println("Login Attempted from client\n");
                loginFunc();
            } else if(choice.equals(code.getRegisteredUsers)){
                System.out.println("Request for all users from client\n");

                objectOutputStream.writeObject(registeredUsers);

                System.out.println("All Users request completed\n");

            } else if(choice.equals(code.getMenu)){
                System.out.println("Request for Food Menu from client\n");

                objectOutputStream.writeObject(restraurentMenu);

                System.out.println("Food Menu request completed\n");

            } else if(choice.equals(code.addSizzler)) {
                System.out.println("Request Add Sizzler from client\n");

                ObjectInputStream dishFromClient = new ObjectInputStream(socket.getInputStream());
                Dishes dish = (Dishes) dishFromClient.readObject();
                
                restraurentMenu.Dishes.get(dishItems[0]).add(dish);

                System.out.println("Add Sizzler request completed\n");

            }  else if(choice.equals(code.addSoup)) {
                System.out.println("Request Add Soup from client\n");

                ObjectInputStream dishFromClient = new ObjectInputStream(socket.getInputStream());
                Dishes dish = (Dishes) dishFromClient.readObject();
                
                restraurentMenu.Dishes.get(dishItems[1]).add(dish);
                System.out.println("Add Soup request completed\n");

            } else if(choice.equals(code.addRoti)) {
                System.out.println("Request Add Roti from client\n");

                ObjectInputStream dishFromClient = new ObjectInputStream(socket.getInputStream());
                Dishes dish = (Dishes) dishFromClient.readObject();
                
                restraurentMenu.Dishes.get(dishItems[2]).add(dish);
                System.out.println("Add Roti request completed\n");

            } else if(choice.equals(code.addMainCourse)) {
                System.out.println("Request Add Main Course from client\n");

                ObjectInputStream dishFromClient = new ObjectInputStream(socket.getInputStream());
                Dishes dish = (Dishes) dishFromClient.readObject();
                
                restraurentMenu.Dishes.get(dishItems[3]).add(dish);
                System.out.println("Add Main Course request completed\n");

            } else if(choice.equals(code.addRice)){
                System.out.println("Request Add Rice from client\n");

                ObjectInputStream dishFromClient = new ObjectInputStream(socket.getInputStream());
                Dishes dish = (Dishes) dishFromClient.readObject();
                
                restraurentMenu.Dishes.get(dishItems[4]).add(dish);
                System.out.println("Add Rice request completed\n");

            } else if(choice.equals(code.addFruitSalad)){
                System.out.println("Request Add Fruit Salad from client\n");

                ObjectInputStream dishFromClient = new ObjectInputStream(socket.getInputStream());
                Dishes dish = (Dishes) dishFromClient.readObject();
                
                restraurentMenu.Dishes.get(dishItems[5]).add(dish);
                System.out.println("Add Fruit Salad request completed\n");

            } else if(choice.equals(code.addDessert)){
                System.out.println("Request Add Dessert from client\n");

                ObjectInputStream dishFromClient = new ObjectInputStream(socket.getInputStream());
                Dishes dish = (Dishes) dishFromClient.readObject();
                System.out.println(dish.nameOfItem);
                
                restraurentMenu.Dishes.get(dishItems[6]).add(dish);
                System.out.println("Add Dessert request completed\n");

            } else if(choice.equals(code.addBeverage)){
                System.out.println("Request Add Beverage from client\n");

                ObjectInputStream dishFromClient = new ObjectInputStream(socket.getInputStream());
                Dishes dish = (Dishes) dishFromClient.readObject();
                System.out.println(dish.nameOfItem);
                
                restraurentMenu.Dishes.get(dishItems[7]).add(dish);
                System.out.println("Add Beverage request completed\n");

            } else if(choice.equals(code.placeOrder)) {
                System.out.println("Request Place Order from client\n");

                ObjectInputStream orderFromClient = new ObjectInputStream(socket.getInputStream());
                Order currentOrder = (Order) orderFromClient.readObject();

                ordersHistory.orders.add(currentOrder);
                System.out.println("Place Order request completed\n");

            } else if(choice.equals(code.orderHistory)){
                System.out.println("Request Order History from client\n");

                
                objectOutputStream.writeObject(ordersHistory);
                System.out.println("Order History request completed\n");


            } else if(choice.equals(code.editOrder)) {
                System.out.println("Request Edit Previous Order from client\n");


                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Order editedOrder = (Order) ois.readObject();

                for(int i=0;i<ordersHistory.orders.size();i++){
                    if(ordersHistory.orders.get(i).orderID.equals(editedOrder.orderID)){
                        ordersHistory.orders.remove(i);
                        ordersHistory.orders.add(i,editedOrder);
                        break;
                    }
                }
                System.out.println("Edit Previous Order request completed\n");

            }

            socket.close();


        } catch(Exception e){
            e.printStackTrace();
        }
    }

    

    public static void addMenuItems(){
        // SIZZLERS
        String[] SIZZLERS = {"Veg Tawa Sizzler","Veg Ala Kiev Sizzler","Soya Chaap Sizzler","Cheese Cutlet Sizzler"};
        Float[] SIZZLERPRICE = {600f,600f,650f,600f};
        restraurentMenu.Dishes.put(dishItems[0], new Vector<Dishes>());
        for(int i=0;i<SIZZLERS.length;i++){
            Dishes dish = new Dishes();
            dish.nameOfItem=SIZZLERS[i];
            dish.price=SIZZLERPRICE[i];
            restraurentMenu.Dishes.get(dishItems[0]).add(dish);
        }

        // SOUP
        String[] SOUP = {"Tomato Soup","Palak Soup","Manchaw Soup","Sweet Corn Soup"};
        Float[] SOUPPRICE = {80f,80f,95f,95f};
        restraurentMenu.Dishes.put(dishItems[1], new Vector<Dishes>());
        for(int i=0;i<SOUP.length;i++){
            Dishes dish = new Dishes();
            dish.nameOfItem=SOUP[i];
            dish.price=SOUPPRICE[i];
            restraurentMenu.Dishes.get(dishItems[1]).add(dish);
        }
        

        // ROTI
        String[] ROTI = {"Roti","ButterRoti","Naan","Butter Naan","Paratha","Butter Paratha","Chapati","Butter Chapati"};
        Float[] ROTIPRICE = {15f,20f,25f,30f,25f,30f,10f,15f};
        restraurentMenu.Dishes.put(dishItems[2], new Vector<Dishes>());
        for(int i=0;i<ROTI.length;i++){
            Dishes dish = new Dishes();
            dish.nameOfItem=ROTI[i];
            dish.price=ROTIPRICE[i];
            restraurentMenu.Dishes.get(dishItems[2]).add(dish);
        }
        
        // MAIN COURSE
        String[] MAINCOURSE = {"Veg Machurian","Paneer Machurian","Veg Schezwan","Paneer Chilly","Veg Crispy"};
        Float[] MAINCOURSEPRICE = {150f,170f,170f,170f,160f};
        restraurentMenu.Dishes.put(dishItems[3], new Vector<Dishes>());
        for(int i=0;i<MAINCOURSE.length;i++){
            Dishes dish = new Dishes();
            dish.nameOfItem=MAINCOURSE[i];
            dish.price=MAINCOURSEPRICE[i];
            restraurentMenu.Dishes.get(dishItems[3]).add(dish);
        }
        

        // RICE
        String[] RICE = {"Veg Biryani","Paneer Biryani","Hyderabadi Biryani","Veg Pulav"};
        Float[] RICEPRICE = {150f,170f,170f,150f};
        restraurentMenu.Dishes.put(dishItems[4], new Vector<Dishes>());
        for(int i=0;i<RICE.length;i++){
            Dishes dish = new Dishes();
            dish.nameOfItem=RICE[i];
            dish.price=RICEPRICE[i];
            restraurentMenu.Dishes.get(dishItems[4]).add(dish);
        }

        // FRUIT SALAD
        String[] FRUITSALAD = {"Green Salad","Cheese Cherry Pineapple"};
        Float[] FRUITSALADPRICE = {90f,135f};
        restraurentMenu.Dishes.put(dishItems[5], new Vector<Dishes>());
        for(int i=0;i<FRUITSALAD.length;i++){
            Dishes dish = new Dishes();
            dish.nameOfItem=FRUITSALAD[i];
            dish.price=FRUITSALADPRICE[i];
            restraurentMenu.Dishes.get(dishItems[5]).add(dish);
        }
        
        // DESSERT
        String[] DESSERT = {"Vanilla Ice Cream","Mango Ice Cream","Strawberry Ice Cream","Chocolate Ice Cream"};
        Float[] DESSERTPRICE = {30f,35f,30f,45f};
        restraurentMenu.Dishes.put(dishItems[6], new Vector<Dishes>());
        for(int i=0;i<DESSERT.length;i++){
            Dishes dish = new Dishes();
            dish.nameOfItem=DESSERT[i];
            dish.price=DESSERTPRICE[i];
            restraurentMenu.Dishes.get(dishItems[6]).add(dish);
        }


        // BEVERAGE
        String[] BEVERAGE = {"Fresh Lime Juice","Fresh Lime Soda","Soft Drinks","Mango Lassi"};
        Float[] BEVERAGEPRICE = {50f,60f,30f,20f};
        restraurentMenu.Dishes.put(dishItems[7], new Vector<Dishes>());
        for(int i=0;i<BEVERAGE.length;i++){
            Dishes dish = new Dishes();
            dish.nameOfItem=BEVERAGE[i];
            dish.price=BEVERAGEPRICE[i];
            restraurentMenu.Dishes.get(dishItems[7]).add(dish);
        }

    }



    public static void addStaticUser(){
        // ADD A ADMIN USER TO THE USERS PROPERTY
        registeredUsers.users.setProperty("admin", "password");
        registeredUsers.users.setProperty("user", "password");
    }
}