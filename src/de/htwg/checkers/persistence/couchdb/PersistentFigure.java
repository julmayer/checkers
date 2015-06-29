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
    private int xPosition;
    private int yPosition;
	private boolean crowned;
	private boolean black;

    public PersistentFigure() { }
    
	public PersistentFigure (Figure figure) {
        this.crowned = figure.isCrowned();
        this.black = figure.isBlack();
        Cell cell = figure.getPosition();
        this.xPosition = cell.getX();
        this.yPosition = cell.getY();
    }

    public int getXPosition() {
        return xPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
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
