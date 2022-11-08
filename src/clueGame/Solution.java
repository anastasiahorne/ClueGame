package clueGame;

public class Solution {
	private Card room;
	private Card person;
	private Card weapon;
	
	// Constructor
	public Solution(Card r, Card p, Card w) {
		room = r;
		person = p;
		weapon = w;
	}
	
	// Getter for room
	public Card getRoom() {
		return room;
	}
	
	// Getter for person
	public Card getPerson() {
		return person;
	}
	
	// Getter for weapon
	public Card getWeapon() {
		return weapon;
	}
	
	// Setter for room
	public void setRoom(Card room) {
		this.room = room;
	}
	
	// Setter for person
	public void setPerson(Card person) {
		this.person = person;
	}
	
	// Setter for weapon
	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}
}
