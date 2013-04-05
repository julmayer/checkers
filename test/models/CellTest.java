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
	public void getterTest(){
		Cell cell = new Cell(0,0);
		assertEquals(0, cell.getX());
		assertEquals(0, cell.getY());
		assertNull(cell.getOccupier());
	}

	@Test
	public void setterTest(){
		Cell cell = new Cell(0,0);
		Figure occupier = new Figure();
		cell.setOccupier(occupier);
		assertNotNull(cell.getOccupier());
	}
	
}
