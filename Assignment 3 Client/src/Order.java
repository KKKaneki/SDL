import java.io.Serializable;
import java.util.*;


public class Order implements Serializable{
    public Integer orderID;
    public ArrayList<Dishes> dishItems = new ArrayList<Dishes>();
    public ArrayList<Integer> qty = new ArrayList<Integer>();
    public Float orderPrice;
    public String name;
    public String phone;


    Order(){
        orderPrice = 0f;
    }
    
    public static void addNewOrder(){
        
    }
}