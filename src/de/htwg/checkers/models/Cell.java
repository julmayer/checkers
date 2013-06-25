package de.htwg.checkers.models;

/**
 *
 * @author jmayer
 */
public class Cell {
	private int x;
	private int y;
	private Figure occupier;
	
	//constructor
	/**
     *
     * @param x
     * @param y
     */
    public Cell(int x, int y){
		this.x = x;
		this.y = y;
		this.occupier = null;
	}
	
	/**
     *
     * @return
     */
    public boolean isOccupied(){
		return occupier != null;
	}
	
	//getter
	/**
     *
     * @return
     */
    public int getX(){
		return x;
	}
	
	/**
     *
     * @return
     */
    public int getY(){
		return y;
	}
	
	/**
     *
     * @return
     */
    public Figure getOccupier() {
		return occupier;
	}
	
	//setter
	/**
     *
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

	
}
	

