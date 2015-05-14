package de.htwg.checkers.controller.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.checkers.controller.GameController;
import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.controller.IPersistenceController;
import de.htwg.checkers.persistence.IGameControllerDAO;

@Singleton
public class PersistenceControllerImpl implements IPersistenceController {
    IGameControllerDAO dao;

    @Inject
    public PersistenceControllerImpl(IGameControllerDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<String> getStoredGames() {
        List<String> result = new ArrayList<String>();
        for (IGameController gc : dao.getAllGameControllers()) {
            result.add(gc.getName());
        }
        return result;
    }

    @Override
    public void save(IGameController gameController) {
        this.dao.saveGameController(gameController);
    }

}
