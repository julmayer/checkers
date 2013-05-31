package de.htwg.checkers.controller.possiblemoves;

import java.util.LinkedList;
import java.util.List;

import de.htwg.checkers.controller.GameController;
import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Figure.COLOR;

public abstract class AbstractPossibleMovesDirection implements PossibleMovesDirection {

	private GameController gameController;
	private boolean isCrowned;
	
	public AbstractPossibleMovesDirection(GameController gameController) {
		this.gameController = gameController;
	}
	
	@Override
	public List<Cell> getPossibleMoves(Cell sourceCell) {
		Cell cell = sourceCell;
		int x = sourceCell.getX();
		int y = sourceCell.getY();
		COLOR myColor = gameController.getFigureOnField(x, y).getColor();
		boolean lastFieldOccupied = false;
		List<Cell> result = new LinkedList<Cell>();
		isCrowned = gameController.getFigureOnField(x, y).isCrowned();
		
		while ((cell = nextCell(cell)) != null) {
			if (cell.isOccupied()) {
				if (cell.getOccupier().getColor().equals(myColor)) {
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
					result.add(cell);
					break;
				} else {
					result.add(cell);
					if (!isCrowned) {
						// normal figure, no more moves
						break;
					}
				}
				lastFieldOccupied = false;
			}
		}
		
		return result;
	}
	
	public GameController getGameController() {
		return gameController;
	}
	
	protected abstract Cell nextCell(Cell cell);
}
