import java.io.Serializable;
import java.util.*;
public class MainCourse extends Dishes implements Serializable {

	
	public void addMainCourse() {
		Scanner scan = new Scanner(System.in);

		System.out.print("ENTER NAME OF THE MAIN COURSE: ");
		nameOfItem = scan.nextLine();
		System.out.print("\nENTER THE PRICE: ");
		price = scan.nextFloat();
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}