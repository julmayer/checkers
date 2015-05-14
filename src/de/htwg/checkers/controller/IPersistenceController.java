package de.htwg.checkers.controller;

import java.util.List;

public interface IPersistenceController {
    /**
     * Returns the names of all stored games as String.
     * @return List with names of stored games as String
     */
    List<String> getStoredGames();

    /**
     * Save the given gameController.
     * @param gameController controller to save
     */
    void save(IGameController gameController);
}
