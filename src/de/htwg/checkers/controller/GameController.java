package de.htwg.checkers.controller;


import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.models.Figure.COLOR;

public class GameController {
	
	private Field field;
	private final int rowsToFill;
	private int size;
	private static int countWhiteFigures;
	private static int countBlackFigures;
	
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
		createBlackFigures();
		createWhiteFigures();
	}
	
	public Field getField() {
		return this.field;
	}
	
	public int getFieldSize(){
		return this.size;
	}
	
	private void fillRow(int y, Figure.COLOR color){
		for (int x = 0; x < size; x++) {
			if (x % 2 == 0 && y % 2 != 0 ){
				new Figure(field.getCellByCoordinates(x, y),color);
				increaseFigureCount(color);
			} else if (x % 2 != 0 && y % 2 == 0 ){
				new Figure(field.getCellByCoordinates(x, y),color);
				increaseFigureCount(color);
			}
		}
	}
	
	private void increaseFigureCount(Figure.COLOR color){
		if (color == Figure.COLOR.black){
			countBlackFigures++;
		} else {
			countWhiteFigures++;
		}
	}
	
	public boolean checkIfWin(StringBuilder stringOutput){
		if (countBlackFigures == 0){
			stringOutput.append("White wins! Congratulations!");
			return true;
		} else if (countWhiteFigures == 0){
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
	
	public boolean validateSelectedFigure(Figure figure, boolean blackTurn, StringBuilder stringOutput, int x, int y){
		if (figure.getColor() == COLOR.black && !blackTurn){
			stringOutput.delete(0, stringOutput.length());
			stringOutput.append("Please select a white figure!");
			return false;
		} else if (figure.getColor() == COLOR.white && blackTurn){
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
	
	public boolean isColorBlack(Figure figure){
		return figure.getColor().equals(COLOR.black);
	}
	
	public void move(Figure from,int toX,int toY){
		from.setPosition(field.getCellByCoordinates(toX, toY));
		
	}
}
