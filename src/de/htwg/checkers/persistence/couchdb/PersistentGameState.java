package de.htwg.checkers.persistence.couchdb;

import org.ektorp.support.CouchDbDocument;

import de.htwg.checkers.models.Bot;
import de.htwg.checkers.models.GameState;
import de.htwg.checkers.models.State;

public class PersistentGameState extends CouchDbDocument {
    private static final long serialVersionUID = -54076051276006271L;
    private boolean blackTurn;
    private int moveCount;
    private boolean hasMoreKills;
    private Bot bot;
    private State currentState;
    
    public PersistentGameState() { }

    public PersistentGameState(GameState gameState) {
        this.blackTurn = gameState.isBlackTurn();
        this.moveCount = gameState.getMoveCount();
        this.hasMoreKills = gameState.isHasMoreKills();
        this.bot = gameState.getBot();
        this.currentState = gameState.getCurrentState();
    }

    public boolean isBlackTurn() {
        return blackTurn;
    }

    public void setBlackTurn(boolean blackTurn) {
        this.blackTurn = blackTurn;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    public boolean isHasMoreKills() {
        return hasMoreKills;
    }

    public void setHasMoreKills(boolean hasMoreKills) {
        this.hasMoreKills = hasMoreKills;
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }   
}