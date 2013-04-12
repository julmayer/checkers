package models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
		cell1.setOccupier(occupier1);
		assertTrue(cell1.isOccupied());
		assertNotNull(cell1.getOccupier());
	}
	
	@Test
	public void equalityTest(){
		Cell cell1 = new Cell(0,0);
		Cell cell2 = new Cell(1,1);
		Cell cell3 = new Cell(1,0);
		Cell cell4 = new Cell(0,0);
		assertFalse(cell1.equals(cell2));
		assertFalse(cell1.equals(cell3));
		assertFalse(cell2.equals(cell3));
		assertTrue(cell1.equals(cell1));
		Figure occupier1 = new Figure(cell1, Figure.COLOR.white);
		assertFalse(cell1.equals(occupier1));
		assertTrue(cell1.equals(cell4));
	}
	
	
}
