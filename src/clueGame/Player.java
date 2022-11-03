package clueGame;

abstract public class Player {
	private String name;
	private String color;
	private int row, column;
	
	public Player(String name, String color, int row, int column) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
	}
	
	abstract void updateHand(Card card);
}
