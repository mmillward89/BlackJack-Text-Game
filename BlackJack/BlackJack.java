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
		dealer.checkForAce(dealer.getDealerHand());
		
		messages.introMessage(playerhand[0], playerhand[1], dealer.getFirstCard());
		
		if(scorer.checkIf21(playerhand)) {
			messages.blackjackMessage(); 
			messages.winMessage();
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
				
				if(scorer.getPlayerValue() == 21) {
					messages.blackjackMessage();
					messages.winMessage();
					quit = true;
				}
				
				//If bust, tells player and quits the app
				if(scorer.checkIfBust()) {
					dealer.currentDealerCards();
					messages.lossMessage();
					quit = true;
				} else {
					in.nextLine();
				}
			
				break;
			
			case "stay":
				System.out.println("You have chosen to stay");
				currentCards();
				
				if(scorer.checkIfBust()) {
					dealer.currentDealerCards();
					messages.lossMessage();
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
			messages.aceMessage();
			scorer.confirmAce(in);				
			}
		}
	
		if(scorer.checkIfBust()) {
			dealer.currentDealerCards();
			messages.lossMessage();
		} else {
			dealer.dealerHandMessage();
			boolean dealerBust = false;
			
			//Confirm if dealer should hit, then check if bust
			while (dealer.shouldDealerHit(scorer.getPlayerValue())) {
				int card = drawNewCard();
				dealer.drawCard(card);
				System.out.println("Dealer hits, and draws a " + card + ".");
				dealerBust = dealer.checkIfBust();
			}
			
			if(dealerBust == false) {
				if(scorer.checkIfWon(dealer.getDealerValue())) {
					messages.winMessage();
				} else if (scorer.getTie()){
					messages.tieMessage();
				} else {
					messages.lossMessage();
				}
			} else {
				System.out.println("The dealer has bust!");
				messages.winMessage();
			}
		}
	}		
	System.out.println("Thank you for playing!");	
}
	
	//Game loop ends here and card drawing functionality begins
	
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
	
