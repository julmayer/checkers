package de.htwg.checkers.controller.bot;

public interface IBot {
	/**
    * Creates a move form the Bot.
    * @return botMove as String with format "FromX FromY ToX ToY"
    */
	public String move();
}
