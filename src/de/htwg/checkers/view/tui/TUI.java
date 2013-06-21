package de.htwg.checkers.view.tui;

import java.util.Scanner;

import de.htwg.checkers.controller.GameController;
import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.util.observer.Observer;


public final class TUI implements Observer {

	
	private static int fieldsize;
	
	private IGameController gameController;
	
	public TUI(IGameController gameController) {
		this.gameController = gameController;
		this.gameController.addObserver(this);
	}
	
	public boolean input (String input) {
		return false;
	}
	
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
		
		TUI tui = new TUI();
		StringBuilder stringOutput = new StringBuilder();
				
		print("Black begins, have fun!");
		print("");
		tui.showLegend();
		print("");
		

		
		while (true) {
			
			TUI.preMovePrints(gameController, tui);
			
			print("Please enter your move (fromX fromY toX toY): ");
			moveFromX = scanner.nextInt();
			moveFromY = scanner.nextInt();
			moveToX = scanner.nextInt();
			moveToY = scanner.nextInt();
			print("");
			
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
					tui.showSituation(gameController);
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
		StringBuilder sb = new StringBuilder();
		print("Current situation:");
		
		for (int i = fieldsize-1; i >= 0; --i) {
			for (int j = 0; j < fieldsize; ++j) {
				fillFigureString(j,i,gameController,sb);
			}
			print(sb.toString());
			sb = new StringBuilder();
		}
		print(String.format("Overall number of moves in game: %d", gameController.getMoveCount()));
	}
	
	public void fillFigureString(int j, int i, GameController gameController, StringBuilder sb){
		if (gameController.getFigureOnField(j,i) == null) {
			sb.append(" - ");
		} else if (gameController.getFigureOnField(j, i).isBlack()) {
			if (!gameController.getFigureOnField(j, i).isCrowned()){
				sb.append(" x ");
			} else {
				sb.append(" X ");
			}
		} else {
			if (!gameController.getFigureOnField(j, i).isCrowned()){
				sb.append(" o ");
			} else {
				sb.append(" O ");
			}
		}
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
		System.out.println(string);
	}
	
	private static void preMovePrints(GameController gameController, TUI tUI) {
		tUI.showPositions();
		print("");
		tUI.showSituation(gameController);
		String activeColor;
		if (gameController.isBlackTurn()) {
			activeColor = "black";
		} else {
			activeColor = "white";
		}
		print("Active color: " + activeColor);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
