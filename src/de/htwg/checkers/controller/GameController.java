package de.htwg.checkers.controller;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.models.Figure.COLOR;
import de.htwg.checkers.models.Move;

public class GameController {
	
	private Field field;
	private final int rowsToFill;
	private int size;
	private List<Figure> blacks;
	private List<Figure> whites;
	private COLOR activeColor;
	private FigureController figureController = new FigureController(this);
	private int moveCount;
	
	public GameController(int size) {
		final int minSize = 4;
		if (size < minSize){
			throw new IllegalArgumentException("Minimun size is 4!");
		} else {
			rowsToFill = size/2 - 1;
		}
		field = new Field(size);
		this.size = size;
	}
	
	public Field getField() {
		return this.field;
	}
	
	public int getFieldSize(){
		return this.size;
	}

	public COLOR getActiveColor() {
		return activeColor;
	}

	public void changeColor() {
		if (activeColor.equals(COLOR.black)) {
			activeColor = COLOR.white;
		} else {
			activeColor = COLOR.black;
		}
	}

	private void createWhiteFigures() {
		for (int y = 0; y < rowsToFill; y++){
			fillRow(y,Figure.COLOR.white);
		}
	}
	
	private void createBlackFigures() {
		for (int y = size - rowsToFill;y < size; y++){
			fillRow(y,Figure.COLOR.black);
		}
	}
	
	public void gameInit(){
		whites = new LinkedList<Figure>();
		blacks = new LinkedList<Figure>();
		createBlackFigures();
		createWhiteFigures();
		activeColor = COLOR.black; // black starts
	}
	
	private void fillRow(int y, Figure.COLOR color){
		for (int x = 0; x < size; x++) {
			if (x % 2 == 0 && y % 2 != 0 ){
				fillList(new Figure(field.getCellByCoordinates(x, y),color));
			} else if (x % 2 != 0 && y % 2 == 0 ){
				fillList(new Figure(field.getCellByCoordinates(x, y),color));
			}
		}
	}
	
	private void fillList(Figure figure){
		if (figure.getColor() == Figure.COLOR.black){
			blacks.add(figure);
		} else {
			whites.add(figure);
		}
	}
	
	public boolean checkIfWin(StringBuilder stringOutput){
		if (blacks.size() == 0){
			stringOutput.append("White wins! Congratulations!");
			return true;
		} else if (whites.size() == 0){
			stringOutput.append("Black wins! Congratulations!");
			return true;
		} else {
			return false;
		}
	}
	
	public Figure getFigureOnField(int x, int y){
		return field.getCellByCoordinates(x, y).getOccupier();
	}
	
	public boolean isValidCoordinate(int x, int y) {
		return x >= 0 && y >= 0 && x < size && y < size;
	}
	
	public boolean validateSelectedFigure(Figure figure, StringBuilder stringOutput, int x, int y){
		if (figure.getColor() == COLOR.black && activeColor == COLOR.white){
			stringOutput.delete(0, stringOutput.length());
			stringOutput.append("Please select a white figure!");
			return false;
		} else if (figure.getColor() == COLOR.white && activeColor == COLOR.black){
			stringOutput.delete(0, stringOutput.length());
			stringOutput.append("Please select a black figure!");
			return false;
		} else if (figure.getPossibleMoves().contains(field.getCellByCoordinates(x, y))){
			stringOutput.delete(0, stringOutput.length());
			stringOutput.append("BLAAAAAARGH GNAAAAAARF");
			return true;
		} else {
			stringOutput.delete(0, stringOutput.length());
			stringOutput.append("This is no possible move!");
			return false;
		}
	}
	
	public boolean isAFigureSelected (Figure figure, StringBuilder stringOutput){
		if (figure == null){
			stringOutput.delete(0, stringOutput.length());
			stringOutput.append("No figure selected!");
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isOccupiedByCoordinates(int x, int y){
		return field.getCellByCoordinates(x, y).isOccupied();
	}
	
	public COLOR getColorOfOccupierByCoordinates(int x, int y){
		return getFigureOnField(x, y).getColor();
	}
	
	public boolean isColorBlack(Figure figure) {
		return figure.getColor().equals(COLOR.black);
	}
	
	public boolean move(Figure from,int toX,int toY) {
		Move dummyMove = new Move(from.getPosition(),new Cell(toX,toY));
		int index = from.getPossibleMoves().indexOf(dummyMove);
		Move currentMove = from.getPossibleMoves().get(index);
		from.setPosition(currentMove.getTo());
		if (!currentMove.isKill()){
			changeColor();
			return false;
		} else {
			 Map<String,Integer> moveMap = currentMove.getCoordinatesLastSkipedCell();
			 Figure occupier = field.getCellByCoordinates(moveMap.get("X"), moveMap.get("Y")).getOccupier();
			 blacks.remove(occupier);
			 whites.remove(occupier);
			 occupier.kill();
			 figureController.createPossibleMoves(from);
			 if (from.hasKillMoves()){
				 deleteAllMovesWithoutFigure(from);
				 return true;
			 } else {
				 changeColor();
				 return false;
				 
			 }
		}
	}
	
	public void deleteAllMovesWithoutFigure(Figure figure){
		List<Figure> list;
		if (figure.getColor().equals(Figure.COLOR.black)) {
			list = blacks;
		} else {
			list = whites;
		}
		for (Figure current : list) {
			if (!current.equals(figure)) {
				current.setPossibleMoves(new LinkedList<Move>());
			}
		}
	}
	
	public void createAllMoves() {
		createAllMoves(activeColor.equals(COLOR.black) ? blacks : whites);
	}
	
	private void createAllMoves(List<Figure> figures) {
		boolean mustkill = false;
		// create all Moves
		for (Figure figure : figures) {
			figureController.createPossibleMoves(figure);
		}
		// check if moves are must kills
		for (Figure figure : figures) {
			if (figure.isMustKillMoves()) {
				mustkill = true;
				break;
			}
		}
		// remove regular moves if only must kills are allowed
		if (mustkill) {
			for (Figure figure : figures) {
				if (!figure.isMustKillMoves()) {
					figure.setPossibleMoves(new LinkedList<Move>());
				}
			}
		}
	}
	
	public void increaseMoveCount(){
		moveCount++;
	}
	
	public int getMoveCount(){
		return moveCount;
	}
}
