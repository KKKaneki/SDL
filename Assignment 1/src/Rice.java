import java.util.*;
public class Rice extends Dishes{
    Scanner scan = new Scanner(System.in);

	
	public void addRice() {
		System.out.print("ENTER NAME OF THE Rice: ");
		nameOfItem = scan.nextLine();
		System.out.print("\nENTER THE PRICE: ");
		price = scan.nextFloat();
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}