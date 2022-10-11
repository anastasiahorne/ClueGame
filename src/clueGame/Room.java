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
	
	public String getName() {
		return name;
	}
	
	public char getSymbol() {
		return symbol;
	}

	public BoardCell getLabelCell() {
		return new BoardCell(0, 0);
	}

	public BoardCell getCenterCell() {
		// TODO Auto-generated method stub
		return new BoardCell(0, 0);
	}
}
