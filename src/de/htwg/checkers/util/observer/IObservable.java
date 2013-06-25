package de.htwg.checkers.util.observer;

/**
 *
 * @author jmayer
 */
public interface IObservable {

	/**
     *
     * @param s
     */
    void addObserver(Observer s);

	/**
     *
     * @param s
     */
    void removeObserver(Observer s);

	/**
     *
     */
    void removeAllObservers();

	/**
     *
     */
    void notifyObservers();
}
