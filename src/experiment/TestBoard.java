package experiment;

import java.util.Map;
import java.util.Set;

public class TestBoard {
	private Map<TestBoardCell, Set<TestBoardCell>> adjMtx;
	private Set<TestBoardCell> targets;
	
	// Constructor that sets up the board
	public TestBoard() {
<<<<<<< HEAD
		
=======
		grid = new TestBoardCell[ROWS][COLS];
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i, j);
			}
		}
>>>>>>> 970f81b59e3ba2944fe7629111595ff02b38c11f
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
		return null;
	}
}
