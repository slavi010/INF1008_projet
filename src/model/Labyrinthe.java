package model;

public class Labyrinthe {
    private int largeur;
    private int longueur;
    private int poidMax;
    private Case[][] map;

    public Labyrinthe(int largeur, int longueur, int poidMax) {
        this.largeur = largeur;
        this.longueur = longueur;
        this.poidMax = poidMax;

        map = new Case[longueur][largeur];
        for (int y = 0; y < largeur; y++) {
            for (int x = 0; x < longueur; x++) {
                map[x][y] = new Case(x, y, this);
            }
        }
    }

    public Case get(int x, int y){
        if (x >= 0 && x < longueur &&
                y >= 0 && y < largeur){
            return map[x][y];
        }
        return null;
    }


    public int getLargeur() {
        return largeur;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getPoidMax() {
        return poidMax;
    }
}
