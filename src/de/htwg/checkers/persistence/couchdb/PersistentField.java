package de.htwg.checkers.persistence.couchdb;

import java.util.LinkedList;
import java.util.List;

import org.ektorp.support.CouchDbDocument;

import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;

/**
 *
 * @author Julian Mayer
 */
public class PersistentField extends CouchDbDocument {
    private static final long serialVersionUID = 664561336773796152L;
    private List<PersistentFigure> figures = new LinkedList<PersistentFigure>();
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