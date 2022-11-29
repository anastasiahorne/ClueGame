package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class KnownCardsPanel extends JPanel {
	private JPanel peoplePanel;
	private JPanel weaponPanel;
	private JPanel roomPanel;
	private JPanel knownCards;
	private HumanPlayer human;

	// Default constructor
	public KnownCardsPanel(HumanPlayer human) {
		this.human = human;
		
		setLayout(new GridLayout(1,0));
		knownCards = new JPanel();
		knownCards.setLayout(new GridLayout(3, 0));
		knownCards.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		
		//set up the layout of our type panels
		peoplePanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(0, 1));
		
		weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(0, 1));
		
		roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(0, 1));

		//add the three panels to our main panel
		knownCards.add(peoplePanel);
		knownCards.add(weaponPanel);
		knownCards.add(roomPanel);
	
		updatePanels();
		add(knownCards);
	}
	
	public void updatePanels() {
		updatePanel(peoplePanel, CardType.PERSON);
		updatePanel(weaponPanel, CardType.WEAPON);
		updatePanel(roomPanel, CardType.ROOM);
		repaint();
	}
	
	private void updatePanel(JPanel panel, CardType type) {
		panel.removeAll();
		panel.setLayout(new GridLayout(0, 1));
		JLabel inHand = new JLabel("In Hand:");
		JLabel inSeen = new JLabel("Seen:");
		switch (type) {
		case PERSON:
			panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
			// Add cards from the player's hand of type Person
			panel.add(inHand);
			ArrayList<Card> people = new ArrayList<Card>();
			for (Card c : human.getHand()) {
				if (c.getCardType().equals(type)) {
					people.add(c);
					setCardTextAtt(panel, c);
				}
			}
			// Condition for no person cards in hand
			if (people.isEmpty()) {
				emptyField(panel);
			}
			
			// Add seen people cards
			ArrayList<Card> seen = human.getSeenPeople();
			panel.add(inSeen);
			for (Card c : human.getSeenPeople()) {
				setCardTextAtt(panel, c);
			}
			// Condition for no person cards seen
			if (seen.isEmpty()) {
				emptyField(panel);
			}
			break;
			
		case WEAPON:
			panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
			// Add weapon cards from the player's hand
			panel.add(inHand);
			ArrayList<Card> weapons = new ArrayList<Card>();
			for (Card c : human.getHand()) {
				if (c.getCardType().equals(type)) {
					weapons.add(c);
					setCardTextAtt(panel, c);
				}
			}
			// Condition for no weapons cards in hand
			if (weapons.isEmpty()) {
				emptyField(panel);
			}
			
			// Add seen weapon cards
			panel.add(inSeen);
			seen = human.getSeenWeapons();
			for (Card c : human.getSeenWeapons()) {
				setCardTextAtt(panel, c);
			}
			// Condition for no weapon cards in hand
			if (seen.isEmpty()) {
				emptyField(panel);
			}
			break;
			
		case ROOM:
			panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
			// Add room cards from the player's hand
			ArrayList<Card> rooms = new ArrayList<Card>();
			panel.add(inHand);
			for (Card c : human.getHand()) {
				if (c.getCardType().equals(type)) {
					rooms.add(c);
					setCardTextAtt(panel, c);
				}
			}
			if (rooms.isEmpty()) {
				emptyField(panel);
			}
			
			// Add seen room cards
			panel.add(inSeen);
			seen = human.getSeenRooms();
			for (Card c : human.getSeenRooms()) {
				setCardTextAtt(panel, c);
			}
			// Condition for no room cards seen
			if (seen.isEmpty()) {
				emptyField(panel);
			}
			break;
		}
		panel.repaint();
		knownCards.add(panel);
		panel.repaint();
	}

	//if there is no card in the hand or seen of a specific type, panel displays none
	public void emptyField(JPanel panel) {
		JTextField none = new JTextField();
		none.setEditable(false);
		none.setText("None");
		panel.add(none);
	}
	
	//sets the card's text field, text and color 
	public void setCardTextAtt(JPanel panel, Card c) {
		JTextField card = new JTextField();
		card.setEditable(false);
		card.setText(c.getCardName());
		card.setBackground(getColor(c));
		panel.add(card);
	}

	// Get the color of the card
	private Color getColor(Card card) {
		String colorString = card.getColor();
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
	
	//have repeating values
	public static void main(String[] args) {
		Board testBoard = Board.getInstance();

		testBoard.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");

		testBoard.initialize();
		
		testBoard.deal();
		
		HumanPlayer human = testBoard.getHumanPlayer();

		// Mark all cards as seen for testing
		for (Card c : testBoard.getPlayerCards()) {
			if (!human.getHand().contains(c) && (!testBoard.getSolution().getPerson().getCardName().equals(c.getCardName()))) {
				human.updateSeenPeople(c);
			}
		}
		for (Card c : testBoard.getWeaponCards()) {
			if (!human.getHand().contains(c) && (!testBoard.getSolution().getWeapon().getCardName().equals(c.getCardName()))) {
				human.updateSeenWeapons(c);
			}
		}
		for (Card c : testBoard.getRoomCards()) {
			if (!human.getHand().contains(c) && (!testBoard.getSolution().getRoom().getCardName().equals(c.getCardName()))) {
				human.updateSeenRooms(c);
			}
		}

		KnownCardsPanel panel = new KnownCardsPanel(human);  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 650);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// Add values
		panel.updatePanels();
	}
}
