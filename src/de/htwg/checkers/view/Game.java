package de.htwg.checkers.view;

import java.util.Scanner;

import de.htwg.checkers.controller.GameController;


public final class Game {

	private Game() { }
	private static int fieldsize;
	private static Scanner scanner = new Scanner(System.in);

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		
		int moveFromX;
		int moveFromY;
		int moveToX;
		int moveToY;
		boolean hasMoreKills = false;
		
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
			String activeColor;
			if (gameController.isBlackTurn()) {
				activeColor = "black";
			} else {
				activeColor = "white";
			}
			print("Active color: " + activeColor);
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
			if (!hasMoreKills){
				gameController.createAllMoves();
			}
				
			if (gameController.validateSelectedFigure(gameController.getFigureOnField(moveFromX, moveFromY), stringOutput, moveToX, moveToY)){
				//possible move
				hasMoreKills = gameController.move(gameController.getFigureOnField(moveFromX, moveFromY),moveToX,moveToY);
				
				if(hasMoreKills){
					continue;
				}
				
				if (gameController.checkIfWin(stringOutput)) {
					game.showSituation(gameController);
					print(stringOutput.toString());
					break;
				}
			} else {
				print(stringOutput.toString());
				continue;
			}
			gameController.increaseMoveCount();
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
				} else if (gameController.getFigureOnField(j, i).isBlack()) {
					if (!gameController.getFigureOnField(j, i).isCrowned()){
						s = (s + " x ");
					} else {
						s = (s + " X ");
					}
				} else {
					if (!gameController.getFigureOnField(j, i).isCrowned()){
						s = (s + " o ");
					} else {
						s = (s + " O ");
					}
				}
			}
			print(s);
			s = "";
		}
		print(String.format("Overall number of moves in game: %d", gameController.getMoveCount()));
	}
	
	private void showLegend() {
		print("x = Black, o = White, - = Empty");
		print("X = Black crowned, O = White crowned");
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
