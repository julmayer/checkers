package de.htwg.checkers.view;

import java.util.Scanner;

import de.htwg.checkers.controller.GameController;
import de.htwg.checkers.controller.FigureController;
import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.models.Figure.COLOR;

public final class Game {

	private Game() { }
	private static int fieldsize = 4;
	private static Field gamefield;
	private static Scanner scanner = new Scanner(System.in);
	private static int moveCount = 1;
	private static boolean blackTurn = true;
	private static Figure currentFigure;

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		
		int moveFromX;
		int moveFromY;
		int moveToX;
		int moveToY;
		
		FigureController figureController = new FigureController();
		GameController fieldController = new GameController(fieldsize);
		figureController.setFieldController(fieldController);
		
		fieldController.gameInit();
		
		Game game = new Game();
		gamefield = fieldController.getField();
				
		System.out.println("Black begins, have fun!");
		System.out.println();
		game.showPositions();
		System.out.println();
		game.showLegend();
		System.out.println();

		
		while (!fieldController.checkIfWin()) {
			
			game.showSituation();
			System.out.print("Please enter your move (fromX fromY toX toY): ");
			moveFromX = scanner.nextInt();
			moveFromY = scanner.nextInt();
			moveToX = scanner.nextInt();
			moveToY = scanner.nextInt();
			System.out.println();
			
			if (!validateInput(moveFromX,moveFromY) || !validateInput(moveToX,moveToY)){
				System.out.println("No Valid Input!");
				continue;
			}
			
			currentFigure = fieldController.getFigureOnField(moveFromX, moveFromY);
			figureController.createPossibleMoves(currentFigure);
			
			if (currentFigure == null) {
				System.out.println("No figure selected!");
				continue;
			}
			
			if (currentFigure.getColor() == COLOR.black && !blackTurn){
				System.out.println("Please select a white figure!");
				continue;
			}
			
			if (currentFigure.getColor() == COLOR.white && blackTurn){
				System.out.println("Please select a black figure!");
				continue;
			}
 
			if(currentFigure.getPossibleMoves().contains(gamefield.getCellByCoordinates(moveToX, moveToY))){
				System.out.println("YAYAY");
				
			} else {
				System.out.println("This is no possible move!");
				continue;
			}
			
			moveCount++;
			return;
		}
	}
	
	private void showSituation() {
		System.out.println("Current situation:");
		for (int i = fieldsize-1; i >= 0; --i) {
			for (int j = 0; j < fieldsize; ++j) {
				Cell cell = gamefield.getCellByCoordinates(j, i);
				Figure figure = cell.getOccupier();
				if (figure == null) {
					System.out.printf(" - ");
				} else if (figure.getColor().equals(Figure.COLOR.black)) {
					System.out.printf(" X ");
				} else {
					System.out.printf(" O ");
				}
			}
			System.out.println();
		}
		if (moveCount%2 != 0){
			blackTurn = true;
			System.out.println("Blacks turn.");
		} else{
			blackTurn = false;
			System.out.println("Whites turn.");
		}
	}
	
	private void showLegend() {
		System.out.println("X = Black, O = White, - = Empty");
	}
	
	private void showPositions() {
		System.out.println("Gamefiled positions:");
		for (int i = fieldsize-1; i >= 0; --i) {
			for (int j = 0; j < fieldsize; ++j) {
				System.out.printf("%d%d ", j, i);
			}
			System.out.println();
		}		
	}
	
	private static boolean validateInput(int x, int y){
		if (x < 0 || x >= fieldsize){
			return false;
		} else if (y < 0 || y >= fieldsize){
			return false;
		}
		return true;
	}
	
	

}
