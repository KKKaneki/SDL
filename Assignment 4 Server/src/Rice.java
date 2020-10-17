import java.io.Serializable;
import java.util.*;
public class Rice extends Dishes implements Serializable{

	public void addRice(String d,Float p) {
		Scanner scan = new Scanner(System.in);
		nameOfItem = d;
        price = p;
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}