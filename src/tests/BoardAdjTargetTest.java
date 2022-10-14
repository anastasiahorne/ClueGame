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
			// set the file names to use my config files
			board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");		
			// Initialize will load config files 
			board.initialize();
		}

		// Ensure that player does not move around within room
		// These cells are LIGHT ORANGE on the planning spreadsheet
		@Test
		public void testAdjacenciesRooms()
		{
			// we want to test a couple of different rooms.
			// First, CoorsTek that only has a single door but a secret room
			Set<BoardCell> testList = board.getAdjList(21, 2);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCell(23, 0)));
			assertTrue(testList.contains(board.getCell(18, 4)));
			
			// now test Green Center which has two doors and no secret passage
			testList = board.getAdjList(12, 21);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCell(10, 17)));
			assertTrue(testList.contains(board.getCell(14, 17)));
			
			// Test Chauvenet, two doors and a secret passage
			testList = board.getAdjList(3, 2);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(0, 0)));
			assertTrue(testList.contains(board.getCell(5, 5)));
			assertTrue(testList.contains(board.getCell(7, 3)));
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
			assertEquals(4, testList.size());
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
		public void testTargetsInKitchen() {
			// test a roll of 1
			board.calcTargets(board.getCell(20, 19), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(17, 18)));
			assertTrue(targets.contains(board.getCell(2, 2)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(20, 19), 3);
			targets= board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCell(17, 20)));
			assertTrue(targets.contains(board.getCell(16, 19)));	
			assertTrue(targets.contains(board.getCell(17, 16)));
			assertTrue(targets.contains(board.getCell(2, 2)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(20, 19), 4);
			targets= board.getTargets();
			assertEquals(9, targets.size());
			assertTrue(targets.contains(board.getCell(16, 18)));
			assertTrue(targets.contains(board.getCell(18, 16)));	
			assertTrue(targets.contains(board.getCell(16, 16)));
			assertTrue(targets.contains(board.getCell(2, 2)));	
		}

		// Tests out of room center, 1, 3 and 4
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsAtDoor() {
			// test a roll of 1, at door
			board.calcTargets(board.getCell(8, 17), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(12, 20)));
			assertTrue(targets.contains(board.getCell(7, 17)));	
			assertTrue(targets.contains(board.getCell(8, 18)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(8, 17), 3);
			targets= board.getTargets();
			assertEquals(12, targets.size());
			assertTrue(targets.contains(board.getCell(12, 20)));
			assertTrue(targets.contains(board.getCell(3, 20)));
			assertTrue(targets.contains(board.getCell(7, 17)));	
			assertTrue(targets.contains(board.getCell(7, 19)));
			assertTrue(targets.contains(board.getCell(9, 15)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(8, 17), 4);
			targets= board.getTargets();
			assertEquals(15, targets.size());
			assertTrue(targets.contains(board.getCell(12, 20)));
			assertTrue(targets.contains(board.getCell(3, 20)));
			assertTrue(targets.contains(board.getCell(10, 15)));	
			assertTrue(targets.contains(board.getCell(6, 17)));
			assertTrue(targets.contains(board.getCell(5, 16)));	
		}

		@Test
		public void testTargetsInWalkway1() {
			// test a roll of 1
			board.calcTargets(board.getCell(11, 2), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(11, 1)));
			assertTrue(targets.contains(board.getCell(11, 3)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(11, 2), 3);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(14, 2)));
			assertTrue(targets.contains(board.getCell(8, 2)));
			assertTrue(targets.contains(board.getCell(11, 5)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(11, 2), 4);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(14, 2)));
			assertTrue(targets.contains(board.getCell(8, 2)));
			assertTrue(targets.contains(board.getCell(11, 6)));	
		}

		@Test
		public void testTargetsInWalkway2() {
			// test a roll of 1
			board.calcTargets(board.getCell(13, 7), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(13, 6)));
			assertTrue(targets.contains(board.getCell(12, 7)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(13, 7), 3);
			targets= board.getTargets();
			assertEquals(10, targets.size());
			assertTrue(targets.contains(board.getCell(15, 6)));
			assertTrue(targets.contains(board.getCell(14, 7)));
			assertTrue(targets.contains(board.getCell(11, 8)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(13, 7), 4);
			targets= board.getTargets();
			assertEquals(15, targets.size());
			assertTrue(targets.contains(board.getCell(14, 2)));
			assertTrue(targets.contains(board.getCell(15, 9)));
			assertTrue(targets.contains(board.getCell(11, 5)));	
		}

		@Test
		// test to make sure occupied locations do not cause problems
		public void testTargetsOccupied() {
			// test a roll of 4 blocked 2 down
			board.getCell(15, 7).setOccupied(true);
			board.calcTargets(board.getCell(13, 7), 4);
			board.getCell(15, 7).setOccupied(false);
			Set<BoardCell> targets = board.getTargets();
			assertEquals(13, targets.size());
			assertTrue(targets.contains(board.getCell(14, 2)));
			assertTrue(targets.contains(board.getCell(15, 9)));
			assertTrue(targets.contains(board.getCell(11, 5)));	
			assertFalse( targets.contains( board.getCell(15, 7))) ;
			assertFalse( targets.contains( board.getCell(17, 7))) ;
		
			// we want to make sure we can get into a room, even if flagged as occupied
			board.getCell(12, 20).setOccupied(true);
			board.getCell(8, 18).setOccupied(true);
			board.calcTargets(board.getCell(8, 17), 1);
			board.getCell(12, 20).setOccupied(false);
			board.getCell(8, 18).setOccupied(false);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(7, 17)));	
			assertTrue(targets.contains(board.getCell(8, 16)));	
			assertTrue(targets.contains(board.getCell(12, 20)));	
			
			// check leaving a room with a blocked doorway
			board.getCell(12, 15).setOccupied(true);
			board.calcTargets(board.getCell(12, 20), 3);
			board.getCell(12, 15).setOccupied(false);
			targets= board.getTargets();
			assertEquals(5, targets.size());
			assertTrue(targets.contains(board.getCell(6, 17)));
			assertTrue(targets.contains(board.getCell(8, 19)));	
			assertTrue(targets.contains(board.getCell(8, 15)));

		}
}
