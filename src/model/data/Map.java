package model.data;

/**
 * Stockage des données de la carte.
 */
public class Map {
    /**
     * Compteur d'operation
     */
    private static int nbOperationMap = 0;

    /**
     * Données brutes de liaison.
     * - Primière dimenssion :
     * Chaque noeud de gauche à droite puis de haut en bas
     * - Seconde dimmension :
     * [0] -> valeur de la liaison vers le voisin de gauche
     * [1] -> valeur de la liaison vers le voisin du bas
     * Valeur indéterminée si aucun
     */
    private int[][] map;

    /**
     * La longueur (horizontale) de la map.
     * Doit être supérieure à 0.
     */
    private int longueur;

    /**
     * La largeur (verticale) de la map.
     * Doit être supérieure à 0.
     */
    private int largeur;

    /**
     * Crée une instance de la classe {@link Map} avec pour
     * dimension x (horizontale) {@code longueur} et
     * y (verticale) {@code lageur}.
     *
     * @param longueur Longeur de la map.
     * @param largeur   Largeur de la map.
     * @throws IllegalArgumentException Si la longueur ou la largeur est inférieure ou égale à zéro.
     */
    public Map(int longueur, int largeur) {
        this.longueur = longueur;
        this.largeur = largeur;
        if (longueur <= 0 || largeur <= 0)
            throw new IllegalArgumentException("La longueur et la largeur de la carte doient être supérieur à zéro.");

        map = new int[longueur * largeur][2];
    }

    /**
     * Cette fonction permet de mettre une {@code valeur} à la liaison
     * {@code versLiaisonDroite} en fonction des coordonées d'une case {@code x}
     * et {@code y}
     *
     * @param x                 Coordonnées x (horizontale) de la valeur à changer.
     * @param y                 Coordonnées y (verticale) de la valeur à changer.
     * @param versLiaisonDroite Vrai si c'est vers la liaison de droite,
     *                          faux si c'est vers celle du bas.
     * @param valeur            Nouvelle valeur de la liaison.
     * @return Retourne cette instance de classe si besoin.
     * @throws IllegalArgumentException Si au moins une des coordonées x ou y est invalide.
     * @throws IllegalArgumentException Si la case n'a pas de voisin de droite ou
     *                                  du bas en fonction de {@code versLiaisonDroite}.
     */
    public Map setValeurLisaison(int x, int y, boolean versLiaisonDroite, int valeur) {
        isValideXY(x, y);

        if (versLiaisonDroite && !aUnVoisinDeDroite(x, y))
            throw new IllegalArgumentException("Cette case n'a pas de voisin à droite.");
        if (!versLiaisonDroite && !aUnVoisinDuBas(x, y))
            throw new IllegalArgumentException("Cette case n'a pas de voisin en bas.");

        // on met la valeur
        map[x + y * longueur][versLiaisonDroite ? 0 : 1] = valeur;

        return this;
    }

    /**
     * Cette fonction permet de d'obtenir la valeur de la liaison
     * {@code versLiaisonDroite} en fonction des coordonées d'une
     * case de coordonées {@code x} et {@code y}
     *
     * @param x                 Coordonnées x (horizontale) de la valeur à prendre.
     * @param y                 Coordonnées y (verticale) de la valeur à prendre.
     * @param versLiaisonDroite Vrai si c'est vers la liaison de droite,
     *                          faux si c'est vers celle du bas.
     * @return La valeur de la liaison.
     * @throws IllegalArgumentException Si au moins une des coordonées x ou y est invalide.
     * @throws IllegalArgumentException Si la case n'a pas de voisin de droite ou
     *                                  du bas en fonction de {@code versLiaisonDroite}.
     */
    public int getValeurLiaison(int x, int y, boolean versLiaisonDroite) {
        isValideXY(x, y);

        if (versLiaisonDroite && !aUnVoisinDeDroite(x, y))
            throw new IllegalArgumentException("Cette case n'a pas de voisin à droite.");
        if (!versLiaisonDroite && !aUnVoisinDuBas(x, y))
            throw new IllegalArgumentException("Cette case n'a pas de voisin en bas.");

        return map[x + y*longueur][versLiaisonDroite ? 0 : 1];
    }

    /**
     * Vérifie si la case de coordonées {@code x} et {@code y} est dans la carte.
     *
     * @param x Coordonnées x (horizontale) de la case à vérifier.
     * @param y Coordonnées y (verticale) de la case à vérifier.
     * @throws IllegalArgumentException Si au moins une des coordonées x ou y est invalide.
     */
    public void isValideXY(int x, int y) throws IllegalArgumentException {
        if (x < 0)
            throw new IllegalArgumentException(String.format("La valeur x ne doit pas être inférieure à zéro ! x = %d", x));
        else if (y < 0)
            throw new IllegalArgumentException(String.format("La valeur y ne doit pas être inférieure à zéro ! y = %d", y));
        else if (x >= longueur)
            throw new IllegalArgumentException(String.format("La valeur x ne doit pas supérieur ou égale a la longueur ! x = %d", x));
        else if (y >= largeur)
            throw new IllegalArgumentException(String.format("La valeur y ne doit pas supérieur ou égale a la largeur ! y  = %d", y));
    }

    /**
     * Retourne vrai si la case de coordonées {@code x} et {@code y}
     * à un voisin de droite.
     *
     * @param x Coordonnées x (horizontale) de la case à vérifier.
     * @param y Coordonnées y (verticale) de la case à vérifier.
     * @return Vrai si la case à un voisin de droite.
     * @throws IllegalArgumentException Si au moins une des coordonées x ou y est invalide.
     */
    private boolean aUnVoisinDeDroite(int x, int y) {
        isValideXY(x, y);
        return x < longueur - 1;
    }

    /**
     * Retourne vrai si la case de coordonées {@code x} et {@code y}
     * à un voisin du bas.
     *
     * @param x Coordonnées x (horizontale) de la case à vérifier.
     * @param y Coordonnées y (verticale) de la case à vérifier.
     * @return Vrai si la case à un voisin du bas.
     * @throws IllegalArgumentException Si au moins une des coordonées x ou y est invalide.
     */
    private boolean aUnVoisinDuBas(int x, int y) {
        isValideXY(x, y);
        return y < largeur - 1;
    }

    /**
     * Met des valeurs aléatoires aux liaisons bornées entre {@code min} (inclue)
     * et {@code max} (exclue).
     * @param min Minimum des valeurs aléatoires (inclue).
     * @param max Maximum des valeurs aléatoires (exclue).
     * @return Retourne cette instance de classe si besoin.
     */
    public Map metDesValeursAleatoires(int min, int max){
        // x et y sont des coordonées en niveau du tableau map
        int x, y;
        for (x = 0; x < longueur*largeur; x++) {
            for (y = 0; y < 2; y++) {
                map[x][y] = (int) Math.floor((Math.random() * ((max - min) + 1)) + min);
                nbOperationMap += 1;
            }
        }
        return this;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getLargeur() {
        return largeur;
    }

    public static int getNbOperationMap() {
        return nbOperationMap;
    }
}
