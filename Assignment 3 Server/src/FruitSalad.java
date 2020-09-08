import java.io.Serializable;
import java.util.*;
public class FruitSalad extends Dishes implements Serializable {

	
	public void addFruitSalad() {
		Scanner scan = new Scanner(System.in);

		System.out.print("ENTER NAME OF THE FRUIT SALAD: ");
		nameOfItem = scan.nextLine();
		System.out.print("\nENTER THE PRICE: ");
		price = scan.nextFloat();
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}