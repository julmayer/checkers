package de.htwg.checkers.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;

public class GameControllerTest {
	GameController gameController;
	
	@Before 
	public void setUp() {
		gameController = new GameController(4);
	}
	
	@Test
	public void createGameController() {
		assertNotNull(gameController);
	}
	
	@Test
	public void gameInit() {
		gameController.gameInit();
		
		Field field = gameController.getField();
		assertTrue(field.getCellByCoordinates(1, 0).isOccupied());
		assertTrue(field.getCellByCoordinates(3, 0).isOccupied());
		assertTrue(field.getCellByCoordinates(0, 3).isOccupied());
		assertTrue(field.getCellByCoordinates(2, 3).isOccupied());
		
		assertFalse(field.getCellByCoordinates(0, 0).isOccupied());
		assertFalse(field.getCellByCoordinates(2, 0).isOccupied());
		assertFalse(field.getCellByCoordinates(0, 1).isOccupied());
		assertFalse(field.getCellByCoordinates(1, 1).isOccupied());
		assertFalse(field.getCellByCoordinates(2, 1).isOccupied());
		assertFalse(field.getCellByCoordinates(3, 1).isOccupied());
		assertFalse(field.getCellByCoordinates(0, 2).isOccupied());
		assertFalse(field.getCellByCoordinates(1, 2).isOccupied());
		assertFalse(field.getCellByCoordinates(2, 2).isOccupied());
		assertFalse(field.getCellByCoordinates(3, 2).isOccupied());
		assertFalse(field.getCellByCoordinates(1, 3).isOccupied());
		assertFalse(field.getCellByCoordinates(3, 3).isOccupied());
		
		assertFalse(field.getCellByCoordinates(1, 0).getOccupier().isBlack());
		assertFalse(field.getCellByCoordinates(3, 0).getOccupier().isBlack());
		assertTrue(field.getCellByCoordinates(0, 3).getOccupier().isBlack());
		assertTrue(field.getCellByCoordinates(2, 3).getOccupier().isBlack());
		
		GameController fc5 = new GameController(5);
		fc5.gameInit();
		Field field5 = fc5.getField();
		for (int x = 0; x < 5; ++x) {
			for (int y = 0; y < 5; ++y) {
				Cell cell = field5.getCellByCoordinates(x, y);
				if (x == 1 || x == 3) {
					if (y == 0) {
						assertTrue(cell.isOccupied());
						assertFalse(cell.getOccupier().isBlack());
					} else if (y == 4) {
						assertTrue(cell.isOccupied());
						assertTrue(cell.getOccupier().isBlack());
					}
				} else {
					assertFalse(cell.isOccupied());
				}
			}
		}
	}
	
	@Test
	public void regularWhiteMoves() {
		Field gamefield = gameController.getField();
		gameController.gameInit();
		gameController.changeColor();
		gameController.createAllMoves();
		
		// both Cells free
		Figure bothFreeL = gameController.getFigureOnField(2, 1);
		Figure bothFreeR = gameController.getFigureOnField(4, 1);
		// only one Cell is free, other is out of Field
		Figure leftBorder = gameController.getFigureOnField(0, 1);
		Figure rightBorder = gameController.getFigureOnField(6, 1);
		// back row, no moves
		Figure nothingFreeLeft = gameController.getFigureOnField(1, 0);
		Figure nothingFreeMiddel = gameController.getFigureOnField(3, 0);
		Figure nothingFreeRight = gameController.getFigureOnField(5, 0);
		
		assertEquals(2, bothFreeL.getPossibleMoves().size());
		assertEquals(2, bothFreeR.getPossibleMoves().size());
		assertEquals(1, leftBorder.getPossibleMoves().size());
		assertEquals(1, rightBorder.getPossibleMoves().size());
		assertEquals(0, nothingFreeLeft.getPossibleMoves().size());
		assertEquals(0, nothingFreeMiddel.getPossibleMoves().size());
		assertEquals(0, nothingFreeRight.getPossibleMoves().size());
		
		Cell c12 = gamefield.getCellByCoordinates(1, 2);
		Cell c32 = gamefield.getCellByCoordinates(3, 2);
		Cell c52 = gamefield.getCellByCoordinates(5, 2);		
		
		assertTrue(leftBorder.getPossibleMoves().contains(c12));
		assertTrue(bothFreeL.getPossibleMoves().contains(c12));
		assertTrue(bothFreeL.getPossibleMoves().contains(c32));
		assertTrue(bothFreeR.getPossibleMoves().contains(c32));
		assertTrue(bothFreeR.getPossibleMoves().contains(c52));
		assertTrue(rightBorder.getPossibleMoves().contains(c52));
		
		Figure dummy = new Figure(c12, false);
		// leftBorader no moves, bothFreeL 1 move
		gameController.createAllMoves();
		assertEquals(1, bothFreeL.getPossibleMoves().size());
		assertEquals(0, leftBorder.getPossibleMoves().size());
		
		assertFalse(bothFreeL.getPossibleMoves().contains(c12));
		assertTrue(bothFreeL.getPossibleMoves().contains(c32));
		
		dummy.kill();
		Figure enemy12 = new Figure(c12, true);
		Figure enemy32 = new Figure(c32, true);
		// only must kills possible.
		gameController.createAllMoves();
		
		assertEquals(2, bothFreeL.getPossibleMoves().size());
		assertEquals(1, bothFreeR.getPossibleMoves().size());
		assertEquals(1, leftBorder.getPossibleMoves().size());
		assertEquals(0, rightBorder.getPossibleMoves().size());
		assertEquals(0, nothingFreeLeft.getPossibleMoves().size());
		assertEquals(0, nothingFreeMiddel.getPossibleMoves().size());
		assertEquals(0, nothingFreeRight.getPossibleMoves().size());
		
		Cell c03 = gamefield.getCellByCoordinates(0, 3);
		Cell c23 = gamefield.getCellByCoordinates(2, 3);
		Cell c43 = gamefield.getCellByCoordinates(4, 3);
		
		assertTrue(leftBorder.getPossibleMoves().contains(c23));
		assertTrue(bothFreeL.getPossibleMoves().contains(c03));
		assertTrue(bothFreeL.getPossibleMoves().contains(c43));
		assertTrue(bothFreeR.getPossibleMoves().contains(c23));
		assertFalse(bothFreeL.getPossibleMoves().contains(c52));
		
		Figure enemy03 = new Figure(c03, true);
		Figure dummy23 = new Figure(c23, false);
		// less moves caus cell behind not free
		gameController.createAllMoves();
		
		assertEquals(1, bothFreeL.getPossibleMoves().size());
		assertEquals(0, leftBorder.getPossibleMoves().size());
		assertTrue(bothFreeL.getPossibleMoves().contains(c43));
		assertFalse(bothFreeL.getPossibleMoves().contains(c03));
	}
}
