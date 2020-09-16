import java.io.Serializable;
import java.util.*;

public class Sizzlers extends Dishes implements Serializable {

	
	public void addSizzlers() {
		Scanner scan = new Scanner(System.in);

		System.out.print("ENTER NAME OF THE SIZZLER: ");
		nameOfItem = scan.nextLine();
		System.out.print("\nENTER THE PRICE: ");
		price = Float.parseFloat(scan.nextLine().toString());
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}