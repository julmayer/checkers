package de.htwg.checkers.controller;

import java.util.LinkedList;

import de.htwg.checkers.controller.possiblemoves.PossibleMovesLowerLeft;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesLowerRight;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesUpperLeft;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesUpperRight;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.models.Move;

public class FigureController {
	
	private PossibleMovesLowerLeft lowerLeft;
	private PossibleMovesLowerRight lowerRight;
	private PossibleMovesUpperLeft upperLeft;
	private PossibleMovesUpperRight upperRight;
	
	public FigureController(GameController gameController) {
		this.lowerLeft = new PossibleMovesLowerLeft(gameController);
		this.lowerRight = new PossibleMovesLowerRight(gameController);
		this.upperLeft = new PossibleMovesUpperLeft(gameController);
		this.upperRight = new PossibleMovesUpperRight(gameController);
	}
	
	public void createPossibleMoves(Figure figure) {
		figure.setPossibleMoves(new LinkedList<Move>());
		if (figure.isCrowned()) {
			crownedMoves(figure);
		} else {
			regulareMoves(figure);
		}
		
	}
	
	private void regulareMoves(Figure figure) {
		if (figure.getColor().equals(Figure.COLOR.black)) {
			lowerLeft.getPossibleMoves(figure);
			lowerRight.getPossibleMoves(figure);
		} else {
			upperLeft.getPossibleMoves(figure);
			upperRight.getPossibleMoves(figure);
		}
	}
	
	private void crownedMoves(Figure figure) {
		upperLeft.getPossibleMoves(figure);
		upperRight.getPossibleMoves(figure);
		lowerLeft.getPossibleMoves(figure);
		lowerRight.getPossibleMoves(figure);
	}
	
	//public void move(Cell from, Cell to) {
		//Figure figure = from.getOccupier();		
	//}
}
