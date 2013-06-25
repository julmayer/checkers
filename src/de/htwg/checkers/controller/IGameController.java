package de.htwg.checkers.controller;

import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.util.observer.IObservable;

/**
 *
 * @author jmayer
 */
public interface IGameController extends IObservable {
	
	/**
     *
     * @return
     */
    Field getField();
	
	/**
     *
     * @return
     */
    int getFieldSize();
	
	/**
     *
     * @return
     */
    boolean isBlackTurn();
	
	/**
     *
     * @return
     */
    int getMoveCount();
	
	/**
     *
     */
    void gameInit();
	
	/**
     *
     * @param stringOutput
     * @return
     */
    boolean checkIfWin(StringBuilder stringOutput);
	
	/**
     *
     * @param x
     * @param y
     * @return
     */
    Figure getFigureOnField(int x, int y);
	
	/**
     *
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
