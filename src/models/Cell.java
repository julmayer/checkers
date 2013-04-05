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
	
}
