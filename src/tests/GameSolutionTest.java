package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
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
		assertTrue(board.checkAccusation(personSol,weaponSol,roomSol));
		// Test an accusation with the wrong person
		Card personWrong=new Card("WrongPerson",CardType.PERSON);
		assertFalse(board.checkAccusation(personWrong,weaponSol,roomSol));
		// Test an accusation with the wrong weapon
		
		// Test an accusation with the wrong room
		
	}
	
	@Test
	void testSuggestionDisproved() {
		// Test if a player only has one matching card it should be returned
		
		// Test if player has >1 matching cards, chosen card is chosen randomly
		
		// Test if player has no matching cards, null is returned
		
	}
	
	@Test
	void testSuggestionHandled() {
		// If no player can disprove, test that null is returned
		
		// Test that if only the suggesting player can disprove, null is returned
		
		// Test that only the human can disprove returns a card that disproves the suggestion
		
		// Test that if more than one player can disprove, the correct player returns the answer
		
	}

}
