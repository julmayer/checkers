package de.htwg.checkers.models;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
		blackFigure = new Figure(startPosition, true);
		whiteFigure = new Figure(startPosition, false);
	}

	@Test
	public void createBlackFigure() {
		
		assertNotNull(blackFigure);
		assertTrue(blackFigure.isAlive());
		assertFalse(blackFigure.isCrowned());
		assertNotNull(blackFigure.getPossibleMoves());
		assertEquals(startPosition, blackFigure.getPosition());
		assertTrue(blackFigure.isBlack());
		assertNotSame(whiteFigure, blackFigure);
	}
	
	@Test
	public void createWhiteFigure() {
		
		assertNotNull(whiteFigure);
		assertTrue(whiteFigure.isAlive());
		assertFalse(whiteFigure.isCrowned());
		assertNotNull(whiteFigure.getPossibleMoves());
		assertEquals(startPosition, whiteFigure.getPosition());
		assertFalse(whiteFigure.isBlack());
		assertNotSame(blackFigure, whiteFigure);
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
		List<Move> possibles = new LinkedList<Move>();
		possibles.add(new Move(blackFigure.getPosition(), new Cell(5, 5)));
		possibles.add(new Move(blackFigure.getPosition(), new Cell(6, 6)));
		blackFigure.setPossibleMoves(possibles);
		
		assertEquals(possibles, blackFigure.getPossibleMoves());
		assertNotSame(whiteFigure.getPossibleMoves(), blackFigure.getPossibleMoves());
	}
	
	@Test
	public void equalityAndHashCode() {
		Figure otherPosition = new Figure(new Cell(1, 1), true);
		Figure same = new Figure(startPosition, true);
		assertEquals(blackFigure, blackFigure);
		assertEquals(blackFigure.hashCode(), blackFigure.hashCode());
		assertFalse(blackFigure.equals(whiteFigure));
		assertFalse(blackFigure.equals(startPosition));
		assertFalse(blackFigure.equals(otherPosition));
		assertFalse(blackFigure.equals(null));
		assertEquals(same, blackFigure);
		assertEquals(same.hashCode(), blackFigure.hashCode());
		blackFigure.kill();
	}
	
	@Test
	public void killMoves() {
		assertFalse(blackFigure.hasKillMoves());
		Move move = new Move(true, blackFigure.getPosition(), new Cell(2, 2));
		blackFigure.getPossibleMoves().add(move);
		assertTrue(blackFigure.hasKillMoves());
	}
}
