package BlackJack;
import java.util.*;

public class BlackJack {
	private int[] hearts, diamonds, spades, clubs, playerhand, extracards;
	private ArrayList<int[]> cards;
	private Scanner in;
	private boolean quit, stay;
	private BlackJackMessages messages;
	private BlackJackDealer dealer;
	private BlackJackScorer scorer;
	
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
		scorer = new BlackJackScorer();
		
		stay = false; quit = false;
	}
	
	public static void main(String[] args) {
		BlackJack bj = new BlackJack();
		bj.startGame();
	}
	
	public void startGame() {
		//Deal hands and add up initial values
		shuffleUpandDeal(playerhand);
		shuffleUpandDeal(dealer.getDealerHand());
		scorer.setPlayerValue(playerhand[0] + playerhand[1]);
		dealer.addDealerValue();
		
		messages.introMessage(playerhand[0], playerhand[1], dealer.getFirstCard());
		if(scorer.checkIf21(playerhand, extracards)) {
			messages.blackjackMessage();
			messages.winMessage(dealer.getFirstCard(), dealer.getSecondCard());
			System.out.println("Thank you for playing!");
		} else {
			gameLoop();
		}
		
}
	
	private void gameLoop() {

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
				scorer.setPlayerValue(newCard);
				currentCards();
				
				//Confirm if drawn card has won player game
				if(scorer.checkIf21(playerhand, extracards)) {
					messages.winMessage(dealer.getFirstCard(), dealer.getSecondCard());
					quit = true;
				}
				
				//If bust, tells player and quits the app
				if(scorer.checkIfBust()) {
					messages.lossMessage(dealer.getFirstCard(), dealer.getSecondCard());
					quit = true;
				} else if (scorer.checkIf21(playerhand, extracards) == false){
					in.nextLine();
				}
			
				break;
			
			case "stay":
				System.out.println("You have chosen to stay");
				currentCards();
				
				if(scorer.checkIfBust()) {
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
			if(scorer.playerHasAce(playerhand, extracards)) {
				//Loop for number of aces player drew
				
				for(int i=0; i<scorer.getAces(); i++) {
				System.out.println("");
				System.out.println("You drew an ace, would you like it"
						+ " to count for 1 or 11?");
				scorer.confirmAce(in);
				}
			}
	
			if(scorer.checkIfWon(dealer.getDealerValue(), playerhand, extracards)) {
				messages.winMessage(dealer.getFirstCard(), dealer.getSecondCard());
			} else if (scorer.getTie()){
				messages.tieMessage();
			} else {
				messages.lossMessage(dealer.getFirstCard(), dealer.getSecondCard());
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
	
	private int drawNewCard() {
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
	
	private void currentCards() {
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
	
