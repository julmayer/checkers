package de.htwg.checkers.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class MoveTest {

	private Cell from;
	private Cell simpleTo;
	private Cell killTo;
	private Move compareSimpleMove;
	
	@Before
	public void setUp() throws Exception {
		from = new Cell(0, 0);
		simpleTo = new Cell(1, 1);
		killTo = new Cell(2, 2);
		compareSimpleMove = new Move(from, simpleTo);
	}

	@Test
	public void createSimpleMove() {
		Move simpleMove = new Move(from, simpleTo);
		assertNotNull(simpleMove);
		assertEquals(from, simpleMove.getFrom());
		assertEquals(simpleTo, simpleMove.getTo());
		assertFalse(simpleMove.isKill());
	}
	
	@Test
	public void createKillMove() {
		Move killMove = new Move(true, from, killTo);
		assertNotNull(killMove);
		assertEquals(from, killMove.getFrom());
		assertEquals(killTo, killMove.getTo());
		assertTrue(killMove.isKill());
	}
	
	@Test
	public void equalityAndHashCode() {
		assertEquals(compareSimpleMove, compareSimpleMove);
		assertEquals(compareSimpleMove.hashCode(), compareSimpleMove.hashCode());
		
		assertFalse(compareSimpleMove.equals(null));
		assertFalse(compareSimpleMove.equals(from));
		
		Move fromNull = new Move(null, simpleTo);
		assertFalse(fromNull.equals(compareSimpleMove));
		assertNotSame(compareSimpleMove.hashCode(), fromNull.hashCode());
		
		Move otherform = new Move(new Cell(2,0), simpleTo);
		assertFalse(compareSimpleMove.equals(otherform));
		
		
		Move toNull = new Move(from, null);
		assertFalse(toNull.equals(compareSimpleMove));
		assertNotSame(compareSimpleMove.hashCode(), toNull.hashCode());
		
		Move otherTo = new Move(from, killTo);
		assertFalse(compareSimpleMove.equals(otherTo));
		Move same = new Move(from, simpleTo);
		assertEquals(compareSimpleMove, same);
		assertEquals(compareSimpleMove.hashCode(), same.hashCode());
	}

	@Test
	public void LastSkipedCell()
	{
		int compareX, compareY, testX, testY;
		compareX = from.getX();
		compareY = from.getY();
		Map<String, Integer> map = compareSimpleMove.getCoordinatesLastSkipedCell();
		testX = map.get("X");
		testY = map.get("Y");
		assertEquals(compareX, testX);
		assertEquals(compareY, testY);
		
		Cell middle = new Cell(3,3);
		
		Move upperLeft = new Move(true, middle, new Cell(1, 5));
		map = upperLeft.getCoordinatesLastSkipedCell();
		assertEquals(Integer.valueOf(2), map.get("X"));
		assertEquals(Integer.valueOf(4), map.get("Y"));
		
		Move upperRight = new Move(true, middle, new Cell(5, 5));
		map = upperRight.getCoordinatesLastSkipedCell();
		assertEquals(Integer.valueOf(4), map.get("X"));
		assertEquals(Integer.valueOf(4), map.get("Y"));
		
		Move lowerLeft = new Move(true, middle, new Cell(1, 1));
		map = lowerLeft.getCoordinatesLastSkipedCell();
		assertEquals(Integer.valueOf(2), map.get("X"));
		assertEquals(Integer.valueOf(2), map.get("Y"));
		
		Move lowerRight = new Move(true, middle, new Cell(5, 1));
		map = lowerRight.getCoordinatesLastSkipedCell();
		assertEquals(Integer.valueOf(4), map.get("X"));
		assertEquals(Integer.valueOf(2), map.get("Y"));
	}
}