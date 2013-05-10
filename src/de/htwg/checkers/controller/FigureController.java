package de.htwg.checkers.controller;

import java.util.LinkedList;
import java.util.List;

import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.models.Figure.COLOR;


public class FigureController {
	
	private FieldController fieldController;
	
	public void setFieldController(FieldController fieldController) {
		this.fieldController = fieldController;
	}
	
	public void createPossibleMoves(Figure figure) {
		if (figure.isCrowned()) {
			figure.setPossibleMoves(crownedMoves(figure));
		} else {
			figure.setPossibleMoves(regulareMoves(figure));
		}
	}
	
	private List<Cell> regulareMoves(Figure figure) {
		List<Cell> possibleMoves = new LinkedList<Cell>();
		int x = figure.getPosition().getX();
		int y = figure.getPosition().getY();
		Cell upperLeft = null;
		Cell upperRight = null;
		Cell lowerLeft = null;
		Cell lowerRight = null;
		List<Cell> neighbourCells = new LinkedList<Cell>();
		
		upperLeft = fillNeighbourCellList(x-1, y+1, upperLeft, neighbourCells);
		upperRight = fillNeighbourCellList(x+1, y+1, upperRight, neighbourCells);
		lowerLeft = fillNeighbourCellList(x-1, y-1, lowerLeft, neighbourCells);
		lowerRight = fillNeighbourCellList(x+1, y-1, lowerRight, neighbourCells);
		
		/*
		if (fieldController.isValidCoordinate(x-1, y+1)){
			upperLeft = fieldController.getField().getCellByCoordinates(x-1, y+1);
			neighbourCells.add(upperLeft);
		}
 
		if (fieldController.isValidCoordinate(x+1, y+1)){
			upperRight = fieldController.getField().getCellByCoordinates(x+1, y+1);
			neighbourCells.add(upperRight);
		}
		
		if (fieldController.isValidCoordinate(x-1, y-1)){
			lowerLeft = fieldController.getField().getCellByCoordinates(x-1, y-1);
			neighbourCells.add(lowerLeft);
		}
		
		if (fieldController.isValidCoordinate(x+1, y-1)){
			lowerRight = fieldController.getField().getCellByCoordinates(x+1, y-1);
			neighbourCells.add(lowerRight);
		}*/
				
		possibleMoves = mustKill(figure, neighbourCells);
		
		if (possibleMoves.size() > 0) {
			return possibleMoves;
		}

		if (figure.getColor().equals(COLOR.black)) {
			if (lowerLeft != null && !lowerLeft.isOccupied()) {
				possibleMoves.add(lowerLeft);
			}
			if (lowerRight != null && !lowerRight.isOccupied()) {
				possibleMoves.add(lowerRight);
			}
		} else {
			if (upperLeft != null && !upperLeft.isOccupied()) {
				possibleMoves.add(upperLeft);
			}
			if (upperRight != null && !upperRight.isOccupied())  {
				possibleMoves.add(upperRight);
			}			
		}
		return possibleMoves;
	}
	
	private List<Cell> crownedMoves(Figure figure) {
		return new LinkedList<Cell>();
	}
	
	private List<Cell> mustKill(Figure figure, List<Cell> cells) {
		List<Cell> result = new LinkedList<Cell>();
		
		for (Cell cell : cells) {
			if (cell.isOccupied() && !cell.getOccupier().getColor().equals(figure.getColor())
					&& cellBehindfree(figure.getPosition(), cell)) {
				result.add(cell);
			}
		}
		return result;
	}
	
	private boolean cellBehindfree(Cell source, Cell destination) {
		int srcX = source.getX();
		int srcY = source.getY();
		int destX, destY;
		boolean left, lower;
		
		left = destination.getX() - srcX < 0;
		lower = destination.getY() - srcY < 0;

		if (left) {
			destX = srcX - 2;
		} else {
			destX = srcX + 2;
		}
		if (lower) {
			destY = srcY - 2;
		} else {
			destY = srcX + 2;
		}
		
		if (!fieldController.isValidCoordinate(destX, destY)) {
			return false;
		}
		return fieldController.getField().getCellByCoordinates(destX, destY).isOccupied();
	}
	
	public void move(Cell from, Cell to) {
		//Figure figure = from.getOccupier();
		
	}
	
	 private Cell fillNeighbourCellList(int x, int y, Cell cell, List<Cell> neighbourCellList){
		 if (fieldController.isValidCoordinate(x, y)){
				cell = fieldController.getField().getCellByCoordinates(x, y);
				neighbourCellList.add(cell);
				return cell;
			}
		 return null;
	 }
}
