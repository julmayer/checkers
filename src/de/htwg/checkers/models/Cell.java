package de.htwg.checkers.models;

/**
 *
 * @author Julian Mayer, Marcel Loevenich
 */
public class Cell implements Drawable {
	private final int x;
	private final int y;
	private Figure occupier;
	
	/**
     * constructor for a cell
     * @param x
     * @param y
     */
    public Cell(int x, int y){
		this.x = x;
		this.y = y;
		this.occupier = null;
	}
    
    public Cell(Cell cell) {
        this.x = cell.x;
        this.y = cell.y;
        if (cell.occupier != null) {
            this.occupier = new Figure(cell.occupier);
            this.occupier.setPosition(this);
        }
    }
	
	/**
     *
     * @return if cell is occupied or not
     */
    public boolean isOccupied(){
		return occupier != null;
	}
	
	/**
     *
     * @return x coordinate of the cell
     */
    public int getX(){
		return x;
	}
	
	/**
     *
     * @return y coordinate of the cell
     */
    public int getY(){
		return y;
	}
	
	/**
     *
     * @return the occupier of the cell
     */
    public Figure getOccupier() {
		return occupier;
	}
	
	/**
     * method to set a occupier to this cell
     * @param occupier
     */
    public void setOccupier(Figure occupier) {
		this.occupier = occupier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Cell)) {
			return false;
		}
		Cell other = (Cell) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(x);
		sb.append(" ");
		sb.append(y);
		return sb.toString();
	}

	@Override
	public String draw() {
		String drawing = " - ";
		if (isOccupied()) {
			drawing = occupier.draw();
		}
		return drawing;
	}
}
	

