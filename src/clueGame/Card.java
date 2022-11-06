package clueGame;

public class Card {
	private String cardName;

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
	
	public boolean equals(Card target) {
		String targetName=target.getCardName();
		CardType targetType=target.getCardType();
		return false;
	}
}
