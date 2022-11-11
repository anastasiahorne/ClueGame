package clueGame;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

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
		JPanel peoplePanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(0, 1));
		peoplePanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		return peoplePanel;
	}

	private JPanel createRoomPanel() {
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(0, 1));
		roomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		return roomPanel;
	}

	private JPanel createWeaponPanel() {
		JPanel weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(0, 1));
		weaponPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		return weaponPanel;
	}

	private void updatePanel(JPanel panel, CardType type) {
		panel.removeAll();
		switch (type) {
		case PERSON:
			panel = createPeoplePanel();
			break;
		case WEAPON:
			panel = createWeaponPanel();
			break;
		case ROOM:
			panel = createRoomPanel();
			break;
		}
		add(panel);
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
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Board testBoard = Board.getInstance();

		testBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");

		testBoard.initialize();
		
		testBoard.deal();
		
		HumanPlayer human = testBoard.getHumanPlayer();

		// mark all cards as seen for testing
		for (Card c : testBoard.getPlayerCards()) {
			switch (c.getCardType()) {
			case PERSON:
				human.updateSeenPeople(c);
				break;
			case WEAPON:
				human.updateSeenWeapons(c);
				break;
			case ROOM:
				human.updateSeenRooms(c);
				break;
			}
		}

		KnownCardsPanel panel = new KnownCardsPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 650);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
}
