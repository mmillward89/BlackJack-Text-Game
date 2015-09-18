package BlackJack;
import java.util.*;

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
	
	void checkForAce(int[] hand) {
		//If dealer has ace and 10 would be better, change it
		for(int i=0; i<hand.length; i++) {
			
			if(hand[i] == 1) {
				int tempValue = dealervalue + 10;
				if(tempValue <= 21) {
					dealervalue += 10;
				}
			}
			
			if(hand[i] == 0) {
				i = hand.length;
			}
		}
	}
	
	boolean checkIfBust() {
		return (dealervalue > 21);
	}
	
	boolean shouldDealerHit(int playervalue) {
		//Returns if dealer should hit
		//'Risky hits' 14-17 have 20% probability to happen
		if(dealervalue > playervalue) {
			return false;
		}
		if(dealervalue > 17 && dealervalue < 22) {
			return false;
		} if(dealervalue < 14) {
			return true;
		}
		
		Random r = new Random();
		int probability = r.nextInt(5);
		
		if(dealervalue > 13 && dealervalue < 18 
				&& probability == 2) {
			return true;
		}
		
		return false;
	}
	
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
		System.out.println("Dealer reveals his hand, he has a "
				+ dealerhand[0] + " " + "and a " + 
				dealerhand[1] + ".");
	}
}
