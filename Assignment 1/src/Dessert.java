import java.util.*;
public class Dessert extends Dishes {
    Scanner scan = new Scanner(System.in);

	
	public void addDessert() {
		System.out.print("ENTER NAME OF THE DESSERT: ");
		nameOfItem = scan.nextLine();
		System.out.print("\nENTER THE PRICE: ");
		price = scan.nextFloat();
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}