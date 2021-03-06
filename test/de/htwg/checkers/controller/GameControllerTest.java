package de.htwg.checkers.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.htwg.checkers.models.Bot;
import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.models.Move;

@SuppressWarnings("unused")
public class GameControllerTest {
	GameController gameController4 = new GameController();
	GameController gameController5 = new GameController();
	GameController gameController7 = new GameController();
	GameController controllerWithEasyBot = new GameController();
	GameController controllerWithMediumBot = new GameController();
	
	@Before 
	public void setUp() {
		gameController4.gameInit(4, Bot.NO_BOT);
		gameController5.gameInit(5, Bot.NO_BOT);
		gameController7.gameInit(7, Bot.NO_BOT);
		controllerWithEasyBot.gameInit(4, Bot.SIMPLE_BOT);
		controllerWithMediumBot.gameInit(4, Bot.MEDIUM_BOT);
	}
	
	@Test
	public void createGameController() {
		assertNotNull(gameController4);
		assertNotNull(gameController5);
		assertNotNull(gameController7);
		assertNotNull(controllerWithEasyBot);
		assertEquals(4, gameController4.getFieldSize());
		assertEquals(5, gameController5.getFieldSize());
		assertEquals(7, gameController7.getFieldSize());
	}
	
	@Test
	public void testDifficulty() {
	    controllerWithEasyBot.getGameState();
	}
	
	@Test
	public void gameInit() {
//		assertNotNull(gameController4.getBlacks());
//		assertNotNull(gameController4.getWhites());
		
		assertTrue(gameController4.isBlackTurn());
		assertEquals(0, gameController4.getMoveCount());
		assertTrue(gameController4.getError().isEmpty());
		
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
	
	@Test
	public void testDraw() {
		String expected = "Current situation:\n" + "3  X  -  x  - \n"
				+ "2  -  -  -  - \n" + "1  -  -  -  - \n" + "0  -  o  -  o \n"
				+ "   A  B  C  D \n";
		gameController4.getFigureOnField(0, 3).setCrowned(true);
		assertEquals(expected, gameController4.getDrawingOfField());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testException() {
		new GameController().gameInit(2, Bot.NO_BOT);
	}
	
	@Test
	public void testBot() {
		assertEquals(Bot.SIMPLE_BOT, Bot.valueOf(1));
	}
	
	@Test
	public void regularWhiteMoves() {
		Field gamefield = gameController7.getField();
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
		
		
		Cell c12 = gameController7.getFigureOnField(0, 1).getPossibleMoves().get(0).getTo();
		Cell c32 = null;
		for (Move move : gameController7.getFigureOnField(2, 1).getPossibleMoves()) {
		    if (move.getTo().getX() == 3 && move.getTo().getY() == 2) {
		        c32 = move.getTo();
		    }
		}
		Cell c52 = gameController7.getFigureOnField(6, 1).getPossibleMoves().get(0).getTo();
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
		
		Cell c03 = null;
		for (Move move : bothFreeL.getPossibleMoves()) {
		    if (move.getTo().getX() == 0 && move.getTo().getY() == 3) {
		        c03 = move.getTo();
		    }
		}
		Cell c23 = leftBorder.getPossibleMoves().get(0).getTo();
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
		
		Cell c14 = leftBorder.getPossibleMoves().get(0).getTo();
		Cell c34 = gamefield.getCellByCoordinates(3, 4);
		for (Move move : bothFreeL.getPossibleMoves()) {
		    if (move.getTo().getX() == 3 && move.getTo().getY() == 4) {
		        c34 = move.getTo();
		        break;
		    }
		}
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
	      for (Move move : bothFreeL.getPossibleMoves()) {
	            if (move.getTo().getX() == 0 && move.getTo().getY() == 3) {
	                c03 = move.getTo();
	                break;
	            }
	        }
		Cell c23 = leftBorder.getPossibleMoves().get(0).getTo();
		
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
		Field gameField = gameController7.getField();
		
		Figure queen = gameController7.getFigureOnField(0, 5);
		queen.setCrowned(true);
		gameController7.createAllMoves();
		
		Cell c14 = queen.getPossibleMoves().get(0).getTo();
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
		Figure enemy23 = new Figure(queen.getPossibleMoves().get(0).getTo(), false);
		gameController7.createAllMoves();
		
		assertEquals(1, queen.getPossibleMoves().size());
		assertTrue(queen.getPossibleMoves().contains(qc32));
		
		// must kill
		enemy23.kill();
		Figure queen25 = gameController7.getFigureOnField(2, 5);
		queen25.setCrowned(true);
		Cell c43 = gameField.getCellByCoordinates(4, 3);
		Cell c52 = gameField.getCellByCoordinates(5, 2);
		gameController7.getGameState().changeTurn();
		gameController7.input("G 1 F 2");
		gameController7.getGameState().changeTurn();
		gameController7.createAllMoves();
		for (Move move : gameController7.getFigureOnField(5, 2).getPossibleMoves()) {
		    if (move.getTo().getX() == 4 && move.getTo().getY() == 3) {
		        c43 = move.getTo();
		    }
		}
		gameController7.getGameState().changeTurn();
		gameController7.getFigureOnField(5, 2).kill();
		Figure enemy43 = new Figure(c43, false);
		Move q2c52 = new Move(queen25.getPosition(), c52);
		
		gameController7.createAllMoves();
		
		assertEquals(1, queen25.getPossibleMoves().size());
		assertTrue(queen25.getPossibleMoves().contains(q2c52));
	}
	

	
	@Test
	public void input() {
		assertFalse(gameController7.input("a"));
		assertFalse(gameController7.input("-1 0 0 0"));
		assertFalse(gameController7.input("0 -1 0 0"));
		assertFalse(gameController7.input("0 0 -1 0"));
		assertFalse(gameController7.input("0 0 0 -1"));
		assertFalse(gameController7.input("6 2 0 0"));
		assertFalse(gameController7.input("6 1 5 2"));
		
		gameController7.getFigureOnField(6, 1).kill();
		
		Figure e1 = new Figure(gameController7.getField().getCellByCoordinates(5, 4), false);
		Figure e2 = new Figure(gameController7.getField().getCellByCoordinates(5, 2), false);
		
		assertFalse(gameController7.input("6 5 4 3"));
		assertFalse(gameController7.input("4 3 6 1"));
		
		assertFalse(gameController7.input("0 1 1 2"));
				
	}
	
	@Test
	public void blackKillsWhite() {
		
		assertFalse(gameController4.checkIfWin());
		gameController4.input("0 3 1 2");
		gameController4.input("3 0 2 1");
		gameController4.input("1 2 3 0");
		gameController4.input("1 0 2 1");
		gameController4.input("3 0 1 2");
		assertTrue(gameController4.checkIfWin());
	}
	
	@Test
	public void WhiteKillsBlack() {
		assertFalse(gameController4.checkIfWin());
		gameController4.input("2 3 1 2");
		gameController4.input("1 0 2 1");
		gameController4.input("1 2 0 1");
		gameController4.input("2 1 3 2");
		gameController4.input("0 3 1 2");
		gameController4.input("3 2 2 3");
		gameController4.input("1 2 2 1");
		gameController4.input("3 0 1 2");
		gameController4.input("0 1 1 0");
		gameController4.input("1 2 0 3");
		gameController4.input("1 0 2 1");
		gameController4.input("0 3 3 0");
		assertTrue(gameController4.checkIfWin());
	}
	
	@Test
	public void BlackBlocksWhite() {
	    gameController4.getFigureOnField(3, 0).kill();
		gameController4.input("0 3 1 2");
		gameController4.input("B 0 A 1");
		gameController4.getGameState().changeTurn();
		assertTrue(gameController4.checkIfWin());
	}
	
	@Test
	public void WhiteBlocksBlack() {
	    // remove one black figure
	    gameController4.getFigureOnField(0, 3).kill();
	    gameController4.input("2 3 3 2");
	    gameController4.getGameState().changeTurn();
	    gameController4.input("3 2 2 1");
	    gameController4.getGameState().changeTurn();
		
		assertTrue(gameController4.checkIfWin());
	}
	
	@Test
	public void validateSelectedMove() {
		gameController4.input("0 3 1 2");
		gameController4.input("1 2 2 1");
		
		assertFalse(gameController4.input("3 0 0 3"));
	}
	
	@Test
	public void deleteAllMovesWithoutFigure() {
		gameController7.input("6 5 5 4");
		
		gameController7.getField().getCellByCoordinates(4, 5).getOccupier().kill();
		Figure f = new Figure(gameController7.getField().getCellByCoordinates(1, 2), true);
		Figure f1 = new Figure(gameController7.getField().getCellByCoordinates(3, 4), true);
		gameController7.input("0 1 2 3");
		assertFalse(gameController7.input("2 3 4 5"));
	}
	
	@Test
	public void botMove() {
		controllerWithEasyBot.input("0 3 1 2");
		assertTrue(controllerWithEasyBot.isBlackTurn());
		
		controllerWithMediumBot.input("0 3 1 2");
		assertTrue(controllerWithMediumBot.isBlackTurn());
	}
}
