import java.util.*;

public class Sizzlers extends Dishes {
    Scanner scan = new Scanner(System.in);

	
	public void addSizzlers() {
		System.out.print("ENTER NAME OF THE SIZZLER: ");
		nameOfItem = scan.nextLine();
		System.out.print("\nENTER THE PRICE: ");
		price = scan.nextFloat();
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}