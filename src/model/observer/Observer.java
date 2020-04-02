package model.observer;

import model.Liaison;

public interface Observer {
    void onEvent(Liaison liaison);
}
