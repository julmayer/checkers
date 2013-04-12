package models;

public class Field {
	private Cell[][] field;
	private final int X = 8;
	private final int Y = 8;
	
	public Field(){
		this.field = new Cell[X][Y];
		initCells();
	}

	public Cell[][] getField(){
		return field;
	}
	
	public Cell getCellByCoordinates(int x, int y){
		return field[x][y];
	}

	final private void initCells(){
		for (int i = 0; i < X; i++){
			for (int j = 0; j < Y; j++){
				field[i][j] = new Cell(i, j);
			}
		}
	}

	
}
