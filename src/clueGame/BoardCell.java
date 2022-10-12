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
	
	public void setIsDoorway(boolean isDoorway) {
		this.isDoorway = isDoorway;
	}
	
	public boolean getIsDoorway() {
		return isDoorway;
	}
	
	public boolean isRoomLabel() {
		return roomLabel;
	}

	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}

	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}
	// Indicates that a cell is part of a room
	public void setIsRoom(boolean room) {
		isRoom = room;
	}
		
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

	public void setInitial(char initial) {
		this.initial = initial;
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
		return false;
	}

	public DoorDirection getDoorDirection() {
		return DoorDirection.NONE;
	}

	public boolean isLabel() {
		return false;
	}

	public boolean isRoomCenter() {
		return false;
	}

	public char getSecretPassage() {
		return 0;
	}

	public char getInitial() {
		return initial;
	}	
}
