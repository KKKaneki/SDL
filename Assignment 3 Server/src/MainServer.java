import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java.sql.*;





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


    ///
    public static ObjectOutputStream objectOutputStream;
    public static DataInputStream dataInputStream;

    public static OrdersHistory ordersHistory;

    public static RestraurentMenu restraurentMenu = new RestraurentMenu();
    public static String[] dishItems = {"SIZZLER", "SOUP","ROTI","MAINCOURSE","RICE","FRUITSALAD", "DESSERT","BEVERAGE"};

    public static Thread x;
    public static ExecutorService executorService;

    // DATABASE CONNECTIVITY
    public static Connection con;
    public static Statement stmt;
    public static void main(final String[] args){
        try {
            // connection to the database
            System.out.println("Connecting to the database...");
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection)  DriverManager.getConnection("jdbc:mysql://localhost:3306/sdl_restraurent?autoReconnect=true&useSSL=false&allowPublicRetrieval=true", "root", "root");
            stmt = con.createStatement();
            System.out.println("Connected to database....");

            isLogged = false;
            registeredUsers.users = new Properties();

            ordersHistory = new OrdersHistory();

            // executorService = Executors.newFixedThreadPool(5);

            addStaticUser();
            addMenuItems();
            createOrderDatabase();
            createChatTable();


            // ESTABLISH A SERVER SOCKET PROGRAM
            serverSocket = new ServerSocket(8080);

            executorService = Executors.newFixedThreadPool(5);

            while (true) {
                getActiviyToPerform();
            }

        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    public static void loginFunc(final ObjectOutputStream oos) {
        try {
            System.out.println("Thread : " + Thread.currentThread().getName());

            // ACCEPT ANY REQUEST THAT COMES
            userObjectInputStream = new ObjectInputStream(socket.getInputStream());

            final LoginUser user = (LoginUser) userObjectInputStream.readObject();

            final ServerResponse serverResponse = new ServerResponse();

            //  FIND THE USER WITH THE GIVEN 
            String userEntered = "SELECT * FROM users WHERE user_name='" + user.USERNAME + "' AND user_password='" + user.PASSWORD + "';"; 
            System.out.println(userEntered);  
            ResultSet rs = stmt.executeQuery(userEntered);
            if(rs.next() && rs.getString("user_name").equals(user.USERNAME) &&  rs.getString("user_password").equals(user.PASSWORD)) {
                if (user.USERNAME.equals("admin")) {
                    serverResponse.msg = "ADMIN";
                } else {
                    serverResponse.msg = "USER";
                }
                serverResponse.success = true;
                isLogged = true;
                System.out.println("Login Successful\n");

                //System.err.println(rs.getString("user_name") + " password=" + rs.getString("user_password"));
            } else {
                serverResponse.msg = "INCORRECT CREDENTIALS";
                serverResponse.success = false;
                System.out.println("Login Failed\n");
            }


            //                  TO REMOVE 
            // WHEN THE USERNAME AND THE PASSWORD EXISTS
            // if (registeredUsers.users.getProperty(user.USERNAME) != null
            //         && registeredUsers.users.getProperty(user.USERNAME).equals(user.PASSWORD)) {
            //     if (user.USERNAME.equals("admin")) {
            //         serverResponse.msg = "ADMIN";
            //     } else {
            //         serverResponse.msg = "USER";
            //     }
            //     serverResponse.success = true;
            //     isLogged = true;

            //     System.out.println("Login Successful\n");
            // } else {
            //     serverResponse.msg = "INCORRECT CREDENTIALS";
            //     serverResponse.success = false;
            //     System.out.println("Login Failed\n");
            // }
            //              REMOVE END

            oos.writeObject(serverResponse);

            socket.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void getActiviyToPerform() {
        try {
            System.out.println("Waiting For Client connection for Menu ...\n");
            socket = serverSocket.accept();
            System.out.println("Connection between Client And Server established for Menu..." + socket + "\n");

            final Tasks tasks = new Tasks(socket);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            // executorService.execute(tasks);
            System.out.println("Before Thread");

            // x = new Thread(tasks);
            // x.start();

            executorService.execute(tasks);

            // socket.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    public static void handleTheUserChoice(final Integer choice) {
        try {
            System.out.println("Thread : " + Thread.currentThread().getName());
            if (choice.equals(code.login)) {
                System.out.println("Login Attempted from client\n");
                loginFunc(objectOutputStream);
            } else if (choice.equals(code.getRegisteredUsers)) {
                System.out.println("Request for all users from client\n");
                getUserFromDatabase();
                objectOutputStream.writeObject(registeredUsers);

                System.out.println("All Users request completed\n");

            } else if (choice.equals(code.getMenu)) {
                System.out.println("Request for Food Menu from client\n");

                getMenuFromDatabase();
                objectOutputStream.writeObject(restraurentMenu);

                System.out.println("Food Menu request completed\n");
                socket.close();
            } else if (choice.equals(code.addSizzler)) {
                System.out.println("Request Add Sizzler from client\n");

                final ObjectInputStream dishFromClient = new ObjectInputStream(socket.getInputStream());
                final Dishes dish = (Dishes) dishFromClient.readObject();

                // CREATE A QUERY FOR INSERTION
                String insertSizzler = "INSERT INTO dishes VALUES(NULL,1,'" + dish.nameOfItem + "'," + dish.price + ");";
                stmt.executeUpdate(insertSizzler);

                getMenuFromDatabase();
                TimeUnit.SECONDS.sleep(1);

                System.out.println("Add Sizzler request completed\n");

            } else if (choice.equals(code.addSoup)) {
                System.out.println("Request Add Soup from client\n");

                final ObjectInputStream dishFromClient = new ObjectInputStream(socket.getInputStream());
                final Dishes dish = (Dishes) dishFromClient.readObject();

                // CREATE A QUERY FOR INSERTION
                String insertQuery = "INSERT INTO dishes VALUES(NULL,2,'" + dish.nameOfItem + "'," + dish.price + ");";
                stmt.executeUpdate(insertQuery);

                getMenuFromDatabase();
                TimeUnit.SECONDS.sleep(1);

                System.out.println("Add Soup request completed\n");

            } else if (choice.equals(code.addRoti)) {
                System.out.println("Request Add Roti from client\n");

                final ObjectInputStream dishFromClient = new ObjectInputStream(socket.getInputStream());
                final Dishes dish = (Dishes) dishFromClient.readObject();

                 // CREATE A QUERY FOR INSERTION
                 String insertQuery = "INSERT INTO dishes VALUES(NULL,3,'" + dish.nameOfItem + "'," + dish.price + ");";
                 stmt.executeUpdate(insertQuery);
 
                 getMenuFromDatabase();
                 TimeUnit.SECONDS.sleep(1);

                System.out.println("Add Roti request completed\n");

            } else if (choice.equals(code.addMainCourse)) {
                System.out.println("Request Add Main Course from client\n");

                final ObjectInputStream dishFromClient = new ObjectInputStream(socket.getInputStream());
                final Dishes dish = (Dishes) dishFromClient.readObject();

                 // CREATE A QUERY FOR INSERTION
                 String insertQuery = "INSERT INTO dishes VALUES(NULL,4,'" + dish.nameOfItem + "'," + dish.price + ");";
                 stmt.executeUpdate(insertQuery);
 
                 getMenuFromDatabase();
                 TimeUnit.SECONDS.sleep(1);

                System.out.println("Add Main Course request completed\n");

            } else if (choice.equals(code.addRice)) {
                System.out.println("Request Add Rice from client\n");

                final ObjectInputStream dishFromClient = new ObjectInputStream(socket.getInputStream());
                final Dishes dish = (Dishes) dishFromClient.readObject();

                 // CREATE A QUERY FOR INSERTION
                 String insertQuery = "INSERT INTO dishes VALUES(NULL,5,'" + dish.nameOfItem + "'," + dish.price + ");";
                 stmt.executeUpdate(insertQuery);
 
                 getMenuFromDatabase();
                 TimeUnit.SECONDS.sleep(1);

                System.out.println("Add Rice request completed\n");

            } else if (choice.equals(code.addFruitSalad)) {
                System.out.println("Request Add Fruit Salad from client\n");

                final ObjectInputStream dishFromClient = new ObjectInputStream(socket.getInputStream());
                final Dishes dish = (Dishes) dishFromClient.readObject();

                 // CREATE A QUERY FOR INSERTION
                 String insertQuery = "INSERT INTO dishes VALUES(NULL,6,'" + dish.nameOfItem + "'," + dish.price + ");";
                 stmt.executeUpdate(insertQuery);
 
                 getMenuFromDatabase();
                 TimeUnit.SECONDS.sleep(1);

                System.out.println("Add Fruit Salad request completed\n");

            } else if (choice.equals(code.addDessert)) {
                System.out.println("Request Add Dessert from client\n");

                final ObjectInputStream dishFromClient = new ObjectInputStream(socket.getInputStream());
                final Dishes dish = (Dishes) dishFromClient.readObject();
                System.out.println(dish.nameOfItem);

                 // CREATE A QUERY FOR INSERTION
                 String insertQuery = "INSERT INTO dishes VALUES(NULL,7,'" + dish.nameOfItem + "'," + dish.price + ");";
                 stmt.executeUpdate(insertQuery);
 
                 getMenuFromDatabase();
                 TimeUnit.SECONDS.sleep(1);

                System.out.println("Add Dessert request completed\n");

            } else if (choice.equals(code.addBeverage)) {
                System.out.println("Request Add Beverage from client\n");

                final ObjectInputStream dishFromClient = new ObjectInputStream(socket.getInputStream());
                final Dishes dish = (Dishes) dishFromClient.readObject();
                
                 // CREATE A QUERY FOR INSERTION
                 String insertQuery = "INSERT INTO dishes VALUES(NULL,8,'" + dish.nameOfItem + "'," + dish.price + ");";
                 stmt.executeUpdate(insertQuery);
 
                 getMenuFromDatabase();
                 TimeUnit.SECONDS.sleep(1);

                System.out.println("Add Beverage request completed\n");

            } else if (choice.equals(code.placeOrder)) {
                System.out.println("Request Place Order from client\n");

                final ObjectInputStream orderFromClient = new ObjectInputStream(socket.getInputStream());
                Order currentOrder = (Order) orderFromClient.readObject();

                
                insertOrderIntoDatabase(currentOrder);


                System.out.println("Place Order request completed\n");
                socket.close();

            } else if (choice.equals(code.orderHistory)) {
                System.out.println("Request Order History from client\n");

                getOrderHistoryFromDatabase();

                objectOutputStream.writeObject(ordersHistory);
                System.out.println("Order History request completed\n");

            } else if (choice.equals(code.editOrder)) {
                System.out.println("Request Edit Previous Order from client\n");

                final ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                final Order editedOrder = (Order) ois.readObject();

                // DELETE QUERY
                stmt.executeUpdate("DELETE FROM orders WHERE order_id=" + editedOrder.orderID);

                insertOrderIntoDatabase(editedOrder);
                
                System.out.println("Edit Previous Order request completed\n");

            } else if (choice.equals(code.chat)) {
                System.out.println("Chat Box Request received\n");
                final ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                System.out.println("Thread Running : " + Thread.currentThread().getName());

                final ClientHandler client = new ClientHandler(objectInputStream, objectOutputStream);
                // new Thread(client).start();
                // System.out.println("Thread Running : " + Thread.currentThread().getName());
                final Chat chat = new Chat(objectOutputStream, objectInputStream);
                chat.chatWithClient(socket,serverSocket);
            }           
            //x.interrupt();
         
            //socket.close();
        } catch(final Exception e){
            e.printStackTrace();
        }
    }

   

    public static void addMenuItems(){
        try {
            // DROP THE TABLE CONTAINING ALL THE SUBCATEGORIES eg. ROTI,SIZZLER etc
            stmt.executeUpdate("DROP TABLE IF EXISTS sub_categories,dishes,orders,order_item;");

            stmt.executeUpdate("CREATE TABLE sub_categories (cat_id INT AUTO_INCREMENT PRIMARY KEY,cat_name VARCHAR(100) NOT NULL);");
            // ADD ALL THE SUB CATEGORIES TO THE TABLE
            for( int i = 0 ; i < dishItems.length; i++) {
                String insertDishSubCategory = "INSERT INTO sub_categories VALUES(NULL,'" + dishItems[i] + "');";
                stmt.executeUpdate(insertDishSubCategory);
            }
            System.out.println("Dish Sub Items inserted\n");



            // DROP A TABLE IF EXISTS WITH 
            //stmt.executeUpdate("DROP TABLE IF EXISTS dishes;");

            // CREATE TABLE DISHES
            stmt.executeUpdate("CREATE TABLE dishes (dish_id INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,cat_id INT, dish_name VARCHAR(100) UNIQUE, dish_price FLOAT NOT NULL,FOREIGN KEY(cat_id) REFERENCES sub_categories(cat_id) ON DELETE CASCADE);");
            // stmt.executeUpdate("ALTER TABLE dishes ADD FOREIGN KEY(cat_id) REFERENCES sub_categories(cat_id);");

            System.out.println("DISHES DATA HAS BEEN CREATED\n");


            final String[] SIZZLERS = {"Veg Tawa Sizzler","Veg Ala Kiev Sizzler","Soya Chaap Sizzler","Cheese Cutlet Sizzler"};
            final Float[] SIZZLERPRICE = {600f,600f,650f,600f};
            for(int i=0;i<SIZZLERS.length;i++){
                String sizQuery = "INSERT INTO dishes VALUES(NULL,1,'" + SIZZLERS[i] + "'," + SIZZLERPRICE[i] + ");";
                stmt.executeUpdate(sizQuery);
            }


           
            // SOUP
            final String[] SOUP = {"Tomato Soup","Palak Soup","Manchaw Soup","Sweet Corn Soup"};
            final Float[] SOUPPRICE = {80f,80f,95f,95f};
            for(int i=0;i<SOUP.length;i++){
                String soupQuery = "INSERT INTO dishes VALUES(NULL,2,'" + SOUP[i] + "'," + SOUPPRICE[i] + ");";
                stmt.executeUpdate(soupQuery);
            }

            final String[] ROTI = {"Roti","ButterRoti","Naan","Butter Naan","Paratha","Butter Paratha","Chapati","Butter Chapati"};
            final Float[] ROTIPRICE = {15f,20f,25f,30f,25f,30f,10f,15f};
            for(int i=0;i<ROTI.length;i++){
                String rotiQuery = "INSERT INTO dishes VALUES(NULL,3,'" + ROTI[i] + "'," + ROTIPRICE[i] + ");";
                stmt.executeUpdate(rotiQuery);
            }
        
            // MAIN COURSE
            final String[] MAINCOURSE = {"Veg Machurian","Paneer Machurian","Veg Schezwan","Paneer Chilly","Veg Crispy"};
            final Float[] MAINCOURSEPRICE = {150f,170f,170f,170f,160f};
            for(int i=0;i<MAINCOURSE.length;i++){
                String mainCourseQuery = "INSERT INTO dishes VALUES(NULL,4,'" + MAINCOURSE[i] + "'," + MAINCOURSEPRICE[i] + ");";
                stmt.executeUpdate(mainCourseQuery);
            }


            final String[] RICE = {"Veg Biryani","Paneer Biryani","Hyderabadi Biryani","Veg Pulav"};
            final Float[] RICEPRICE = {150f,170f,170f,150f};
            for(int i=0;i<RICE.length;i++){
                String riceQuery = "INSERT INTO dishes VALUES(NULL,5,'" + RICE[i] + "'," + RICEPRICE[i] + ");";
                stmt.executeUpdate(riceQuery);
            }


            // FRUIT SALAD
            final String[] FRUITSALAD = {"Green Salad","Cheese Cherry Pineapple"};
            final Float[] FRUITSALADPRICE = {90f,135f};
            for(int i=0;i<FRUITSALAD.length;i++){
                String fruitSaladQuery = "INSERT INTO dishes VALUES(NULL,6,'" + FRUITSALAD[i] + "'," + FRUITSALADPRICE[i] + ");";
                stmt.executeUpdate(fruitSaladQuery);
            }

            // DESSERT
            final String[] DESSERT = {"Vanilla Ice Cream","Mango Ice Cream","Strawberry Ice Cream","Chocolate Ice Cream"};
            final Float[] DESSERTPRICE = {30f,35f,30f,45f};
            for(int i=0;i<DESSERT.length;i++){
                String dessertQuery = "INSERT INTO dishes VALUES(NULL,7,'" + DESSERT[i] + "'," + DESSERTPRICE[i] + ");";
                stmt.executeUpdate(dessertQuery);
            }

            final String[] BEVERAGE = {"Fresh Lime Juice","Fresh Lime Soda","Soft Drinks","Mango Lassi"};
            final Float[] BEVERAGEPRICE = {50f,60f,30f,20f};
            for(int i=0;i<BEVERAGE.length;i++){
                String beverageQuery = "INSERT INTO dishes VALUES(NULL,8,'" + BEVERAGE[i] + "'," + BEVERAGEPRICE[i] + ");";
                stmt.executeUpdate(beverageQuery);
            }



            getMenuFromDatabase();


        } catch(SQLException e) {
            e.printStackTrace();
        }
    }



    public static void addStaticUser() {
        try {
            // DROP USER TABLE IF EXISTS
            stmt.executeUpdate("DROP TABLE IF EXISTS users");

            // CREATE USER TABLE
            String createUserTable = "CREATE TABLE users(user_id INT AUTO_INCREMENT, user_name VARCHAR(100),user_password VARCHAR(100),PRIMARY KEY(user_id,user_name));";
            stmt.executeUpdate(createUserTable);
            System.out.println("User table has been created...\n");

            // INSERT DATA INTO THE TABLE
            String insertValueInUserTable = "INSERT INTO users(user_name,user_password) VALUES ('admin','password'),('user','password')";
            stmt.executeUpdate(insertValueInUserTable);
            System.out.println("User values entered...\n");


            //                          TO REMOVE
            // ADD A ADMIN USER TO THE USERS PROPERTY
            // registeredUsers.users.setProperty("admin", "password");
            // registeredUsers.users.setProperty("user", "password");
            //                          TILL HERE

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void getUserFromDatabase(){
        try {   
            ResultSet rs = stmt.executeQuery("SELECT * FROM users;");
            while(rs.next()){
                registeredUsers.users.setProperty(rs.getString("user_name"),rs.getString("user_password"));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void getMenuFromDatabase(){
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM sub_categories;");
            String[] subCat = new String[20];
            Integer[] subId = new Integer[20];
            int i = 0;
            while(rs.next()){
                subCat[i] =  String.valueOf(rs.getString("cat_name"));
                subId[i++] = rs.getInt("cat_id");
            }

            for(int j = 0 ; j < i ; j++){

                restraurentMenu.Dishes.put(subCat[j], new Vector<Dishes>());

                ResultSet rsDish = stmt.executeQuery("SELECT * FROM dishes WHERE cat_id=" + subId[j] + ";");

                while(rsDish.next()){
                    Dishes dish = new Dishes();
                    dish.dishID = String.valueOf(rsDish.getInt("dish_id"));
                    dish.nameOfItem = rsDish.getString("dish_name");
                    dish.price = rsDish.getFloat("dish_price");
                    restraurentMenu.Dishes.get(subCat[j]).add(dish);
                }  
            }

            // System.out.println(restraurentMenu.Dishes.get("ROTI").firstElement().nameOfItem);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void createOrderDatabase(){
        try {
            stmt.executeUpdate("DROP TABLE IF EXISTS orders,order_item");
            stmt.executeUpdate("CREATE TABLE orders ( order_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,order_price FLOAT NOT NULL,name VARCHAR(100),phone VARCHAR(100));");

            stmt.executeUpdate("CREATE TABLE order_item(order_id INT,dish_id INT, qty INT,FOREIGN KEY(order_id) REFERENCES orders(order_id) ON DELETE CASCADE ON UPDATE CASCADE,FOREIGN KEY(dish_id) REFERENCES dishes(dish_id) ON DELETE CASCADE ON UPDATE CASCADE);");
        
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void getOrderHistoryFromDatabase(){
        try {
            ordersHistory = new OrdersHistory();
            ResultSet rs = stmt.executeQuery("SELECT * FROM orders NATURAL JOIN order_item NATURAL JOIN dishes;");
            while(rs.next()){
                int orderID = rs.getInt("order_id");
                int idx = -1;
                for (int i = 0 ; i < ordersHistory.orders.size() ; i++){
                    if(ordersHistory.orders.get(i).orderID == orderID) {
                        idx = i;
                    } 
                }

                if(idx == -1){
                    Order order = new Order();
                    order.orderID = rs.getInt("order_id");
                    order.name = rs.getString("name");
                    order.orderPrice = rs.getFloat("order_price");
                    order.phone = rs.getString("phone");
                    Dishes dish = new Dishes();
                    dish.dishID = String.valueOf(rs.getInt("dish_id"));
                    dish.nameOfItem = rs.getString("dish_name");
                    dish.price = rs.getFloat("dish_price");
                    order.dishItems.add(dish);
                    order.qty.add(rs.getInt("qty"));
                    ordersHistory.orders.add(order);
                } else {
                    Dishes dish = new Dishes();
                    dish.dishID = String.valueOf(rs.getInt("dish_id"));
                    dish.nameOfItem = rs.getString("dish_name");
                    dish.price = rs.getFloat("dish_price");
                    ordersHistory.orders.get(idx).dishItems.add(dish);
                    ordersHistory.orders.get(idx).qty.add(rs.getInt("qty"));
                }

            }

        } catch(Exception e){
            e.printStackTrace();
        }


    }

    public static void insertOrderIntoDatabase(Order currentOrder){
        try {
            // CREATE ORDER QUERY
            String orderQuery = "INSERT INTO orders VALUES (NULL," + currentOrder.orderPrice + ",'" + currentOrder.name + "'," + currentOrder.phone + ");";
            stmt.executeUpdate(orderQuery);

            String getOrderId = "SELECT order_id,MAX(order_id) FROM orders WHERE order_price=" + currentOrder.orderPrice + " AND name='" + currentOrder.name + "' AND phone=" + currentOrder.phone + ";";
            ResultSet rs = stmt.executeQuery(getOrderId);
            int orderNo;
            if(rs.next()){
                orderNo = rs.getInt("order_id");
                
                for(int i = 0 ; i < currentOrder.dishItems.size(); i++ ) {
                    String orderItem = "INSERT INTO order_item VALUES(" + orderNo + "," + currentOrder.dishItems.get(i).dishID + "," + currentOrder.qty.get(i) + ");";
                    stmt.executeUpdate(orderItem);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void createChatTable(){
        try {
            stmt.executeUpdate("DROP TABLE IF EXISTS chatTable,messageTable;");
            stmt.executeUpdate("CREATE TABLE chatTable(chat_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,client VARCHAR(100));");
            stmt.executeUpdate("CREATE TABLE messageTable(chat_id INT,message VARCHAR(255),server_message VARCHAR(255),time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(chat_id) REFERENCES chatTable(chat_id));");
            
        } catch(Exception e){
            e.printStackTrace();
        }
       
    }
}