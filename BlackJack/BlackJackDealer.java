package BlackJack;
import java.util.*;

/**
 * @author Mmillward89
 *
 */
/**
 * @author Mmillward89
 *
 */
public class BlackJackDealer {

	private int[] dealerhand, extracards;
	private int dealervalue;

	public BlackJackDealer() {
		dealerhand = new int[2];
		extracards = new int[5];
	}
	
	int[] getDealerHand() {
		return dealerhand;
	}
	
	int getFirstCard() {
		return dealerhand[0];
	}
	
	int getSecondCard() {
		return dealerhand[1];
	}
	
	void addDealerValue() {
		dealervalue = dealerhand[0] + dealerhand[1];
	}
	
	int getDealerValue() {
		return dealervalue;
	}

	
	/**
	 * Adds a drawn card to the dealer's hand
	 * @param card Card drawn from deck
	 */
	void drawCard(int card) {
		for(int i=0; i<extracards.length; i++) {
			if(extracards[i] == 0) {
				extracards[i] = card;
				i = extracards.length - 1;
			}
		}
		dealervalue += card;
		checkForAce(extracards);
	}
	
	/**
	 * Determines whether dealer should use ace as 1 or 10
	 * @param hand Hand to check for aces
	 */
	void checkForAce(int[] hand) {
		for(int i=0; i<hand.length; i++) {
			
			if(hand[i] == 1) {
				int tempValue = dealervalue + 10;
				if(tempValue <= 21) {
					dealervalue += 10;
				}
			}
			
			//If value is zero, no more cards exist
			if(hand[i] == 0) {
				i = hand.length;
			}
		}
	}
	
	boolean checkIfBust() {
		return (dealervalue > 21);
	}
	
	
	/**
	 * Looking at dealer's and player's hand, determines if
	 * dealer should or shouldn't hit.
	 * @param playervalue Used to determine dealer card value vs. player's
	 * @return True/false if dealer should or shouldn't hit
	 */
	boolean shouldDealerHit(int playervalue) {
		
		//Don't hit if already winning
		if(dealervalue > playervalue) {
			return false;
		}
		
		//Don't hit if value is 18 or above (too risky)
		//Always hit if value is 13 or below (too low not to)
		if(dealervalue > 17 && dealervalue < 22) {
			return false;
		} if(dealervalue < 14) {
			return true;
		}
		
		Random r = new Random();
		int probability = r.nextInt(5);
		
		// 1/5 chance dealer will hit between 14-17
		if(dealervalue > 13 && dealervalue < 18 
				&& probability == 2) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Prints all cards in dealer's hand
	 */
	void currentDealerCards() {
		System.out.println("The dealer's cards are");
		System.out.print(dealerhand[0] + " " + dealerhand [1] + " ");
		
		//Print each extra card that isn't 0
		for(int i=0; i<extracards.length; i++) {
			if(extracards[i] == 0) {
				i = extracards.length;
			} else {
				System.out.print(extracards[i] + " ");
			}
		}
	}
	
	void dealerHandMessage() {
		System.out.println("");
		System.out.println("Dealer reveals his hand");
		System.out.println("he has a " + dealerhand[0] + 
				" and a " + dealerhand[1] + ".");
	}
	
}
