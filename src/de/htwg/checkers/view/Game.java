package de.htwg.checkers.view;

import java.util.Scanner;

import de.htwg.checkers.controller.GameController;
import de.htwg.checkers.controller.FigureController;
import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;

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
		GameController gameController = new GameController(fieldsize);
		figureController.setFieldController(gameController);
		
		gameController.gameInit();
		
		Game game = new Game();
		gamefield = gameController.getField();
		StringBuilder stringOutput = new StringBuilder();
				
		print("Black begins, have fun!");
		print(null);
		game.showPositions();
		print(null);
		game.showLegend();
		print(null);
		

		
		while (!gameController.checkIfWin(stringOutput)) {
			
			game.showSituation();
			print("Please enter your move (fromX fromY toX toY): ");
			moveFromX = scanner.nextInt();
			moveFromY = scanner.nextInt();
			moveToX = scanner.nextInt();
			moveToY = scanner.nextInt();
			print(null);
			
			if (!validateInput(moveFromX,moveFromY) || !validateInput(moveToX,moveToY)){
				print("No Valid Input!");
				continue;
			}
			
			currentFigure = gameController.getFigureOnField(moveFromX, moveFromY);
			
			if (!gameController.isAFigureSelected(currentFigure, stringOutput)){
				print(stringOutput.toString());
				continue;
			}
			
			figureController.createPossibleMoves(currentFigure);
			
			if (gameController.validateSelectedFigure(currentFigure, blackTurn, stringOutput, moveToX, moveToY)){
				print(stringOutput.toString());
			} else {
				print(stringOutput.toString());
				continue;
			}
			
			
			
			moveCount++;
			return;
		}
		print(stringOutput.toString());
	}
	
	private void showSituation() {
		print("Current situation:");
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
			print(null);
		}
		if (moveCount%2 != 0){
			blackTurn = true;
			print("Blacks turn.");
		} else{
			blackTurn = false;
			print("Whites turn.");
		}
	}
	
	private void showLegend() {
		print("X = Black, O = White, - = Empty");
	}
	
	private void showPositions() {
		print("Gamefiled positions:");
		for (int i = fieldsize-1; i >= 0; --i) {
			for (int j = 0; j < fieldsize; ++j) {
				System.out.printf("%d%d ", j, i);
			}
			print(null);
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
	
	private static void print(String string){
		if (string == null){
			System.out.println();
		} else {
			System.out.println(string);
		}
	}
}
