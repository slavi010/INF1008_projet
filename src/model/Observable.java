package model;

public interface Observable {
    void sendEvent();

    void setObserver(Observer observer);
}
