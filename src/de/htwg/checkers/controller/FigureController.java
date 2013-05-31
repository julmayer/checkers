package de.htwg.checkers.controller;

import java.util.LinkedList;
import java.util.List;

import de.htwg.checkers.controller.possiblemoves.PossibleMovesLowerLeft;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesLowerRight;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesUpperLeft;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesUpperRight;
import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Figure;

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
		if (figure.isCrowned()) {
			figure.setPossibleMoves(crownedMoves(figure));
		} else {
			figure.setPossibleMoves(regulareMoves(figure));
		}
	}
	
	private List<Cell> regulareMoves(Figure figure) {
		List<Cell> possibleMoves = new LinkedList<Cell>();
		
		if (figure.getColor().equals(Figure.COLOR.black)) {
			possibleMoves.addAll(lowerLeft.getPossibleMoves(figure.getPosition()));
			possibleMoves.addAll(lowerRight.getPossibleMoves(figure.getPosition()));
		} else {
			possibleMoves.addAll(upperLeft.getPossibleMoves(figure.getPosition()));
			possibleMoves.addAll(upperRight.getPossibleMoves(figure.getPosition()));
		}
		
		return possibleMoves;
	}
	
	private List<Cell> crownedMoves(Figure figure) {
		List<Cell> possibleMoves = new LinkedList<Cell>();
		
		possibleMoves.addAll(upperLeft.getPossibleMoves(figure.getPosition()));
		possibleMoves.addAll(upperRight.getPossibleMoves(figure.getPosition()));
		possibleMoves.addAll(lowerLeft.getPossibleMoves(figure.getPosition()));
		possibleMoves.addAll(lowerRight.getPossibleMoves(figure.getPosition()));
		
		return possibleMoves;
	}
	
	public void move(Cell from, Cell to) {
		//Figure figure = from.getOccupier();
		
	}
}
