package programbydoing.classes;
import java.util.*;

public class BlackJack {
	private int[] hearts, diamonds, spades, clubs, playerhand, extracards;
	private ArrayList<int[]> cards;
	private int playervalue, aces;
	private Scanner in;
	private boolean quit, stay, tie;
	private BlackJackMessages messages;
	private BlackJackDealer dealer;
	
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
		extracards = new int[10];
		
		in = new Scanner(System.in);
		messages = new BlackJackMessages();
		dealer = new BlackJackDealer();
		
		stay = false; quit = false; tie = false;
		int aces = 0;
	}
	
	public void game() {
		//Deal hands and add up initial values
		shuffleUpandDeal(playerhand);
		shuffleUpandDeal(dealer.getDealerHand());
		playervalue = playerhand[0] + playerhand[1];
		dealer.addDealerValue();
		
		messages.introMessage(playerhand[0], playerhand[1], dealer.getFirstCard());
		if(checkIf21()) {
			System.out.println("21! Isn't that fortunate?");
			messages.winMessage(dealer.getFirstCard(), dealer.getSecondCard());
			System.out.println("Thank you for playing!");
		} else {
			gameLoop();
		}
		
}
	
	public void gameLoop() {

		String input = "";
		
		//main gameplay loop
		while(quit == false && stay == false) {
			
			input = in.nextLine();
			
			switch(input) {
			
			case "hit":
				int newCard = drawNewCard();
				
				//add card to extra cards
				for(int i=0; i<extracards.length; i++) {
					if(extracards[i] == 0) {
						extracards[i] = newCard;
						i = extracards.length;
					}
				}
				
				//add new value to total and tell player cards
				playervalue += newCard;
				currentCards();
				
				//Confirm if drawn card has won player game
				if(checkIf21()) {
					messages.winMessage(dealer.getFirstCard(), dealer.getSecondCard());
					quit = true;
				}
				
				//If bust, tells player and quits the app
				if(checkIfBust()) {
					messages.lossMessage(dealer.getFirstCard(), dealer.getSecondCard());
					quit = true;
				} else if (checkIf21() == false){
					in.nextLine();
				}
			
				break;
			
			case "stay":
				System.out.println("You have chosen to stay");
				currentCards();
				
				if(checkIfBust()) {
					messages.lossMessage(dealer.getFirstCard(), dealer.getSecondCard());
					quit = true;
				}
				
				stay = true;
				break;
				
			case "quit":
				System.out.println("You collect your chips and leave the table...");
				quit = true;
				break;
			
			default:
				System.out.println("Pick an option, please, the dealer doesn't like"
						+ " small talk.");
				break;
			}
			
		}	
		
		if(stay == true) {
			if(playerHasAce()) {
				//Loop for number of aces player drew
				
				for(int i=0; i<aces; i++) {
				System.out.println("");
				System.out.println("You drew an ace, would you like it"
						+ " to count for 1 or 11?");
				confirmAce();
				}
			}
	
			if(checkIfWon()) {
				messages.winMessage(dealer.getFirstCard(), dealer.getSecondCard());
			} else if (tie == false){
				messages.lossMessage(dealer.getFirstCard(), dealer.getSecondCard());
			} else {
				messages.tieMessage();
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
	
	public boolean playerHasAce() {
		boolean ace = false;
		
		for(int i=0; i<playerhand.length;i++) {
			if(playerhand[i] == 1) {
				ace = true; aces++;
			}
		}
		
		for(int i=0; i<extracards.length; i++) {
			if(extracards[i] == 0) {
				i = extracards.length - 1;
			} if (extracards[i] == 1) {
				ace = true; aces++;
			}
		}
		return ace;
	}
	
	public void confirmAce() {
		int i = 0;
		boolean b = false;
		
		while(b == false) {
			try {
				i = in.nextInt();
				
				if(i == 11) {
					playervalue+=10;
				} if(i == 1 || i==11){
					b = true;
				} else {
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
	
	public boolean checkIf21() {
		if(playervalue == 21) { return true; }
		
		//Check that a player has an ace, and if that ace being
		//11 would result in a 21 (will otherwise register automatically
		// if a 1.
		if(playerHasAce()) {
				int tempValue = playervalue + 10;
				if(tempValue == 21) {
					return true;
				}
			}
		return false;
	}
	
	
	public boolean checkIfWon() {
		if(checkIf21()) {
			return true;
		} else if (dealer.getDealerValue() == 21){
			return false;
		} else if(playervalue > dealer.getDealerValue()){
			return true;
		} else if (playervalue == dealer.getDealerValue()) {
			tie = true; return false; 
		}
		return false;
	}
	
	public void currentCards() {
		System.out.println("");
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
	
	}
	
