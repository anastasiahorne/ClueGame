package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;

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
	
	// Test that all weapons were loaded in
	@Test
	void testWeaponsLoaded() {
		assertEquals(6, board.getWeapons().size());
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
		// test deck is equal to the number of rooms plus the number of people plus the number of weapons
		assertEquals(board.getDeck().size(), 21);
		// test number of rooms, weapons, and people in deck
		int countRooms = 0;
		int countWeapons = 0;
		int countPeople = 0;
		for (Card c : board.getDeck()) {
			CardType type = c.getCardType();
			switch (type) {
			case ROOM:
				countRooms++;
				break;
			case WEAPON:
				countWeapons++;
			case PERSON:
				countPeople++;
			}
		}
		assertEquals(countRooms, 9);
		assertEquals(countWeapons, 6);
		assertEquals(countPeople, 6);
	}
	
	// The solution to the game is dealt
	@Test
	void testSolution() {
		Solution testSol=board.getSolution();
		// Test that solution variables are not empty
		
		
		// Test the types/classes in Solution to make sure you have one room, one weapon, and one person
		// Test number of rooms, weapons, and people left in the deck
		
	}
	
	// Test that every card in the deck was given to the players roughly equally
	// Every card was given out once because they are stored in a set which does not allow for duplicates
	@Test
	void testCardsDealt() {
		
	}
	
	
}
