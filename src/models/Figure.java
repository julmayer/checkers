package models;

import java.util.LinkedList;
import java.util.List;

import javax.sql.PooledConnection;

public class Figure {
	public enum COLOR{
		white,
		black};
	
	private Cell position;
	private boolean crowned;
	private boolean alive;
	private COLOR color;
	private List<Cell> possibleMoves;
	
	public Figure(Cell position, COLOR color) {
		this.position = position;
		this.position.setOccupier(this);
		this.color = color;
		this.alive = true;
		this.crowned = false;
		this.possibleMoves = new LinkedList<>();
	}

	public Cell getPosition() {
		return position;
	}

	public void setPosition(Cell position) {
		if (this.position != null) {
			this.position.setOccupier(null);
		}
		this.position = position;
		this.position.setOccupier(this);
	}

	public boolean isCrowned() {
		return crowned;
	}

	public void setCrowned(boolean crowned) {
		this.crowned = crowned;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public List<Cell> getPossibleMoves() {
		return possibleMoves;
	}

	public COLOR getColor() {
		return color;
	}

	public void createPossibleMoves() {
		if (crowned) {
			crownedMoves();
		} else {
			regulareMoves();
		}
	}
	
	private void regulareMoves() {
		int x = position.getX();
		int y = position.getY();
		Cell upperLeft, upperRight, lowerLeft, lowerRight;
		List<Cell> cells = new LinkedList<>();
		
		upperLeft = new Cell(x-1, y+1);
		upperRight = new Cell(x+1, y+1);
		lowerLeft = new Cell(x-1, y-1);
		lowerRight = new Cell(x+1, y-1);
		
		cells.add(upperRight);
		cells.add(upperLeft);
		cells.add(lowerRight);
		cells.add(lowerLeft);
		
		possibleMoves = mustKill(cells);
		
		if (possibleMoves.size() > 0) {
			return;
		}
		
		if (color.equals(COLOR.black)) {
			
		} else {
			
		}
	}
	
	private void crownedMoves() {
		
	}
	
	private List<Cell> mustKill(List<Cell> cells) {
		List<Cell> result = new LinkedList<>();
		for (Cell cell : cells) {
			if (cell.isOccupied() && !cell.getOccupier().getColor().equals(this.color)) {
				result.add(cell);
			}
		}
		return result;
	}
}
