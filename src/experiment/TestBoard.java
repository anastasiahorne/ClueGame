package experiment;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestBoard {
	private Map<TestBoardCell, Set<TestBoardCell>> adjMtx;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	private TestBoardCell[][] grid;
	private final static int ROWS = 4;
	private final static int COLS = 4;
	
	// Constructor that sets up the board
	public TestBoard() {
		grid = new TestBoardCell[ROWS][COLS];
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
	}
	
	// Calculates legal targets for a move from startCell of length pathlength
	public void calcTargets(TestBoardCell startCell, int pathLength) {
	
	}
	
	// Gets the targets last created by calcTargets()
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	// Returns the cell from the board at row, col
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
}
