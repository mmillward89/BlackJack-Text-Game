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
	
	int getAces() {
		return aces;
	}
	
	boolean getTie() {
		return tie;
	}
	
	boolean playerHasAce(int[] playerhand, int[] extracards) {
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
	
	public boolean checkIfBust() {
		if(playervalue>21){
			System.out.println("");
			System.out.println("You've bust I'm afraid!");
		return true;
		}
		return false;
	}
	
	boolean checkIf21(int[] playerhand, int[] extracards) {
		if(playervalue == 21) { return true; }
		
		if(playerHasAce(playerhand, extracards)) {
				int tempValue = playervalue + 10;
				if(tempValue == 21) {
					return true;
				}
			}
		return false;
	}
	
	
	boolean checkIfWon(int dealervalue, int[] playerhand, int[] extracards) {
		if(checkIf21(playerhand, extracards)) {
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