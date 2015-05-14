package de.htwg.checkers.persistence.impl;

import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.google.inject.Singleton;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.persistence.IGameControllerDAO;

@Singleton
public class DB40Impl implements IGameControllerDAO {
    /** Name of the database */
    private static final String DB_NAME = "CheckersDb4o.db";
    ObjectContainer db;

    public DB40Impl() {
        this.db = Db4oEmbedded.openFile(DB_NAME);
    }

    @Override
    public void saveGameController(final IGameController gameController) {
        db.store(gameController);
    }

    @Override
    public IGameController getGameControllerByName(final String name) {
        List<IGameController> gameControllers = db
                .query(new Predicate<IGameController>() {

                    /**
                     * auto generated
                     */
                    private static final long serialVersionUID = 1L;

                    @Override
                    public boolean match(IGameController arg0) {
                        return arg0.getName().equals(name);
                    }

                });

        if (gameControllers.isEmpty()) {
            return null;
        }
        return gameControllers.get(0);
    }

    @Override
    public List<IGameController> getAllGameControllers() {
        List<IGameController> gameControllers = db.query(IGameController.class);
        return gameControllers;
    }

    @Override
    public void updateGameController(final IGameController gameController) {
        db.store(gameController);
    }

    @Override
    public void deleteGameController(final IGameController gameController) {
        db.delete(gameController);
    }

}
