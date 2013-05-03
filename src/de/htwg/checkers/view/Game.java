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
	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		
		FigureController figureController = new FigureController();
		FieldController fieldController = new FieldController(fieldsize);
		figureController.setFieldController(fieldController);
		
		fieldController.gameInit();
		
		Game game = new Game();
		gamefield = fieldController.getField();
		
		while (true) {
			game.showPositions();
			System.out.println();
			game.showSituation();
			System.out.println();
			game.showLegend();
			System.out.println();

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
					System.out.printf(" _ ");
				} else if (figure.getColor().equals(Figure.COLOR.black)) {
					System.out.printf(" X ");
				} else {
					System.out.printf(" O ");
				}
			}
			System.out.println();
		}
	}
	
	private void showLegend() {
		System.out.println("X = Black, O = White, _ = Empty");
	}
	
	private void showPositions() {
		System.out.println("Gamefiled positions:");
		for (int i = fieldsize-1; i >= 0; --i) {
			for (int j = 0; j < fieldsize; ++j) {
				System.out.printf("%d%d ", j, i);
			}
			System.out.println("");
		}		
	}

}
