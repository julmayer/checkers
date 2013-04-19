package de.htwg.checkers.models;

import static org.junit.Assert.*;

import org.junit.Test;

import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Figure;

public class CellTest {

	@Test
	public void createInstance() {
		Cell cell = new Cell(0,0);
		assertNotNull(cell);
	}

	@Test
	public void methodsTests(){
		Cell cell1 = new Cell(0,0);
		assertEquals(0, cell1.getX());
		assertEquals(0, cell1.getY());
		assertNull(cell1.getOccupier());
		assertFalse(cell1.isOccupied());
		Figure occupier1 = new Figure(cell1, Figure.COLOR.black);
		assertTrue(cell1.isOccupied());
		assertNotNull(cell1.getOccupier());
		assertEquals(occupier1, cell1.getOccupier());
	}
	
	@Test
	public void equalityAndHashTest(){
		Cell cell1 = new Cell(0,0);
		Cell cell2 = new Cell(1,1);
		Cell cell3 = new Cell(1,0);
		Cell cell4 = new Cell(0,0);
		assertFalse(cell1.equals(cell2));
		assertFalse(cell1.equals(cell3));
		assertFalse(cell2.equals(cell3));
		assertEquals(cell1, cell1);
		assertEquals(cell1.hashCode(), cell1.hashCode());
		Figure occupier1 = new Figure(cell1, Figure.COLOR.white);
		assertFalse(cell1.equals(occupier1));
		assertEquals(cell4, cell1);
		assertEquals(cell4.hashCode(), cell1.hashCode());
		assertFalse(cell1.equals(null));
	}
	
	
}
