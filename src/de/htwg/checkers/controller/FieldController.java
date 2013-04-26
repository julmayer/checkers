package de.htwg.checkers.controller;

import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;

public class FieldController {
	
	private Field field;
	private final int rowsToFill;
	
	public FieldController(int size) {
		final int minSize = 3;
		if (size < minSize){
			throw new IllegalArgumentException();
		} else if (size == minSize){
			rowsToFill = 1;
		} else {
			rowsToFill = size/2 - 1;
		}
		field = new Field(size);
	}

	final private void createWhiteFigures(int size) {
		for (int y = 0; y < rowsToFill; y++){
			for (int x = 0; x < size; x++) {
				if (x % 2 == 0 && y % 2 != 0 ){
					new Figure(field.getCellByCoordinates(x, y),Figure.COLOR.white);
				} else if (x % 2 != 0 && y % 2 == 0 ){
					new Figure(field.getCellByCoordinates(x, y),Figure.COLOR.white);
				}
			}
		}
	}
	
	final private void createBlackFigures(int size) {
		if (size % 2 == 0){
			for (int y = size - rowsToFill;y <= size; y++){
				for (int x = 0; x < size; x++) {
					if (x % 2 == 0 && y % 2 != 0 ){
						new Figure(field.getCellByCoordinates(x, y),Figure.COLOR.white);
					} else if (x % 2 != 0 && y % 2 == 0 ){
						new Figure(field.getCellByCoordinates(x, y),Figure.COLOR.white);
					}
				}
			}
		} else {
			for (int y = size - rowsToFill;y <= size; y++){
				for (int x = 0; x < size; x++) {
					if (x % 2 != 0 && y % 2 == 0 ){
						new Figure(field.getCellByCoordinates(x, y),Figure.COLOR.white);
					} else if (x % 2 == 0 && y % 2 != 0 ){
						new Figure(field.getCellByCoordinates(x, y),Figure.COLOR.white);
					}
				}
			}
		}
	}

		
		
		/*for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 8; j++) {
				if (i % 2 == 0 && j % 2 != 0 ){
					new Figure(field.getCellByCoordinates(i, j),Figure.COLOR.white);
				} else if (i % 2 != 0 && j % 2 == 0 ){
					new Figure(field.getCellByCoordinates(i, j),Figure.COLOR.white);
				}
			}
		}
	}
	
	final private void createBlackFigures(int s) {
		for (int i = 5; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i % 2 != 0 && j % 2 == 0 ){
					new Figure(field.getCellByCoordinates(i, j),Figure.COLOR.black);
				} else if (i % 2 == 0 && j % 2 != 0 ){
					new Figure(field.getCellByCoordinates(i, j),Figure.COLOR.black);
				}
			}
		}
	}*/
	
	public void gameInit(int size){
		createBlackFigures(size);
		createWhiteFigures(size);
	}
	
	public Field getField() {
		return this.field;
	}
	
}
