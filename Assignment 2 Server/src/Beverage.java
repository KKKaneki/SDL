import java.io.Serializable;
import java.util.*;
public class Beverage extends Dishes implements Serializable{
	public void addBeverage() {
		Scanner scan = new Scanner(System.in);

		System.out.print("ENTER NAME OF THE BEVERAGE: ");
		nameOfItem = scan.nextLine();
		System.out.print("\nENTER THE PRICE: ");
        price = scan.nextFloat();
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}