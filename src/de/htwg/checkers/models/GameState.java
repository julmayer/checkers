package de.htwg.checkers.models;


public class GameState {
    private boolean blackTurn;
    private int moveCount;
    private boolean hasMoreKills;
    private Bot bot;
    private State currentState;
    
    public GameState() {
        this.reset();
        this.bot = Bot.NO_BOT;
        this.currentState = State.NEW_GAME;
    }
    
    public final void reset() {
        this.blackTurn = true;
        this.moveCount = 0;
        this.hasMoreKills = false;
    }

    public boolean isBlackTurn() {
        return blackTurn;
    }

    public void changeTurn() {
        this.blackTurn = !this.blackTurn;
    }

    public int getMoveCount() {
        return moveCount;
    }
    
    public void incMoveCount() {
        this.moveCount++;
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
