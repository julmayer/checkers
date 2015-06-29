package de.htwg.checkers.controller.bot;


/**
 * Interface for the bots
 * @author jmayer
 *
 */
public interface IBot {
	/**
    * Creates a move form the Bot.
    * @return botMove as String with format "FromX FromY ToX ToY"
    */
	String move();
}
