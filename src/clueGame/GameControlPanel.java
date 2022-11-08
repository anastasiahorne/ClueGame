package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	private JTextField guess;
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		setLayout(new GridLayout(2,0));
		JPanel guessPanel=new JPanel();
		guessPanel.setLayout(new GridLayout(0,2));
		
		JPanel panel = createGuessPanel("Guess");
		guessPanel.add(panel);
		panel = createGuessPanel("Guess Result");
		guessPanel.add(panel);
		add(guessPanel);
	}
	
	private JPanel createGuessPanel(String title) {
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(1,0));
		guess = new JTextField(20);
		guess.setEditable(false);
		panel.add(guess);
		panel.setBorder(new TitledBorder (new EtchedBorder(), title));
		return panel;
	}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		panel.setLayout(new GridLayout(2,0));
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", "orange", 0, 0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuess( "So you have nothing?");
	}

	private void setTurn(Player player, int roll) {
		// TODO Auto-generated method stub
		
	}

	private void setGuess(String message) {
		guess.setText(message);
	}
}