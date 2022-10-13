package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Board {

	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	
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
    	try {
    		this.loadSetupConfig();
    	} 
    	catch (BadConfigFormatException e) {
    		System.out.println("Bad format in file " + layoutConfigFile);
    	}
    	try {
    		this.loadLayoutConfig();
    	}
    	catch (BadConfigFormatException e) {
    		System.out.println("Bad format in file " + setupConfigFile);
    	}
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
			char character;
			String roomName;
			String type;
			String line=in.nextLine();
			while (in.hasNextLine()) {
				if (!(line.charAt(0)== '/')) {
					String[] parts = line.split(", ");
					type = parts[0];
					if (!(type.equals("Room")) || !(type.equals("Space"))) {
						throw new BadConfigFormatException();
					}
					roomName = parts[1];
					character = parts[2].charAt(0);
					Room room = new Room(roomName, character);
					roomMap.put(character, room);
				}
				line=in.nextLine();
			}
			String[] parts = line.split(", ");
			type = parts[0];
			if (!(type.equals("Room")) || !(type.equals("Space"))) {
				throw new BadConfigFormatException();
			}
			roomName = parts[1];
			character = parts[2].charAt(0);
			Room room = new Room(roomName, character);
			roomMap.put(character, room);
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find " + setupConfigFile);
		}
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
			numRows=cellValues.size();
			numColumns=cellValues.get(0).length;
			in.close();
			
			grid=new BoardCell[numRows][numColumns];
			//iterate first through rows then columns
			for (int i=0;i<numRows;++i) {
				String[] row=cellValues.get(i);
				if (row.length != numColumns) {
					throw new BadConfigFormatException();
				}
				for (int j=0;j<numColumns;++j) {
					String cellData=row[j];
					BoardCell cell= new BoardCell(i,j);
					if (cellData.length()==1) {
						cell.setInitial(cellData.charAt(0));
						cell.setDoorDirection(DoorDirection.NONE);
						cell.setIsOccupied(false);
						cell.setRoomCenter(false);
						cell.setRoomLabel(false);
						cell.setIsDoorway(false);
						// Test if cell is a walkway or unused space
						if (!cellData.equals("W") && !cellData.equals("X")) {
							cell.setIsRoom(true);
						}
						else {
							cell.setIsRoom(false);
						}
					}
					else {
						char name=cellData.charAt(0);
						char specChar=cellData.charAt(1);
						cell.setInitial(name);
						if (name == 'W') {
							cell.setIsRoom(false);
							cell.setRoomCenter(false);
							cell.setRoomLabel(false);
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
							Room room = roomMap.get(name);
							switch(specChar) {
							case '#':
								cell.setRoomCenter(false);
								cell.setRoomLabel(true);
								room.setLabelCell(cell);
								break;
							case '*':
								cell.setRoomCenter(true);
								cell.setRoomLabel(false);
								room.setCenterCell(cell);
								break;
							default:
								cell.setDoorDirection(DoorDirection.NONE);
								cell.setRoomCenter(false);
								cell.setRoomLabel(false);
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

	public Map<Character, Room> getRoomMap() {
		return roomMap;
	}
}
