package de.htwg.checkers.persistence;

import java.util.List;

public interface IDAO {
    /**
     * Store the given PersistContainer.
     * @param container container to store
     */
    void savePersistContainer(final PersistContainer container);

    /**
     * Receive a single PersistContainer by its name.
     * @param name Name of the PersistContainer to receive.
     * @return PersistContainer with given name.
     */
    PersistContainer getPersistContainerBy(final String name);

    /**
     * Receive all stored PersistContainer as List.
     * @return List of stored PersistContainer
     */
    List<PersistContainer> getAllPersistContainers();

    /**
     * Edit a single PersistContainer by updating it in the database.
     * @param container container to be updated
     */
    void updatePersistContainer(final PersistContainer container);

    /**
     * Delete a s single PersistContainer from the database.
     * @param container container to be deleted
     */
    void deletePersistContainer(final PersistContainer container);
}
