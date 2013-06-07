package de.htwg.checkers.models;

import java.util.LinkedList;
import java.util.List;

public class Figure {
	public enum COLOR{
		white,
		black};
	
	private Cell position;
	private boolean crowned;
	private boolean alive;
	private COLOR color;
	private List<Cell> possibleMoves;
	private boolean mustKillMoves;
	
	public Figure(Cell position, COLOR color) {
		this.position = position;
		this.position.setOccupier(this);
		this.color = color;
		this.alive = true;
		this.crowned = false;
		this.possibleMoves = new LinkedList<Cell>();
		this.mustKillMoves = false;
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
		this.position.setOccupier(null);
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

	public boolean isMustKillMoves() {
		return mustKillMoves;
	}

	public void setMustKillMoves(boolean mustKillMoves) {
		this.mustKillMoves = mustKillMoves;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		final int isAliveKey = 1231;
		final int notAliveKey = 1237;
		int result = 1;
		result = prime * result + (alive ? isAliveKey : notAliveKey);
		result = prime * result + (color.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
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
		if (!(obj instanceof Figure)) {
			return false;
		}
		Figure other = (Figure) obj;
		if (alive != other.alive) {
			return false;
		}
		if (color != other.color) {
			return false;
		}
		if (position != null && !position.equals(other.position)) {
			return false;
		}
		return true;
	}
}
