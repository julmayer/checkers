/**
 * 
 */
package de.htwg.checkers;

import com.google.inject.AbstractModule;

import de.htwg.checkers.controller.GameController;
import de.htwg.checkers.controller.IGameController;

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
        bind(IGameController.class).to(GameController.class);
	}
}
