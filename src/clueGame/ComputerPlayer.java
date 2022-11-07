package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	public Solution createSuggestion() {
		Card card1 = new Card("", CardType.PERSON);
		Card card2 = new Card("", CardType.WEAPON);
		Card card3 = new Card("", CardType.ROOM);
		return new Solution(card1, card2, card3);
	}
	
	public BoardCell selectAMoveTarget(Set<BoardCell> targets) {
		return null;
	}
}
