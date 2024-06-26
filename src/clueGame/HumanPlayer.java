package clueGame;

public class HumanPlayer extends Player{
	private Board board = Board.getInstance();
	private boolean isFinished;
	private boolean moved;
	
	public HumanPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
		isFinished = false;
		moved = false;
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
	
	//error flag to make sure the human finishes their turn before next is processed
	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	//ensures the human player can't move twice in one turn
	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}
}
