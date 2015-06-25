package de.htwg.checkers;

import com.google.inject.AbstractModule;

import de.htwg.checkers.controller.IPersistenceController;
import de.htwg.checkers.controller.impl.PersistenceControllerImpl;
import de.htwg.checkers.persistence.IDAO;
import de.htwg.checkers.persistence.couchdb.CouchdbDAO;

public class PersistenceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IPersistenceController.class).to(PersistenceControllerImpl.class);
        //bind(IDAO.class).to(DB40Impl.class);
        //bind(IDAO.class).to(HibernateDAO.class);
        bind(IDAO.class).to(CouchdbDAO.class);
    }

}
