package clueGame;

import java.util.ArrayList;
import java.util.Random;

public class Player {
	private String name;
	private String color;
	protected int row;
	protected int column;
	protected ArrayList<Card> hand;
	protected ArrayList<Card> seenPeople;
	protected ArrayList<Card> seenWeapons;
	protected ArrayList<Card> seenRooms;

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
		switch (card.getCardType()) {
			case ROOM:
				seenRooms.add(card);
				break;
			case PERSON:
				seenPeople.add(card);
				break;
			case WEAPON:
				seenWeapons.add(card);
		}
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
		int countMatch = 0;
		Random rand = new Random();
		ArrayList<Card> matches = new ArrayList<Card>();
		for (Card c: this.hand) {
			if (c.equals(room) || c.equals(weapon) || c.equals(person)) {
				countMatch++;
				matches.add(c);
			}
		}
		//if player has one matching card, return that card
		if (countMatch == 1) {
			return matches.get(0);
		}
		//if multiple cards match, randomly return 1
		else if (countMatch > 1) {
			return matches.get(rand.nextInt(matches.size()));
		}
		//if player has no cards that match return null
		else {
			return null;
		}
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
