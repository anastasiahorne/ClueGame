package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	private JTextField guess;
	private JTextField result;
	private JTextField turn;
	private JTextField roll;
	private JButton accusation;
	private JButton next;
	private Board board = Board.getInstance();
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel() {
		setLayout(new GridLayout(2,0));
		JPanel topPanel = createTopPanel();
		JPanel bottomPanel = createBottomPanel();
		add(topPanel);
		add(bottomPanel);
		// Add button listeners
		NextButtonListener nextListener = new NextButtonListener();
		AccusationButtonListener accusationListener = new AccusationButtonListener();
		next.addActionListener(nextListener);
		accusation.addActionListener(accusationListener);
	}

	private JPanel createTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 4));
		//set whose turn it is
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,0));
		JLabel turnLabel = new JLabel("Whose turn?");
		turnPanel.add(turnLabel);
	
		turn = new JTextField();
		turn.setEditable(false);
		turnPanel.add(turn);
		topPanel.add(turnPanel);
		
		//set the roll number
		JPanel rollPanel = new JPanel();
		JLabel rollLabel = new JLabel("Roll: ");
		rollPanel.add(rollLabel);
		
		roll = new JTextField();
		roll.setEditable(false);
		rollPanel.add(roll);
		topPanel.add(rollPanel);
		
		//set the make accusation panel
		accusation = new JButton("Make Accusation");
		topPanel.add(accusation);
		next = new JButton("NEXT!");
		topPanel.add(next);
		
		return topPanel;
	}
	
	private JPanel createBottomPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(0, 2));
		//set our guess panel, and get our guess from another method
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		guess = new JTextField();
		guess.setEditable(false);
		panel.add(guess);
		bottomPanel.add(panel);
		//set our guess result panel
		panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		result = new JTextField();
		result.setEditable(false);
		panel.add(result);
		
		bottomPanel.add(panel);
		return bottomPanel;
	}
	
	// Setter for turn
	public void setTurn(Player player, int r) {
		String name = player.getName();
		turn.setText(name);
		turn.setBackground(getColor(player));
		roll.setText(String.valueOf(r));
		repaint();
	}

	// Setter for guess
	public void setGuess(String message) {
		guess.setText(message);
		repaint();
	}
	
	// Setter for result
	public void setResult(String message) {
		result.setText(message);
		repaint();
	}
	
	// Get the color of the player
	private Color getColor(Player player) {
		String colorString = player.getColor();
		int r = 192;
		int b = 192;
		int g = 192;
		switch (colorString) {
		case "Red":
			r = 255;
			g = 51;
			b = 51;
			break;
		case "Blue":
			r = 102;
			g = 178;
			b = 255;
			break;
		case "White":
			r = 255;
			g = 255;
			b = 255;
			break;
		case "Yellow":
			r = 255;
			g = 255;
			b = 153;
			break;
		case "Purple":
			r = 153;
			g = 153;
			b = 255;
			break;
		case "Green":
			r = 153;
			g = 255;
			b = 153;
			break;
		default:
			break;
		}
		return new Color(r, g, b);
	}

	// What to do when the user presses the NEXT button
	class NextButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			board.next();
		}
	}
	
	// What to do when the user presses the accusation button
		class AccusationButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				board.accuse();
			}
		}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		//panel.setLayout(new GridLayout(2,0));
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Blaster", "Blue", 0, 0), 5);
		panel.setGuess("I have no guess!");
		panel.setResult("So you have nothing?");
	}
}