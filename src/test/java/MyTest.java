import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {

	@Test
	void testWin1() throws Exception {
		CFourInfo game = new CFourInfo();       // vertical win
		int[][] gB = {
				{2, 1, 1, 1, 2, 2, 2},
				{2, 1, 2, 1, 2, 1, 2},
				{1, 2, 1, 2, 1, 2, 2},
				{1, 2, 1, 2, 1, 2, 1},
				{2, 2, 1, 2, 1, 2, 1},
				{1, 2, 2, 1, 2, 1, 1},
		};
		ClientGUI b = new ClientGUI();
		assertTrue(b.checkVert(gB));
	}


	@Test
	void testWin2() throws Exception {
		CFourInfo game = new CFourInfo();       // horizontal win
		int[][] gB = {
				{2, 1, 1, 1, 2, 2, 2},
				{2, 1, 1, 2, 2, 1, 2},
				{1, 2, 1, 2, 1, 2, 2},
				{1, 1, 1, 2, 1, 2, 1},
				{2, 2, 2, 2, 1, 2, 1},
				{1, 2, 2, 1, 2, 1, 1},
		};
		ClientGUI b = new ClientGUI();
		assertTrue(b.checkHor(gB));
	}

	@Test
	void testWin3() throws Exception {
		CFourInfo game = new CFourInfo();       // diagonal win (NE)
		int[][] gB = {
				{2, 1, 1, 1, 2, 2, 2},
				{2, 1, 2, 1, 2, 1, 2},
				{1, 2, 1, 2, 1, 2, 2},
				{1, 2, 1, 1, 1, 2, 1},
				{2, 2, 1, 2, 1, 2, 1},
				{1, 1, 2, 1, 2, 1, 1},
		};
		ClientGUI b = new ClientGUI();
		assertTrue(b.checkDiagonal(gB));
	}

	@Test
	void testWin4() throws Exception {
		CFourInfo game = new CFourInfo();       // diagonal win (NW)
		int[][] gB = {
				{2, 1, 1, 1, 2, 2, 2},
				{2, 1, 2, 1, 2, 1, 2},
				{1, 2, 1, 2, 2, 1, 2},
				{1, 2, 1, 1, 1, 2, 1},
				{2, 2, 1, 2, 1, 2, 1},
				{1, 1, 2, 1, 2, 2, 1},
		};
		ClientGUI b = new ClientGUI();
		assertTrue(b.checkDiagonal(gB));
	}

	@Test
	void testWin5() throws Exception {
		CFourInfo game = new CFourInfo();       // check tie
		int[][] gB = {
				{2, 1, 1, 1, 2, 2, 2},
				{2, 1, 2, 1, 2, 1, 2},
				{1, 2, 1, 2, 2, 1, 2},
				{1, 2, 1, 2, 1, 2, 1},
				{2, 2, 1, 2, 1, 2, 1},
				{1, 1, 2, 1, 2, 2, 1},
		};
		ClientGUI b = new ClientGUI();
		assertFalse(b.checkHor(gB));
		assertFalse(b.checkVert(gB));
		assertFalse(b.checkDiagonal(gB));
		assertTrue(b.checkTie(gB));



	}









}

