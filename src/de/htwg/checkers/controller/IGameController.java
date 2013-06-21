package de.htwg.checkers.controller;

import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.util.observer.IObservable;

public interface IGameController extends IObservable {
	
	Field getField();
	
	int getFieldSize();
	
	boolean isBlackTurn();
	
	void increaseMoveCount();
	
	int getMoveCount();
	
	void changeColor();
	
	void gameInit();
	
	boolean checkIfWin(StringBuilder stringOutput);
	
	Figure getFigureOnField(int x, int y);
	
	boolean isValidCoordinate(int x, int y);
	
	boolean validateSelectedFigure(Figure figure, StringBuilder stringOutput, int x, int y);
	
	boolean isAFigureSelected (Figure figure, StringBuilder stringOutput);
	
	boolean move(Figure from,int toX,int toY);
	
	void deleteAllMovesWithoutFigure(Figure figure);
	
	void createAllMoves();
	
	void crownFigureIfNeeded(Figure figure);
}
