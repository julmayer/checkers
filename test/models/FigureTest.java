package models;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
	}

	@Test
	public void killFigure() {
		blackFigure.kill();
		assertFalse(blackFigure.isAlive());
		assertNull(blackFigure.getPosition());
		assertTrue(whiteFigure.isAlive());
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
		List<Cell> possibles = new LinkedList<>();
		possibles.add(new Cell(5, 5));
		possibles.add(new Cell(6, 6));
		blackFigure.setPossibleMoves(possibles);
		
		assertEquals(possibles, blackFigure.getPossibleMoves());
		assertNotSame(whiteFigure.getPossibleMoves(), blackFigure.getPossibleMoves());
	}
	
	@Test
	public void equality() {
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
		assertEquals(same, blackFigure);
		assertEquals(same.hashCode(), blackFigure.hashCode());
	}
}
