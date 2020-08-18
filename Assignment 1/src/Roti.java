import java.util.*;

public class Roti extends Dishes {
	Scanner scan = new Scanner(System.in);

	Roti getItemInfo() {
		return this;
	}
	
	public void addRoti() {
		System.out.print("ENTER NAME OF THE ROTI: ");
		nameOfItem = scan.nextLine();
		System.out.print("\nENTER THE PRICE: ");
		price = scan.nextFloat();
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}
