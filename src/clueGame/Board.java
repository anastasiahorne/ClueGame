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
	private Map<Character,String> roomKey;
	
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
			roomKey=new HashMap<>();
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
					roomKey.put(character,roomName);
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

	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}

	public Room getRoom(char c) {
		return new Room();
	}

	public BoardCell getCell(int i, int j) {
		return new BoardCell(i, j);
	}

	public Room getRoom(BoardCell cell) {
		return new Room();
	}

	public int getNumRooms() {
		return 0;
	}

	public Room getRoom(String string) {
		return new Room();
	}
}
