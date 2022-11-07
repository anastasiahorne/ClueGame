package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

class GameSolutionTest {
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
		board.deal();
	}

	@Test
	void testAccusationCheck() {
		// First player is the human player
		HumanPlayer player = (HumanPlayer) board.getPlayers().get(0);
		// Test accusation is equal to the solution (player, weapon, and room are correct)
		Card personSol = board.getSolution().getPerson();
		Card weaponSol = board.getSolution().getWeapon();
		Card roomSol = board.getSolution().getRoom();
		assertTrue(board.checkAccusation(personSol, weaponSol, roomSol));
		
		// Test an accusation with the wrong person
		Card personWrong = new Card("WrongPerson",CardType.PERSON);
		assertFalse(board.checkAccusation(personWrong, weaponSol, roomSol));
		
		// Test an accusation with the wrong weapon
		Card weaponWrong = new Card("WrongWeapon", CardType.WEAPON);
		assertFalse(board.checkAccusation(personSol, weaponWrong, roomSol));
		
		// Test an accusation with the wrong room
		Card roomWrong = new Card("WrongRoom",CardType.ROOM);
		assertFalse(board.checkAccusation(personSol, weaponSol, roomWrong));
	}
	
	@Test
	void testSuggestionDisproved() {
		HumanPlayer test1 = new HumanPlayer("test1", "red", 5, 5);
		Card test1Person = new Card ("test1",CardType.PERSON);
		Card test1Weapon = new Card("weap",CardType.WEAPON);
		Card test1Room = new Card("home", CardType.ROOM);
		test1.updateHand(test1Room);
		test1.updateHand(test1Weapon);
		test1.updateHand(test1Person);
		Card Person = new Card ("rando",CardType.PERSON);
		Card Weapon = new Card("Axe",CardType.WEAPON);
		Card Room = new Card("library", CardType.ROOM);
		// Test if a player only has one matching card it should be returned
		assertEquals(test1Person, test1.disproveSuggestion(test1Person, Weapon, Room));
		
		// Test if player has >1 matching cards, chosen card is chosen randomly
		int personCount = 0;
		int weaponCount = 0;
		int roomCount = 0;
		for (int i = 0;i < 30; ++i) {
			if (test1Person.equals(test1.disproveSuggestion(test1Person, test1Weapon, test1Room))) {
				personCount++;
			}
			else if (test1Weapon.equals(test1.disproveSuggestion(test1Person, test1Weapon, test1Room))) {
				weaponCount++;
			}
			else {
				roomCount++;
			}
		}
		assertTrue(personCount > 0);
		assertTrue(weaponCount > 0);
		assertTrue(roomCount > 0);
		
		// Test if player has no matching cards, null is returned
		assertEquals(null, test1.disproveSuggestion(Person, Weapon, Room));
	}
	
	@Test
	void testSuggestionHandled() {
		HumanPlayer test2 = new HumanPlayer("test2", "red", 5, 5);
		Card test2Person = new Card ("test2",CardType.PERSON);
		Card test2Weapon = new Card("test2",CardType.WEAPON);
		Card test2Room = new Card("test2", CardType.ROOM);
		// If no player can disprove, test that null is returned
		assertEquals(board.handleSuggestion(test2Person, test2Weapon, test2Room, test2), null);
		
		// Test that if only the suggesting player can disprove, null is returned
		test2.updateHand(test2Person);
		assertEquals(board.handleSuggestion(test2Person, test2Weapon, test2Room, test2), null);
		
		// Test that only the human can disprove returns a card that disproves the suggestion
		ComputerPlayer test3 = new ComputerPlayer("test3", "red", 5, 5);
		test3.updateHand(test2Weapon);
		assertEquals(board.handleSuggestion(test2Person, test2Weapon, test2Room, test3), test2Weapon);
		
		// Test that if more than one player can disprove, the correct player returns the answer
		ComputerPlayer test4 = new ComputerPlayer("test4", "red", 5, 5);
		test4.updateHand(test2Room);
		assertEquals(board.handleSuggestion(test2Person, test2Weapon, test2Room, test4), test2Weapon);
	}

}
