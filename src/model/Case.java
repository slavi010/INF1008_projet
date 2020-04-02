package model;

import model.data.Map;

/**
 * Une case du {@link Labyrinthe}.
 */
public class Case implements Comparable<Case> {
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
     * @param direction La direction de la {@link Case} voisine.
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
     * Retourne la liaison entre cette instance et la {@link Case} voisine
     * dans la {@code direction} donnée.
     *
     * @param direction La direction de la liaison
     * @return La {@link Liaison} entre les deux {@link Case}.
     * @throws NullPointerException     Si la {@link Case} n'a pas de voisin dans la {@code direction} donnée.
     * @throws IllegalArgumentException Si la {@code direction} donnée est inconnue.
     */
    public Liaison getLiaison(Direction direction) {
        siVoisinExiste(direction);
        Map map = labyrinthe.getMap();

        return new Liaison(this, getVoisin(direction), getValeurLiaison(direction));
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
     * @param obj La {@link Case} à tester.
     * @return Vrai si {@code obj} est une {@link Case}, qu'il a le même {@link Labyrinthe}
     * et a les même coordonnées {@code x} et {@code y} que cette instance.
     */
    @Override
    public boolean equals(Object obj) {
        // si obj n'est pas une Case
        if (!(obj instanceof Case))
            return false;

        Case c = (Case) obj;

        // on fait le test de comparaison.
        return this.labyrinthe == c.labyrinthe &&
                this.x == c.x &&
                this.y == c.y;
    }

    /**
     * Retourne la position x de la case.
     *
     * @return La position x de la case.
     */
    public int getX() {
        return x;
    }

    /**
     * Retourne la position y de la case.
     *
     * @return La position y de la case.
     */
    public int getY() {
        return y;
    }

    /**
     * Retourne 0 si cette instance et {@code c} ont les même coordonnées
     * et les deux {@link Case} font parti du même labyrinthe.
     * Retourne -1 si la coordonnée {@code y} de cette instance est inférieur au {@code y} de {@code c}.
     * Si les {@code y} sont égaux, fait de même avec les coordonnées {@code x}.
     * Retourn 1 sinon.
     *
     * @param c La {@link Case} avec la quelle comparer.
     * @return -1|0|1
     */
    @Override
    public int compareTo(Case c) {
        // même coordonnées
        if (this.equals(c))
            return 0;
        else if (this.getY() < c.getY() ||
                (this.getY() == c.getY()
                        && this.getX() < c.getX()))
            return -1;
        return 1;
    }

    @Override
    public String toString() {
        return "{\"x\":" + x + ", " +
                "\"y\":" + y + "}";
    }

    @Override
    public int hashCode() {
        int hash = labyrinthe.hashCode()*31 + x;
        hash = hash*31 + y;
        return hash;
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
