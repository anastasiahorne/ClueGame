package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;

class GameSetupTests {
	private static Board board;
	@BeforeEach
	void setUp() {
		board=Board.getInstance();

	}
	// Add BeforeAll
	
	// Test that all people are loaded in
	@Test
	void testPlayersLoaded() {
		fail("Not yet implemented");
	}
	
	// Proper Human or Computer player is initialized based on people data
	// Deck of all cards is created (composed of rooms, weapons, and people)
	// The solution to the game is dealt
	// The other cards are dealt to the players.

}
