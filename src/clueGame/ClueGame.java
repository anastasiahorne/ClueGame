package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class ClueGame extends JFrame {
	public static Board board = Board.getInstance();
	
	// Default constructor
	public ClueGame() {
		setSize(new Dimension(900,700));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		// Create board, cards, and control panel
		add(board, BorderLayout.CENTER);
		GameControlPanel controlPanel = new GameControlPanel();
		add(controlPanel, BorderLayout.SOUTH);
		KnownCardsPanel cardPanel = new KnownCardsPanel(board.getHumanPlayer());
		add(cardPanel, BorderLayout.EAST);
	}
	
	public static void main(String[] args) {
		// Create the frame
		ClueGame gui = new ClueGame();
		gui.setVisible(true); // make it visible
	}
}
