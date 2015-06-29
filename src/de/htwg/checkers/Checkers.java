package de.htwg.checkers;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.view.gui.IGui;
import de.htwg.checkers.view.gui.impl.GameFrame;
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
        // Set up logging through log4j
    	PropertyConfigurator.configure("log4j.properties");

		// Set up Google Guice Dependency Injector
    	Injector persistenceInjectior = Guice.createInjector(new PersistenceModule());

    	Injector injector = persistenceInjectior.createChildInjector(
		        new CheckersModule(), new PluginModule());

		IGameController gameController = injector.getInstance(IGameController.class);

		scanner = new Scanner(System.in);

		@SuppressWarnings("unused")
		TUI tui = injector.getInstance(TUI.class);
		@SuppressWarnings("unused")
		IGui gui = injector.getInstance(GameFrame.class);

		boolean finished = false;

		while (!finished) {
			finished = gameController.input(scanner.nextLine());
		}
		
		
	}
}
