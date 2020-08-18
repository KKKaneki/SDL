import java.util.*;

public class Soup extends Dishes {
    Scanner scan = new Scanner(System.in);

	
	public void addSoup() {
		System.out.print("ENTER NAME OF THE SOUP: ");
		nameOfItem = scan.nextLine();
		System.out.print("\nENTER THE PRICE: ");
		price = scan.nextFloat();
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}