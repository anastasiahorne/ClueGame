package clueGame;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BooleanSupplier;

import experiment.TestBoardCell;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	private boolean isRoom;
	private boolean isOccupied;
	private boolean isDoorway;
	
	// Constructor that specifies the row and column and sets all variables to the default
	public BoardCell(int i, int j) {
		row = i;
		col = j;
		adjList = new HashSet<BoardCell>();
		roomLabel = false;
		roomCenter = false;
		doorDirection = DoorDirection.NONE;
		isRoom = false;
		isOccupied = false;
		isDoorway = false;
	}
	
	// Add cell to the adjacency list for the cell
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	
	// Returns the adjacency list for the cell
	public Set<BoardCell> getAdjList() {
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

	public boolean isDoorway() {
		return isDoorway;
	}
	
	public void setIsDoorway(boolean isDoorway) {
		this.isDoorway = isDoorway;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

	public boolean isLabel() {
		return roomLabel;
	}
	
	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}

	public boolean isRoomCenter() {
		return roomCenter;
	}
	
	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}

	public char getSecretPassage() {
		return secretPassage;
	}
	
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	public char getInitial() {
		return initial;
	}
	
	public void setInitial(char initial) {
		this.initial = initial;
	}
}
