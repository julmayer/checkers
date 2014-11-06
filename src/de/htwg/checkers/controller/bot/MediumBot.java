package de.htwg.checkers.controller.bot;

import java.util.List;

import de.htwg.checkers.models.Figure;

/**
 * Class for Medium bot. Returns random move of 2nd Figure.
 * @author jmayer
 *
 */
public class MediumBot extends AbstractBot {

	/**
	 * Constructor with List of own Figures.
	 * @param figures List of own Figures.
	 */
	public MediumBot(List<Figure> figures) {
		super(figures, Bot.MEDIUM_BOT);
	}

	@Override
	public String move() {
		String move = "";
		boolean first = true;
		for (Figure figure : getFigures()) {
			if (!figure.getPossibleMoves().isEmpty()) {
				int i = (int)(Math.random() * ((figure.getPossibleMoves().size()-1) + 1));
				move = figure.getPossibleMoves().get(i).toString();
				if (!first) {
					break;
				}
				first = false;
			}
		}
		
		return move;
	}

}
