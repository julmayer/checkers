package de.htwg.checkers.persistence.hibernate;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.htwg.checkers.persistence.PersistContainer;

@Entity
@Table(name = "persist_container")
public class PersistentPersistContainer implements Serializable {
    private static final long serialVersionUID = -5532887426346982279L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private PersistentField field;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private PersistentGameState state;
    @Column(name = "name", unique = true)
    private String name;
    
    public PersistentPersistContainer() { }
    
    public PersistentPersistContainer(final PersistContainer container) {
        this.name = container.getName();
        this.field = new PersistentField(container.getField());
        this.state = new PersistentGameState(container.getGameState());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
