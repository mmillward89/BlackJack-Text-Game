package programbydoing.classes;
import java.util.*;

public class BlackJack {
	private int[] hearts, diamonds, spades, clubs, playerhand, dealerhand;
	private ArrayList<int[]> cards;
	private int playervalue, dealervalue;
	private Scanner in;
	
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
		
		in = new Scanner(System.in);
	}
	
	public void game() {
		shuffleUpandDeal(playerhand);
		shuffleUpandDeal(dealerhand);
		playervalue = playerhand[0] + playerhand[1];
		dealervalue= dealerhand[0] + dealerhand[1];
		
		introMessage();
		String input = "";
		
		while(!(input.equals("quit"))) {
			input = in.nextLine();
			switch(input) {
			
			case "hit":
				int newCard = drawNewCard();
				playervalue += newCard;
				break;
			
			case "stay":
				if(checkIfWon()) {
					System.out.println("Congratulations! You won! "
							+ "Now cash in before you get ahead of yourself");
				} else {
					System.out.println("Sadly you lost. The house always wins...");
				}
				input="quit";
				break;
			
			default:
				System.out.println("Pick an option, please, dealer doesn't like"
						+ "small talk.");
				break;
			}
			
			in.nextLine();
			
			if(hasAce(playerhand)) {
				System.out.println("You drew an ace, would you like it"
						+ " to count for 1 or 11?");
				confirmAce();
			}
			
			if(checkIfBust()) {
				input = "quit";
			}			
		}
		
		System.out.println("Thank you for playing!");
	}
	
	public void shuffleUpandDeal(int[] hand) {
		for(int i=0;i<2;i++) {
			int card = drawNewCard();
			hand[i] = card;
			}
		}
	
	public int drawNewCard() {
		//pick a random suit
		Random r = new Random();
		int suit = r.nextInt(4-1)+1;
		int[] randomsuit = cards.get(suit);
		
		//draw a random card from the deck
		int cardNumber = r.nextInt(13);
		int card = randomsuit[cardNumber];
		
		//if card has already been drawn
		while(card == 0) {
			int newCardNumber = r.nextInt(13);
			card = randomsuit[newCardNumber];
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
		while(i!=1 || i!=11) {
			try {
				i = in.nextInt();
				if(i==11){playervalue += 10;}
			} catch (InputMismatchException e) {
				System.out.println("Please enter 1 or 11");
				in.nextLine();
			}
		}
		in.nextLine();
	}
	
	public boolean checkIfBust() {
		if(playervalue>21){
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
	
	}
	
