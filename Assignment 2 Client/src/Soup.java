import java.util.*;
import java.io.Serializable;
import java.net.*;

public class Soup extends Dishes implements Serializable {
	
	public void addSoup() {
		Scanner scan = new Scanner(System.in);
			System.out.print("ENTER NAME OF THE SOUP: ");
			nameOfItem = scan.nextLine();
			System.out.print("\nENTER THE PRICE: ");
			price = scan.nextFloat();
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}