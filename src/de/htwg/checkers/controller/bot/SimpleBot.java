package de.htwg.checkers.controller.bot;

import java.util.List;

import de.htwg.checkers.models.Figure;

/**
 * Class for simple bot. Always returns first move.
 * @author jmayer
 *
 */
public class SimpleBot extends AbstractBot {

	/**
	 * Constructor with List of own Figures.
	 * @param figures List of own Figures.
	 */
	public SimpleBot(List<Figure> figures) {
		super(figures, Bot.SIMPLE_BOT);
	}

	@Override
	public String move() {
		String move = "";
		
		for (Figure figure : getFigures()) {
			if (!figure.getPossibleMoves().isEmpty()) {
				move = figure.getPossibleMoves().get(0).toString();
				break;
			}
		}
		
		return move;
	}
}
