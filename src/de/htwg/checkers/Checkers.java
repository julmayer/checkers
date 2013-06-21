package de.htwg.checkers;

import java.util.Scanner;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.view.tui.TUI;

public class Checkers {
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(final String[] args0) {
		// Set up Google Guice Dependency Injector		
		Injector injector = Guice.createInjector(new CheckersModel(8));
		
		IGameController gameController = injector.getInstance(IGameController.class);
		
		TUI tui = new TUI(gameController);
		boolean finished = false;
		
		while (!finished) {
			finished = tui.input(scanner.next("[0-9]{1,2} [0-9] [0-"));
		}
	}
}
