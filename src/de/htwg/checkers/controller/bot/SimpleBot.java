package de.htwg.checkers.controller.bot;

import java.util.List;

import de.htwg.checkers.models.Figure;


public class SimpleBot extends AbstractBot {

	
	public SimpleBot(List<Figure> figures) {
		super(figures);
	}

	@Override
	public String move() {
		String move = "";
		
		for (Figure figure : getFigures()) {
			if (figure.getPossibleMoves().size() != 0) {
				move = figure.getPossibleMoves().get(0).toString();
				break;
			}
		}
		
		return move;
	}

}
