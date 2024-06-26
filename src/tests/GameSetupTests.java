package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
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
			if (p instanceof HumanPlayer) {
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
				break;
			case PERSON:
				countPeople++;
				break;
			}
		}
		assertEquals(9, countRooms);
		assertEquals(6, countWeapons);
		assertEquals(6, countPeople);
	}
	
	// Test that every card in the deck was given to the players roughly equally
	// Every card was given out once because they are stored in a set which does not allow for duplicates
	@Test
	void testCardsDealt() {
		int deckSize = board.getDeck().size();
		board.deal();
		int numPlayers = board.getPlayers().size();
		int cardsPerPerson = deckSize / numPlayers;
		for (Player p: board.getPlayers()) {
			assertEquals(cardsPerPerson, p.getHand().size(), 1);
		}
		
		//solution tests, make sure we have the right solution
		Solution testSol=board.getSolution();
		Card room1=testSol.getRoom();
		Card person1=testSol.getPerson();
		Card weapon1=testSol.getWeapon();
		// Test the types/classes in Solution to make sure you have one room, one weapon, and one person
		assertEquals(room1.getCardType(),CardType.ROOM);
		assertEquals(person1.getCardType(),CardType.PERSON);
		assertEquals(weapon1.getCardType(),CardType.WEAPON);
		
		//once players and solutions have their cards, our deck should be empty
		assertEquals(0, board.getDeck().size());
		
		
	}
	//test to make sure the cards equals method is functioning properly
	@Test
	void testCardsEquals() {
		//test of cards have same type but different names
		Card A= new Card("ABC",CardType.WEAPON);
		Card B= new Card("BCA", CardType.WEAPON);
		assertFalse(A.equals(B));
		//test cards that have same name but different type
		Card C= new Card("ABC",CardType.WEAPON);
		Card D= new Card("ABC", CardType.ROOM);
		assertFalse(C.equals(D));
		//test cards that have same name and type
		Card E= new Card("ABC",CardType.WEAPON);
		Card F= new Card("ABC", CardType.WEAPON);
		assertTrue(E.equals(F));
	}
	
	
}
