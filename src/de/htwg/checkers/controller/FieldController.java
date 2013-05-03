package de.htwg.checkers.controller;

import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;

public class FieldController {
	
	private Field field;
	private final int rowsToFill;
	private int size;
	
	public FieldController(int size) {
		final int minSize = 4;
		if (size < minSize){
			throw new IllegalArgumentException();
		} else {
			rowsToFill = size/2 - 1;
		}
		field = new Field(size);
		this.size = size;
	}

	private void createWhiteFigures(int size) {
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
	
	private void createBlackFigures(int size) {
		if (size % 2 == 0){
			for (int y = size - rowsToFill;y < size; y++){
				for (int x = 0; x < size; x++) {
					if (x % 2 == 0 && y % 2 != 0 ){
						new Figure(field.getCellByCoordinates(x, y),Figure.COLOR.black);
					} else if (x % 2 != 0 && y % 2 == 0 ){
						new Figure(field.getCellByCoordinates(x, y),Figure.COLOR.black);
					}
				}
			}
		} else {
			for (int y = size - rowsToFill;y < size; y++){
				for (int x = 0; x < size; x++) {
					if (x % 2 != 0 && y % 2 == 0 ){
						new Figure(field.getCellByCoordinates(x, y),Figure.COLOR.black);
					} else if (x % 2 == 0 && y % 2 != 0 ){
						new Figure(field.getCellByCoordinates(x, y),Figure.COLOR.black);
					}
				}
			}
		}
	}
	
	public void gameInit(){
		createBlackFigures(size);
		createWhiteFigures(size);
	}
	
	public Field getField() {
		return this.field;
	}
	
}
