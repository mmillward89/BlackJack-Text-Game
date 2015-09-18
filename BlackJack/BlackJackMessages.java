package BlackJack;

public class BlackJackMessages {

	void introMessage(int a, int b, int c) {
		System.out.println("Welcome to Blackjack! The cards have been dealt"
				+ " and your hand is " + a + " and " + b + ".");
		System.out.println("Keep in mind face cards are represented as 10s, and Aces as 1s in this game.");
		System.out.println("The dealer shows " + c + ". Type hit, stay, or quit.");
	}
	
	void blackjackMessage() {
		System.out.println("");
		System.out.println("You drew 21! Excellent!");
	}
	
	void winMessage() {
		System.out.println("");
		System.out.println("Congratulations! You won! "
				+ "Now cash in before you get ahead of yourself.");
	}
	
	void lossMessage() {
		System.out.println("");
		System.out.println("Sadly you lost. The house always wins...");
	}
	
	void tieMessage() {
		System.out.println("");
		System.out.println("It's a tie! You split the pot. Yaaay.");
	}
	
	void aceMessage() {
		System.out.println("");
		System.out.println("You drew an ace, would you like it"
				+ " to count for 1 or 11?");
	}
}
