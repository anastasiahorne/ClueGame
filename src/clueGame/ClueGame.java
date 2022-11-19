package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	public static Board board = Board.getInstance();
	private GameControlPanel controlPanel = new GameControlPanel();
	
	// Default constructor
	public ClueGame() {
		setTitle("Clue Game - CSCI306");
		setSize(new Dimension(650,650));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		// Create board, cards, and control panel
		KnownCardsPanel cardPanel = new KnownCardsPanel(board.getHumanPlayer());
		add(cardPanel, BorderLayout.EAST);
		add(board, BorderLayout.CENTER);
		getControlPanel().setTurn(board.getPlayers().get(board.getCurrentPlayerIdx()), board.getRoll());
		add(controlPanel, BorderLayout.SOUTH);
	}

	public GameControlPanel getControlPanel() {
		return controlPanel;
	}
	
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		board.deal();
		
		// Create the frame
		ClueGame gui = new ClueGame();
		gui.setVisible(true); // make it visible
		JOptionPane.showMessageDialog(gui, "You are " + board.getHumanPlayer() + ".\nCan you find the solution\nbefore the Computer players?");
		// Process first player's turn
		Player firstPlayer = board.getPlayers().get(board.getCurrentPlayerIdx());
		Random rand = new Random();
		int die = rand.nextInt(6) + 1;
		int row = firstPlayer.getRow();
		int col = firstPlayer.getColumn();
		// Get targets for player
		board.calcTargets(board.getCell(row, col), die);
		for (BoardCell cell : board.getTargets()) {
			cell.setTarget(true);
		}
		// Update GameControlPanel
		gui.getControlPanel().setTurn(firstPlayer, die);
		board.setGame(gui);
		gui.repaint();
	}
}
