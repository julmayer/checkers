package de.htwg.checkers.controller;

import java.util.List;

import de.htwg.checkers.models.Bot;
import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.models.GameState;
import de.htwg.checkers.models.Move;
import de.htwg.checkers.models.State;
import de.htwg.checkers.persistence.PersistContainer;
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
    
    GameState getGameState();
	
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
    void gameInit(int size, Bot difficulty);
    
    void gameInit(PersistContainer container);
	
	/**
     * check if somebody has won. 
     * @return true if someone has won, in this case String info was filled.
     */
    boolean checkIfWin();
	
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
    
    String getInfo();
    
    public String getDrawingOfField();
    
    State getCurrentState();
    
    List<Move> getPossibleMoves();
}
