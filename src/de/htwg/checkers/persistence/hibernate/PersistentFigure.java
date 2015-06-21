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
    private int x_position;
    @Column(name = "y_position")
    private int y_position;
    @Column(name = "crowned")
	private boolean crowned;
    @Column(name = "black")
	private boolean black;

    public PersistentFigure() { }
    
	public PersistentFigure (Figure figure) {
        this.crowned = figure.isCrowned();
        this.black = figure.isBlack();
        Cell cell = figure.getPosition();
        this.x_position = cell.getX();
        this.y_position = cell.getY();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
