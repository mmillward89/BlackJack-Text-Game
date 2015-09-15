package programbydoing.classes;
import java.util.*;

public class BlackJack {
	private int[] hearts, diamonds, spades, clubs, playerhand, dealerhand, extracards;
	private ArrayList<int[]> cards;
	private int playervalue, dealervalue;
	private Scanner in;
	private boolean bust;
	
	public BlackJack() {
		hearts = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
		diamonds = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
		spades = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
		clubs = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
		
		cards = new ArrayList<int[]>();
		cards.add(hearts);
		cards.add(diamonds);
		cards.add(spades);
		cards.add(clubs);
		
		playerhand = new int[2];
		dealerhand = new int[2];
		extracards = new int[21];
		
		in = new Scanner(System.in);
		bust = false;
	}
	
	public void game() {
		shuffleUpandDeal(playerhand);
		shuffleUpandDeal(dealerhand);
		playervalue = playerhand[0] + playerhand[1];
		dealervalue= dealerhand[0] + dealerhand[1];
	
		introMessage();
		String input = "";
		
		while(!(input.equals("quit")) || !(input.equals("stay"))) {
			input = in.nextLine();
			switch(input) {
			
			case "hit":
				int newCard = drawNewCard();
				
				for(int i=0; i<extracards.length; i++) {
					if(extracards[i] == 0) {
						extracards[i] = newCard;
						i = extracards.length;
					}
				}
				
				playervalue += newCard;
				
				currentCards();
				if(checkIfBust()) {
					bust = true;
					input = "stay";			
				}
				break;
			
			case "stay":
				System.out.println("You have chosen to stay");
				currentCards();
				if(checkIfBust()) {
					bust = true;		
				}
				break;
				
			case "quit":
				System.out.println("You collect your chips and leave the table...");
				break;
			default:
				if(!(input.equalsIgnoreCase("quit"))) {
				System.out.println("Pick an option, please, the dealer doesn't like"
						+ " small talk.");
				}
			}
			
		}	
		if(bust == false && !(input.equals("quit"))) {
			if(hasAce(playerhand)) {
				System.out.println("You drew an ace, would you like it"
						+ " to count for 1 or 11?");
				confirmAce();
			}
		
			if(checkIfWon()) {
				winMessage();
			} else {
				lossMessage();
			}
		}
		System.out.println("Thank you for playing!");
}
	
	public void shuffleUpandDeal(int[] hand) {
		for(int i=0;i<hand.length;i++) {
			int card = drawNewCard();
			hand[i] = card;
			}
		}
	
	public int drawNewCard() {
		//pick a random suit
		Random r = new Random();
		int suit = r.nextInt(4);
		int[] randomsuit = cards.get(suit);
		
		//draw a random card from the deck
		int cardNumber = r.nextInt(13);
		int card = randomsuit[cardNumber];
		
		//if card has already been drawn
		while(card == 0) {
			cardNumber = r.nextInt(13);
			card = randomsuit[cardNumber];
		}
		
		//Replace card with zero so it can't be chosen again
		randomsuit[cardNumber] = 0;
		
		return card;
	}
	
	public boolean hasAce(int[] hand) {
		for(int i=0; i<hand.length; i++) {
			if(hand[i] == 1) {
				return true;
			}
		}
		return false;
	}
	
	public void confirmAce() {
		int i = 0;
		while(i != 1 || i != 11) {
			try {
				i = in.nextInt();
				if(i==11){
					playervalue += 10;
				}
				else if (i != 1){
					System.out.println("Please enter 1 or 11");
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter 1 or 11");
				in.nextLine();
			}
		}

		in.nextLine();
	}
	
	public boolean checkIfBust() {
		if(playervalue>21){
			System.out.println("");
			System.out.println("You've bust I'm afraid!");
		return true;
		}
		return false;
	}
	
	public boolean checkIfWon() {
		if(playervalue == 21) {
			return true;
		} else if (dealervalue == 21){
			return false;
		} else if(playervalue > dealervalue){
			return true;
		}
		return false;
	}
	
	public void introMessage() {
		System.out.println("Welcome to Blackjack! The cards have been dealt"
				+ " and your hand is " + playerhand[0] + " and " + playerhand[1] + 
				". Keep in mind face cards are all represented as 10 in this game."
				+ "The dealer shows " + dealerhand[0] + ". Type hit, stay, or quit.");
	}
	
	public void newCardMessage(int newCard) {
		System.out.println("You drew a " + newCard + ", "
				+ "your cards now add up to " + playervalue + ".");
	}
	
	public void currentCards() {
		System.out.println("You current cards are: ");
		for(int i=0; i<playerhand.length;i++) {
			if(i != 0) {
				System.out.print(" " + playerhand[i]);
			} else {
			System.out.print(playerhand[i]);
			}
		}
		
		for(int i=0; i<extracards.length;i++) {
			if(extracards[i] == 0) {
				i = extracards.length;
			} else {
			System.out.print(" " + extracards[i]);
			}
		}
		
	}
	
	public void winMessage() {
		System.out.println("");
		System.out.println("Dealer reveals his hand, he has a " + dealerhand[0] + 
				" and a " + dealerhand[1] + ".");
		System.out.println("Congratulations! You won! "
				+ "Now cash in before you get ahead of yourself.");
	}
	
	public void lossMessage() {
		System.out.println("");
		System.out.println("Dealer reveals his hand, he has a " + dealerhand[0] + 
				" and a " + dealerhand[1] + ".");
		System.out.println("Sadly you lost. The house always wins...");
	}
	
	}
	
