package clueGame;

public class HumanPlayer extends Player{
	private boolean isFinished;
	
	public HumanPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
		isFinished = false;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
}
