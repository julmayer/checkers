package models;

public class Field {
	private Cell[][] field;
	private final int X = 8;
	private final int Y =8;
	
	public Field(){
		this.field = new Cell[X][Y];
		createField();
	}

	public Cell[][] getField(){
		return field;
	}
	
	public Cell getCellByCoordinates(int x, int y){
		return field[x][y];
	}

	final public void createField(){
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				field[i][j] = new Cell(i, j);
			}
		}
	}

	
}
