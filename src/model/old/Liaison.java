package model.old;

public class Liaison {
    private int poid;

    /*
    liaison haut-bas
    # <- case haut
    | <- liaison
    # <- case bas

    liaison gauche-droite
    # - #

     */
    private Case caseParent_1; // haut ou gauche
    private Case caseParent_2; // bas ou droite
    private boolean chemin = false; // liaison retenue après algo PRIM


    public Liaison(int poid, Case caseParent) {
        this.poid = poid;
//        this.caseParent = caseParent;
    }

    public int getPoid() {
        return poid;
    }

//    public Case getCaseParent() {
//        return caseParent;
//    }
//
//    @Override
//    public int hashCode() {
//        return caseParent.hashCode();
//    }

    @Override
    public String toString() {
        return "(poid:" + poid + ")";
    }
}
