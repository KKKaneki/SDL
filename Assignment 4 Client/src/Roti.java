import java.io.Serializable;
import java.util.*;

public class Roti extends Dishes implements Serializable {

	Roti getItemInfo() {
		return this;
	}
	public void addRoti(String d,Float p) {
		Scanner scan = new Scanner(System.in);
		nameOfItem = d;
        price = p;
	}
	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}
