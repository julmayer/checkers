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
public class CheckersModule extends AbstractModule {
	
	private int size;
	private boolean onePlayer;
	private int difficulty;
	
	/** 
     * Constructor for the checkersmodule
     * @param size
     */
    public CheckersModule(int size, boolean onePlayer, int difficulty) {
		this.size = size;
		this.onePlayer = onePlayer;
		this.difficulty = difficulty;
	}

	/**
     * method to configure 
     */
    @Override
	protected void configure() {
		bind(IGameController.class).to(GameController.class);
		
		bindConstant().annotatedWith(Names.named("size")).to(size);
		bindConstant().annotatedWith(Names.named("onePlayer")).to(onePlayer);
		bindConstant().annotatedWith(Names.named("difficulty")).to(difficulty);
	}

}
