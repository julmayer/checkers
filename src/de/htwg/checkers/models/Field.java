package de.htwg.checkers.models;

/**
 *
 * @author Julian Mayer, Marcel Loevenich
 */
public class Field implements Drawable {
	// 65 = 'A', used to translate letters to int
	public static final int ASCII_OFFSET = 65;
	private final Cell[][] field;
	private final int size;
	
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
	public String draw() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Current situation:\n");
		
		// iterate through rows top to down
		for (int i = size-1; i >= -1; --i) {
			if (i == -1) {
				// last row, add space for lower labels
				sb.append("  ");
			} else {
				// add left labels as numbers
				sb.append(i);
				sb.append(" ");
			}
			// iterate through columns left to right
			for (int j = 0; j < size; ++j) {
				if (i == -1) {
					// last row, add lower labels as letters
					sb.append(" ");
					sb.append(Character.toChars(j + ASCII_OFFSET));
					sb.append(" ");
				} else {
					sb.append(field[j][i].draw());
				}
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
