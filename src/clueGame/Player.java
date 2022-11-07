package clueGame;

import java.util.ArrayList;

public class Player {
	private String name;
	private String color;
	private int row, column;
	private ArrayList<Card> hand;
	private ArrayList<Card> seenPeople;
	private ArrayList<Card> seenWeapons;
	private ArrayList<Card> seenRooms;

	// Constructor
	public Player(String name, String color, int row, int column) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		hand = new ArrayList<Card>();
		seenPeople = new ArrayList<Card>();
		seenWeapons = new ArrayList<Card>();
		seenRooms = new ArrayList<Card>();
	}

	// Add card to hand
	public void updateHand(Card card) {
		hand.add(card);
	}

	// Add card to hand
	public void updateSeenPeople(Card card) {
		seenPeople.add(card);
	}
	// Add card to hand
	public void updateSeenWeapons(Card card) {
		seenWeapons.add(card);
	}
	// Add card to hand
	public void updateSeenRooms(Card card) {
		seenRooms.add(card);
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public ArrayList<Card> getSeenPeople() {
		return seenPeople;
	}
	
	public ArrayList<Card> getSeenWeapons() {
		return seenWeapons;
	}
	
	public ArrayList<Card> getSeenRooms() {
		return seenRooms;
	}

	public Card disproveSuggestion(Card person, Card weapon, Card room) {
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
