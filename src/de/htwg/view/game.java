package de.htwg.view;

import de.htwg.checkers.controller.FieldController;
import de.htwg.checkers.controller.FigureController;

public class game {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		FigureController figureController = new FigureController();
		FieldController fieldController = new FieldController();
		figureController.setFieldController(fieldController);
		
		fieldController.gameInit();
	}

}
