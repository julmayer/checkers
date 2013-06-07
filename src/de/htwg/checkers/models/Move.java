package de.htwg.checkers.models;

import java.util.HashMap;
import java.util.Map;

public class Move {
	boolean kill;
	Cell from;
	Cell to;
	public Move(boolean kill, Cell from, Cell to) {
		super();
		this.kill = kill;
		this.from = from;
		this.to = to;
	}
	
	public Move(Cell from, Cell to) {
		super();
		this.from = from;
		this.to = to;
	}
	
	public boolean isKill() {
		return kill;
	}
	public Cell getFrom() {
		return from;
	}
	public Cell getTo() {
		return to;
	}
	
	public Map<String, Integer> getCoordinatesLastSkipedCell() {
		Map<String, Integer> result = new HashMap<String, Integer>();
		if (!kill) {
			result.put("X", from.getX());
			result.put("Y", from.getY());
			return result;
		}
		int srcX = from.getX();
		int srcY = from.getY();
		int destX = to.getX();
		int destY = to.getY();
		int resultX, resultY;
		if (destX > srcX) {
			resultX = destX - 1; 
		} else {
			resultX = destX + 1;
		}
		if (destY > srcY) {
			resultY = destY - 1;
		} else {
			resultY = destY + 1;
		}
		result.put("X", resultX);
		result.put("Y", resultY);
		
		return result;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
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
		if (!(obj instanceof Move)) {
			return false;
		}
		Move other = (Move) obj;
		if (from == null) {
			if (other.from != null) {
				return false;
			}
		} else if (!from.equals(other.from)) {
			return false;
		}
		if (to == null) {
			if (other.to != null) {
				return false;
			}
		} else if (!to.equals(other.to)) {
			return false;
		}
		return true;
	}
}
