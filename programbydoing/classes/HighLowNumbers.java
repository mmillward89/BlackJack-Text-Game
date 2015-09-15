package programbydoing.classes;
import java.util.*;

public class HighLowNumbers {

	public void guess() {
		Scanner in = new Scanner(System.in);
		System.out.println("I'm thinking of a number between 1 and 100."
				+ " You have 7 guesses.");
		int i = 0;
		int count = 7;
		while(i != 57 && count > 0) {
			try {
			i = in.nextInt();
			if (i != 57) {
				count--;
				if(i > 57 && count > 0) {
					System.out.println("Lower");
				} else if (i < 57 && count > 0){
					System.out.println("Higher");
				}
			}
			} catch (InputMismatchException e) {
				System.out.println("Integers only plz. That won't count as "
						+ "a guess.");
				in.nextLine();
			}
		}
		
		if(i == 57) {
			System.out.println("Congratulations! Well guessed.");
		} else {
			System.out.println("You didn't get it! Answer was 57.");
		}
		
		in.close();
	}
}
