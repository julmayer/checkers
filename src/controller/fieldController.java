package controller;

import models.Field;
import models.Figure;

public class fieldController {
	
	private Field field;
	
	public fieldController() {
		field = new Field();
	}

	final public void createWhiteFigures() {
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
	
	final public void createBLackFigures() {
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
}
