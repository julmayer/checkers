package de.htwg.checkers.controller.possiblemoves;

import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;

/**
 *
 * @author jmayer
 */
public class PossibleMovesUpperLeft extends AbstractPossibleMovesDirection {

	/**
     *
     * @param gamefield
     */
    public PossibleMovesUpperLeft(Field gamefield) {
		super(gamefield);
	}

	@Override
	protected Cell nextCell(Cell cell) {
		int nextX = cell.getX() - 1;
		int nextY = cell.getY() + 1;
		
		if (!getGamefield().isValidCoordinate(nextX, nextY)) {
			return null;
		}
		return getGamefield().getCellByCoordinates(nextX, nextY);
	}

}
