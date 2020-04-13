package model.observer;

import model.Liaison;

/**
 * @author Sviatoslav Besnard
 */
public interface Observer {
    void onEvent1(Liaison liaison);
    void onEvent2(Liaison liaison);
    void onEvent3(Liaison liaison);
    void onEvent4(Liaison liaison);
}
