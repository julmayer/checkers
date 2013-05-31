package de.htwg.checkers.controller.possiblemoves;

import java.util.List;

import de.htwg.checkers.models.Cell;

public interface PossibleMovesDirection {
	List<Cell> getPossibleMoves(Cell sourceCell);
}
