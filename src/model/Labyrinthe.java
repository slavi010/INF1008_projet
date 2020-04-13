package model;

import model.data.Map;
import model.observer.Observable;
import model.observer.Observer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;


/**
 * Le labyrinthe.
 *
 * @author Sviatoslav Besnard
 */
public class Labyrinthe implements Observable {
    /**
     *  Compteur d'operation
     */
    private static int nbOperationLabyrinthe = 0;

    /**
     * La {@link Map} de liaisons assossié au {@link Labyrinthe}.
     */
    private Map map;

    private Observer observer;

    private HashSet<Liaison> liaisonsSolution = null;

    private boolean debug = false;

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
        nbOperationLabyrinthe += map.getNbOperationMap();
    }

    /**
     * Retourne la {@link Case} du l'{@link Labyrinthe} de coordonées {@code x}
     * et {@code y}
     *
     * @param x La coordonées x de la case souhaitée.
     * @param y La coordonées y de la case souhaitée.
     * @return la {@link Case} correspondantes aux coordonées
     * @throws IllegalArgumentException Si au moins une des coordonées x ou y est invalide.
     */
    public Case getCase(int x, int y) {
        map.isValideXY(x, y);
        return new Case(this, x, y);
    }

    public static int getNbOperationLabyrinthe() {
        return nbOperationLabyrinthe;
    }

    public Map getMap() {
        return map;
    }

    @Override
    public void sendEvent(Liaison liaison, int code) {
        if (observer != null)
            switch (code){
                case 1:
                    observer.onEvent1(liaison);
                    break;
                case 2:
                    observer.onEvent2(liaison);
                    break;
                case 3:
                    observer.onEvent3(liaison);
                    break;
                case 4:
                    observer.onEvent4(liaison);
                    break;
            }
        try {
            if (this.debug)
                Thread.sleep(1000, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    /**
     * Applique l'algo de PRIM minimal.
     */
    public HashSet<Liaison> prim() {
        if (debug)
            System.out.println("Cela peux prendre du temps car le mode debug est activé !");

        nbOperationLabyrinthe = 0;

        // liaisons rejetées (en double)
        HashSet<Liaison> liaisonsRejetees = new HashSet<>();
        // liaisons possibles reliant une Case déjà vue (solution) et une non visité
        HashSet<Liaison> liaisonsPossible = new HashSet<>();
        // les liaisons du future labyrunthe
        liaisonsSolution = new HashSet<>();
        HashSet<Case> casesSolutions = new HashSet<>();

        Case caseToAdd = getCase(0, 0);
        Liaison liaisonTmp = null;
        int valeurTmp;

        while (casesSolutions.size() < getMap().getLargeur() * getMap().getLongueur()) {
            casesSolutions.add(caseToAdd);

            // on ajoute les voisins de la case ajouté dans les liaisons possible
            for (Case.Direction direction : Case.Direction.values()) {
                // try si la case n'a pas de voisin dans la direction donnée
                try {
                    liaisonTmp = caseToAdd.getLiaison(direction);

                    // si la liaison est déjà présente dans les liaisons possibles
                    if (liaisonsPossible.contains(liaisonTmp)) {
                        // on supprime la liaison des liaisons possibles
                        // et on l'ajoute dans les liaisons rejété
                        // (car elle connecte deux Case déjà solution)
                        liaisonsPossible.remove(liaisonTmp);
                        liaisonsRejetees.add(liaisonTmp);
                        if (this.debug)
                            sendEvent(liaisonTmp, 4);
                    }
                    // si la liaison n'a pas encore été rejeté
                    else if (!liaisonsRejetees.contains(liaisonTmp) && !liaisonsSolution.contains(liaisonTmp)) {
                        // on l'ajoute dans les liaisons possibles
                        liaisonsPossible.add(liaisonTmp);
                        // on notifie le UI nouvelle liaison possible
                        if (this.debug)
                            sendEvent(liaisonTmp, 2);
                    }

                } catch (Exception e) {
                    // pass
                }
                nbOperationLabyrinthe += 1;
            }

            // s'il y a encore des liaison possible
            if (!liaisonsPossible.isEmpty()) {
                // recherche la prochaine liaison potentielle
                liaisonTmp = Collections.min(liaisonsPossible, Liaison::compareTo);

                // on prend la case pas encore solution
                if (!casesSolutions.contains(liaisonTmp.getCase1()))
                    caseToAdd = liaisonTmp.getCase1();
                else
                    caseToAdd = liaisonTmp.getCase2();

                // on transfère la liaison de possible à solution
                liaisonsPossible.remove(liaisonTmp);
                liaisonsSolution.add(liaisonTmp);

                // on notifie le UI nouvelle liaison solution
                sendEvent(liaisonTmp, 1);
            }

        }

        return liaisonsSolution;
    }

    /**
     * Sauvegarde sous un format visuel le labyrunthe avec comme mur, les liaisons données.
     */
    public void saveToFile() {
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("solution.txt");
            myWriter.write(this.toString());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        if (liaisonsSolution == null)
            throw new NullPointerException("Il n'y a pas de solution trouvée, avez-vous lancé l'algo de PRIM ?");


        // le labyrinthe en chaîne de caractères
        StringBuilder str = new StringBuilder();

        // grille des liaisons
        Liaison[][] tab = new Liaison[getMap().getLargeur()*2 - 1][];
        for (int y = 0; y < getMap().getLargeur()*2 - 1; y++) {
            tab[y] = new Liaison[getMap().getLongueur()*2 - 1];
        }

        // on parcour les liaison puis les ajoutons dans le tableau
        Iterator<Liaison> liaisonIterator = liaisonsSolution.iterator();
        Liaison liaisonTmp;
        while (liaisonIterator.hasNext()) {
            liaisonTmp = liaisonIterator.next();
            int y_tmp = liaisonTmp.getCoordY();
            int x_tmp = liaisonTmp.getCoordX();
            tab[y_tmp][x_tmp] = liaisonTmp;
        }

        for (int y = 0; y < getMap().getLongueur() * 2 - 1; y++) {
            for (int x = 0; x < getMap().getLargeur() * 2 - 1; x++) {


                if (x % 2 == 0 && y % 2 == 0) {
                    // une case
                    str.append(caseCaractereEnFonctionDesLiaisons(
                            exsite(x, y - 1, tab),
                            exsite(x + 1, y, tab),
                            exsite(x, y + 1, tab),
                            exsite(x - 1, y, tab)
                    ));

                } else if (x % 2 == 1 && y % 2 == 0) {
                    // liaison horizontale
                    if (exsite(x, y, tab))
                        str.append((char) 0x2550);
                    else
                        str.append(" ");
                } else if (x % 2 == 0) {
                    // liaison verticale
                    if (exsite(x, y, tab))
                        str.append((char) 0x2551);
                    else
                        str.append(" ");
                }else {
                    str.append(" ");
                }
                str.append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }

    /**
     * Retourne faux si la de coordonnées {@code x} et {@code y} est null ou est hors dans un {@code tableau[y][x]}.
     * Retourne vrai sinon.
     *
     * @return Vrai si non null.
     */
    private boolean exsite(int x, int y, Object[][] tableau) {
        try {
            return tableau[y][x] != null;
        } catch (Exception ignored) {
        }
        return false;
    }

    private String caseCaractereEnFonctionDesLiaisons(
            boolean haut,
            boolean droite,
            boolean bas,
            boolean gauche
    ){
        if (haut){
            if (droite){
                if (bas){
                    if (gauche)
                        return String.valueOf ((char) 0x256C); // 1111
                    else
                        return String.valueOf ((char) 0x2560); // 1110
                } else {
                    if (gauche)
                        return String.valueOf ((char) 0x2567); // 1101
                    else
                        return String.valueOf ((char) 0x255A); // 1100
                }
            } else {
                if (gauche){
                    if (bas)
                        return String.valueOf ((char) 0x2563); // 1011
                    else
                        return String.valueOf ((char) 0x255D); // 1001
                } else {
                    return String.valueOf ((char) 0x2551); // 1000 || 1010
                }
            }
        } else {
            if (droite){
                if (bas){
                    if (gauche)
                        return String.valueOf ((char) 0x2566); // 0111
                    else
                        return String.valueOf ((char) 0x2554); // 0110
                } else {
                        return String.valueOf ((char) 0x2550); // 0101 || 0100
                }
            } else {
                if (bas){
                    if (gauche)
                        return String.valueOf ((char) 0x2557); // 0011
                    else
                        return String.valueOf ((char) 0x2551); // 0010
                } else {
                    if (gauche)
                        return String.valueOf ((char) 0x2550); // 0001
                    else
                        return "?"; // 0000
                }
            }
        }
    }

    public boolean isDebug() {
        return debug;
    }

    public Labyrinthe setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public HashSet<Liaison> getLiaisonsSolution() {
        return liaisonsSolution;
    }
}
