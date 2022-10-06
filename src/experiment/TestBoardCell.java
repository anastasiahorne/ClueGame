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
	
	// Indicates that a cell is part of a room
	public void setRoom(boolean b) {
		
	}
	
	// Get whether or not a cell is part of a room
	public boolean isRoom() {
		return false;
	}
	
	// Setter for indicating a cell is occupied by another player
	public void setOccupied(boolean b) {
		
	}
	
	// Getter for indicating a cell is occupied by another player
	public boolean getOccupied() {
		return false;
	}

}
