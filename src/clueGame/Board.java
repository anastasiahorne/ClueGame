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
	private Map<Character, String> roomMap;
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
     public void initialize(){
    	 roomMap=new HashMap<>();
    	 grid = new BoardCell[numRows][numColumns];
 		for (int i = 0; i < numRows; i++) {
 			for (int j = 0; j < numColumns; j++) {
 				grid[i][j] = new BoardCell(i, j);
 			}
 		}
     }
     
 	public void setLayoutConfigFile(String layoutConfigFile) {
 		this.layoutConfigFile = layoutConfigFile;
 	}
 	public void setSetupConfigFile(String setupConfigFile) {
 		this.setupConfigFile = setupConfigFile;
 	}
	
	//this should throw badconfig exception
	public void loadSetupConfig() throws FileNotFoundException{
		FileReader reader=new FileReader(setupConfigFile);
		Scanner in=new Scanner(reader);
		char character;
		String roomName;
		String Type;
		while (in.hasNextLine()) {
			String line=in.nextLine();
			try {
				if (line.charAt(0)== '/') {
					in.useDelimiter(", ");
					Type=in.next();
					roomName=in.next();
					character=in.nextLine().charAt(0);
					roomMap.put(character,roomName);
				}
			}
			catch(FileNotFoundException e) {
		}
		
	}
	//this should throw badconfig exception
	public void loadLayoutConfig() {
		
	}
}
