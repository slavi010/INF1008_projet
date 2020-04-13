package model.observer;

import model.Liaison;

/**
 * @author Sviatoslav Besnard
 */
public interface Observable {
    void sendEvent(Liaison liaison, int code);

    void setObserver(Observer observer);
}
