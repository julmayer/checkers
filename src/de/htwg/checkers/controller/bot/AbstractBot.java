package de.htwg.checkers.controller.bot;

import java.util.List;

import com.google.inject.Inject;

import de.htwg.checkers.models.Figure;

/**
 * Abstrace class for bots with Constuctor.
 * @author jmayer
 *
 */
public abstract class AbstractBot implements IBot {

	private List<Figure> figures;
	
	/**
	 * Constructor with List of own Figures.
	 * @param figures List of own Figures.
	 */
	@Inject
	public AbstractBot(List<Figure> figures) {
		this.figures = figures;
	}
	
	/**
	 * Getter for the List of own Figures.
	 * @return List of own Figures.
	 */
	protected List<Figure> getFigures() {
		return this.figures;
	}
	
	/**
	 * Funktion for automatically generatet Move.
	 * @return String for move.
	 */
	public abstract String move();
}
