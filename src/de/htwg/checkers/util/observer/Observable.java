package de.htwg.checkers.util.observer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Julian Mayer, Marcel Loevenich
 */
public class Observable implements IObservable {
	
	private List<Observer> subscribers = new ArrayList<Observer>(2);

	/**
     * method to add a observer
     * @param s
     */
    @Override
	public void addObserver(Observer s) {
		subscribers.add(s);
	}

	/**
     * method to remove one specified observer
     * @param s
     */
    @Override
	public void removeObserver(Observer s) {
		subscribers.remove(s);
	}

	/**
     * method to remove all observers
     */
    @Override
	public void removeAllObservers() {
		subscribers.clear();
	}

	/**
     * send notify
     */
    @Override
	public void notifyObservers() {
		for (Observer observer : subscribers) {
			observer.update();
		}
	}
}
