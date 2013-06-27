package de.htwg.checkers.util.observer;

/**
 *
 * @author Julian Mayer, Marcel Loevenich
 */
public interface IObservable {

	/**
     * method to add a observer
     * @param s
     */
    void addObserver(Observer s);

	/**
     * method to remove one specified observer
     * @param s
     */
    void removeObserver(Observer s);

	/**
     * method to remove all observer
     */
    void removeAllObservers();

	/**
     * send a notify
     */
    void notifyObservers();
}
