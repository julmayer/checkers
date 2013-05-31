package de.htwg.checkers.controller.possiblemoves;

import de.htwg.checkers.controller.GameController;
import de.htwg.checkers.models.Cell;

public class PossibleMovesLowerLeft extends AbstractPossibleMovesDirection {

	public PossibleMovesLowerLeft(GameController gameController) {
		super(gameController);
	}

	@Override
	protected Cell nextCell(Cell cell) {
		int nextX = cell.getX() - 1;
		int nextY = cell.getY() - 1;
		
		if (!getGameController().isValidCoordinate(nextX, nextY)) {
			return null;
		}
		return getGameController().getField().getCellByCoordinates(nextX, nextY);
	}

}
