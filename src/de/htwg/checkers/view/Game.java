package de.htwg.checkers.view;

import java.util.Scanner;

import de.htwg.checkers.controller.GameController;
import de.htwg.checkers.controller.FigureController;


public final class Game {

	private Game() { }
	private static int fieldsize;
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
		
		print("Welcome to checkers!");
		print("Please enter size of field (minimun size is 4):");
		fieldsize = scanner.nextInt();
		
		GameController gameController = new GameController(fieldsize);
		FigureController figureController = new FigureController();
		figureController.setFieldController(gameController);
		
		gameController.gameInit();
		
		Game game = new Game();
		StringBuilder stringOutput = new StringBuilder();
				
		print("Black begins, have fun!");
		print(null);
		game.showPositions();
		print(null);
		game.showLegend();
		print(null);
		

		
		while (!gameController.checkIfWin(stringOutput)) {
			
			game.showSituation(gameController);
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

			gameController.isOccupiedByCoordinates(moveFromX, moveFromY);
			
			if (!gameController.isAFigureSelected(gameController.getFigureOnField(moveFromX, moveFromY), stringOutput)){
				print(stringOutput.toString());
				continue;
			}
			
			figureController.createPossibleMoves(gameController.getFigureOnField(moveFromX, moveFromY));
			
			if (gameController.validateSelectedFigure(gameController.getFigureOnField(moveFromX, moveFromY), blackTurn, stringOutput, moveToX, moveToY)){
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
	
	private void showSituation(GameController gameController) {
		String s = "";
		print("Current situation:");
		for (int i = fieldsize-1; i >= 0; --i) {
			for (int j = 0; j < fieldsize; ++j) {
				if (gameController.getFigureOnField(j,i) == null) {
					s = (s + " - ");
				} else if (gameController.isColorBlack(gameController.getFigureOnField(j, i))) {
					s = (s + " X ");
				} else {
					s = (s + " O ");
				}
			}
			print(s);
			s = "";
		}
		print(String.format("Overall number of moves in game: %d", moveCount));
		if (moveCount%2 != 0){
			blackTurn = false;
			print("Whites turn");
		} else{
			blackTurn = true;
			print("Blacks turn.");
		}
	}
	
	private void showLegend() {
		print("X = Black, O = White, - = Empty");
	}
	
	private void showPositions() {
		String s = "";
		print("Gamefiled positions:");
		for (int i = fieldsize-1; i >= 0; --i) {
			for (int j = 0; j < fieldsize; ++j) {
				s = s + String.format("%d%d ",j,i);
			}
			print(s);
			s = "";
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
