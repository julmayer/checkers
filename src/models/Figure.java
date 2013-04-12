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

	public void kill() {
		this.alive = false;
		this.position = null;
	}

	public List<Cell> getPossibleMoves() {
		return possibleMoves;
	}
	
	public void setPossibleMoves(List<Cell> possibleMoves) {
		this.possibleMoves = possibleMoves;
	}

	public COLOR getColor() {
		return color;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		Figure figure;
		if (obj instanceof Figure) {
			figure = (Figure) obj;
		} else {
			return false;
		}
		
		if (!this.color.equals(figure.color)) {
			return false;
		} else if (this.alive != figure.alive) {
			return false;
		} else if (!this.position.equals(figure.getPosition())) {
			return false;
		}
		
		return true;
	}
}
