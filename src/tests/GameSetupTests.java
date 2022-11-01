package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;

class GameSetupTests {
	private static Board board;
	@BeforeEach
	void setUp() {
		board=Board.getInstance();
	}

}
