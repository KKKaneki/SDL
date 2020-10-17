import java.io.Serializable;
import java.util.*;

public class Sizzlers extends Dishes implements Serializable {

	public void addSizzlers(String d,Float p) {
		nameOfItem = d;
        price = p;
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}