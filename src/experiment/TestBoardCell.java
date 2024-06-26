package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	int row;
	int column;
	Set<TestBoardCell> adjList;
	private boolean isRoom;
	private boolean isOccupied;
	
	// Default constructor
	public TestBoardCell(int r, int c) {
		row = r;
		column = c;
		adjList = new HashSet<TestBoardCell>();
		isRoom = false;
		isOccupied = false;
	}
	
	// Add a cell to the adjacency list
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	// Returns the adjacency list for the cell
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	// Indicates that a cell is part of a room
	public void setIsRoom(boolean room) {
		isRoom = room;
	}
	
	// Get whether or not a cell is part of a room
	public boolean getIsRoom() {
		return isRoom;
	}
	
	// Setter for indicating a cell is occupied by another player
	public void setIsOccupied(boolean occupied) {
		isOccupied = occupied;
	}
	
	// Getter for indicating a cell is occupied by another player
	public boolean getIsOccupied() {
		return isOccupied;
	}

}

