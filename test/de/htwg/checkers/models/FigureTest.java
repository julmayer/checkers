package de.htwg.checkers.models;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Figure;

public class FigureTest {
	
	Cell startPosition;
	Figure blackFigure;
	Figure whiteFigure;
	
	@Before 
	public void setUp() {
		startPosition = new Cell(0, 0);
		blackFigure = new Figure(startPosition, Figure.COLOR.black);
		whiteFigure = new Figure(startPosition, Figure.COLOR.white);
	}

	@Test
	public void createBlackFigure() {
		
		assertNotNull(blackFigure);
		assertTrue(blackFigure.isAlive());
		assertFalse(blackFigure.isCrowned());
		assertNotNull(blackFigure.getPossibleMoves());
		assertEquals(startPosition, blackFigure.getPosition());
		assertEquals(Figure.COLOR.black, blackFigure.getColor());
		assertNotSame(whiteFigure, blackFigure);
		assertFalse(blackFigure.isMustKillMoves());
	}
	
	@Test
	public void createWhiteFigure() {
		
		assertNotNull(whiteFigure);
		assertTrue(whiteFigure.isAlive());
		assertFalse(whiteFigure.isCrowned());
		assertNotNull(whiteFigure.getPossibleMoves());
		assertEquals(startPosition, whiteFigure.getPosition());
		assertEquals(Figure.COLOR.white, whiteFigure.getColor());
		assertNotSame(blackFigure, whiteFigure);
		assertFalse(blackFigure.isMustKillMoves());
	}

	@Test
	public void killFigure() {
		Cell oldPosition = blackFigure.getPosition();
		blackFigure.kill();
		assertFalse(blackFigure.isAlive());
		assertNull(blackFigure.getPosition());
		assertTrue(whiteFigure.isAlive());
		assertNotNull(blackFigure.hashCode());
		assertNull(oldPosition.getOccupier());		
	}
	
	@Test
	public void crownFigure() {
		blackFigure.setCrowned(true);
		
		assertTrue(blackFigure.isCrowned());
		assertFalse(whiteFigure.isCrowned());
	}
	
	@Test
	public void changePosition() {
		Cell newPosition = new Cell(1,1);
		blackFigure.setPosition(newPosition);
		
		assertEquals(newPosition, blackFigure.getPosition());
		assertNotSame(blackFigure.getPosition(), startPosition);
	}
	
	@Test
	public void changePossibleMoves() {
		List<Cell> possibles = new LinkedList<Cell>();
		possibles.add(new Cell(5, 5));
		possibles.add(new Cell(6, 6));
		blackFigure.setPossibleMoves(possibles);
		
		assertEquals(possibles, blackFigure.getPossibleMoves());
		assertNotSame(whiteFigure.getPossibleMoves(), blackFigure.getPossibleMoves());
	}
	
	@Test
	public void equalityAndHashCode() {
		Figure otherPosition = new Figure(new Cell(1, 1), Figure.COLOR.black);
		Figure notAlive = new Figure(startPosition, Figure.COLOR.black);
		notAlive.kill();
		Figure same = new Figure(startPosition, Figure.COLOR.black);
		assertEquals(blackFigure, blackFigure);
		assertEquals(blackFigure.hashCode(), blackFigure.hashCode());
		assertFalse(blackFigure.equals(whiteFigure));
		assertFalse(blackFigure.equals(startPosition));
		assertFalse(blackFigure.equals(otherPosition));
		assertFalse(blackFigure.equals(notAlive));
		assertFalse(blackFigure.equals(null));
		assertEquals(same, blackFigure);
		assertEquals(same.hashCode(), blackFigure.hashCode());
		blackFigure.kill();
		assertEquals(notAlive, blackFigure);
	}
	
	@Test
	public void enumTest() {
		Figure.COLOR[] colors = Figure.COLOR.values();
		Figure.COLOR[] testcolors = new Figure.COLOR[2];
		Figure.COLOR white = Figure.COLOR.valueOf("white");
		Figure.COLOR black = Figure.COLOR.valueOf(Figure.COLOR.class, "black");
		testcolors[0] = white;
		testcolors[1] = black;
		assertArrayEquals(testcolors, colors);		
	}
}
