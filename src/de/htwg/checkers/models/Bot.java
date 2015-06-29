package de.htwg.checkers.models;

/**
 * 
 * @author jmayer
 *
 */
public enum Bot {
	NO_BOT, SIMPLE_BOT, MEDIUM_BOT;
	
	public static Bot valueOf(int i) {
		return Bot.values()[i];
	}
}
