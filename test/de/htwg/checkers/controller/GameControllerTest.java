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
import de.htwg.checkers.models.Move;

public class GameControllerTest {
	GameController gameController4;
	GameController gameController5;
	GameController gameController7;
	
	@Before 
	public void setUp() {
		gameController4 = new GameController(4);
		gameController5 = new GameController(5);
		gameController7 = new GameController(7);
	}
	
	@Test
	public void createGameController() {
		assertNotNull(gameController4);
		assertNotNull(gameController5);
		assertNotNull(gameController7);
		assertEquals(4, gameController4.getFieldSize());
		assertEquals(5, gameController5.getFieldSize());
		assertEquals(7, gameController7.getFieldSize());
	}
	
	@Test
	public void gameInit() {
		gameController4.gameInit();
		
		Field field = gameController4.getField();
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
		
		gameController5.gameInit();
		Field field5 = gameController5.getField();
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
	
	@Test(expected=IllegalArgumentException.class)
	public void testException() {
		new GameController(2);
	}
	
	@Test
	public void regularWhiteMoves() {
		Field gamefield = gameController7.getField();
		gameController7.gameInit();
		gameController7.input("6 5 5 4");
		gameController7.createAllMoves();
		
		// both Cells free
		Figure bothFreeL = gameController7.getFigureOnField(2, 1);
		Figure bothFreeR = gameController7.getFigureOnField(4, 1);
		// only one Cell is free, other is out of Field
		Figure leftBorder = gameController7.getFigureOnField(0, 1);
		Figure rightBorder = gameController7.getFigureOnField(6, 1);
		// back row, no moves
		Figure nothingFreeLeft = gameController7.getFigureOnField(1, 0);
		Figure nothingFreeMiddel = gameController7.getFigureOnField(3, 0);
		Figure nothingFreeRight = gameController7.getFigureOnField(5, 0);
		
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
		Move lbC12 = new Move(leftBorder.getPosition(), c12);
		Move bflC12 = new Move(bothFreeL.getPosition(), c12);
		Move bflC32 = new Move(bothFreeL.getPosition(), c32);
		Move bfrC32 = new Move(bothFreeR.getPosition(), c32);
		Move bfrC52 = new Move(bothFreeR.getPosition(), c52);
		Move rbC52 = new Move(rightBorder.getPosition(), c52);
		
		
		assertTrue(leftBorder.getPossibleMoves().contains(lbC12));
		assertTrue(bothFreeL.getPossibleMoves().contains(bflC12));
		assertTrue(bothFreeL.getPossibleMoves().contains(bflC32));
		assertTrue(bothFreeR.getPossibleMoves().contains(bfrC32));
		assertTrue(bothFreeR.getPossibleMoves().contains(bfrC52));
		assertTrue(rightBorder.getPossibleMoves().contains(rbC52));
		
		Figure dummy = new Figure(c12, false);
		// leftBorader no moves, bothFreeL 1 move
		gameController7.createAllMoves();
		assertEquals(1, bothFreeL.getPossibleMoves().size());
		assertEquals(0, leftBorder.getPossibleMoves().size());
		
		assertFalse(bothFreeL.getPossibleMoves().contains(bflC12));
		assertTrue(bothFreeL.getPossibleMoves().contains(bflC32));
		
		dummy.kill();
		Figure enemy12 = new Figure(c12, true);
		Figure enemy32 = new Figure(c32, true);
		// only must kills possible.
		gameController7.createAllMoves();
		
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
		Cell c63 = gamefield.getCellByCoordinates(6, 3);
		Move lbC23 = new Move(leftBorder.getPosition(), c23);
		Move bflC03 = new Move(bothFreeL.getPosition(), c03);
		Move bflC43 = new Move(bothFreeL.getPosition(), c43);
		Move bfrC63 = new Move(bothFreeR.getPosition(), c63);
		Move bfrC23 = new Move(bothFreeR.getPosition(), c23);
		
		assertTrue(leftBorder.getPossibleMoves().contains(lbC23));
		assertTrue(bothFreeL.getPossibleMoves().contains(bflC03));
		assertTrue(bothFreeL.getPossibleMoves().contains(bflC43));
		assertTrue(bothFreeR.getPossibleMoves().contains(bfrC23));
		assertFalse(bothFreeR.getPossibleMoves().contains(bfrC63));
		
		Figure enemy03 = new Figure(c03, true);
		Figure dummy23 = new Figure(c23, false);
		// less moves caus cell behind not free
		gameController7.createAllMoves();
		
		assertEquals(1, bothFreeL.getPossibleMoves().size());
		assertEquals(0, leftBorder.getPossibleMoves().size());
		assertTrue(bothFreeL.getPossibleMoves().contains(bflC43));
		assertFalse(bothFreeL.getPossibleMoves().contains(bflC03));		
	}
	
	@Test
	public void regularBlackMoves() {
		Field gamefield = gameController7.getField();
		gameController7.gameInit();
		gameController7.createAllMoves();
		
		// both Cells free
		Figure bothFreeL = gameController7.getFigureOnField(2, 5);
		Figure bothFreeR = gameController7.getFigureOnField(4, 5);
		// only one Cell is free, other is out of Field
		Figure leftBorder = gameController7.getFigureOnField(0, 5);
		Figure rightBorder = gameController7.getFigureOnField(6, 5);
		// back row, no moves
		Figure nothingFreeLeft = gameController7.getFigureOnField(1, 6);
		Figure nothingFreeMiddel = gameController7.getFigureOnField(3, 6);
		Figure nothingFreeRight = gameController7.getFigureOnField(5, 6);
		
		assertEquals(2, bothFreeL.getPossibleMoves().size());
		assertEquals(2, bothFreeR.getPossibleMoves().size());
		assertEquals(1, leftBorder.getPossibleMoves().size());
		assertEquals(1, rightBorder.getPossibleMoves().size());
		assertEquals(0, nothingFreeLeft.getPossibleMoves().size());
		assertEquals(0, nothingFreeMiddel.getPossibleMoves().size());
		assertEquals(0, nothingFreeRight.getPossibleMoves().size());
		
		Cell c14 = gamefield.getCellByCoordinates(1, 4);
		Cell c34 = gamefield.getCellByCoordinates(3, 4);
		Cell c54 = gamefield.getCellByCoordinates(5, 4);
		Move lbC14 = new Move(leftBorder.getPosition(), c14);
		Move bflC14 = new Move(bothFreeL.getPosition(), c14);
		Move bflC34 = new Move(bothFreeL.getPosition(), c34);
		Move bfrC34 = new Move(bothFreeR.getPosition(), c34);
		Move bfrC54 = new Move(bothFreeR.getPosition(), c54);
		Move rbC54 = new Move(rightBorder.getPosition(), c54);
		
		assertTrue(leftBorder.getPossibleMoves().contains(lbC14));
		assertTrue(bothFreeL.getPossibleMoves().contains(bflC14));
		assertTrue(bothFreeL.getPossibleMoves().contains(bflC34));
		assertTrue(bothFreeR.getPossibleMoves().contains(bfrC34));
		assertTrue(bothFreeR.getPossibleMoves().contains(bfrC54));
		assertTrue(rightBorder.getPossibleMoves().contains(rbC54));
		
		Figure dummy = new Figure(c14, true);
		// leftBorader no moves, bothFreeL 1 move
		gameController7.createAllMoves();
		assertEquals(1, bothFreeL.getPossibleMoves().size());
		assertEquals(0, leftBorder.getPossibleMoves().size());
		
		assertFalse(bothFreeL.getPossibleMoves().contains(bflC14));
		assertTrue(bothFreeL.getPossibleMoves().contains(bflC34));
		
		dummy.kill();
		Figure enemy14 = new Figure(c14, false);
		Figure enemy34 = new Figure(c34, false);
		// only must kills possible.
		gameController7.createAllMoves();
		
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
		Cell c63 = gamefield.getCellByCoordinates(6, 3);
		Move lbC23 = new Move(leftBorder.getPosition(), c23);
		Move bflC03 = new Move(bothFreeL.getPosition(), c03);
		Move bflC43 = new Move(bothFreeL.getPosition(), c43);
		Move bfrC63 = new Move(bothFreeR.getPosition(), c63);
		Move bfrC23 = new Move(bothFreeR.getPosition(), c23);
		
		assertTrue(leftBorder.getPossibleMoves().contains(lbC23));
		assertTrue(bothFreeL.getPossibleMoves().contains(bflC03));
		assertTrue(bothFreeL.getPossibleMoves().contains(bflC43));
		assertTrue(bothFreeR.getPossibleMoves().contains(bfrC23));
		assertFalse(bothFreeR.getPossibleMoves().contains(bfrC63));
		
		Figure enemy03 = new Figure(c03, false);
		Figure dummy23 = new Figure(c23, true);
		// less moves caus cell behind not free
		gameController7.createAllMoves();
		
		assertEquals(1, bothFreeL.getPossibleMoves().size());
		assertEquals(0, leftBorder.getPossibleMoves().size());
		assertTrue(bothFreeL.getPossibleMoves().contains(bflC43));
		assertFalse(bothFreeL.getPossibleMoves().contains(bflC03));		
	}
	
	@Test
	public void crownedBlackMoves()
	{
		gameController7.gameInit();
		Field gameField = gameController7.getField();
		
		Figure queen = gameController7.getFigureOnField(0, 5);
		queen.setCrowned(true);
		
		Cell c14 = gameField.getCellByCoordinates(1, 4);
		Cell c23 = gameField.getCellByCoordinates(2, 3);
		Cell c32 = gameField.getCellByCoordinates(3, 2);
		Move qc14 = new Move(queen.getPosition(), c14);
		Move qc23 = new Move(queen.getPosition(), c23);
		Move qc32 = new Move(queen.getPosition(), c32);
		
		// normal moves, no kill
		gameController7.createAllMoves();
		
		assertEquals(3, queen.getPossibleMoves().size());
		assertTrue(queen.getPossibleMoves().contains(qc14));
		assertTrue(queen.getPossibleMoves().contains(qc23));
		assertTrue(queen.getPossibleMoves().contains(qc32));
		
		// no moves
		Figure dummy = new Figure(c14, true);
		gameController7.createAllMoves();
		
		assertEquals(0, queen.getPossibleMoves().size());
		
		// short kill
		dummy.kill();
		Figure enemy14 = new Figure(c14, false);
		gameController7.createAllMoves();
		
		assertEquals(1, queen.getPossibleMoves().size());
		assertTrue(queen.getPossibleMoves().contains(qc23));
		
		// long kill
		enemy14.kill();
		Figure enemy23 = new Figure(c23, false);
		gameController7.createAllMoves();
		
		assertEquals(1, queen.getPossibleMoves().size());
		assertTrue(queen.getPossibleMoves().contains(qc32));
		
		// must kill
		enemy23.kill();
		Figure queen25 = gameController7.getFigureOnField(2, 5);
		queen25.setCrowned(true);
		Cell c43 = gameField.getCellByCoordinates(4, 3);
		Cell c52 = gameField.getCellByCoordinates(5, 2);
		Figure enemy43 = new Figure(c43, false);
		Move q2c52 = new Move(queen25.getPosition(), c52);
		
		gameController7.createAllMoves();
		
		assertEquals(1, queen25.getPossibleMoves().size());
		assertTrue(queen25.getPossibleMoves().contains(q2c52));
	}
	
	@Test
	public void crownedWhiteMoves()
	{
		gameController7.gameInit();
		Field gameField = gameController7.getField();
		
		Figure queen = gameController7.getFigureOnField(0, 5);
		queen.setCrowned(true);
		
		Cell c14 = gameField.getCellByCoordinates(1, 4);
		Cell c23 = gameField.getCellByCoordinates(2, 3);
		Cell c32 = gameField.getCellByCoordinates(3, 2);
		Move qc14 = new Move(queen.getPosition(), c14);
		Move qc23 = new Move(queen.getPosition(), c23);
		Move qc32 = new Move(queen.getPosition(), c32);
		
		// normal moves, no kill
		gameController7.createAllMoves();
		
		assertEquals(3, queen.getPossibleMoves().size());
		assertTrue(queen.getPossibleMoves().contains(qc14));
		assertTrue(queen.getPossibleMoves().contains(qc23));
		assertTrue(queen.getPossibleMoves().contains(qc32));
		
		// no moves
		Figure dummy = new Figure(c14, true);
		gameController7.createAllMoves();
		
		assertEquals(0, queen.getPossibleMoves().size());
		
		// short kill
		dummy.kill();
		Figure enemy14 = new Figure(c14, false);
		gameController7.createAllMoves();
		
		assertEquals(1, queen.getPossibleMoves().size());
		assertTrue(queen.getPossibleMoves().contains(qc23));
		
		// long kill
		enemy14.kill();
		Figure enemy23 = new Figure(c23, false);
		gameController7.createAllMoves();
		
		assertEquals(1, queen.getPossibleMoves().size());
		assertTrue(queen.getPossibleMoves().contains(qc32));
		
		// must kill
		enemy23.kill();
		Figure queen25 = gameController7.getFigureOnField(2, 5);
		queen25.setCrowned(true);
		Cell c43 = gameField.getCellByCoordinates(4, 3);
		Cell c52 = gameField.getCellByCoordinates(5, 2);
		Figure enemy43 = new Figure(c43, false);
		Move q2c52 = new Move(queen25.getPosition(), c52);
		
		gameController7.createAllMoves();
		
		assertEquals(1, queen25.getPossibleMoves().size());
		assertTrue(queen25.getPossibleMoves().contains(q2c52));
	}
}
