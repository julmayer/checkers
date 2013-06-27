package de.htwg.checkers.controller.bot;

import java.util.List;

import de.htwg.checkers.models.Figure;

public class MediumBot extends AbstractBot {

	public MediumBot(List<Figure> figures) {
		super(figures);
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
