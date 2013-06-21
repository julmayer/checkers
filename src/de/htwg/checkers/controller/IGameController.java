package de.htwg.checkers.controller;

import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.util.observer.IObservable;

public interface IGameController extends IObservable {
	
	Field getField();
	
	int getFieldSize();
	
	boolean isBlackTurn();
	
	int getMoveCount();
	
	void gameInit();
	
	boolean checkIfWin(StringBuilder stringOutput);
	
	Figure getFigureOnField(int x, int y);
	
	void createAllMoves();
	
	boolean input(String input);
	
	String getError();
}
