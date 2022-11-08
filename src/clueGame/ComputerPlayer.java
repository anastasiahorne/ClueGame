package clueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import clueGame.Board;

public class ComputerPlayer extends Player {
	private Board board = Board.getInstance();
	private Random rand = new Random();

	// Constructor
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	// Create a suggestion based on seen cards in the current room
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
		
		return new Solution(room, person, weapon);
	}
	
	// Selects which BoardCell the player should move to
	public BoardCell selectAMoveTarget(Set<BoardCell> targets) {
		for (BoardCell target: targets) {
			if (target.isRoomCenter()) {
				String roomName = board.getRoom(target).getName();
				boolean seen = false;
				for (Card room : getSeenRooms()) {
					if (roomName.equals(room.getCardName())) {
						seen = true;
					}
				}
				for (Card room : getHand()) {
					if (roomName.equals(room.getCardName())) {
						seen = true;
					}
				}
				// If neither hand nor seenRooms contains the current room, pick that target
				if (seen == false) {
					return target;
				}
			}
		}
		// If none of the targets are an unseen room, choose randomly
		ArrayList<BoardCell> targetList = new ArrayList<BoardCell>();
		for (BoardCell target : targets) {
			targetList.add(target);
		}
		return targetList.get(rand.nextInt(targetList.size()));
	}
}
