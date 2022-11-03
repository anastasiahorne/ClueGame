package clueGame;

import java.util.ArrayList;

abstract public class Player {
	private String name;
	private String color;
	private int row, column;
	private ArrayList<Card> hand;
	
	public Player(String name, String color, int row, int column) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		hand = new ArrayList<Card>();
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}

	abstract void updateHand(Card card);
}
