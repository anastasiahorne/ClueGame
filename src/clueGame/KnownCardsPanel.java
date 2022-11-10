package clueGame;

import javax.swing.JFrame;
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
		for (Card c : testBoard.getPlayerCards()) {
			
		}
		
		KnownCardsPanel panel = new KnownCardsPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 650);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
}
