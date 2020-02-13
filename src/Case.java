public class Case {
    private int x, y;
    private Labyrinthe labyrinthe;
    private Liaison ld; // liaison droite
    private Liaison lb; // liaison bas

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

    private int randPoid(){
        return (int)(Math.random() * labyrinthe.getPoidMax());
    }
}
