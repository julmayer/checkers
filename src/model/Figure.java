package model;

import java.util.List;

public abstract class Figure {
	public enum COLOR{white, black};
	private Field position;
	private boolean crowned;
	private boolean alive;
	private COLOR color;
	private List<Field> possibleMoves;
}
