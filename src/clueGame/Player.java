package clueGame;

abstract public class Player {
	private String name;
	private String color;
	private int row, column;
	
	abstract void updateHand(Card card);
}
