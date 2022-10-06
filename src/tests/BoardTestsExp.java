package tests;

import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import experiment.TestBoard;
import experiment.TestBoardCell;

class BoardTestsExp {
	TestBoard board;
	
	// @BeforeEach method to set up
	@BeforeEach
	public void setUp() {
		// board should create adjacency list
		board = new TestBoard();
	}
	
	/*
	 * Test adjacencies for several different locations
	 * Test centers and edges
	 */
	@Test
	public void testAdjacencyTopLeft() {
		// Test top left cell adjacency list
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacencyBottomRight() {
		// Test bottom right cell adjacency list
		TestBoardCell cell = board.getCell(3, 3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacencyRightEdge() {
		// Test right edge cell adjacency list
		TestBoardCell cell = board.getCell(1, 3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 3)));
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacencyLeftEdge() {
		// Test left edge cell adjacency list
		TestBoardCell cell = board.getCell(2, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(2, 1)));
		Assert.assertTrue(testList.contains(board.getCell(3, 0)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacencyCenter() {
		// Test left edge cell adjacency list
		TestBoardCell cell = board.getCell(2, 2);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertTrue(testList.contains(board.getCell(2, 1)));
		Assert.assertEquals(2, testList.size());
	}
	
	/*
	 * Test targets with several rolls and start locations
	 */
	@Test
	public void testTargetsNormal3() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6,  targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	@Test
	public void testTargetsNormal1() {
		TestBoardCell cell = board.getCell(2, 1);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(4,  targets.size());
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
	}
	
	@Test
	public void testTargetsNormal4() {
		TestBoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 4);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(7,  targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 0)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}
	/*
	 * Test targets with occupied cell
	 */
	@Test
	public void testTargetsMixed() {
		// set up occupied cells
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3,  targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}
	@Test
	public void testTargetsMixed4() {
		TestBoardCell cell = board.getCell(1, 1);
		board.getCell(1, 0).setOccupied(true);
		board.getCell(2, 0).setOccupied(true);
		board.calcTargets(cell, 4);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6,  targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 0)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}

	
	
}
