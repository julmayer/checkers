package de.htwg.checkers.models;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jmayer
 */
public class Figure {
	private Cell position;
	private boolean crowned;
	private boolean alive;
	private boolean black;
	private List<Move> possibleMoves;
	
	/**
     *
     * @param position
     * @param black
     */
    public Figure(Cell position, boolean black) {
		this.position = position;
		if (this.position != null) {
			this.position.setOccupier(this);
		}
		this.black = black;
		this.alive = true;
		this.crowned = false;
		this.possibleMoves = new LinkedList<Move>();
	}

	/**
     *
     * @return
     */
    public Cell getPosition() {
		return position;
	}

	/**
     *
     * @param position
     */
    public void setPosition(Cell position) {
		if (this.position != null) {
			this.position.setOccupier(null);
		}
		this.position = position;
		this.position.setOccupier(this);
	}

	/**
     *
     * @return
     */
    public boolean isCrowned() {
		return crowned;
	}

	/**
     *
     * @param crowned
     */
    public void setCrowned(boolean crowned) {
		this.crowned = crowned;
	}

	/**
     *
     * @return
     */
    public boolean isAlive() {
		return alive;
	}

	/**
     *
     */
    public void kill() {
		this.alive = false;
		this.position.setOccupier(null);
		this.position = null;
	}

	/**
     *
     * @return
     */
    public List<Move> getPossibleMoves() {
		return possibleMoves;
	}
	
	/**
     *
     * @param possibleMoves
     */
    public void setPossibleMoves(List<Move> possibleMoves) {
		this.possibleMoves = possibleMoves;
	}

	/**
     *
     * @return
     */
    public boolean isBlack() {
		return black;
	}
	
	/**
     *
     * @return
     */
    public boolean hasKillMoves() {
		for (Move move : possibleMoves) {
			if (move.isKill()) {
				return true;
			}
		}
		return false;
	}
	
	/**
     *
     */
    public void removeNonkillMoves() {
		List<Move> nonkills = new LinkedList<Move>();
		for (Move move : possibleMoves) {
			if (!move.isKill()) {
				nonkills.add(move);
			}
		}
		possibleMoves.removeAll(nonkills);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		final int isBlack = 1231;
		final int isWhite = 1237;
		int result = 1;
		result = prime * result + (black ? isBlack : isWhite);
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
		if (black != other.black) {
			return false;
		}
		if (position == null) {
			if (other.position != null) {
				return false;
			}
		} else if (!position.equals(other.position)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		String s;
		if (black) {
			s = " x ";
		} else {
			s = " o ";
		}
		
		if (crowned) {
			s = s.toUpperCase();
		}
		
		return s;
	}
}
