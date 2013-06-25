package de.htwg.checkers.util.observer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jmayer
 */
public class Observable implements IObservable {
	
	private List<Observer> subscribers = new ArrayList<Observer>(2);

	/**
     *
     * @param s
     */
    @Override
	public void addObserver(Observer s) {
		subscribers.add(s);
	}

	/**
     *
     * @param s
     */
    @Override
	public void removeObserver(Observer s) {
		subscribers.remove(s);
	}

	/**
     *
     */
    @Override
	public void removeAllObservers() {
		subscribers.clear();
	}

	/**
     *
     */
    @Override
	public void notifyObservers() {
		for (Observer observer : subscribers) {
			observer.update();
		}
	}
}
