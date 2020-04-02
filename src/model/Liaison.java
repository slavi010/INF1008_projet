package model;


/**
 * Cette classe est la représentation d'une {@link Liaison} entre deux {@link Case}.
 */
public class Liaison implements Comparable<Liaison> {
    /**
     * Les cases que relie la {@link Liaison}.
     * {@code case1} aura toujours un {@code y} plus petit ou égale à celui de {@code case2}.
     * Si il c'est égale, {@code case1} aura toujours un {@code x} plus petit que celui de {@code case2}
     */
    private Case case1, case2;

    /**
     * La valeur de la liaison
     */
    private int valeur;

    /**
     * Retourne un objet de type {@link Liaison} reliant deux {@link Case} données en paramètre.
     * L'ordre des paramètres n'a pas d'importance.
     *
     * @param case1  La première {@link Case}.
     * @param case2  La seconde {@link Case}.
     * @param valeur La valeur/poid de la liaison.
     * @throws IllegalArgumentException Si les cases sont identique ou ne font pas parti du même labyrinthe.
     */
    public Liaison(Case case1, Case case2, int valeur) {
        this.valeur = valeur;

        // si case1 et case2 ne sont pas identiques
        if (case1.equals(case2))
            throw new IllegalArgumentException("Les cases données sont identiques ou ne font pas parti du même labyrinthe !");

        if (case1.compareTo(case2) < 0) {
            this.case1 = case1;
            this.case2 = case2;
        } else {
            this.case1 = case2;
            this.case2 = case1;
        }
    }

    /**
     * @param obj La {@link Liaison} à tester.
     * @return Vrai si {@code obj} est une {@link Liaison}, qu'il a les même
     * {@code case1}, {@code case2} et valeur que cette instance.
     */
    @Override
    public boolean equals(Object obj) {
        // Si obj n'est pas une Liaison
        if (!(obj instanceof Liaison))
            return false;

        Liaison l = (Liaison) obj;

        return this.case1.equals(l.case1) &&
                this.case2.equals(l.case2) &&
                this.valeur == l.valeur;
    }

    @Override
    public int hashCode() {
        int hash = case1.getX() * 31 + case2.getY();
        hash = hash * 31 + case2.getX();
        hash = hash * 31 + case2.getY();
        hash = hash * 31 + valeur;

        return hash;
    }

    /**
     * Retourne la {@code valeur} de cette instance.
     *
     * @return La {@code valeur} de cette instance.
     */
    public int getValeur() {
        return valeur;
    }

    /**
     * Retourne la {@code case1} de cette instance.
     *
     * @return La {@code case1} de cette instance.
     */
    public Case getCase1() {
        return case1;
    }

    /**
     * Retourne la {@code case2} de cette instance.
     *
     * @return La {@code case2} de cette instance.
     */
    public Case getCase2() {
        return case2;
    }

    /**
     * Compare cette instance et une autre {@link Liaison}.
     * Retourne -1 ou 1 en fonction du signe de la soustraction entre la valeur de cette instance et
     * de la {@link Liaison} à comparer.
     * Si les deux valeurs sont égales alors la comparaison se fait entre les deux {@code case2}.
     * Si les {@code case2} sont égales alors fait de même avec {@code case1}.
     * Sinon retourne 0;
     *
     * @param l La liaison avec qui comparer.
     * @return -1|0|1
     */
    @Override
    public int compareTo(Liaison l) {
        int tmp;

        if (this.valeur - l.valeur != 0) {
            // les deux valeurs ne sont pas égales

            return (this.valeur < l.valeur) ? -1 : 1;
        } else {
            // les deux valeurs sont égales

            tmp = this.case2.compareTo(l.case2);
            if (tmp != 0)
                return tmp;
            else
                return this.case1.compareTo(l.case1);
        }
    }

    @Override
    public String toString() {
        return "{\"case1\":" + case1 + ", " +
                "\"case2\":" + case2 + ", " +
                "\"valeur\":" + valeur + "}";
    }
}
