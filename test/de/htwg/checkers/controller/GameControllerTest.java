package de.htwg.checkers.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;

public class GameControllerTest {

	@Test
	public void methodTest(){
		GameController fc = new GameController(4);
		fc.gameInit();
		assertNotNull(fc);
		
		Field field = fc.getField();
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
		
		assertEquals(Figure.COLOR.white, field.getCellByCoordinates(1, 0).getOccupier().getColor());
		assertEquals(Figure.COLOR.white, field.getCellByCoordinates(3, 0).getOccupier().getColor());
		assertEquals(Figure.COLOR.black, field.getCellByCoordinates(0, 3).getOccupier().getColor());
		assertEquals(Figure.COLOR.black, field.getCellByCoordinates(2, 3).getOccupier().getColor());
		
		GameController fc5 = new GameController(5);
		fc5.gameInit();
		Field field5 = fc5.getField();
		for (int x = 0; x < 5; ++x) {
			for (int y = 0; y < 5; ++y) {
				Cell cell = field5.getCellByCoordinates(x, y);
				if (x == 1 || x == 3) {
					if (y == 0) {
						assertTrue(cell.isOccupied());
						assertEquals(Figure.COLOR.white, cell.getOccupier().getColor());
					} else if (y == 4) {
						assertTrue(cell.isOccupied());
						assertEquals(Figure.COLOR.black, cell.getOccupier().getColor());
					}
				} else {
					assertFalse(cell.isOccupied());
				}
			}
		}
	}
}
