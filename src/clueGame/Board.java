package clueGame;

import java.util.HashSet;
import java.util.Map;

import experiment.TestBoardCell;

public class Board {

	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character,Room> roomMap;
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
    	 grid = new BoardCell[numRows][numColumns];
 		for (int i = 0; i < numRows; i++) {
 			for (int j = 0; j < numColumns; j++) {
 				grid[i][j] = new BoardCell(i, j);
 			}
 		}
     }
	
	//this should throw badconfig exception
	public void loadSetupConfig() {
		
	}
	//this should throw badconfig exception
	public void loadLayoutConfig() {
		
	}
}
