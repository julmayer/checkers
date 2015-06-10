package de.htwg.checkers.controller;

import java.util.List;

import de.htwg.checkers.persistence.PersistContainer;

public interface IPersistenceController {
    /**
     * Returns the names of all stored games as String.
     * @return List with names of stored games as String
     */
    List<String> getStoredGames();

    /**
     * Save the given SaveGame.
     * @param gameController controller to save
     */
    void save(PersistContainer saveGame);
    
    /**
     * Returns a stored SaveGame by its name
     * @param name name of the stored game as String
     * @return stored IGameController
     */
    PersistContainer getByName(String name);
}
