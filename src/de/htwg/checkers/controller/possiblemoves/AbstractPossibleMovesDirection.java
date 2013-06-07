package de.htwg.checkers.controller.possiblemoves;

import java.util.LinkedList;
import java.util.List;

import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.models.Figure.COLOR;
import de.htwg.checkers.models.Move;

public abstract class AbstractPossibleMovesDirection implements PossibleMovesDirection {

	private Field gamefield;
	
	public AbstractPossibleMovesDirection(Field gamefield) {
		this.gamefield = gamefield;
	}
	
	@Override
	public void getPossibleMoves(Figure figure) {
		Cell cell = figure.getPosition();
		COLOR myColor = figure.getColor();
		boolean lastFieldOccupied = false;
		List<Move> result = new LinkedList<Move>();
		boolean isCrowned = figure.isCrowned();
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
	
	public Field getGamefield() {
		return gamefield;
	}
	
	protected abstract Cell nextCell(Cell cell);
}
