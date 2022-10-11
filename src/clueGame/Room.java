package clueGame;

public class Room {

	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public Room(String name) {
		this.name = name;
	}
	
	public String getName() {
		return "";
	}

	public BoardCell getLabelCell() {
		return new BoardCell(0, 0);
	}

	public BoardCell getCenterCell() {
		// TODO Auto-generated method stub
		return new BoardCell(0, 0);
	}
}
