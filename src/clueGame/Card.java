package clueGame;

public class Card {
	private String cardName;
	private String Color; //for GUI display
	private CardType cardType;

	
	// Constructor
	public Card(String name, CardType type) {
		cardName = name;
		cardType = type;
	}
	
	// Getter for cardName
	public String getCardName() {
		return cardName;
	}
	
	// Getter for cardType
	public CardType getCardType() {
		return cardType;
	}
	
	// Test for equivalency
	public boolean equals(Card target) {
		String thisName = this.getCardName();
		CardType thisType = this.getCardType();
		if (thisName == target.getCardName() && thisType == target.getCardType()) {
			return true;
		}
		return false;
	}
	
	//set and get the color of the card
	public String getColor() {
		return Color;
	}

	public void setColor(String color) {
		Color = color;
	}

	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", cardType=" + cardType + "]";
	}
}
