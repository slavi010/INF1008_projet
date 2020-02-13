package model;

public class Case {
    private int x, y;
    private Labyrinthe labyrinthe;
    private Liaison ld = null; // liaison droite
    private Liaison lb = null; // liaison bas

    // direction
    public final static int HAUT = 0;
    public final static int DROIT = 1;
    public final static int BAS = 2;
    public final static int GAUCHE = 3;

    public Case(int x, int y, Labyrinthe labyrinthe) {
        this.x = x;
        this.y = y;
        this.labyrinthe = labyrinthe;

        // pas case la plus à droite
        if (x + 1 < labyrinthe.getLongueur())
            ld = new Liaison(randPoid());

        // pas case la plus en bas
        if (y + 1 < labyrinthe.getLargeur())
            lb = new Liaison(randPoid());
    }

    // retourne case adjacente
    // exemple : case.getCase(Case.HAUT);
    public Case getCase(int direction) {
        switch (direction){
            case Case.HAUT:
                return labyrinthe.get(x, y - 1);
            case Case.DROIT:
                return labyrinthe.get(x + 1, y);
            case Case.BAS:
                return labyrinthe.get(x, y + 1);
            case Case.GAUCHE:
                return labyrinthe.get(x-1, y);
            default:
                return null;
        }
    }

    // retourne la liaison
    public Liaison getLiaison(int direction) {
        switch (direction){
            case Case.HAUT:
                return labyrinthe.get(x, y - 1).lb;
            case Case.DROIT:
                return ld;
            case Case.BAS:
                return lb;
            case Case.GAUCHE:
                return labyrinthe.get(x-1, y).ld;
            default:
                return null;
        }
    }

    private int randPoid() {
        return (int) (Math.random() * labyrinthe.getPoidMax());
    }
}
