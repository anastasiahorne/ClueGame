package experiment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

class TestBoardCell {
	int row;
	int column;
	Set<TestBoardCell> adjList;
	
	// Default constructor
	public TestBoardCell(int r, int c) {
		row = r;
		column = c;
	}
	
	// Setter to add a cell to the adjacency list
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
