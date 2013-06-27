package de.htwg.checkers.controller;

import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.util.observer.IObservable;

/**
 * interface for the gamecontroller
 * @author Julian Mayer, Marcel Loevenich
 */
public interface IGameController extends IObservable {
	
	/**
     * 
     * @return the gamefield
     */
    Field getField();
	
	/**
     *
     * @return the size of the gamefield
     */
    int getFieldSize();
	
	/**
     *
     * @return if its black turn or not
     */
    boolean isBlackTurn();
	
	/**
     *
     * @return the actual move count
     */
    int getMoveCount();
	
	/**
     * make a game init
     */
    void gameInit();
	
	/**
     * 
     * @param stringOutput
     * @return if someone has won or not
     */
    boolean checkIfWin(StringBuilder stringOutput);
	
	/**
     *
     * @param x
     * @param y
     * @return the figure on a field
     */
    Figure getFigureOnField(int x, int y);
	
	/**
     * method to create all moves
     */
    void createAllMoves();
	
	/**
     * 
     * @param input
     * @return
     */
    boolean input(String input);
	
	/**
     *
     * @return
     */
    String getError();
}
