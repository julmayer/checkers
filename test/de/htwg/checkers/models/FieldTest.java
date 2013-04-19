package de.htwg.checkers.models;

import static org.junit.Assert.*;

import org.junit.Test;

import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;

public class FieldTest {

	@Test
	public void createInstance() {
		Field field = new Field();
		Cell cell = new Cell(5,4);
		assertNotNull(field);
		assertNotNull(field.getField());
		assertEquals(field.getCellByCoordinates(5, 4), cell);
	}
}
