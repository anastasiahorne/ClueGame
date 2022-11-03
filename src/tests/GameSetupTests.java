package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Player;

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
	@Test
	void testPlayersInititialized() {
		int countHuman = 0;
		int countComputer = 0;
		for (Player p: board.getPlayers()) {
			Class c = p.getClass();
			if (c.getName().equals("HumanPlayer")) {
				countHuman++;
			}
			else {
				countComputer++;
			}
		}
		assertEquals(5, countComputer);
		assertEquals(1, countHuman);
	}
	
	// Deck of all cards is created (composed of rooms, weapons, and people)
	@Test
	void testDeck() {
		// test number of rooms, weapons, and people in deck
	}
	
	// The solution to the game is dealt
	@Test
	void testSolution() {
		// Test that solution variables are not empty
		// Test number of rooms, weapons, and people left in the deck
	}
	
	// The other cards are dealt to the players.
	@Test
	void testCardsDealt() {
		// Test that every card in the deck was given to the players roughly equally
	}
	
	
}
