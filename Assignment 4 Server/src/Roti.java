import java.io.Serializable;
import java.util.*;

public class Roti extends Dishes implements Serializable {

	Roti getItemInfo() {
		return this;
	}
	
	public void addRoti() {
		Scanner scan = new Scanner(System.in);

		System.out.print("ENTER NAME OF THE ROTI: ");
		nameOfItem = scan.nextLine();
		System.out.print("\nENTER THE PRICE: ");
		price = scan.nextFloat();
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}
