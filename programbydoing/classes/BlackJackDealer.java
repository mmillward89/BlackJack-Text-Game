package programbydoing.classes;

public class BlackJackDealer {

	private int[] dealerhand;
	private int dealervalue;

	public BlackJackDealer() {
		dealerhand = new int[2];
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
}
