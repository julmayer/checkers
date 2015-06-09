package de.htwg.checkers.view.tui;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.util.observer.Observer;


/**
 *the tui for the game
 * @author Julian Mayer, Marcel Loevenich
 */
public final class TUI implements Observer {

	private IGameController gameController;
	private static Logger logger = Logger.getLogger("de.htwg.checkers.view.tui");
	private String oldField;
	
	/**
     *constructor for the tui
     * @param gameController
     */
    @Inject
	public TUI(IGameController gameController) {
		this.gameController = gameController;
		this.gameController.addObserver(this);
		
		print("Welcome to checkers!");
	    update();
	}
    
	private void askForInitialization() {
		print("Insert size of field between 4 and 12,"
				+ "(M)ulti- (S)ingelplayer and easy (0) or medium (1) bot"
				+ "(not necessary in multiplayer mode");
	}
	
	private void askAfterWin() {
		print("(Q)uit, (R)estart, (N)ew game?");
	}

	private void printTui() {
		String newField = gameController.getDrawingOfField();
		if (oldField != null && oldField.equals(newField)) {
			return;
		}
		oldField = newField;
		print(newField);
		print(String.format("Overall number of moves in game: %d", gameController.getMoveCount()));
		showLegend();
		String activeColor;
		if (gameController.isBlackTurn()) {
			activeColor = "black";
		} else {
			activeColor = "white";
		}
		print("Active color: " + activeColor);
		askAfterWin();
		print("Insert Move: ");
	}
	
	private void showLegend() {
		print("x = Black, o = White, - = Empty");
		print("X = Black crowned, O = White crowned");
	}

	private static void print(String string){
		logger.info(string);
	}
	
	/**
     *method to update tui
     */
    @Override
	public void update() {
		String error = gameController.getError();
		
		if (error.isEmpty()) {
			switch (gameController.getCurrentState()) {
			case NEW_GAME:
				askForInitialization();
				break;
			case RUNNING:
				printTui();
				if (gameController.checkIfWin()) {
					print(gameController.getInfo());
					askAfterWin();
				}
				break;
			default:
				// Quit
				break;
			}
		} else {
			print(error);
		}
	}
}
