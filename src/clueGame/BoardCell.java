package clueGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;


public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	private boolean isRoom;
	private boolean isOccupied;
	private boolean isDoorway;
	private boolean isSecretPassage;
	
	// Constructor that specifies the row and column and sets all variables to the default
	public BoardCell(int i, int j) {
		row = i;
		col = j;
		adjList = new HashSet<BoardCell>();
		roomLabel = false;
		roomCenter = false;
		doorDirection = DoorDirection.NONE;
		isRoom = false;
		isOccupied = false;
		isDoorway = false;
		isSecretPassage = false;
	}
	
	// Each BoardCell can draw itself
	public void draw(Graphics g, int width, int height) {
		// Draw a rectangle (X offset: col * width, Y offset: row * height)
		// Unused spaces (X)
		if (getInitial() == 'X') {
			g.setColor(Color.BLACK);
			g.fillRect(col * width, row * height, width, height);
			g.drawRect(col * width, row * height, width, height);
		}
		// Walkways (W)
		if (getInitial() == 'W') {
			g.setColor(Color.YELLOW);
			g.fillRect(col * width, row * height, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(col * width, row * height, width, height);
			if (isDoorway) {
				g.setColor(Color.BLUE);
				Graphics2D g2D= (Graphics2D) g;
			switch (doorDirection) {
			case LEFT:
				g2D.setStroke(new BasicStroke(4));
				g.drawLine(col*width, row*height, col*width,row*height+ height);
				break;
			case RIGHT:
				g2D.setStroke(new BasicStroke(4));
				g.drawLine(col*width +width,row* height, col*width +width,row*height+ height);
				break;
			case DOWN:
				g2D.setStroke(new BasicStroke(4));
				g.drawLine(col*width, row*height+height, col*width +width, row*height+ height);
				break;
			case UP:
				g2D.setStroke(new BasicStroke(4));
				g.drawLine(col*width, row*height, col*width +width, row*height);
				
				break;
			}
			g2D.setStroke(new BasicStroke(1));
			}
		}
		// Rooms (Other)
		else {
			g.setColor(Color.GRAY);
			g.fillRect(col * width, row * height, width, height);
			g.drawRect(col * width, row * height, width, height);
		}
	}
	
	// Getter for isSecretPassage
	public boolean isSecretPassage() {
		return isSecretPassage;
	}

	// Setter for isSecretPassage
	public void setSecretPassage(boolean isSecretPassage) {
		this.isSecretPassage = isSecretPassage;
	}

	// Add cell to the adjacency list for the cell
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	
	// Returns the adjacency list for the cell
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	
	// Indicates that a cell is part of a room
	public void setIsRoom(boolean room) {
		isRoom = room;
	}

	// Get whether or not a cell is part of a room
	public boolean getIsRoom() {
		return isRoom;
	}
		
	// Setter for indicating a cell is occupied by another player
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}
		
	// Getter for indicating a cell is occupied by another player
	public boolean getIsOccupied() {
		return isOccupied;
	}

	// Getter for isDoorway variable
	public boolean isDoorway() {
		return isDoorway;
	}
	
	// Setter for isDoorway variable
	public void setIsDoorway(boolean isDoorway) {
		this.isDoorway = isDoorway;
	}
	
	// Set door direction
	public void setDoorwayAttributes(BoardCell cell, char doorDirection) {
		cell.setIsDoorway(true);
		switch(doorDirection) {
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

	// Getter for doorDirection variable
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	// Setter for doorDirection variable
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

	// Getter for roomLabel variable
	public boolean isLabel() {
		return roomLabel;
	}
	
	// Setter for roomLabel variable
	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}

	// Getter for roomCenter variable
	public boolean isRoomCenter() {
		return roomCenter;
	}
	
	// Setter for roomCenter variable
	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}
	
	// Getter for secretPassage variable
	public char getSecretPassage() {
		return secretPassage;
	}
	
	// Setter for secretPassage variable
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	// Getter for initial variable
	public char getInitial() {
		return initial;
	}
	
	// Setter for initial variable
	public void setInitial(char initial) {
		this.initial = initial;
	}
	
	@Override
	public String toString() {
		return "(" + row + ", " + col + ")";
	}
	
	// Set attributes of cells with special characters
	void setSpecialRoomAttributes(Board board, char initial, char specChar) {
		setInitial(initial);
		// If doorway, set direction
		if (initial == 'W') {
			setDoorwayAttributes(this, specChar);
		}
		else {
			setIsRoom(true);
			Room room = board.getRoomMap().get(initial);
			switch(specChar) {
			case '#':
				// Set cell to be room label
				setRoomLabel(true);
				room.setLabelCell(this);
				break;
			case '*':
				// Set cell to be room center
				setRoomCenter(true);
				room.setCenterCell(this);
				break;
			default:
				// Set cell to be secretPassage
				setSecretPassage(true);
				setSecretPassage(specChar);
				break;
			}
		}
	}
}
