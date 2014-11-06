package de.htwg.checkers.controller.possiblemoves;

import java.util.LinkedList;
import java.util.List;

import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.models.Move;

/**
 * Abstraction for possible Moves.
 * @author Julian Mayer, Marcel Loevenich
 */
public abstract class AbstractPossibleMovesDirection implements PossibleMovesDirection {
    
    private Field gamefield;
	
    /**
     * Constructor to create new Moves.
     * @param gamefield Field on which the Moves were calculated.
     */
    public AbstractPossibleMovesDirection(Field gamefield) {
		this.gamefield = gamefield;
	}
	
	@Override
	public void getPossibleMoves(Figure figure) {
		Cell cell = figure.getPosition();
		if (cell == null) {
			return;
		}
		boolean iAmBlack = figure.isBlack();
		boolean lastFieldOccupied = false;
		List<Move> result = new LinkedList<Move>();
		boolean isCrowned = figure.isCrowned();
		while ((cell = nextCell(cell)) != null) {
			if (cell.isOccupied()) {
				if (cell.getOccupier().isBlack() == iAmBlack) {
					// same colored figure -> no more moves
					break;
				} else {
					if (lastFieldOccupied) {
						// two figures in a row -> no more moves
						break;
					}
					lastFieldOccupied = true;
				}
			} else {
				if (lastFieldOccupied) {
					// mustKill move = only possible move
					result.clear();
					result.add(new Move(true, figure.getPosition(), cell));
					break;
				} else {
					result.add(new Move(false, figure.getPosition(), cell));
					if (!isCrowned) {
						// normal figure, no more moves
						break;
					}
				}
				lastFieldOccupied = false;
			}
		}
		List<Move> moves = figure.getPossibleMoves();
		moves.addAll(result);
		figure.setPossibleMoves(moves);
	}
	
	/**
     *
     * @return the gamefield
     */
    protected Field getGamefield() {
		return gamefield;
	}
	
	/**
     *
     * @param cell 
     * @return the next cell
     */
    protected abstract Cell nextCell(Cell cell);
}
