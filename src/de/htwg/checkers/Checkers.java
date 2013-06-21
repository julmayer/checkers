package de.htwg.checkers;

import java.util.Scanner;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.util.observer.Observer;
import de.htwg.checkers.view.gui.GameFrame;
import de.htwg.checkers.view.gui.InitFrame;
import de.htwg.checkers.view.tui.TUI;

public class Checkers implements Observer {
	
	private static Scanner scanner = new Scanner(System.in);
	private static InitFrame initFrame;
	
	public static void main(final String[] args0) {
		// Set up Google Guice Dependency Injector
		initFrame = new InitFrame();
		Checkers checkers = new Checkers();
		initFrame.addObserver(checkers);
	}
	
	@Override
	public void update() {
		Injector injector = Guice.createInjector(new CheckersModel(initFrame.getSize()));
		initFrame.exit();
		IGameController gameController = injector.getInstance(IGameController.class);
		
		gameController.gameInit();
		
		new GameFrame(gameController);
		new TUI(gameController);		
		
		boolean finished = false;
		
		while (!finished) {
			finished = gameController.input(scanner.nextLine());
		}
	}
}
