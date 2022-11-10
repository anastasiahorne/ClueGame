package clueGame;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class KnownCardsPanel extends JPanel {
	
	// Default constructor
	public KnownCardsPanel() {
		setLayout(new GridLayout(3,1));
		JPanel peoplePanel = createPeoplePanel();
		JPanel weaponPanel = createWeaponPanel();
		JPanel roomPanel = createRoomPanel();
		add(peoplePanel);
		add(weaponPanel);
		add(roomPanel);
	}
	
	private JPanel createPeoplePanel() {
		return null;
	}
	
	private JPanel createRoomPanel() {
		return null;
	}
	
	private JPanel createWeaponPanel() {
		return null;
	}
	
	private void updatePanel(JPanel name, CardType type) {
		
	}
	public static void main(String[] args) {
		Board testBoard = Board.getInstance();
		
		testBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		
		testBoard.initialize();
		
		// mark all cards as seen for testing
		for (Card c : testBoard.getPlayerCards()) {
			switch (c.getCardType()) {
			case PERSON:
				break;
			case WEAPON:
				break;
			case ROOM:
				break;
			}
		}
		
		KnownCardsPanel panel = new KnownCardsPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 650);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// Set values
		
	}
}
