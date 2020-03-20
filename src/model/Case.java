package model;

import model.data.Map;

/**
 * Une case du {@link Labyrinthe}.
 */
public class Case {
    /**
     * Le {@link Labyrinthe} au quel est rataché cette case.
     */
    private Labyrinthe labyrinthe;

    /**
     * Les coordonnées x et y de cette instance dans
     * le {@link Labyrinthe}.
     */
    private int x, y;

    /**
     * Crée une instance {@link Case} de coordonnées {@code x} et
     * {@code y} ratachée au {@code labyrinthe}.
     *
     * @param labyrinthe La labyrinthe au quel la nouvelle {@link Case}.
     *                   est ratachée.
     * @param x          La coordonnées x de la nouvelle {@link Case}.
     * @param y          La coordonnées x=y de la nouvelle {@link Case}.
     */
    public Case(Labyrinthe labyrinthe, int x, int y) {
        this.labyrinthe = labyrinthe;
        this.x = x;
        this.y = y;
    }

    /**
     * Retourne la {@link Case} voisine dans la {@code direction} donnée.
     *
     * @param direction La direction de la {@link Case} voisine
     * @return La {@link Case} voisine.
     * @throws NullPointerException Si la {@link Case} n'a pas de voisin dans la {@code direction} donnée.
     */
    public Case getVoisin(Direction direction) {
        siVoisinExiste(direction);

        switch (direction) {
            case HAUT:
                return labyrinthe.getCase(x, y - 1);
            case DROITE:
                return labyrinthe.getCase(x + 1, y);
            case BAS:
                return labyrinthe.getCase(x, y + 1);
            case GAUCHE:
                return labyrinthe.getCase(x - 1, y);
            default:
                throw new IllegalArgumentException("Direction inconnue.");
        }
    }

    /**
     * Retourne la valeur de la liaison entre cette instance et
     * la {@link Case} dan la {@code direction}.
     *
     * @param direction La direction de la liaison.
     * @return La valeur de la liaison.
     * @throws NullPointerException     Si la {@link Case} n'a pas de voisin dans la {@code direction} donnée.
     * @throws IllegalArgumentException Si la {@code direction} donnée est inconnue.
     */
    public int getValeurLiaison(Direction direction) {
        siVoisinExiste(direction);
        Map map = labyrinthe.getMap();

        switch (direction) {
            case HAUT:
                return map.getValeurLiaison(x, y - 1, false);
            case DROITE:
                return map.getValeurLiaison(x, y, true);
            case BAS:
                return map.getValeurLiaison(x, y, false);
            case GAUCHE:
                return map.getValeurLiaison(x - 1, y, true);
            default:
                throw new IllegalArgumentException("Direction inconnue.");
        }
    }

    /**
     * Lance une exception si la {@link Case} voisine n'exista pas
     * dans la {@code direction}
     *
     * @param direction Direction à verifier.
     * @throws NullPointerException Si la case n'a pas de voisin dans la {@code direction} donnée.
     */
    public void siVoisinExiste(Direction direction) {
        switch (direction) {
            case HAUT:
                if (y == 0)
                    throw new NullPointerException("Cette case n'a pas de voisin en HAUT");
                break;
            case DROITE:
                if (x == labyrinthe.getMap().getLongueur() - 1)
                    throw new NullPointerException("Cette case n'a pas de voisin a DROITE");
                break;
            case BAS:
                if (y == labyrinthe.getMap().getLargeur() - 1)
                    throw new NullPointerException("Cette case n'a pas de voisin en BAS");
                break;
            case GAUCHE:
                if (x == 0)
                    throw new NullPointerException("Cette case n'a pas de voisin a GAUCHE");
                break;
        }
    }

    /**
     * Direction de la {@link Case} vers...
     */
    public enum Direction {
        HAUT(0),
        DROITE(1),
        BAS(2),
        GAUCHE(3);

        int direction;

        Direction(int i) {
            direction = i;
        }
    }
}
