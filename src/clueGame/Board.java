package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Board extends JPanel{

	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	Map<Character, Room> roomMap;
	private ArrayList<Player> players;
	private ArrayList<String> weapons;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private Solution solution;
	private ArrayList<Card> deck;
	private ArrayList<Card> playerCards;
	private ArrayList<Card> weaponCards;
	private ArrayList<Card> roomCards;
	private int currentPlayerIdx;
	private int roll;
	private int width;
	private int height;
	private ClueGame game;

	/*
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		super();
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize() {
		// Initialize board variables
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		// Load configuration
		try {
			this.loadSetupConfig();
			this.loadLayoutConfig();
		} 
		catch (BadConfigFormatException e) {
			System.out.println("Bad format in file(s)");
		}

		// Calculate adjacencies for each cell
		calculateAdjacencies();

		// Add MouseListener
		addMouseListener(new CellSelector());
	}

	public void setConfigFiles(String layoutConfigFile,String setupConfigFile) {
		this.layoutConfigFile = "data/" + layoutConfigFile;
		this.setupConfigFile = "data/" + setupConfigFile;
	}

	//load our txt file so we know what character represents what room, and our room names
	public void loadSetupConfig() {
		// initialize containers
		roomMap = new HashMap<Character, Room>();
		players = new ArrayList<Player>();
		weapons = new ArrayList<String>();
		deck = new ArrayList<Card>();
		roomCards = new ArrayList<Card>();
		playerCards = new ArrayList<Card>();
		weaponCards = new ArrayList<Card>();
		try {
			FileReader reader = new FileReader(setupConfigFile);
			Scanner in=new Scanner(reader);
			String line=in.nextLine();
			while (in.hasNextLine()) {
				if (!(line.charAt(0) == '/')) {
					setInformation(line);
				}
				line=in.nextLine();
			}
			setInformation(line);
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find " + setupConfigFile);
		}
	}

	/*
	 * From line, extract information about room or space and put it into roomMap
	 */
	private void setInformation(String line) {
		char character;
		String roomName;
		String type;
		String[] parts = line.split(", ");
		type = parts[0];
		// Throw exception if type is not Room, Space, Player, or Weapon
		if (!(type.equals("Room")) && !(type.equals("Space")) && !(type.equals("Player")) && !(type.equals("Weapon"))) {
			throw new BadConfigFormatException();
		}
		if (type.equals("Room") || type.equals("Space")) {
			roomName = parts[1];
			character = parts[2].charAt(0);
			Room room = new Room(roomName, character);
			roomMap.put(character, room);
			// Create a new room card and add it to the deck
			if (type.equals("Room")) {
				Card roomCard = new Card(roomName, CardType.ROOM);
				deck.add(roomCard);
				roomCards.add(roomCard);
			}
		}
		else if (type.equals("Player")) {
			// Create new player and add attributes
			String name = parts[1];
			String color = parts[2];
			//starting position
			int r = Integer.parseInt(parts[3]);
			int c = Integer.parseInt(parts[4]);
			// Make the first player the human player
			if (players.size() == 0) {
				// First person is human
				HumanPlayer player = new HumanPlayer(name, color, r, c);
				players.add(player);
			}
			else {
				// All other people are computers
				ComputerPlayer player = new ComputerPlayer(name, color, r, c);
				players.add(player);
			}
			// Create a new card and add it to the deck
			Card personCard = new Card(name, CardType.PERSON);
			deck.add(personCard);
			playerCards.add(personCard);
		}
		else {
			// Add the weapon to the weapons set
			weapons.add(parts[1]);
			// Create a new card and add it to the deck
			Card weaponCard = new Card(parts[1], CardType.WEAPON);
			deck.add(weaponCard);
			weaponCards.add(weaponCard);
		}
	}
	//set the solution using the created deck



	//load our csv file, so we know where our rooms are, where our center etc.	
	public void loadLayoutConfig() {
		try {
			FileReader reader = new FileReader(layoutConfigFile);
			Scanner in = new Scanner(reader);
			ArrayList<String[]> cellValues = new ArrayList<>();
			cellValues.add(in.nextLine().split(","));
			while (in.hasNextLine()) {
				cellValues.add(in.nextLine().split(","));
			}
			// number of rows is the size of the arrayList read in
			numRows = cellValues.size();
			// The number of columns is the size of the first array in the arrayList
			numColumns = cellValues.get(0).length;
			in.close();
			grid = new BoardCell[numRows][numColumns];
			//iterate first through rows then columns
			for (int row = 0; row < numRows; ++row) {
				String[] rowArray = cellValues.get(row);
				// If all rows do not have the same number of elements, throw exception
				if (rowArray.length != numColumns) {
					throw new BadConfigFormatException();
				}
				for (int col = 0; col < numColumns; ++col) {
					BoardCell cell = new BoardCell(row,col);
					String cellData = rowArray[col];
					// Throw exception if the character read in is not a key in the map
					if (!roomMap.containsKey(cellData.charAt(0))) {
						throw new BadConfigFormatException();
					}
					// Determine type of cell and set attributes for the cell
					setCellAttributes(cell, cellData);
					grid[row][col]= cell;
				}
			}
		} catch (FileNotFoundException e) {
			e.getMessage();
		}
	}

	// Determine the cell's type and set the corresponding attributes
	private void setCellAttributes(BoardCell cell, String cellData) {
		if (cellData.length()==1) {
			cell.setInitial(cellData.charAt(0));
			// Test if cell is a walkway or unused space
			if (!cellData.equals("W") && !cellData.equals("X")) {
				cell.setIsRoom(true);
			}
			else {
				cell.setIsRoom(false);
			}
		}
		else {
			char initial=cellData.charAt(0);
			char specChar=cellData.charAt(1);
			cell.setSpecialRoomAttributes(this, initial, specChar);
		}
	}

	/*
	 * Calculate adjacency list for each cell
	 */
	private void calculateAdjacencies() {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				BoardCell cell = grid[row][col];
				if (cell.getInitial() == 'W') {
					if ((row != 0) && (grid[row-1][col].getInitial() == 'W')) {
						cell.addAdjacency(grid[row-1][col]);
					}
					if ((row != numRows - 1) && (grid[row+1][col].getInitial() == 'W')) {
						cell.addAdjacency(grid[row+1][col]);
					}
					if ((col != 0) && (grid[row][col-1].getInitial() == 'W')) {
						cell.addAdjacency(grid[row][col-1]);
					}
					if ((col != numColumns - 1) && (grid[row][col+1].getInitial() == 'W')) {
						cell.addAdjacency(grid[row][col+1]);
					}
					if (cell.isDoorway()) {
						// If cell is a door, add room center to door's adjList and and add the door to the room center's adjList
						DoorDirection direction = cell.getDoorDirection();
						switch (direction) {
						case RIGHT:
							cell.addAdjacency(getRoom(grid[row][col+1]).getCenterCell());
							(getRoom(grid[row][col+1]).getCenterCell()).addAdjacency(cell);
							break;
						case LEFT:
							cell.addAdjacency(getRoom(grid[row][col-1]).getCenterCell());
							(getRoom(grid[row][col-1]).getCenterCell()).addAdjacency(cell);
							break;
						case UP:
							cell.addAdjacency(getRoom(grid[row-1][col]).getCenterCell());
							(getRoom(grid[row-1][col]).getCenterCell()).addAdjacency(cell);
							break;
						case DOWN:
							cell.addAdjacency(getRoom(grid[row+1][col]).getCenterCell());
							(getRoom(grid[row+1][col]).getCenterCell()).addAdjacency(cell);
							break;
						default:
							break;
						}
					}
				}
				else {
					secretPassageAdj(cell);
				}
			}
		}	
	}

	public void secretPassageAdj(BoardCell cell) {
		if (cell.isSecretPassage()) {
			// If room has a secret passage add the attached room's center cell to the center room's center cell
			(getRoom(cell).getCenterCell()).addAdjacency(getRoom(cell.getSecretPassage()).getCenterCell());
		}
	}

	// Clear sets and make a call to findAllTargets
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited.clear();
		targets.clear();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
		targets.remove(startCell);
	}

	// Get all cells that are pathLength cells away from thisCell
	public void findAllTargets(BoardCell thisCell, int numSteps) {
		for (BoardCell adjCell: thisCell.getAdjList()) {
			if (adjCell.getIsRoom()) {
				targets.add(adjCell);
			}
			//add cell if not yet visited and it is an open space
			if (!visited.contains(adjCell) && !adjCell.getIsOccupied() && !adjCell.getIsRoom()) {
				visited.add(adjCell);
				if (numSteps == 1) {
					targets.add(adjCell);
				}
				//recursive call to determine possible targets if our roll is >1
				else {
					findAllTargets(adjCell, numSteps - 1);
				}
				visited.remove(adjCell);
			}
		}
	}

	public void deal() {
		Random rand = new Random();
		setSolution();
		// Deal a random card to players
		int playerNum = 0;
		while (!deck.isEmpty()) {
			// Pick a random card from the deck
			Card card = deck.get(rand.nextInt(deck.size()));
			// Update the player's hand
			players.get(playerNum).updateHand(card);
			// Remove the card from the deck
			deck.remove(card);
			// Increment player index
			playerNum = (playerNum + 1) % players.size();
		}
		// Current player set to the first in the array
		currentPlayerIdx = 0;
	}

	public void setSolution() {
		Random rand = new Random();
		// Get a random room, person, and card
		Card room = roomCards.get(rand.nextInt(roomCards.size()));
		Card person = playerCards.get(rand.nextInt(playerCards.size()));
		Card weapon = weaponCards.get(rand.nextInt(weaponCards.size()));

		// Set solution
		solution = new Solution(room, person, weapon);

		// Remove cards from the deck
		deck.remove(room);
		deck.remove(person);
		deck.remove(weapon);
	}

	public boolean checkAccusation(Card person, Card weapon, Card room) {
		Card personSol = solution.getPerson();
		Card weaponSol = solution.getWeapon();
		Card roomSol = solution.getRoom();
		if (person.equals(personSol) && weapon.equals(weaponSol) && room.equals(roomSol)) {
			return true;
		}
		return false;
	}

	public Card handleSuggestion(Card person, Card weapon, Card room, Player suggester) {
		// Get the index of the player making the suggestion so we have the order
		int playerIdx = 0;
		for (Player player : getPlayers()) {
			if (suggester.equals(player)) {
				break;
			}
			playerIdx++;
		}
		// Get the index of the next player
		int nextIdx = (playerIdx + 1) % getPlayers().size();
		game.getControlPanel().setGuess(person + " with the " + weapon + " in " + room);
		game.repaint();
		// Check if players can disprove
		while (nextIdx != playerIdx) {
			Card disproval = getPlayers().get(nextIdx).disproveSuggestion(person, weapon, room);
			if (disproval != null) {
				if (suggester instanceof HumanPlayer) {
					game.getControlPanel().setResult(disproval.getCardName());
				//if human player already saw a card and they guess something similar, we don't see a new clue
					switch (disproval.getCardType()) {
					case PERSON:
						if (getHumanPlayer().getSeenPeople().contains(disproval)) {
							noNewClue(disproval);
						}
						break;
					case WEAPON:
						if (getHumanPlayer().getSeenWeapons().contains(disproval)) {
							noNewClue(disproval);
						}
						break;
					case ROOM:
						if (getHumanPlayer().getSeenRooms().contains(disproval)) {
							noNewClue(disproval);
						}
						break;
					}
				}
				else {
					game.getControlPanel().setResult("Suggestion disproved by " + getPlayers().get(nextIdx));
				}
				//update panels
				game.getCardPanel().updatePanels();
				game.getCardPanel().repaint();
				game.repaint();
				return disproval;
			}
			nextIdx = (nextIdx + 1) % getPlayers().size();
		}
		// If no player can disprove, return null
		game.getControlPanel().setResult("No new clue");
		game.repaint();
		return null;
	}
	//method to set the guessResult to no new clue
	public void noNewClue(Card disproval) {
		game.getControlPanel().setResult(disproval.getCardName() + " - No new clue");
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Get the size of the panel to get the height and width of each cell
		width = getWidth() / numColumns;
		height = getHeight() / numRows;

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j].draw(g, width, height);
			}
		}

		// Draw doors
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j].isDoorway()) {
					Graphics2D g2D = (Graphics2D) g;
					grid[i][j].drawDoorLine(g, width, height, g2D);
				}
			}
		}

		// Draw room names
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				// If the cell is a label cell, draw the room label
				if (grid[i][j].isLabel()) {
					g.setColor(Color.BLUE);
					g.drawString(getRoom(grid[i][j]).getName(), j * width, i * height);
				}
			}
		}

		// Walk through players and have them draw themselves
		for (Player player : getPlayers()) {
			player.draw(g, width, height);
		}
	}

	// When the NEXT button is pressed, perform these actions
	public void next() {
		Player currentPlayer = getPlayers().get(currentPlayerIdx);
		// If current player is human and is not finished, error
		if (currentPlayer instanceof HumanPlayer) {
			if (!((HumanPlayer) currentPlayer).isFinished()) {
				JOptionPane.showMessageDialog(null, "Turn must be finished before\nthe next player can have a turn.");
				return;
			}
		}
		// Update the current player
		currentPlayerIdx = (currentPlayerIdx + 1) % getPlayers().size();
		currentPlayer = getPlayers().get(currentPlayerIdx);
		// Roll the dice
		setRoll();

		// Calc targets
		BoardCell currentCell = getCell(currentPlayer.getRow(), currentPlayer.getColumn());
		calcTargets(currentCell, getRoll());

		// Update panels
		game.getControlPanel().setGuess("\t");
		game.getControlPanel().setResult("\t");
		game.getControlPanel().setTurn(currentPlayer, roll);
		game.getControlPanel().repaint();
		game.getCardPanel().repaint();
		game.repaint();

		// If player is HumanPlayer, display targets
		if (currentPlayer instanceof HumanPlayer) {
			((HumanPlayer) currentPlayer).setMoved(false);
			// Check if the player was pulled into the room on the previous turn
			if (currentPlayer.isPulled()) {
				targets.add(getCell(currentPlayer.getRow(), currentPlayer.getColumn()));
				currentPlayer.setPulled(false);
			}
			// Display targets
			for (BoardCell cell : getTargets()) {
				cell.setTarget(true);
				// If a target is a room, mark the whole room as a target
				if (cell.getIsRoom()) {
					char initial = cell.getInitial();
					for (int i = 0; i < grid.length; i++) {
						for (int j = 0; j < grid[0].length; j++) {
							if (grid[i][j].getInitial() == initial) {
								grid[i][j].setTarget(true);
							}
						}
					}
				}
			}
	
			// Flag unfinished
			((HumanPlayer) currentPlayer).setFinished(false);
			repaint();
		}
		// If player is not human, check if accusation or suggestion should be made
		else {
			// Check if accusation should be made
			((ComputerPlayer) currentPlayer).shouldAccuse();
			// Check if the player was pulled into the room on the previous turn
			if (currentPlayer.isPulled()) {
				targets.add(getCell(currentPlayer.getRow(), currentPlayer.getColumn()));
				currentPlayer.setPulled(false);
			}
			BoardCell target = ((ComputerPlayer) currentPlayer).selectAMoveTarget(targets);
			
			int row = target.getRow();
			int col = target.getCol();
			// Move the player
			currentPlayer.setLocation(row, col);
			// If in room, make a suggestion
			if (target.getIsRoom()) {
				Solution suggestion = ((ComputerPlayer) currentPlayer).createSuggestion();
				for (Player p : getPlayers()) {
					if (suggestion.getPerson().getCardName().equals(p.getName())) {
						getCell(p.getRow(), p.getColumn()).setOccupied(false);
						p.setLocation(row, col);
						getCell(row, col).setOccupied(true);
						p.setPulled(true);
					}
				}
				
				//if a player disproves computer suggestion, update players seen cards of that type
				Card disproval = handleSuggestion(suggestion.getPerson(), suggestion.getWeapon(), suggestion.getRoom(), currentPlayer);
				if (disproval != null) {
					switch (disproval.getCardType()) {
					case PERSON:
						currentPlayer.updateSeenPeople(disproval);
						break;
					case WEAPON:
						currentPlayer.updateSeenWeapons(disproval);
						break;
					case ROOM:
						currentPlayer.updateSeenRooms(disproval);
						break;
					default:
						break;
					}
				}
			}
		}
		//update the control panel, to display next roll and player
		game.getControlPanel().repaint();
		
		//ensure the panels show the updated versions without having to resize the entire game window
		game.getControlPanel().setVisible(false);
		game.getControlPanel().setVisible(true);
		
		//update card panel values and gui
		game.getCardPanel().updatePanels();
		game.getCardPanel().repaint();
		game.repaint();
	}

	public void accuse() {
		// If it is not the human player's turn error
		if (getPlayers().get(currentPlayerIdx) instanceof ComputerPlayer) {
			JOptionPane.showMessageDialog(null, "You cannot make an accusation\nwhen it is not your turn!");
			return;
		}
		// If the player has moved, error because accusations must be made at the start of the turn
		if (((HumanPlayer) getPlayers().get(currentPlayerIdx)).isMoved()) {
			JOptionPane.showMessageDialog(null, "You cannot make an accusation\nafter you have moved!");
			return;
		}
		// Else, allow the player to make an accusation
		accusation(getPlayers().get(currentPlayerIdx));
	}

	//listener for when the user clicks on the board
	class CellSelector implements MouseListener {
		private Board board = Board.getInstance();
		@Override
		public void mouseClicked(MouseEvent e) {
			// Find the x and y positions of where the user clicked
			int x = e.getX();
			int y = e.getY();
			// Get the cell where the click occurred
			int row = y / (getHeight() / board.getNumRows());
			int col = x / (getWidth() / board.getNumColumns());

			// If cell is not a target, error
			boolean validTarget = false;
			for (BoardCell cell : targets) {
				if (cell.getCol() == col && cell.getRow() == row) {
					validTarget = true;
				}
				// If room is clicked move player to center
				else if (cell.getIsRoom()) {
					// If the clicked cell is the same room as the target cell, update
					if (getCell(row, col).getIsRoom() && (cell.getInitial() == getCell(row, col).getInitial())) {
						validTarget = true;
						row = cell.getRow();
						col = cell.getCol();
					}
				}
			}

			//if current player is the human player, and they have not moved/clicked on another cell yet, move them
			Player currPlayer = getPlayers().get(currentPlayerIdx);
			if(validTarget && (currPlayer == board.getHumanPlayer()) && !getHumanPlayer().isMoved()) { 
				// Move the player
				board.getHumanPlayer().move(row, col);
				board.getHumanPlayer().setMoved(true);

				// Stop displaying targets
				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid[0].length; j++) {
						grid[i][j].setTarget(false);
					}
				}
				repaint(); // MUST CALL REPAINT

				// If player is in a room, let them make a suggestion
				if (getCell(row, col).getIsRoom()) {
					suggestion();
				}
				return;
			}
			//if the player clicks on the board when it is not their turn
			else if (!(currPlayer == board.getHumanPlayer())) {
				JOptionPane.showMessageDialog(null, "Patience you must have my young Padawan -Yoda\n It is not your turn.");
			}

			//player selects a target that is not an option
			else {
				JOptionPane.showMessageDialog(null, "Invalid location selected.");
			}
		}
		//not used mouse methods
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
	}

	// Dialog for getting the suggestion from the user
	public void suggestion() {
		
		//set combo box for the possible players
		JComboBox<Card> people = new JComboBox<Card>();
		for (Card card : getPlayerCards()) {
			people.addItem(card);
		}
		//set combo box for the possible weapons
		JComboBox<Card> weapons = new JComboBox<Card>();
		for (Card card : getWeaponCards()) {
			weapons.addItem(card);
		}
		//no need for room choice, that is decided by what room the player is in
		
		//determine the room the suggester is in
		int row = getPlayers().get(currentPlayerIdx).getRow();
		int col = getPlayers().get(currentPlayerIdx).getColumn();
		Room room = getRoom(getCell(row, col));
		
		//set up the suggestion window dialog
		JDialog dialog = new JDialog(game, "Make a Suggestion");
		dialog.setLayout(new GridLayout(4,2));
		JLabel roomLabel = new JLabel("Room");
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");
		JLabel currentRoom = new JLabel(room.getName());
		JButton submit = new JButton("Submit");
		
		//if suggester submits, handle the suggestion
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Card roomCard = null;
				for (Card card : getRoomCards()) {
					if (room.getName() == card.getCardName()) {
						roomCard = card;
					}
				}
				// Move the player from the suggestion to the room if the suggested player is not the suggester
				if (!people.getItemAt(people.getSelectedIndex()).getCardName().equals(getHumanPlayer().getName())) {
					for (Player p : getPlayers()) {
						if (people.getItemAt(people.getSelectedIndex()).getCardName().equals(p.getName())) {
							getCell(p.getRow(), p.getColumn()).setOccupied(false);
							p.setLocation(row, col);
							getCell(row, col).setOccupied(true);
							p.setPulled(true);
						}
					}
				}
				//if the human player makes a suggestion, and it is disproved, update seen cards
				
				Card disproval = handleSuggestion(people.getItemAt(people.getSelectedIndex()), weapons.getItemAt(weapons.getSelectedIndex()), roomCard, getHumanPlayer());
				if (disproval != null) {
					switch (disproval.getCardType()) {
					case PERSON:
						getHumanPlayer().updateSeenPeople(disproval);
						break;
					case WEAPON:
						getHumanPlayer().updateSeenWeapons(disproval);
						break;
					case ROOM:
						getHumanPlayer().updateSeenRooms(disproval);
						break;
					default:
						break;
					}
				}
				
				//update our panels, ensure they display new versions without resizing the game window
				game.getCardPanel().updatePanels();
				game.getControlPanel().setVisible(false);
				game.getControlPanel().setVisible(true);
				game.repaint();
				dialog.dispose();
			}
		});
		
		//allow the player to not make suggestion if they change their mind
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		
		//set borders for our labels and current room box
		roomLabel.setBorder(new TitledBorder(new EtchedBorder()));
		personLabel.setBorder(new TitledBorder(new EtchedBorder()));
		weaponLabel.setBorder(new TitledBorder(new EtchedBorder()));
		currentRoom.setBorder(new TitledBorder(new EtchedBorder()));
		//size and close option
		dialog.setSize(300,200);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setModal(true);
		//add our labels and combo boxes to the suggestion window
		dialog.add(roomLabel);
		dialog.add(currentRoom);
		dialog.add(personLabel);
		dialog.add(people);
		dialog.add(weaponLabel);
		dialog.add(weapons);
		dialog.add(submit);
		dialog.add(cancel);
		dialog.setVisible(true);
	}

	// Dialog for getting the accusation from the user
	public void accusation(Player player) {
		
		//combo boxes for weapon,room and player choices
		JComboBox<Card> people = new JComboBox<Card>();
		for (Card card : getPlayerCards()) {
			people.addItem(card);
		}
		JComboBox<Card> weapons = new JComboBox<Card>();
		for (Card card : getWeaponCards()) {
			weapons.addItem(card);
		}
		JComboBox<Card> rooms = new JComboBox<Card>();
		for (Card card : getRoomCards()) {
			rooms.addItem(card);
		}
		
		//window for accusation selection
		JDialog dialog = new JDialog(game, "Make an Accusation");
		dialog.setLayout(new GridLayout(4,2));
		JLabel roomLabel = new JLabel("Room");
		JLabel personLabel = new JLabel("Person");
		JLabel weaponLabel = new JLabel("Weapon");
		JButton submit = new JButton("Submit");
		
		//listener for submit button
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//get who, how and where from the combo box selections
				Card p = people.getItemAt(people.getSelectedIndex());
				Card w = weapons.getItemAt(weapons.getSelectedIndex());
				Card r = rooms.getItemAt(rooms.getSelectedIndex());
				//check if accusation is the same as the solution
				if (checkAccusation(p, w, r)) {
					end(true);
				}
				else {
					end(false);
				}
				game.repaint();
				dialog.dispose();
			}
		});
		
		//allow person to cancel making a suggestion
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		
		dialog.setSize(300,200);
		dialog.setModal(true);
		//add borders to labels
		roomLabel.setBorder(new TitledBorder(new EtchedBorder()));
		personLabel.setBorder(new TitledBorder(new EtchedBorder()));
		weaponLabel.setBorder(new TitledBorder(new EtchedBorder()));
		//add labels an combo boxes to the window
		dialog.add(roomLabel);
		dialog.add(rooms);
		dialog.add(personLabel);
		dialog.add(people);
		dialog.add(weaponLabel);
		dialog.add(weapons);
		dialog.add(submit);
		dialog.add(cancel);
		dialog.setVisible(true);
	}

	// End dialog for player accusation
	public void end(boolean status) {
		JDialog end = new JDialog(game, "The End!");
		end.setModal(true);
		end.setUndecorated(true);
		end.setLayout(new GridLayout(2,0));
		if (status) {
			JLabel win = new JLabel("Congratuations, you win!");
			end.add(win);
		}
		else {
			JTextArea  lost= new JTextArea();
			lost.setEditable(false);
			lost.setText("Sorry, you lose!\n"
					+ "The correct answer is "
					+ solution.getPerson()+"\n" + "with the "
					+ solution.getWeapon() +"\n"+ "in "
					+ solution.getRoom() +".");
			end.add(lost);
		}
		// Button that closes the entire game
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				end.dispose();
				game.dispose();
			}
		});
		end.add(ok);
		end.setSize(300,300);
		end.setVisible(true);
	}

	// End dialog for computer accusation
	public void computerEnd(ComputerPlayer player) {
		JDialog end = new JDialog(game, "The End!");
		end.setModal(true);
		end.setUndecorated(true);
		end.setLayout(new GridLayout(3, 0));
		JLabel win = new JLabel(player.getName() + " won!");
		end.add(win);
		
		//text if you lose
		JLabel lose = new JLabel("Sorry, you lose!\nThe correct answer is\n"
				+ solution.getPerson() + " with the "
				+ solution.getWeapon() + " in "
				+ solution.getRoom());
		end.add(lose);
		// Button that closes the entire game
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				end.dispose();
				game.dispose();
			}
		});
		end.add(ok);
		end.setSize(300,300);
		end.setVisible(true);
	}

	// Getter for roll
	public int getRoll() {
		return roll;
	}

	// Setter for roll
	public void setRoll() {
		Random rand = new Random();
		roll = rand.nextInt(6) + 1;
	}

	// Getter for currentPlayerIdx
	public int getCurrentPlayerIdx() {
		return currentPlayerIdx;
	}

	// Return the number of rows in the game board
	public int getNumRows() {
		return numRows;
	}

	// Return the number of columns in the game board
	public int getNumColumns() {
		return numColumns;
	}

	// Return the room given by the character that represents the room
	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	// Return the cell at position i, j
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

	// Return the room at the given cell on the board
	public Room getRoom(BoardCell cell) {
		char symbol = cell.getInitial();
		return getRoom(symbol);
	}

	// Return the number of rooms on the board by counting the elements in the map
	public int getNumRooms() {
		return roomMap.size();
	}

	// Getter for roomMap variable
	public Map<Character, Room> getRoomMap() {
		return roomMap;
	}

	// Getter for the set of BoardCells that is targets
	public Set<BoardCell> getTargets() {
		return targets;
	}

	// Return the adjacency list for the cell at row i and column j
	public Set<BoardCell> getAdjList(int i, int j) {
		return getCell(i, j).getAdjList();
	}

	// Getter for the array of players
	public ArrayList<Player> getPlayers() {
		return players;
	}
	//Getter for the human player
	public HumanPlayer getHumanPlayer() {
		for (Player p: players) {
			if (p instanceof HumanPlayer) {
				return (HumanPlayer) p;
			}
		}
		return null;
	}

	// Getter for the array of weapons
	public ArrayList<String> getWeapons() {
		return weapons;
	}

	// Getter for the deck of Cards
	public ArrayList<Card> getDeck() {
		return deck;
	}

	// Getter for the solution
	public Solution getSolution() {
		return solution;
	}

	// Getter for the array of weaponCards
	public ArrayList<Card> getWeaponCards() {
		return weaponCards;
	}

	// Getter for the array of playerCards
	public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}

	// Getter for the array of roomCards
	public ArrayList<Card> getRoomCards() {
		return roomCards;
	}

	// Getter for ClueGame
	public ClueGame getGame() {
		return game;
	}

	// Setter for ClueGame
	public void setGame(ClueGame game) {
		this.game = game;
	}
}
