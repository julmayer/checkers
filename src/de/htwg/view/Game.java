package de.htwg.view;

import de.htwg.checkers.controller.FieldController;
import de.htwg.checkers.controller.FigureController;

public final class Game {

	private Game() { }
	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		FigureController figureController = new FigureController();
		FieldController fieldController = new FieldController(8);
		figureController.setFieldController(fieldController);
		
		fieldController.gameInit();
	}

}
