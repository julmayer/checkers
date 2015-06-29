package de.htwg.checkers.persistence.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Figure;

/**
 * 
 * @author Julian Mayer
 */
@Entity
@Table(name = "figure")
public class PersistentFigure implements Serializable {
    private static final long serialVersionUID = 8461774934168356312L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "x_position")
    private int xPosition;
    @Column(name = "y_position")
    private int yPosition;
    @Column(name = "crowned")
	private boolean crowned;
    @Column(name = "black")
	private boolean black;

    public PersistentFigure() { }
    
	public PersistentFigure (Figure figure) {
        this.crowned = figure.isCrowned();
        this.black = figure.isBlack();
        Cell cell = figure.getPosition();
        this.xPosition = cell.getX();
        this.yPosition = cell.getY();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
