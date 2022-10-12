package clueGame;

public class Room {

	private String name;
	private char symbol;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public Room(String name) {
		this.name = name;
	}
	
	public Room(String name, char symbol) {
		this.name = name;
		this.symbol = symbol;
	}
	
	// Returns name of the room
	public String getName() {
		return name;
	}
	
	// Returns symbol associated with the room
	public char getSymbol() {
		return symbol;
	}
	
	// Return labelCell
	public BoardCell getLabelCell() {
		return labelCell;
	}
	
	// Setter for private instance variable labelCell
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
	
	// Return centerCell
	public BoardCell getCenterCell() {
		return centerCell;
	}

	// Setter for private instance variable centerCell
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}
}
