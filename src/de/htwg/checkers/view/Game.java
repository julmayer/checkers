package de.htwg.checkers.view;

import java.util.Scanner;

import de.htwg.checkers.controller.FieldController;
import de.htwg.checkers.controller.FigureController;
import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;

public final class Game {

	private Game() { }
	private static int fieldsize = 4;
	private static Field gamefield;
	private static Scanner scanner = new Scanner(System.in);
	private static int moveCount = 0;
	private static boolean blackTurn = true;

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		
		int moveFromX;
		int moveFromY;
		int moveToX;
		int moveToY;
		
		FigureController figureController = new FigureController();
		FieldController fieldController = new FieldController(fieldsize);
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

			moveCount++;
			game.showSituation();
			System.out.print("Please enter your move (fromX fromY toX toY): ");
			moveFromX = scanner.nextInt();
			moveFromY = scanner.nextInt();
			moveToX = scanner.nextInt();
			moveToY = scanner.nextInt();

 
			if(gamefield.getCellByCoordinates(moveFromX, moveFromY).getOccupier().getPossibleMoves().contains(gamefield.getCellByCoordinates(moveToX, moveToY))){
				System.out.println("YAYAY");
			} else {
				System.out.println("no possible move");
				continue;
			}

			return;
		}
	}
	
	private void showSituation() {
		System.out.println("actual situation:");
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
		System.out.println();
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
	
	

}
