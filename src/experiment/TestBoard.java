package experiment;

import java.util.Map;
import java.util.Set;

public class TestBoard {
	private Map<TestBoardCell, Set<TestBoardCell>> adjMtx;
	private Set<TestBoardCell> targets;
	
	// Constructor that sets up the board
	public TestBoard() {
		
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
