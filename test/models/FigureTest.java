package models;

import static org.junit.Assert.*;

import java.awt.Point;

import javax.swing.text.Position;

import org.junit.Before;
import org.junit.Test;

public class FigureTest {
	
	Cell startPosition = new Cell(0, 0);

	@Test
	public void createInstance() {
		Figure figure = getBlackFigure();
		
		assertNotNull(figure);
		assertTrue(figure.isAlive());
		assertFalse(figure.isCrowned());
		assertNotNull(figure.getPossibleMoves());
		assertNotNull(figure.getPosition());
		assertEquals(figure.getColor(), Figure.COLOR.black);
	}

	@Test
	public void killFigure() {
		Figure figure = getBlackFigure();
		figure.setAlive(false);
		
		assertFalse(figure.isAlive());
	}
	
	@Test
	public void crownFigure() {
		Figure figure = getBlackFigure();
		figure.setCrowned(true);
		
		assertTrue(figure.isCrowned());
	}
	
	@Test
	public void changePosition() {
		Figure figure = getBlackFigure();
		
		Cell newPosition = new Cell(0,0);
		figure.setPosition(newPosition);
		
		assertEquals(figure.getPosition(), newPosition);
		assertNotSame(startPosition, newPosition);
	}
	
	private Figure getBlackFigure() {
		return new Figure(startPosition, Figure.COLOR.black);
	}
}
