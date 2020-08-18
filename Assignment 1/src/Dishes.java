
public class Dishes {
	protected String nameOfItem;
	protected Float price;
	protected String dishID = "";

	private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";

	Dishes(){
		for(int i=0;i<2;i++){
			dishID += CHAR_LOWER.charAt((int) Math.ceil(Math.random() * 25));
			dishID += CHAR_UPPER.charAt((int) Math.ceil(Math.random() * 25));
			dishID += NUMBER.charAt((int) Math.ceil(Math.random() * 9));
		}
	}
}
