package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;

class GameSetupTests {
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use our config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	
	// Test that all people are loaded in
	@Test
	void testPlayersLoaded() {
		assertEquals(6, board.getPlayers().size());
	}
	
	// Proper Human or Computer player is initialized based on people data
	// Deck of all cards is created (composed of rooms, weapons, and people)
	// The solution to the game is dealt
	// The other cards are dealt to the players.

}
