package de.htwg.checkers.persistence.couchdb;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.models.GameState;
import de.htwg.checkers.persistence.IDAO;
import de.htwg.checkers.persistence.PersistContainer;

public class CouchdbDAO implements IDAO {
    private CouchDbConnector db;
    
    public CouchdbDAO() {
        HttpClient client;
        try {
            client = new StdHttpClient.Builder()
                .url("http://lenny2.in.htwg-konstanz.de:5984")
                .build();
            CouchDbInstance dbInstance = new StdCouchDbInstance(client);
            db = dbInstance.createConnector("checkers", true);
        } catch (MalformedURLException e) {
            Logger.getGlobal().throwing("CouchdbDAO", "CouchdbDAO", e);
        }
    }

    @Override
    public void savePersistContainer(PersistContainer container) {
        PersistentPersistContainer ppc = getPPContainerBy(container.getName());
        if (ppc == null) {
            ppc = new PersistentPersistContainer(container);
            db.create(ppc);
        } else {
            db.update(ppc);
        }
    }

    @Override
    public PersistContainer getPersistContainerBy(String name) {
        return generateContainer(getPPContainerBy(name));
    }

    @Override
    public List<PersistContainer> getAllPersistContainers() {
        ViewQuery query = new ViewQuery().allDocs().includeDocs(true);

        List<PersistContainer> pcontainer = new ArrayList<PersistContainer>();
        for (PersistentPersistContainer ppc : db.queryView(query, PersistentPersistContainer.class)) {
            pcontainer.add(generateContainer(ppc));
        }

        return pcontainer;
    }

    @Override
    public void updatePersistContainer(PersistContainer container) {
        PersistentPersistContainer ppc = getPPContainerBy(container.getName());
        db.update(ppc);
    }

    @Override
    public void deletePersistContainer(PersistContainer container) {
        PersistentPersistContainer ppc = getPPContainerBy(container.getName());
        db.delete(ppc);
    }
    
    private PersistentPersistContainer getPPContainerBy(String name) {
        ViewQuery query = new ViewQuery().allDocs().includeDocs(true);
        PersistentPersistContainer result = null;
        for (PersistentPersistContainer ppc : db.queryView(query, PersistentPersistContainer.class)) {
            if (ppc.getName().equals(name)) {
                result = ppc;
                break;
            }
        }
        return result;
    }

    private PersistContainer generateContainer(PersistentPersistContainer ppc) {
        PersistentField pField = ppc.getField();
        PersistentGameState pGameState = ppc.getState();
        GameState gameState = new GameState(pGameState.isBlackTurn(), pGameState.getMoveCount(),
                pGameState.isHasMoreKills(), pGameState.getBot(), pGameState.getCurrentState());
        Field field = new Field(pField.getSize());
        
        for (PersistentFigure pFigure : pField.getFigures()) {
            Cell cell = field.getCellByCoordinates(pFigure.getXPosition(), pFigure.getYPosition());
            Figure figure = new Figure(cell, pFigure.isBlack());
            figure.setCrowned(pFigure.isCrowned());
        }
        
        return new PersistContainer(ppc.getName(), field, gameState);
    }
}
