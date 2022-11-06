package clueGame;

import java.util.ArrayList;

public class Player {
	private String name;
	private String color;
	private int row, column;
	private ArrayList<Card> hand;
	private ArrayList<Card> seen;
	
	// Constructor
	public Player(String name, String color, int row, int column) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		hand = new ArrayList<Card>();
		seen = new ArrayList<Card>();
	}

	// Add card to hand
	public void updateHand(Card card) {
		hand.add(card);
	}

	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public ArrayList<Card> getSeen() {
		return seen;
	}

	public Card disproveSuggestion() {
		return new Card("", CardType.PERSON);
	}
	
	public void setLocation(int r, int c) {
		row = r;
		column = c;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}
