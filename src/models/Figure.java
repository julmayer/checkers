package models;

import java.util.LinkedList;
import java.util.List;

public class Figure {
	public enum COLOR{
		white,
		black};
	
	private Cell position;
	private boolean crowned;
	private boolean alive;
	private COLOR color;
	private List<Cell> possibleMoves;
	
	public Figure(Cell position, COLOR color) {
		this.position = position;
		this.color = color;
		this.alive = true;
		this.crowned = false;
		this.possibleMoves = new LinkedList<>();
	}

	public Cell getPosition() {
		return position;
	}

	public void setPosition(Cell position) {
		this.position = position;
	}

	public boolean isCrowned() {
		return crowned;
	}

	public void setCrowned(boolean crowned) {
		this.crowned = crowned;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public List<Cell> getPossibleMoves() {
		return possibleMoves;
	}

	public COLOR getColor() {
		return color;
	}

	public void createPossibleMoves() {
		if (crowned) {
			crownedMoves();
		} else {
			regulareMoves();
		}
	}
	
	private void regulareMoves() {
		
	}
	
	private void crownedMoves() {
		
	}
}
