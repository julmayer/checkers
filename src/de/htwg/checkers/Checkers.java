package de.htwg.checkers;

import java.util.Scanner;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.view.gui.GameFrame;
import de.htwg.checkers.view.gui.InitFrame;
import de.htwg.checkers.view.tui.TUI;

/**
 * @author Julian Mayer, Marcel Loevenich
 *
 */
public final class Checkers {
	
	private static Scanner scanner;
	
	private Checkers() {
		
	}
	
	/**
     *main method, starts the programm
     * @param args0
     */
    public static void main(final String[] args0) {
		// Set up Google Guice Dependency Injector
		InitFrame initFrame = new InitFrame();
		synchronized (initFrame) {
			try {
				initFrame.wait();
			} catch (InterruptedException e) {
				
			}
		}
		Injector injector = Guice.createInjector(new CheckersModel(initFrame.getSize()));
		initFrame.exit();
		IGameController gameController = injector.getInstance(IGameController.class);
		
		gameController.gameInit();
		
		scanner = new Scanner(System.in);
		
		new TUI(gameController);
		new GameFrame(gameController);
		
		
		boolean finished = false;
		
		while (!finished) {
			finished = gameController.input(scanner.nextLine());
		}
		
		
	}
}
