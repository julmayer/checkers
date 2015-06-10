package de.htwg.checkers.models;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Julian Mayer, Marcel Loevenich
 */
public class Figure implements Drawable {
	private Cell position;
	private boolean crowned;
	private final boolean black;
	private List<Move> possibleMoves;
	
	/**
     *constructor for a figure
     * @param position
     * @param black
     */
    public Figure(Cell position, boolean black) {
		this.position = position;
		if (this.position != null) {
			this.position.setOccupier(this);
		}
		this.black = black;
		this.crowned = false;
		this.possibleMoves = new LinkedList<Move>();
	}
    
    public Figure (Figure figure) {
        this.crowned = figure.crowned;
        this.black = figure.black;
        this.possibleMoves = new LinkedList<Move>();
    }

	/**
     *
     * @return the position of a figure
     */
    public Cell getPosition() {
		return position;
	}

	/**
     * method to set the position of a cell
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
     * @return if figure is corwned or not
     */
    public boolean isCrowned() {
		return crowned;
	}

	/**
     * method to crown a figure
     * @param crowned
     */
    public void setCrowned(boolean crowned) {
		this.crowned = crowned;
	}


	/**
     * method to kill a figure
     */
    public void kill() {
		this.position.setOccupier(null);
		this.position = null;
	}

	/**
     *
     * @return a list of all possible moves
     */
    public List<Move> getPossibleMoves() {
		return possibleMoves;
	}
	
	/**
     * method to set all possible moves 
     * @param possibleMoves
     */
    public void setPossibleMoves(List<Move> possibleMoves) {
		this.possibleMoves = possibleMoves;
	}

	/**
     *
     * @return if a figure is black or not
     */
    public boolean isBlack() {
		return black;
	}
	
	/**
     *
     * @return if a figure has killmoves or not
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
     *method to remove all nonkill moves
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

	@Override
	public String draw() {
		String drawing;
		if (black) {
			drawing = " x ";
		} else {
			drawing = " o ";
		}
		
		if (crowned) {
			drawing = drawing.toUpperCase();
		}
		return drawing;
	}
}
