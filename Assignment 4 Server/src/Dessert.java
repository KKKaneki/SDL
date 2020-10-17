import java.io.Serializable;
import java.util.*;
public class Dessert extends Dishes implements Serializable {

	public void addDessert(String d,Float p) {
		Scanner scan = new Scanner(System.in);
		nameOfItem = d;
        price = p;
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}