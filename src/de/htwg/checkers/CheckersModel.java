/**
 * 
 */
package de.htwg.checkers;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import de.htwg.checkers.controller.GameController;
import de.htwg.checkers.controller.IGameController;

/**
 * @author jmayer
 *
 */
public class CheckersModel extends AbstractModule {
	
	private int size;
	
	/**
     *
     * @param size
     */
    public CheckersModel(int size) {
		this.size = size;
	}

	/**
     *
     */
    @Override
	protected void configure() {
		bind(IGameController.class).to(GameController.class);
		
		bindConstant().annotatedWith(Names.named("size")).to(size);
	}

}
