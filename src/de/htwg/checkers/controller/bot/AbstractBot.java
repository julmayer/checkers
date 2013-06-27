package de.htwg.checkers.controller.bot;

import java.util.List;

import com.google.inject.Inject;

import de.htwg.checkers.models.Figure;

public abstract class AbstractBot implements IBot {

	private List<Figure> figures;
	
	@Inject
	public AbstractBot(List<Figure> figures) {
		this.figures = figures;
	}
	
	protected List<Figure> getFigures() {
		return this.figures;
	}
	
	public abstract String move();
}
