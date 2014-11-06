package de.htwg.checkers.controller.bot;

public enum Bot {
	NO_BOT, SIMPLE_BOT, MEDIUM_BOT;
	
	public static Bot valueOf(int i) {
		return Bot.values()[i];
	}
}
