package de.htwg.checkers.view;

import java.util.Scanner;

import de.htwg.checkers.controller.GameController;


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
		gameController.gameInit();
		
		Game game = new Game();
		StringBuilder stringOutput = new StringBuilder();
				
		print("Black begins, have fun!");
		print(null);
		game.showPositions();
		print(null);
		game.showLegend();
		print(null);
		

		
		while (true) {
			
			game.showSituation(gameController);
			print("active color: " + gameController.getActiveColor().toString());
			print("Please enter your move (fromX fromY toX toY): ");
			moveFromX = scanner.nextInt();
			moveFromY = scanner.nextInt();
			moveToX = scanner.nextInt();
			moveToY = scanner.nextInt();
			print(null);
			
			if (!gameController.isValidCoordinate(moveFromX,moveFromY) || !gameController.isValidCoordinate(moveToX,moveToY)){
				print("No Valid Input!");
				continue;
			}

			if (!gameController.isAFigureSelected(gameController.getFigureOnField(moveFromX, moveFromY), stringOutput)){
				print(stringOutput.toString());
				continue;
			}
			
			gameController.createAllMoves();
			
			if (gameController.validateSelectedFigure(gameController.getFigureOnField(moveFromX, moveFromY), blackTurn, stringOutput, moveToX, moveToY)){
				//possible move
				gameController.move(gameController.getFigureOnField(moveFromX, moveFromY),moveToX,moveToY);
				print(stringOutput.toString());
				if (gameController.checkIfWin(stringOutput)) {
					print(stringOutput.toString());
					break;
				}
				// TODO: if killed, multikill?
				gameController.changeColor();
			} else {
				print(stringOutput.toString());
				continue;
			}
			moveCount++;
			continue;
		}
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
	
	
	private static void print(String string){
		if (string == null){
			System.out.println();
		} else {
			System.out.println(string);
		}
	}
}
