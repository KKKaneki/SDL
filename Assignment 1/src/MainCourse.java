import java.util.*;
public class MainCourse extends Dishes {
    Scanner scan = new Scanner(System.in);

	
	public void addMainCourse() {
		System.out.print("ENTER NAME OF THE MAIN COURSE: ");
		nameOfItem = scan.nextLine();
		System.out.print("\nENTER THE PRICE: ");
		price = scan.nextFloat();
	}

	public String toString(){
		return this.nameOfItem + " costs " + this.price + "\n";
	}
}