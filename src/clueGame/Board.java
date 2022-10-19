package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import experiment.TestBoardCell;

public class Board {

	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	/*
     * variable and methods used for singleton pattern
     */
    private static Board theInstance = new Board();
    // constructor is private to ensure only one can be created
    private Board() {
    	super() ;
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
    }
     
 	public void setConfigFiles(String layoutConfigFile,String setupConfigFile) {
 		this.layoutConfigFile = "data/" + layoutConfigFile;
 		this.setupConfigFile = "data/" + setupConfigFile;
 	}
 
	//load our txt file so we know what character represents what room, and our room names
	public void loadSetupConfig() {
		roomMap=new HashMap<Character, Room>();
		try {
			FileReader reader=new FileReader(setupConfigFile);
			Scanner in=new Scanner(reader);
			String line=in.nextLine();
			while (in.hasNextLine()) {
				if (!(line.charAt(0)== '/')) {
					getRoomInformation(line);
				}
				line=in.nextLine();
			}
			getRoomInformation(line);
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find " + setupConfigFile);
		}
	}

	/*
	 * From line, extract information about room or space and put it into roomMap
	 */
	private void getRoomInformation(String line) {
		char character;
		String roomName;
		String type;
		String[] parts = line.split(", ");
		type = parts[0];
		// Throw exception if type is not Room or Space
		if (!(type.equals("Room")) && !(type.equals("Space"))) {
			throw new BadConfigFormatException();
		}
		roomName = parts[1];
		character = parts[2].charAt(0);
		Room room = new Room(roomName, character);
		roomMap.put(character, room);
	}
	
	//load our csv file, so we know where our rooms are, where our center etc.	
	public void loadLayoutConfig() {
		try {
			FileReader reader=new FileReader(layoutConfigFile);
			Scanner in=new Scanner(reader);
			ArrayList<String[]> cellValues=new ArrayList<>();
			cellValues.add(in.nextLine().split(","));
			while (in.hasNextLine()) {
				cellValues.add(in.nextLine().split(","));
			}
			// number of rows is the size of the arrayList read in
			numRows=cellValues.size();
			// The number of columns is the size of the first array in the arrayList
			numColumns=cellValues.get(0).length;
			in.close();
			grid=new BoardCell[numRows][numColumns];
			//iterate first through rows then columns
			for (int i=0;i<numRows;++i) {
				String[] row=cellValues.get(i);
				// If all rows do not have the same number of elements, throw exception
				if (row.length != numColumns) {
					throw new BadConfigFormatException();
				}
				for (int j=0;j<numColumns;++j) {
					BoardCell cell = new BoardCell(i,j);
					String cellData=row[j];
					// Throw exception if the character read in is not a key in the map
					if (!roomMap.containsKey(cellData.charAt(0))) {
						throw new BadConfigFormatException();
					}
					// Determine type of cell and set attributes for the cell
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
						cell.setInitial(initial);
						// If doorway, set direction
						if (initial == 'W') {
							cell.setIsDoorway(true);
							switch(specChar) {
							case '<':
								cell.setDoorDirection(DoorDirection.LEFT);
								break;
							case '>':
								cell.setDoorDirection(DoorDirection.RIGHT);
								break;
							case 'v':
								cell.setDoorDirection(DoorDirection.DOWN);
								break;
							case '^':
								cell.setDoorDirection(DoorDirection.UP);
								break;
							}
						}
						else {
							cell.setIsRoom(true);
							Room room = roomMap.get(initial);
							switch(specChar) {
							case '#':
								// Set cell to be room label
								cell.setRoomLabel(true);
								room.setLabelCell(cell);
								break;
							case '*':
								// Set cell to be room center
								cell.setRoomCenter(true);
								room.setCenterCell(cell);
								break;
							default:
								// Set cell to be secretPassage
								cell.setSecretPassage(true);
								cell.setSecretPassage(specChar);
								break;
							}
						}
					}
					grid[i][j]= cell;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not find " + setupConfigFile);
		}
	}

	/*
	 * Calculate adjacency list for each cell
	 */
	private void calculateAdjacencies() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				BoardCell cell = grid[i][j];
				if (cell.getInitial() == 'W') {
					if ((i != 0) && (grid[i-1][j].getInitial() == 'W')) {
						cell.addAdjacency(grid[i-1][j]);
					}
					if ((i != numRows - 1) && (grid[i+1][j].getInitial() == 'W')) {
						cell.addAdjacency(grid[i+1][j]);
					}
					if ((j != 0) && (grid[i][j-1].getInitial() == 'W')) {
						cell.addAdjacency(grid[i][j-1]);
					}
					if ((j != numColumns - 1) && (grid[i][j+1].getInitial() == 'W')) {
						cell.addAdjacency(grid[i][j+1]);
					}
					if (cell.isDoorway()) {
						// If cell is a door, add room center to door's adjList and and add the door to the room center's adjList
						DoorDirection direction = cell.getDoorDirection();
						switch (direction) {
						case RIGHT:
							cell.addAdjacency(getRoom(grid[i][j+1]).getCenterCell());
							(getRoom(grid[i][j+1]).getCenterCell()).addAdjacency(cell);
							break;
						case LEFT:
							cell.addAdjacency(getRoom(grid[i][j-1]).getCenterCell());
							(getRoom(grid[i][j-1]).getCenterCell()).addAdjacency(cell);
							break;
						case UP:
							cell.addAdjacency(getRoom(grid[i-1][j]).getCenterCell());
							(getRoom(grid[i-1][j]).getCenterCell()).addAdjacency(cell);
							break;
						case DOWN:
							cell.addAdjacency(getRoom(grid[i+1][j]).getCenterCell());
							(getRoom(grid[i+1][j]).getCenterCell()).addAdjacency(cell);
							break;
						default:
							break;
						}
					}
				}
				else {
					if (cell.isSecretPassage()) {
						// If room has a secret passage add the attached room's center cell to the center room's center cell
						(getRoom(cell).getCenterCell()).addAdjacency(getRoom(cell.getSecretPassage()).getCenterCell());
					}
				}
			}
		}
		BoardCell cell = getCell(18, 20);
		System.out.println("Cells in adjacency list for cell: " + cell);
		for (BoardCell c: cell.getAdjList()) {
			System.out.println(c);
		}
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
	public BoardCell getCell(int i, int j) {
		return grid[i][j];
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

	public void calcTargets(BoardCell startCell, int pathLength) {
		visited.clear();
		targets.clear();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
		targets.remove(startCell);
	}
	
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

	public Set<BoardCell> getTargets() {
		for (BoardCell i: targets) {
			System.out.println(i);
		}
		return targets;
	}

	// Return the adjacency list for the cell at row i and column j
	public Set<BoardCell> getAdjList(int i, int j) {
		return getCell(i, j).getAdjList();
	}
}
