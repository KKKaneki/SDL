import java.util.*;
import java.io.Serializable;
import java.net.*;

public class Soup extends Dishes implements Serializable {
	
	public void addSoup(String d,Float p) {
		Scanner scan = new Scanner(System.in);
		nameOfItem = d;
        price = p;
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}