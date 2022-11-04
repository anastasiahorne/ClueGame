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
	
	public Card getRoom() {
		return room;
	}
	public Card getPerson() {
		return person;
	}
	public Card getWeapon() {
		return weapon;
	}
	public void setRoom(Card room) {
		this.room = room;
	}
	public void setPerson(Card person) {
		this.person = person;
	}
	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}
	
	
}
