package model;

import model.data.Map;

/**
 * Le labyrinthe.
 */
public class Labyrinthe implements Observable{
    /**
     * La {@link Map} de liaisons assossié au {@link Labyrinthe}.
     */
    private Map map;

    private Observer observer;

    /**
     * Crée une instance de {@link Labyrinthe} de dimension {@code longueur}
     * et {@code largeur}.
     * Les liaisons entre les cases sont générées à partire de valeurs aléatoire
     * bornées entre {@code min} et {@code max}.
     *
     * @param longueur La longueur du nouveau {@link Labyrinthe}.
     * @param largeur  la largeur du nouveau {@link Labyrinthe}.
     * @param min      Le minimum des valeurs aléatoires pour les liaison (inclue).
     * @param max      Le maximum des valeurs aléatoires pour les liaison (exclue).
     * @throws IllegalArgumentException Si la longeur ou la largeur est inférieure ou égale à zéro.
     */
    public Labyrinthe(int longueur, int largeur, int min, int max) {
        map = new Map(longueur, largeur);
        map.metDesValeursAleatoires(min, max);
    }

    /**
     * Retourne la {@link Case} du l'{@link Labyrinthe} de coordonées {@code x}
     * et {@code y}
     * @param x La coordonées x de la case souhaitée.
     * @param y La coordonées y de la case souhaitée.
     * @return la {@link Case} correspondantes aux coordonées
     * @throws IllegalArgumentException Si au moins une des coordonées x ou y est invalide.
     */
    public Case getCase(int x, int y){
        map.isValideXY(x, y);
        return new Case(this, x, y);
    }

    public Map getMap() {
        return map;
    }

    @Override
    public void sendEvent() {
        assert observer != null;
        observer.onEvent();
    }

    @Override
    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    /*
    TODO algo de PRIM
     */
}
