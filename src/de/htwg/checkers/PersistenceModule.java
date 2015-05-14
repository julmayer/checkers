package de.htwg.checkers;

import com.google.inject.AbstractModule;

import de.htwg.checkers.controller.IPersistenceController;
import de.htwg.checkers.controller.impl.PersistenceControllerImpl;
import de.htwg.checkers.persistence.IGameControllerDAO;
import de.htwg.checkers.persistence.impl.DB40Impl;

public class PersistenceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IPersistenceController.class).to(PersistenceControllerImpl.class);
        bind(IGameControllerDAO.class).to(DB40Impl.class);
    }

}
