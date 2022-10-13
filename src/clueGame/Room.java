package clueGame;

public class Room {
	private String name;
	private char symbol;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	// Constructor that takes only the name of the room as an argument
	public Room(String name) {
		this.name = name;
	}
	
	// Constructor that takes name and the symbol as arguments
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
