package models;

public class Cell {
	private int x;
	private int y;
	private Figure occupier;
	
	//constructor
	public Cell(int x, int y){
		this.x = x;
		this.y = y;
		this.occupier = null;
	}
	
	public boolean isOccupied(){
		return occupier != null;
	}
	
	//getter
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Figure getOccupier() {
		return occupier;
	}
	
	//setter
	public void setOccupier(Figure occupier) {
		this.occupier = occupier;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		
		Cell cell;
		if (obj instanceof Cell) {
			cell = (Cell) obj;
		} else {
			return false;
		}

		if (! (this.x == cell.x)){
			return false;
		}
		if (! (this.y == cell.y)){
			return false;
		}
		
		return true;
		}
}
	

