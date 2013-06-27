package de.htwg.checkers.view.tui;

import com.google.inject.Inject;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.util.observer.Observer;


/**
 *the tui for the game
 * @author Julian Mayer, Marcel Loevenich
 */
public final class TUI implements Observer {

	private IGameController gameController;
	
	/**
     *constructor for the tui
     * @param gameController
     */
    @Inject
	public TUI(IGameController gameController) {
		this.gameController = gameController;
		this.gameController.addObserver(this);
		
		print("Welcome to checkers!");
		printTui();
	}
	
	private void printTui() {
		print(gameController.getField().toString());
		print(String.format("Overall number of moves in game: %d", gameController.getMoveCount()));
		showLegend();
		String activeColor;
		if (gameController.isBlackTurn()) {
			activeColor = "black";
		} else {
			activeColor = "white";
		}
		print("Active color: " + activeColor);
		print("Insert Move: ");
	}
	
	private void showLegend() {
		print("x = Black, o = White, - = Empty");
		print("X = Black crowned, O = White crowned");
	}

	private static void print(String string){
		System.out.println(string);
	}
	
	/**
     *method to update tui
     */
    @Override
	public void update() {
		StringBuilder sb = new StringBuilder();
		String error = gameController.getError();
		
		if (error == null) {
			printTui();
			if (gameController.checkIfWin(sb)) {
				print(sb.toString());
			}
		} else {
			print(error);
		}
	}
}
