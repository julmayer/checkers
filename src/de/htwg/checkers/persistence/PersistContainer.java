package de.htwg.checkers.persistence;

import com.google.inject.Singleton;

import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.GameState;

@Singleton
public class PersistContainer {
    private final Field field;
    private final GameState state;
    private final String name;
    
    public PersistContainer(final String name, final Field field, final GameState state) {
        this.name = name;
        this.field = new Field(field);
        this.state = new GameState(state);
    }
    
    public String getName() {
        return this.name;
    }
    
    public Field getField() {
        return new Field(this.field);
    }
    
    public GameState getGameState() {
        return new GameState(this.state);
    }
}
