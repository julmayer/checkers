/**
 * 
 */
package de.htwg.checkers;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import de.htwg.checkers.controller.GameController;
import de.htwg.checkers.controller.IGameController;

/**
 * @author Julian Mayer, Marcel Loevenich
 *
 */
public class CheckersModel extends AbstractModule {
	
	private int size;
	
	/** 
     * Constructor for the checkersmodel
     * @param size
     */
    public CheckersModel(int size) {
		this.size = size;
	}

	/**
     * method to configure 
     */
    @Override
	protected void configure() {
		bind(IGameController.class).to(GameController.class);
		
		bindConstant().annotatedWith(Names.named("size")).to(size);
	}

}
