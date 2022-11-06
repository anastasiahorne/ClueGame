package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
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
		// Test if no rooms in list, select randomly
		
		// Test if room in list that has not been seen it is selected
		
		// Test if room in list that has been seen, select randomly
		
	}
	
	@Test
	void testSuggestionCreation() {
		// Get second player which will be a ComputerPlayer since the first player is the HumanPlayer
		ComputerPlayer computer = (ComputerPlayer) board.getPlayers().get(1);
		// Set the player's location to be in a room
		computer.setLocation(12, 21);
		// Create a suggestion
		Solution suggestion = computer.createSuggestion();
		// Test that the room matches the current location
		int row = computer.getRow();
		int col = computer.getColumn();
		BoardCell cell = board.getCell(row, col);
		Room room = board.getRoom(cell);
		assertEquals(room.getName(), suggestion.getRoom().getCardName());
		// Test if only one weapon or person not seen it is selected
		
		// Test if multiple weapons or people not seen one is chosen randomly
		
	}

}
