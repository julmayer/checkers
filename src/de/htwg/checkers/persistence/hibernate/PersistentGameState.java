package de.htwg.checkers.persistence.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import de.htwg.checkers.models.Bot;
import de.htwg.checkers.models.GameState;
import de.htwg.checkers.models.State;

@Entity
@Table(name = "game_state")
public class PersistentGameState implements Serializable {
    private static final long serialVersionUID = -54076051276006271L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "black_turn")
    private boolean blackTurn;
    @Column(name = "move_count")
    private int moveCount;
    @Column(name = "has_more_kills")
    private boolean hasMoreKills;
    @Column(name = "bot")
    private Bot bot;
    @Column(name = "current_state")
    private State currentState;
    
    public PersistentGameState() { }

    public PersistentGameState(GameState gameState) {
        this.blackTurn = gameState.isBlackTurn();
        this.moveCount = gameState.getMoveCount();
        this.hasMoreKills = gameState.isHasMoreKills();
        this.bot = gameState.getBot();
        this.currentState = gameState.getCurrentState();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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