package de.htwg.checkers.controller;

import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;

public class FieldController {
	
	private Field field;
	
	public FieldController() {
		field = new Field();
	}

	final private void createWhiteFigures() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 8; j++) {
				if (i % 2 == 0 && j % 2 != 0 ){
					new Figure(field.getCellByCoordinates(i, j),Figure.COLOR.white);
				} else if (i % 2 != 0 && j % 2 == 0 ){
					new Figure(field.getCellByCoordinates(i, j),Figure.COLOR.white);
				}
			}
		}
	}
	
	final private void createBlackFigures() {
		for (int i = 5; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i % 2 != 0 && j % 2 == 0 ){
					new Figure(field.getCellByCoordinates(i, j),Figure.COLOR.black);
				} else if (i % 2 == 0 && j % 2 != 0 ){
					new Figure(field.getCellByCoordinates(i, j),Figure.COLOR.black);
				}
			}
		}
	}
	
	public void gameInit(){
		createBlackFigures();
		createWhiteFigures();
	}
	
	public Field getField() {
		return this.field;
	}
	
}
