package de.htwg.checkers.persistence;

import java.util.List;

import de.htwg.checkers.controller.IGameController;

public interface IGameControllerDAO {
    /**
     * Store the given IGameController.
     * @param gameController controller to store
     */
    void saveGameController(final IGameController gameController);

    /**
     * Receive a single IGameController by its name.
     * @param name Name of the IGameController to receive.
     * @return IGameController with given name.
     */
    IGameController getGameControllerByName(final String name);

    /**
     * Receive all stored IGameController as List.
     * @return List of stored IGameController
     */
    List<IGameController> getAllGameControllers();

    /**
     * Edit a single IGameController by updating it in the database.
     * @param gameController controller to be updated
     */
    void updateGameController(final IGameController gameController);

    /**
     * Delete a s single IGameController from the database.
     * @param gameController controller to be deleted
     */
    void deleteGameController(final IGameController gameController);
}
