package model.observer;

import model.Liaison;

public interface Observable {
    void sendEvent(Liaison liaison);

    void setObserver(Observer observer);
}
