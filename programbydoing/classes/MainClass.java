package programbydoing.classes;
import java.util.*;

public class MainClass {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Guess a number between 1 and 10");
		int count = 0;
		int i = 0;
		while (i != 6) {
		try {
			i = in.nextInt();
			if (i != 6) {
			System.out.println("Please try again");
			}
			count++;
		} catch (InputMismatchException e) {
			System.out.println("Integers only plz");
			count++;
			in.nextLine();
		}
		}
		if (i == 6) {
			System.out.println("Correct! You guessed " + 
		count + " times.");
			in.close();
		}
		
	}

}
