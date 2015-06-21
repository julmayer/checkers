package de.htwg.checkers.persistence.hibernate;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;

/**
 *
 * @author Julian Mayer
 */
@Entity
@Table(name = "field")
public class PersistentField implements Serializable {
    private static final long serialVersionUID = 4665155963348140419L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "figures")
    private List<PersistentFigure> figures = new LinkedList<PersistentFigure>();
	@Column(name = "size")
    private int size;
	
	
	public PersistentField() { }
	
    /**
     * Copyconstructor for the Gamefiels
     * @param field source field for copy
     */
    public PersistentField(Field field) {
        for (int i = 0; i < field.getSize(); ++i) {
            for (int j = 0; j < field.getSize(); ++j) {
                Figure figure = field.getCellByCoordinates(i, j).getOccupier();
                if (figure == null) {
                    continue;
                }
                PersistentFigure pFigure = new PersistentFigure(figure);
                this.figures.add(pFigure);
            }
        }
    	this.size = field.getSize();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<PersistentFigure> getFigures() {
        return figures;
    }

    public void setFigures(List<PersistentFigure> figures) {
        this.figures = figures;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    
}