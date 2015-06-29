package de.htwg.checkers.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class GameStateTest {

	@Test
	public void createInstance() {
		GameState gameState = new GameState();
		assertNotNull(gameState);
	}
	
	@Test
	public void constructer() {
	    GameState gameState = new GameState(true, 0, false, Bot.NO_BOT, State.NEW_GAME);
	    assertEquals(true, gameState.isBlackTurn());
	}

	@Test
	public void testCopy() {
	    GameState gameState = new GameState();
	    GameState copy = new GameState(gameState);
	    
	    assertEquals(gameState.getBot(), copy.getBot());
	    assertEquals(gameState.getCurrentState(), copy.getCurrentState());
	    assertEquals(gameState.getMoveCount(), copy.getMoveCount());
	    assertEquals(gameState.isBlackTurn(), copy.isBlackTurn());
	    assertEquals(gameState.isHasMoreKills(), copy.isHasMoreKills());
	}
	
	
}
