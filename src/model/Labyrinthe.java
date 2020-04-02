package model;

import model.data.Map;
import model.observer.Observable;
import model.observer.Observer;

import java.util.Collections;
import java.util.HashSet;

/**
 * Le labyrinthe.
 */
public class Labyrinthe implements Observable {
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

    public Map getMap() {
        return map;
    }

    @Override
    public void sendEvent(Liaison liaison) {
        assert observer != null;
        try {
            Thread.sleep(0, 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        observer.onEvent(liaison);
    }

    @Override
    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    /**
     * Applique l'algo de PRIM minimal.
     */
    public HashSet<Liaison> prim() {
        // liaisons rejetées (en double)
        HashSet<Liaison> liaisonsRejetees = new HashSet<>();
        // liaisons possibles reliant une Case déjà vue (solution) et une non visité
        HashSet<Liaison> liaisonsPossible = new HashSet<>();
        // les liaisons du future labyrunthe
        HashSet<Liaison> liaisonsSolution = new HashSet<>();
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
                    }

                    // si la liaison n'a pas encore été rejeté
                    else if (!liaisonsRejetees.contains(liaisonTmp) && !liaisonsSolution.contains(liaisonTmp))
                    {
                        // on l'ajoute dans les liaisons possibles
                        liaisonsPossible.add(liaisonTmp);
                    }

                } catch (Exception e) {
                    // pass
                }
            }

            // s'il y a encore des liaison possible
            if (!liaisonsPossible.isEmpty()){
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

                // on notifie le UI
                sendEvent(liaisonTmp);
            }

        }

//        for (Liaison liaison : liaisonsRejetees)
//        System.out.println(liaisonsSolution);

        return liaisonsSolution;
    }

}
