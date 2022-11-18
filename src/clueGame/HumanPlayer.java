package clueGame;

public class HumanPlayer extends Player{
	private Board board = Board.getInstance();
	private boolean isFinished;
	
	public HumanPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
		isFinished = false;
	}
	
	public void move(int newRow, int newCol) {
		// Set old location to unoccupied, move to new cell, and set that cell as occupied
		board.getCell(getRow(), getColumn()).setOccupied(false);
		board.getHumanPlayer().setLocation(newRow, newCol);
		board.getCell(newRow, newCol).setOccupied(true);
		
		// If player is in a room, they can make a suggestion
		
		// Human is done
		board.getHumanPlayer().setFinished(true);
		
	}
	
	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
}
