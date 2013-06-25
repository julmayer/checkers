package de.htwg.checkers.controller.possiblemoves;

import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;

/**
 *
 * @author jmayer
 */
public class PossibleMovesUpperRight extends AbstractPossibleMovesDirection {
	
	/**
     *
     * @param gamefield
     */
    public PossibleMovesUpperRight(Field gamefield) {
		super(gamefield);
	}
	
	protected Cell nextCell(Cell cell) {
		int nextX = cell.getX() + 1;
		int nextY = cell.getY() + 1;
		if (!getGamefield().isValidCoordinate(nextX, nextY)) {
			return null;
		}
		return getGamefield().getCellByCoordinates(nextX, nextY);
	}
}
