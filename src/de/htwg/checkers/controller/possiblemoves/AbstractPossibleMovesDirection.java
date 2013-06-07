package de.htwg.checkers.controller.possiblemoves;

import java.util.LinkedList;
import java.util.List;

import de.htwg.checkers.controller.GameController;
import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.models.Figure.COLOR;

public abstract class AbstractPossibleMovesDirection implements PossibleMovesDirection {

	private GameController gameController;
	private boolean isCrowned;
	
	public AbstractPossibleMovesDirection(GameController gameController) {
		this.gameController = gameController;
	}
	
	@Override
	public void getPossibleMoves(Figure figure) {
		Cell cell = figure.getPosition();
		int x = cell.getX();
		int y = cell.getY();
		COLOR myColor = gameController.getFigureOnField(x, y).getColor();
		boolean lastFieldOccupied = false;
		List<Cell> result = new LinkedList<Cell>();
		isCrowned = figure.isCrowned();
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
					figure.setMustKillMoves(true);
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
		List<Cell> moves = figure.getPossibleMoves();
		moves.addAll(result);
		figure.setPossibleMoves(moves);
	}
	
	public GameController getGameController() {
		return gameController;
	}
	
	protected abstract Cell nextCell(Cell cell);
}
