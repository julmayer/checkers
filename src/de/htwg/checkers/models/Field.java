package de.htwg.checkers.models;

public class Field {
	private Cell[][] field;
	
	public Field(int size){
		this.field = new Cell[size][size];
		initCells(size);
	}

	public Cell[][] getField(){
		return field;
	}
	
	public Cell getCellByCoordinates(int x, int y){
		return field[x][y];
	}

	private void initCells(int size){
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				field[i][j] = new Cell(i, j);
			}
		}
	}

	
}
