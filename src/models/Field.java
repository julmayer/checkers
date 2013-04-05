package models;

import models.Figure.COLOR;

public class Field {
	private Cell[][] field;
	
	public Field(){
		this.field = new Cell[8][8];
		createField();
		createBLackFigures();
		createWhiteFigures();
	}

	final public void createField(){
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				field[i][j] = new Cell(i, j);
			}
		}
	}

	final public void createWhiteFigures() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 8; j++) {
				if (i % 2 == 0 && j % 2 != 0 ){
					new Figure(field[i][j],Figure.COLOR.white);
				} else if (i % 2 != 0 && j % 2 == 0 ){
					new Figure(field[i][j],Figure.COLOR.white);
				}
			}
		}
	}
	
	final public void createBLackFigures() {
		for (int i = 5; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i % 2 != 0 && j % 2 == 0 ){
					new Figure(field[i][j],Figure.COLOR.black);
				} else if (i % 2 == 0 && j % 2 != 0 ){
					new Figure(field[i][j],Figure.COLOR.black);
				}
			}
		}
	}
}
