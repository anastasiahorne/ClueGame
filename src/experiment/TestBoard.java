package experiment;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestBoard {
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
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i, j);
			}
		}
		// Create adjacency list for each cell in the grid
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (i != 0) {
					grid[i][j].addAdjacency(grid[i-1][j]);
				}
				if (i != ROWS - 1) {
					grid[i][j].addAdjacency(grid[i+1][j]);
				}
				if (j != 0) {
					grid[i][j].addAdjacency(grid[i][j-1]);
				}
				if (j != COLS - 1) {
					grid[i][j].addAdjacency(grid[i][j+1]);
				}
			}
		}
	}
	
	// Calculates legal targets for a move from startCell of length pathlength
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}
	
	public void findAllTargets(TestBoardCell thisCell, int numSteps) {
		for (TestBoardCell adjCell: thisCell.adjList) {
			if (!visited.contains(adjCell) && !adjCell.getIsRoom() && !adjCell.getIsOccupied()) {
				visited.add(adjCell);
				if (numSteps == 1) {
					targets.add(adjCell);
				}
				else {
					findAllTargets(adjCell, numSteps - 1);
				}
				visited.remove(adjCell);
			}
		}
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
