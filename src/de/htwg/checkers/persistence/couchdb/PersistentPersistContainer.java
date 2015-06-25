package de.htwg.checkers.persistence.couchdb;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.ektorp.support.CouchDbDocument;

import de.htwg.checkers.persistence.PersistContainer;

@Entity
@Table(name = "persist_container")
public class PersistentPersistContainer extends CouchDbDocument implements Serializable {
    private static final long serialVersionUID = -5532887426346982279L;
    private PersistentField field;
    private PersistentGameState state;
    private String name;
    
    public PersistentPersistContainer() { }
    
    public PersistentPersistContainer(final PersistContainer container) {
        this.name = container.getName();
        this.field = new PersistentField(container.getField());
        this.state = new PersistentGameState(container.getGameState());
    }

    public PersistentField getField() {
        return field;
    }

    public void setField(PersistentField field) {
        this.field = field;
    }

    public PersistentGameState getState() {
        return state;
    }

    public void setState(PersistentGameState state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
