package clueGame;

import java.util.Set;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassge;
	private Set<BoardCell> adjList;
	
	public BoardCell(int i, int j) {
		// TODO Auto-generated constructor stub
	}

	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}
	
}
