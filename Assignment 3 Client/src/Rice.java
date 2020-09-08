import java.io.Serializable;
import java.util.*;
public class Rice extends Dishes implements Serializable{

	
	public void addRice() {
		Scanner scan = new Scanner(System.in);

		System.out.print("ENTER NAME OF THE Rice: ");
		nameOfItem = scan.nextLine();
		System.out.print("\nENTER THE PRICE: ");
		price = scan.nextFloat();
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}