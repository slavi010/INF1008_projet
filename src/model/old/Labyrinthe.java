package model.old;

import java.util.HashSet;
import java.util.Iterator;

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


    // retourne la case en fonctoin (x,y)
    // retourne null si pas de case
    public Case get(int x, int y){
        if (x >= 0 && x < longueur &&
                y >= 0 && y < largeur){
            return map[x][y];
        }
        return null;
    }

    public void prim(){
        HashSet<Case> cases = new HashSet<>(); // case déjà examiné
        HashSet<Liaison> liaisons = new HashSet<>(); // liaison à examiner
        int totalCases = longueur * largeur;

        Liaison liaisonMin = null;
        Liaison liaisonTemp;
        Case caseTemp;
        Iterator<Liaison> itr;

        cases.add(get(0, 0));
        liaisons.add(get(0, 0).getLiaison(Case.DROITE));
        liaisons.add(get(0, 0).getLiaison(Case.BAS));


        // nouvelles case
        // Toutes les liaison son possible et associes une case déjà examinée et non
        // recherche de la liaison avec le plus petit poid
        itr = liaisons.iterator();
        while (itr.hasNext()){
            liaisonTemp = itr.next();
            if (liaisonMin == null ||
                (liaisonTemp != null &&
                        liaisonMin.getPoid() > liaisonTemp.getPoid())){
                liaisonMin = liaisonTemp;
            }
        }
        // add case
//        caseTemp = liaisonMin.getCaseParent();
//        cases.add(caseTemp);
//        // add liaison de la case si l'autre case reliée n'es pas dans "cases"
//        // et supprime les liaisons déjà existantes inutiles
//        for (int direction = 0; direction < 4; direction++) {
//            liaisonTemp = caseTemp.getLiaison(direction);
//            if (!cases.contains(caseTemp.getCase(direction)))
//                liaisons.add(liaisonTemp);
//            else
//                liaisons.remove(liaisonTemp);
//        }


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
