package de.htwg.checkers.persistence.couchdb;

import org.ektorp.support.CouchDbDocument;

import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Figure;

/**
 * 
 * @author Julian Mayer
 */
public class PersistentFigure extends CouchDbDocument {
    private static final long serialVersionUID = 8461774934168356312L;
    private int x_position;
    private int y_position;
	private boolean crowned;
	private boolean black;

    public PersistentFigure() { }
    
	public PersistentFigure (Figure figure) {
        this.crowned = figure.isCrowned();
        this.black = figure.isBlack();
        Cell cell = figure.getPosition();
        this.x_position = cell.getX();
        this.y_position = cell.getY();
    }

    public int getX_position() {
        return x_position;
    }

    public void setX_position(int x_position) {
        this.x_position = x_position;
    }

    public int getY_position() {
        return y_position;
    }

    public void setY_position(int y_position) {
        this.y_position = y_position;
    }

    public boolean isCrowned() {
        return crowned;
    }

    public void setCrowned(boolean crowned) {
        this.crowned = crowned;
    }

    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black = black;
    }

}
