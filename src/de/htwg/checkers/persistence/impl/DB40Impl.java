package de.htwg.checkers.persistence.impl;

import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.google.inject.Singleton;

import de.htwg.checkers.persistence.IDAO;
import de.htwg.checkers.persistence.PersistContainer;

@Singleton
public class DB40Impl implements IDAO {
    /** Name of the database */
    private static final String DB_NAME = "CheckersDb4o.db";
    private ObjectContainer db;

    public DB40Impl() {
        this.db = Db4oEmbedded.openFile(DB_NAME);
    }

    @Override
    public void savePersistContainer(final PersistContainer container) {
        db.store(container);
    }

    @Override
    public PersistContainer getPersistContainerBy(final String name) {
        List<PersistContainer> containers = db.query(
                new Predicate<PersistContainer>() {

                    /**
                     * auto generated
                     */
                    private static final long serialVersionUID = 1L;

                    @Override
                    public boolean match(PersistContainer arg0) {
                        return arg0.getName().equals(name);
                    }

                });

        if (containers.isEmpty()) {
            return null;
        }
        assert(containers.size() == 1);
        return containers.get(0);
    }

    @Override
    public List<PersistContainer> getAllPersistContainers() {
        return db.query(PersistContainer.class);
    }

    @Override
    public void updatePersistContainer(final PersistContainer container) {
        db.store(container);
    }

    @Override
    public void deletePersistContainer(final PersistContainer container) {
        db.delete(container);
    }
}
