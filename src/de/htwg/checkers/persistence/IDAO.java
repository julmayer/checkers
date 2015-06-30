package de.htwg.checkers.persistence;

import java.util.List;

public interface IDAO {
    /**
     * Stores the given PersistContainer.
     * @param container container to store
     */
    void savePersistContainer(final PersistContainer container);

    /**
     * Receives a single PersistContainer by its name.
     * @param name Name of the PersistContainer to receive.
     * @return PersistContainer with given name.
     */
    PersistContainer getPersistContainerBy(final String name);

    /**
     * Receives all stored PersistContainer as List.
     * @return List of stored PersistContainer
     */
    List<PersistContainer> getAllPersistContainers();

    /**
     * Updates a single PersistContainer in the database.
     * @param container container to be updated
     */
    void updatePersistContainer(final PersistContainer container);

    /**
     * Deletes a s single PersistContainer from the database.
     * @param container container to be deleted
     */
    void deletePersistContainer(final PersistContainer container);
}
