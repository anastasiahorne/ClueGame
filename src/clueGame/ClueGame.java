package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	public static Board board = Board.getInstance();
	
	// Default constructor
	public ClueGame() {
		setTitle("Clue Game - CSCI306");
		setSize(new Dimension(650,650));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		// Create board, cards, and control panel
		KnownCardsPanel cardPanel = new KnownCardsPanel(board.getHumanPlayer());
		add(cardPanel, BorderLayout.EAST);
		add(board, BorderLayout.CENTER);
		GameControlPanel controlPanel = new GameControlPanel();
		add(controlPanel, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		board.deal();
		/*
		HumanPlayer human = board.getHumanPlayer();
		// Mark all cards as seen for testing
		for (Card c : board.getPlayerCards()) {
			if (!human.getHand().contains(c) && (!board.getSolution().getPerson().getCardName().equals(c.getCardName()))) {
				human.updateSeenPeople(c);
			}
		}
		for (Card c : board.getWeaponCards()) {
			if (!human.getHand().contains(c) && (!board.getSolution().getWeapon().getCardName().equals(c.getCardName()))) {
				human.updateSeenWeapons(c);
			}
		}
		for (Card c : board.getRoomCards()) {
			if (!human.getHand().contains(c) && (!board.getSolution().getRoom().getCardName().equals(c.getCardName()))) {
				human.updateSeenRooms(c);
			}
		}
		*/
		// Create the frame
		ClueGame gui = new ClueGame();
		gui.setVisible(true); // make it visible
		JOptionPane.showMessageDialog(gui, "You are " + board.getHumanPlayer() + ".\nCan you find the solution\nbefore the Computer players?");
	}
}
