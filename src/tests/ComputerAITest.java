package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Room;
import clueGame.Solution;

class ComputerAITest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use our config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	@Test
	void testTargetsSelected() {
		// Get second player which will be a ComputerPlayer since the first player is the HumanPlayer
		ComputerPlayer computer = (ComputerPlayer) board.getPlayers().get(1);
		computer.setLocation(7, 8);
		int row = computer.getRow();
		int col = computer.getColumn();
		BoardCell cell = board.getCell(row, col);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		BoardCell target = computer.selectAMoveTarget(targets);
		
		// Test if room in list that has not been seen it is selected
		assertEquals(target, board.getCell(3, 9));
		
		// Test if no rooms in list, select randomly
		computer.setLocation(16, 13);
		row = computer.getRow();
		col = computer.getColumn();
		cell = board.getCell(row, col);
		board.calcTargets(cell, 1);
		targets = board.getTargets();
		int countUp = 0;
		int countDown = 0;
		int countLeft = 0;
		int countRight = 0;
		for (int i = 0; i < 100; i++) {
			target = computer.selectAMoveTarget(targets);
			if (target.equals(board.getCell(row - 1, col))) {
				countUp++;
			}
			else if (target.equals(board.getCell(row + 1, col))) {
				countDown++;
			}
			else if (target.equals(board.getCell(row, col - 1))) {
				countLeft++;
			}
			else if (target.equals(board.getCell(row, col + 1))) {
				countRight++;
			}
		}
		assertTrue(countUp > 10);
		assertTrue(countDown > 10);
		assertTrue(countLeft > 10);
		assertTrue(countRight > 10);
		
		// Test if room in list that has been seen, select randomly
		computer.setLocation(14, 17);
		Card c = new Card(board.getRoom(board.getCell(12, 21)).getName(), CardType.ROOM);
		computer.updateSeenRooms(c);
		row = computer.getRow();
		col = computer.getColumn();
		cell = board.getCell(row, col);
		board.calcTargets(cell, 1);
		targets = board.getTargets();
		int countRoom = 0;
		countDown = 0;
		countLeft = 0;
		for (int i = 0; i < 100; i++) {
			target = computer.selectAMoveTarget(targets);
			if (target.equals(board.getCell(row + 1, col))) {
				countDown++;
			}
			else if (target.equals(board.getCell(row, col - 1))) {
				countLeft++;
			}
			else if (target.equals(board.getCell(12, 21))) {
				countRoom++;
			}
		}
		// FIXME: Room is chosen every time
		assertEquals(10, countRoom);
		assertTrue(countDown > 10);
		assertTrue(countLeft > 10);
		assertTrue(countRoom > 10);
	}
	
	@Test
	void testSuggestionCreation() {
		// Get second player which will be a ComputerPlayer since the first player is the HumanPlayer
		ComputerPlayer computer = new ComputerPlayer("test", "test", 12, 21);
		// Create a suggestion
		Solution suggestion = computer.createSuggestion();
		// Test that the room matches the current location
		int row = computer.getRow();
		int col = computer.getColumn();
		BoardCell cell = board.getCell(row, col);
		Room room = board.getRoom(cell);
		assertEquals(room.getName(), suggestion.getRoom().getCardName());
		
		// Test if multiple weapons or people not seen one is chosen randomly
		Random rand = new Random();
		ArrayList<Card> unseenWeapons = new ArrayList<Card>();
		for (Card card : board.getWeaponCards()) {
			if (!computer.getSeenWeapons().contains(card)) {
				unseenWeapons.add(card);
			}
		}
		int oddCards = 0;
		int evenCards = 0;
		for (int i = 0; i < 100; i++) {
			suggestion = computer.createSuggestion();
			int index = rand.nextInt(unseenWeapons.size());
			Card c = unseenWeapons.get(index);
			if (index % 2 == 0) {
				evenCards++;
			}
			else {
				oddCards++;
			}
		}
		assertTrue(oddCards > 10);
		assertTrue(evenCards > 10);
		
		// Test if only one weapon or person not seen it is selected
		for (int i = 0; i < board.getWeaponCards().size() -1; i++) {
			computer.updateSeenWeapons(board.getWeaponCards().get(i));
		}
		//after we update our seenHand, we must also update our unseenHand/suggestion
		suggestion=computer.createSuggestion();
		assertEquals(board.getWeaponCards().get(board.getWeaponCards().size() - 1), suggestion.getWeapon());
	}

}
