package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
		// then do all the tests. 
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

		// Ensure that player does not move around within room,
		// These cells are LIGHT ORANGE on the planning spreadsheet
		@Test
		public void testAdjacenciesRooms()
		{
			// we want to test a couple of different rooms.
			// First, CTLM that only has a two doors and a secret room
			Set<BoardCell> testList = board.getAdjList(21, 2);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(21, 5)));
			assertTrue(testList.contains(board.getCell(18, 4)));
			assertTrue(testList.contains(board.getCell(3, 22)));
			
			// now test Green Center which has two doors and no secret passage
			testList = board.getAdjList(12, 21);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(10, 17)));
			assertTrue(testList.contains(board.getCell(14, 17)));
			assertTrue(testList.contains(board.getCell(16, 21)));
			
			// Test Chauvenet, two doors and a secret passage
			testList = board.getAdjList(3, 2);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(21, 22)));
			assertTrue(testList.contains(board.getCell(5, 5)));
			assertTrue(testList.contains(board.getCell(7, 3)));
			
			// Test room cell that is not the center (should be empty)
			testList = board.getAdjList(10, 5);
			assertEquals(0, testList.size());
		}

		
		// Ensure door locations include their rooms and also additional walkways
		// These cells are LIGHT ORANGE on the planning spreadsheet
		@Test
		public void testAdjacencyDoor()
		{
			Set<BoardCell> testList = board.getAdjList(6, 7);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(3, 9)));
			assertTrue(testList.contains(board.getCell(6, 6)));
			assertTrue(testList.contains(board.getCell(7, 7)));

			testList = board.getAdjList(18, 4);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(17, 4)));
			assertTrue(testList.contains(board.getCell(18, 5)));
			assertTrue(testList.contains(board.getCell(21, 2)));
			
			testList = board.getAdjList(18, 20);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(17, 20)));
			assertTrue(testList.contains(board.getCell(18, 19)));
			assertTrue(testList.contains(board.getCell(21, 22)));
		}
		
		// Test a variety of walkway scenarios
		// These tests are Dark Orange on the planning spreadsheet
		@Test
		public void testAdjacencyWalkways()
		{
			// Test on bottom edge of board, just one walkway piece
			Set<BoardCell> testList = board.getAdjList(24, 15);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCell(23, 15)));
			
			// Test near a door but not adjacent
			testList = board.getAdjList(18, 6);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(17, 6)));
			assertTrue(testList.contains(board.getCell(18, 5)));
			assertTrue(testList.contains(board.getCell(18, 7)));
			assertTrue(testList.contains(board.getCell(19, 6)));

			// Test adjacent to walkways
			testList = board.getAdjList(11, 7);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(11, 6)));
			assertTrue(testList.contains(board.getCell(10, 7)));
			assertTrue(testList.contains(board.getCell(11, 8)));
			assertTrue(testList.contains(board.getCell(12, 7)));

			// Test next to closet
			testList = board.getAdjList(15,14);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(15, 13)));
			assertTrue(testList.contains(board.getCell(15, 15)));
			assertTrue(testList.contains(board.getCell(16, 14)));
			
			// Test next to room without doorway
			testList = board.getAdjList(7, 22);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(7, 21)));
			assertTrue(testList.contains(board.getCell(7, 23)));
			assertTrue(testList.contains(board.getCell(8, 22)));
		
		}
		
		
		// Tests out of room center, 1, 3 and 4
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsInCoolbaugh() {
			// test a roll of 1
			board.calcTargets(board.getCell(11, 3), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(11, 6)));
			assertTrue(targets.contains(board.getCell(12, 6)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(11, 3), 3);
			targets= board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCell(10, 7)));
			assertTrue(targets.contains(board.getCell(11, 8)));	
			assertTrue(targets.contains(board.getCell(12, 7)));
			assertTrue(targets.contains(board.getCell(12, 8)));
			assertTrue(targets.contains(board.getCell(13, 7)));
			assertTrue(targets.contains(board.getCell(11, 7)));
			
			// test a roll of 4
			board.calcTargets(board.getCell(11, 3), 4);
			targets= board.getTargets();
			assertEquals(10, targets.size());
			assertTrue(targets.contains(board.getCell(9, 7)));
			assertTrue(targets.contains(board.getCell(10, 8)));	
			assertTrue(targets.contains(board.getCell(11, 9)));
			assertTrue(targets.contains(board.getCell(12, 8)));
			assertTrue(targets.contains(board.getCell(12, 9)));
			assertTrue(targets.contains(board.getCell(13, 8)));
			assertTrue(targets.contains(board.getCell(14, 7)));
		}
		
		@Test
		public void testTargetsInMarquez() {
			// test a roll of 1
			board.calcTargets(board.getCell(3, 22), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(6, 20)));
			assertTrue(targets.contains(board.getCell(21, 2)));
			assertTrue(targets.contains(board.getCell(3, 19)));
			
			
			// test a roll of 3
			board.calcTargets(board.getCell(3, 22), 3);
			targets= board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCell(21, 2)));
			assertTrue(targets.contains(board.getCell(5, 19)));	
			assertTrue(targets.contains(board.getCell(7, 21)));
			assertTrue(targets.contains(board.getCell(7, 19)));	
			assertTrue(targets.contains(board.getCell(1, 19)));
			assertTrue(targets.contains(board.getCell(5, 19)));
			
			// test a roll of 4
			board.calcTargets(board.getCell(3, 22), 4);
			targets= board.getTargets();
			assertEquals(9, targets.size());
			assertTrue(targets.contains(board.getCell(21, 2)));
			assertTrue(targets.contains(board.getCell(7, 22)));
			assertTrue(targets.contains(board.getCell(8, 21)));
			assertTrue(targets.contains(board.getCell(4, 19)));
			assertTrue(targets.contains(board.getCell(7, 18)));
			assertTrue(targets.contains(board.getCell(8, 19)));
			assertTrue(targets.contains(board.getCell(7, 20)));
			assertTrue(targets.contains(board.getCell(0, 19)));
			assertTrue(targets.contains(board.getCell(6, 19)));
		}

		// Tests out of room center, 1, 3 and 4
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsAtDoor() {
			// test a roll of 1, at door
			board.calcTargets(board.getCell(6, 16), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(3, 16)));
			assertTrue(targets.contains(board.getCell(7, 16)));		
			
			// test a roll of 3
			board.calcTargets(board.getCell(6, 16), 3);
			targets= board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCell(3, 16)));
			assertTrue(targets.contains(board.getCell(7, 14)));
			assertTrue(targets.contains(board.getCell(8, 15)));	
			assertTrue(targets.contains(board.getCell(9, 16)));
			assertTrue(targets.contains(board.getCell(7, 18)));
			assertTrue(targets.contains(board.getCell(8, 17)));
			
			// test a roll of 4
			board.calcTargets(board.getCell(6, 16), 4);
			targets= board.getTargets();
			assertEquals(11, targets.size());
			assertTrue(targets.contains(board.getCell(3, 16)));
			assertTrue(targets.contains(board.getCell(7, 13)));
			assertTrue(targets.contains(board.getCell(8, 14)));	
			assertTrue(targets.contains(board.getCell(9, 15)));
			assertTrue(targets.contains(board.getCell(8, 16)));
			assertTrue(targets.contains(board.getCell(7, 19)));
			assertTrue(targets.contains(board.getCell(8, 18)));
			assertTrue(targets.contains(board.getCell(9, 17)));
			assertTrue(targets.contains(board.getCell(10, 16)));
			assertTrue(targets.contains(board.getCell(7, 17)));
			assertTrue(targets.contains(board.getCell(7, 15)));
		}

		@Test
		public void testTargetsInWalkway1() {
			// test a roll of 1
			board.calcTargets(board.getCell(6, 12), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(5, 12)));
			assertTrue(targets.contains(board.getCell(6, 11)));
			assertTrue(targets.contains(board.getCell(6, 13)));
			assertTrue(targets.contains(board.getCell(7, 12)));
			
			// test a roll of 3
			board.calcTargets(board.getCell(6, 12), 3);
			targets= board.getTargets();
			assertEquals(11, targets.size());
			assertTrue(targets.contains(board.getCell(3, 9)));
			assertTrue(targets.contains(board.getCell(3, 12)));
			assertTrue(targets.contains(board.getCell(7, 10)));
			assertTrue(targets.contains(board.getCell(8, 11)));
			assertTrue(targets.contains(board.getCell(7, 12)));
			assertTrue(targets.contains(board.getCell(7, 14)));
			assertTrue(targets.contains(board.getCell(5, 12)));
			assertTrue(targets.contains(board.getCell(4, 13)));
			assertTrue(targets.contains(board.getCell(8, 13)));
			
			// test a roll of 4
			board.calcTargets(board.getCell(6, 12), 4);
			targets= board.getTargets();
			assertEquals(12, targets.size());
			assertTrue(targets.contains(board.getCell(3, 9)));
			assertTrue(targets.contains(board.getCell(3, 16)));
			assertTrue(targets.contains(board.getCell(2, 12)));
			assertTrue(targets.contains(board.getCell(5, 13)));
			assertTrue(targets.contains(board.getCell(7, 9)));
			assertTrue(targets.contains(board.getCell(8, 10)));
			assertTrue(targets.contains(board.getCell(8, 12)));
			assertTrue(targets.contains(board.getCell(8, 14)));
			assertTrue(targets.contains(board.getCell(4, 12)));
			assertTrue(targets.contains(board.getCell(7, 11)));
			assertTrue(targets.contains(board.getCell(7, 13)));
			assertTrue(targets.contains(board.getCell(7, 15)));
		}

		@Test
		public void testTargetsInWalkway2() {
			// test a roll of 1
			board.calcTargets(board.getCell(17, 12), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(17, 11)));
			assertTrue(targets.contains(board.getCell(17, 13)));
			assertTrue(targets.contains(board.getCell(16, 12)));
			
			// test a roll of 3
			board.calcTargets(board.getCell(17, 12), 3);
			targets= board.getTargets();
			assertEquals(9, targets.size());
			assertTrue(targets.contains(board.getCell(15, 13)));
			assertTrue(targets.contains(board.getCell(15, 11)));
			assertTrue(targets.contains(board.getCell(16, 10)));
			assertTrue(targets.contains(board.getCell(16, 12)));
			assertTrue(targets.contains(board.getCell(16, 14)));
			assertTrue(targets.contains(board.getCell(17, 15)));
			assertTrue(targets.contains(board.getCell(17, 9)));
			assertTrue(targets.contains(board.getCell(17, 11)));
			assertTrue(targets.contains(board.getCell(17, 13)));
			
			// test a roll of 4
			board.calcTargets(board.getCell(17, 12), 4);
			targets= board.getTargets();
			assertEquals(12, targets.size());
			assertTrue(targets.contains(board.getCell(16, 13)));
			assertTrue(targets.contains(board.getCell(16, 11)));
			assertTrue(targets.contains(board.getCell(16, 9)));
			assertTrue(targets.contains(board.getCell(16, 15)));
			assertTrue(targets.contains(board.getCell(15, 10)));
			assertTrue(targets.contains(board.getCell(15, 12)));
			assertTrue(targets.contains(board.getCell(15, 14)));
			assertTrue(targets.contains(board.getCell(17, 14)));
			assertTrue(targets.contains(board.getCell(17, 10)));
			assertTrue(targets.contains(board.getCell(18, 9)));
			assertTrue(targets.contains(board.getCell(17, 16)));
			assertTrue(targets.contains(board.getCell(17, 8)));
		}

		@Test
		// test to make sure occupied locations do not cause problems
		public void testTargetsOccupied() {
			// test a roll of 4 blocked 2 down
			board.getCell(19, 17).setOccupied(true);
			board.calcTargets(board.getCell(18, 17), 4);
			board.getCell(19, 17).setOccupied(false);
			Set<BoardCell> targets = board.getTargets();
			assertEquals(15, targets.size());
			assertTrue(targets.contains(board.getCell(21, 11)));
			assertTrue(targets.contains(board.getCell(21, 16)));
			assertTrue(targets.contains(board.getCell(21, 18)));	
			assertFalse(targets.contains(board.getCell(18, 19)));
			assertFalse(targets.contains(board.getCell(17, 16)));
			assertTrue(targets.contains(board.getCell(17, 18)));	
			assertFalse(targets.contains(board.getCell(17, 14)));
			assertFalse(targets.contains(board.getCell(16, 15)));
			assertTrue(targets.contains(board.getCell(17, 20)));	
			assertFalse(targets.contains(board.getCell(14, 17)));
			assertFalse(targets.contains(board.getCell(16, 19)));
			assertFalse(targets.contains(board.getCell(19, 16)));
			assertFalse(targets.contains(board.getCell(19, 18)));
			assertFalse(targets.contains(board.getCell(17, 16)));
			assertFalse(targets.contains(board.getCell(17, 18)));
		
			// we want to make sure we can get into a room, even if flagged as occupied
			board.getCell(21, 11).setOccupied(true);
			board.calcTargets(board.getCell(18, 9), 1);
			board.getCell(21, 11).setOccupied(false);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(18, 8)));	
			assertTrue(targets.contains(board.getCell(17, 9)));	
			assertTrue(targets.contains(board.getCell(21, 11)));	
			
			// check leaving a room with a blocked doorway
			board.getCell(18, 9).setOccupied(true);
			board.calcTargets(board.getCell(21, 11), 3);
			board.getCell(18, 9).setOccupied(false);
			targets= board.getTargets();
			assertEquals(5, targets.size());
			assertTrue(targets.contains(board.getCell(17, 8)));
			assertTrue(targets.contains(board.getCell(16, 9)));	
			assertTrue(targets.contains(board.getCell(17, 10)));
			assertTrue(targets.contains(board.getCell(19, 8)));	
			assertTrue(targets.contains(board.getCell(18, 7)));
		}
}
