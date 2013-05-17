package de.htwg.checkers.controller;

import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;

public class GameController {
	
	private Field field;
	private final int rowsToFill;
	private int size;
	private static int countWhiteFigures;
	private static int countBlackFigures;
	
	public GameController(int size) {
		final int minSize = 4;
		if (size < minSize){
			throw new IllegalArgumentException();
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
	
	public boolean checkIfWin(){
		if (countBlackFigures == 0){
			System.out.println("White wins! Congratulations!");
			return true;
		} else if (countWhiteFigures == 0){
			System.out.println("Black wins! Congratulations!");
			return true;
		} else {
			return false;
		}
	}
	
	public Figure getFigureOnField(int x, int y){
		return field.getCellByCoordinates(x, y).getOccupier();
	}
	
	public boolean isValidCoordinate(int x, int y) {
		return x > 0 && y > 0 && x < size-1 && y < size-1;
	}
}
