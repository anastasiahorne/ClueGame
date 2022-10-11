package clueGame;

import java.util.Set;
import java.util.function.BooleanSupplier;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassge;
	private Set<BoardCell> adjList;
	private boolean isRoom;
	private boolean isOccupied;
	
	public BoardCell(int i, int j) {
		// TODO Auto-generated constructor stub
	}

	public void addAdj(BoardCell adj) {
		adjList.add(adj);
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

		
}
