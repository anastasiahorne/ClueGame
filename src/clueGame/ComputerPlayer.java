package clueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import clueGame.Board;

public class ComputerPlayer extends Player {
	private Board board = Board.getInstance();
	private Random rand = new Random();

	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	public Solution createSuggestion() {
		// Room should be the current room
		Card room = new Card(board.getRoom(board.getCell(row, column)).getName(), CardType.ROOM);
		
		// Get a random player and weapon by determining which cards have not been seen
		ArrayList<Card> unseenWeapons = new ArrayList<Card>();
		ArrayList<Card> unseenPeople = new ArrayList<Card>();
		for (Card card : board.getWeaponCards()) {
			// If the card is not in the hand or has not been seen, add it to the unseen array
			if (!seenWeapons.contains(card) && !hand.contains(card)) {
				unseenWeapons.add(card);
			}
		}
		int index = rand.nextInt(unseenWeapons.size());
		Card weapon = unseenWeapons.get(index);
		
		for (Card card : board.getPlayerCards()) {
			// If the card is not in the hand or has not been seen, add it to the unseen array
			if (!seenPeople.contains(card) && !hand.contains(card)) {
				unseenPeople.add(card);
			}
		}
		index = rand.nextInt(unseenPeople.size());
		Card person = unseenPeople.get(index);
		
		return new Solution(person, weapon, room);
	}
	
	public BoardCell selectAMoveTarget(Set<BoardCell> targets) {
		return null;
	}
}
