package BlackJack;

import java.util.InputMismatchException;
import java.util.*;

public class BlackJackScorer {
	
	private int playervalue, aces;
	private boolean tie;
	
	public BlackJackScorer() {
		playervalue = 0; aces = 0;
		tie = false;
	}

	void setPlayerValue(int playervalue) {
		this.playervalue += playervalue;
	}
	
	int getPlayerValue() {
		return playervalue;
	}
	
	int getAces() {
		return aces;
	}
	
	boolean getTie() {
		return tie;
	}
	
	/**
	 * Checks whether player has been dealt blackjack
	 * @param playerhand - takes player's hand
	 * @return true if player has 21
	 */
	boolean checkIf21(int[] playerhand) {
		if(playerhand[0] == 10 && playerhand[1] == 1){
			return true;
		} if(playerhand[0] == 1 && playerhand[1] == 10){
			return true;
		}
		return false;
	}
	
	/**
	 * Looks at all cards player has drawn, returns true if
	 * the player has an ace
	 * @param playerhand 
	 * @param extracards
	 * @return
	 */
	boolean playerHasAce(int[] playerhand, int[] extracards) {
		boolean ace = false;
		
		for(int i=0; i<playerhand.length;i++) {
			if(playerhand[i] == 1) {
				ace = true; aces++;
			}
		}
		
		for(int i=0; i<extracards.length; i++) {
			if (extracards[i] == 1) {
				ace = true; aces++;
			} if(extracards[i] == 0) {
				i = extracards.length;
			} 
		}
		return ace;
	}
	
	/**
	 * Asks player to confirm 1 or 11 for a drawn ace
	 * @param in Allows player input
	 */
	void confirmAce(Scanner in) {
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
	
	/**
	 * Confirms true/false whether player has bust
	 * @return 
	 */
	boolean checkIfBust() {
		if(playervalue>21){
			System.out.println("");
			System.out.println("You've bust I'm afraid!");
		return true;
		}
		return false;
	}
	
	/**
	 * Compares player value to dealer value and returns
	 * true false based on who won
	 * 
	 * @param dealervalue 
	 * @return 
	 */
	boolean checkIfWon(int dealervalue) {
		if(playervalue == 21) {
			return true;
		} else if (dealervalue == 21){
			return false;
		} else if(playervalue > dealervalue){
			return true;
		} else if (playervalue == dealervalue) {
			tie = true; return false; 
		}
		return false;
	}
	
}
