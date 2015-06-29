/**
 * 
 */
package de.htwg.checkers;

import com.google.inject.AbstractModule;

import de.htwg.checkers.controller.GameController;
import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.view.gui.GameFrame;
import de.htwg.checkers.view.gui.IGui;

/**
 * @author Julian Mayer, Marcel Loevenich
 *
 */
public class CheckersModule extends AbstractModule {
	/**
     * method to configure 
     */
    @Override
	protected void configure() {
        bind(IGui.class).to(GameFrame.class);
        bind(IGameController.class).to(GameController.class);
	}
}
