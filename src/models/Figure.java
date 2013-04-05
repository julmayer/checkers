package models;

import java.util.List;

public abstract class Figure {
	public enum COLOR{white, black};
	private Cell position;
	private boolean crowned;
	private boolean alive;
	private COLOR color;
	private List<Cell> possibleMoves;
}
