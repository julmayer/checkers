package de.htwg.checkers.controller.possiblemoves;

import de.htwg.checkers.models.Figure;

/**
 * Interfaces for possible Moves in any Direction.
 * @author Julian Mayer, Marcel Loevenich
 */
public interface PossibleMovesDirection {
    /**
     * Adds the possible move for this direction to the Figure.
     * @param figure Figure for that the Moves where added.
     */
    void getPossibleMoves(Figure figure);
}
