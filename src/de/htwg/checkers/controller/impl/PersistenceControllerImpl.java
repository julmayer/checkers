package de.htwg.checkers.controller.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.checkers.controller.IPersistenceController;
import de.htwg.checkers.persistence.IDAO;
import de.htwg.checkers.persistence.PersistContainer;

@Singleton
public class PersistenceControllerImpl implements IPersistenceController {
    private IDAO dao;

    @Inject
    public PersistenceControllerImpl(IDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<String> getStoredGames() {
        List<String> result = new ArrayList<String>();
        for (PersistContainer container : dao.getAllPersistContainers()) {
            result.add(container.getName());
        }
        return result;
    }

    @Override
    public void save(PersistContainer container) {
        this.dao.savePersistContainer(container);
    }
    
    @Override
    public PersistContainer getByName(String name) {
        return this.dao.getPersistContainerBy(name);
    }

}
