package de.htwg.checkers.models;

/**
 *
 * @author Julian Mayer, Marcel Loevenich
 */
public class Field{
	private Cell[][] field;
	private int size;
	
	/**
     * constructor for the gamefield
     * @param size
     */
    public Field(int size){
		this.size = size;
		this.field = new Cell[size][size];
		initCells(size);
	}
    
    /**
     * Copyconstructor for the Gamefiels
     * @param field source field for copy
     */
    public Field(Field field) {
    	this.field = field.field;
    	this.size = field.size;
    }

	/**
     *
     * @return the field
     */
    public Cell[][] getField(){
		return field;
	}
	
	/**
     * 
     * @param x
     * @param y
     * @return specified cell
     */
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
	
	/**
     * method to validate a given coordinate
     * @param x
     * @param y
     * @return if is valid or not
     */
    public boolean isValidCoordinate(int x, int y) {
		return x >= 0 && y >= 0 && x < size && y < size;
	}	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Current situation:\n");
		
		for (int i = size-1; i >= -1; --i) {
			if (i == -1) {
				sb.append("  ");
			} else {
				sb.append(i);
				sb.append(" ");
			}
			for (int j = 0; j < size; ++j) {
				if (i == -1) {
					sb.append(" ");
					sb.append(j);
					sb.append(" ");
				} else {
					fillFigureString(j, i, sb);
				}
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	private void fillFigureString(int j, int i, StringBuilder sb){
		Figure figure = field[j][i].getOccupier();
		if (figure == null) {
			sb.append(" - ");
		} else {
			sb.append(figure.toString());
		}
	}
}
