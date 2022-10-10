package clueGame;

import java.util.Map;

public class Board {

	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character,Room> roomMap;
	private static Board theInstance;
	
	public void initialize() {
		
	}
	//this should throw badconfig exception
	public void loadSetupConfig() {
		
	}
	//this should throw badconfig exception
	public void loadLayoutConfig() {
		
	}
	public static Board getInstance() {
		return theInstance;
	}
}
