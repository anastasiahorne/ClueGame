package clueGame;

import javax.swing.JPanel;

public class KnownCardsPanel extends JPanel {
	
	// Default constructor
	public KnownCardsPanel() {
		
	}
	
	public static void main(String[] args) {
		Board testBoard = Board.getInstance();
		
		testBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		
		testBoard.initialize();
		
		// mark all cards as seen for testing
		for () {
			
		}
	}
}
