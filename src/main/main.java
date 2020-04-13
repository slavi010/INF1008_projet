package main;

import model.Labyrinthe;
import model.data.Map;
import vue.Interface;

import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @author Nicolas Landry
 * @author Sviatoslav Besnard
 */
public class main {
    public static void main(String[] args) {
        menu test = new menu();
        int[] storage = new int[3];
        String localisation = null;
        int choice;
        boolean bool = true;
        Interface i = null;
        Labyrinthe minotaur = null;

        while (bool) {
            Scanner scan = new Scanner(System.in);
            try {
                System.out.println( "\n\n" +
                        "1 - Générer le labyrinthe (pseudo matrice d'adjacence)\n" +
                        "2 - Afficher le labyrinthe dans une fenêtre (ne pas faire si taille > 100x100)\n" +
                        "3 - Lancer algo de PRIM\n" +
                        "4 - Afficher la solution de PRIM dans la console\n" +
                        "5 - Enregistrer la solution de PRIM dans le fichier \"solution.txt\"\n" +
                        "6 - Quitter\n");
                choice = scan.nextInt();
                switch (choice) {
                    case 1: {
                        localisation = "la longueur";
                        storage[0] = test.getnumber(localisation);
                        localisation = "la largeur";
                        storage[1] = test.getnumber(localisation);
                        localisation = "le poid maximal du labyrithe";
                        storage[2] = test.getnumber(localisation);
                        minotaur = new Labyrinthe(storage[0], storage[1], 1, storage[2]);
                        System.out.println("Labyrinthe a été créé avec succès.");

                        System.out.println("Nombre d'opération : " + Map.getNbOperationMap());


                        break;
                    }
                    case 2: {
                        //Test pour l'affichage. Evité de faire 100x100 sur un petit processeur.
                        if (minotaur != null) {
                            i = new Interface("Labyrinthe", storage[0], storage[1], minotaur);
                            System.out.println("Interface chargé.\n");

                            System.out.println("1 - Mode normal");
                            System.out.println("2 - Mode debug (affiche lentement + couleurs)");
                            int debug = scan.nextInt();

                            // Le labyrinthe existe bien

                            minotaur.setDebug(debug == 2);

                            // On supprime la fenêtre déjà existante
                            if (i != null)
                                i.dispatchEvent(new WindowEvent(i, WindowEvent.WINDOW_CLOSED));
                        } else {
                            // Le labyrinthe n'a pas encore été généré !
                            throw new NullPointerException();
                        }
                        break;
                    }
                    case 3: {
                        if (minotaur != null){
                            System.out.println("Algo de PRIM...");

                            minotaur.prim();
                            System.out.println("Nombre d'opération : " + Labyrinthe.getNbOperationLabyrinthe());

                            System.out.println("Algo de PRIM fini");
                        }
                        else {
                            // Le labyrinthe n'a pas encore été généré !
                            throw new NullPointerException();
                        }
                        break;
                    }case 4: {
                        if (minotaur != null && minotaur.getLiaisonsSolution() != null)
                            System.out.println(minotaur.toString());
                        else {
                            // Le labyrinthe n'a pas encore été généré ou il n'y a pas de solutiob !
                            throw new NullPointerException();
                        }
                        break;
                    }case 5: {
                        if (minotaur != null && minotaur.getLiaisonsSolution() != null)
                            minotaur.saveToFile();
                        else {
                            // Le labyrinthe n'a pas encore été généré ou il n'y a pas de solutiob !
                            throw new NullPointerException();
                        }
                        break;
                    }
                    case 6: {
                        bool = false;
                        scan.close();
                        break;
                    }
                    default: {
                        System.err.println("Votre choix n'est pas compris dans le menu!");
                        break;
                    }
                }


            } catch (NullPointerException e) {
                System.err.println("Veillez générer le labyrinthe / lancer l'algo de PRIM en premier !");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("L'application s'est terminée avec succès");
    }
}
