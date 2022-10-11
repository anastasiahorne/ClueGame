package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

import experiment.TestBoardCell;

public class Board {

	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private int numRooms;
	
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
    public void initialize() throws FileNotFoundException {
    	this.loadSetupConfig();
    	this.loadLayoutConfig();
    	grid = new BoardCell[numRows][numColumns];
    }
     
 	public void setConfigFiles(String layoutConfigFile,String setupConfigFile) {
 		this.layoutConfigFile = layoutConfigFile;
 		this.setupConfigFile = setupConfigFile;
 	}
 
	//load our txt file so we know what character represents what room, and our room names
	public void loadSetupConfig() throws FileNotFoundException {
		try {
			FileReader reader=new FileReader(setupConfigFile);
			Scanner in=new Scanner(reader);
			char character;
			String roomName;
			String Type;
			while (in.hasNextLine()) {
				String line=in.nextLine();
				if (line.charAt(0)== '/') {
					in.useDelimiter(", ");
					Type=in.next();
					roomName=in.next();
					character=in.nextLine().charAt(0);
					Room room = new Room(roomName);
					roomMap.put(character, room);
				}
			}
			in.close();
		}
		catch (Exception e) {
			System.out.println("Could not find " + setupConfigFile);
		}
	}
	
	//load our csv file, so we know where our rooms are, where our center etc.	
	public void loadLayoutConfig() throws FileNotFoundException {
		roomMap=new HashMap<>();
		try {
			FileReader reader=new FileReader(layoutConfigFile);
			Scanner in=new Scanner(reader);
			in.useDelimiter(",");
			String designation;
			for (int i=0;i<numRows;++i) {
				for (int j=0;j<numColumns;++j) {
					designation=in.next();
					//is not a center, or a name, or a door
					if (designation.length()==1) {
						
					}
				}
			}
			in.close();
		}
		catch (Exception e) {
			System.out.println("Could not find " + layoutConfigFile);
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
	
	// 
	public BoardCell getCell(int i, int j) {
		return new BoardCell(i, j);
	}
	
	// Return the room at the given cell on the board
	public Room getRoom(BoardCell cell) {
		char symbol = cell.getInitial();
		return getRoom(symbol);
	}
	
	// Return the number of rooms on the board
	public int getNumRooms() {
		return numRooms;
	}
}
