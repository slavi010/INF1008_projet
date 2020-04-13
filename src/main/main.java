package main;

import model.Labyrinthe;
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
                        "1-Généré le labyrinthe (pseudo matrice d'adjacence)\n" +
                        "2-Afficher le labyrinthe dans une fenêtre puis lance Prim (ne pas faire si taille > 100x100)\n" +
                        "3-Générer un fichier visuel du labyrinthe après algo de Prim\n" +
                        "4-Quitter\n");
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

                            // On supprime la fenêtre déjà existante
                            if (i != null)
                                i.dispatchEvent(new WindowEvent(i, WindowEvent.WINDOW_CLOSED));


                            System.out.println("Algo de PRIM...");

                            if (debug == 2){
                                // mode debug
                                minotaur.prim(true);
                            }else{
                                // mode normal
                                minotaur.prim(false);
                            }
                            System.out.println("Algo de PRIM fini");



                        } else {
                            // Le labyrinthe n'a pas encore été généré !
                            throw new NullPointerException();
                        }
                        break;
                    }
                    case 3: {
                        if (minotaur != null)
                            minotaur.saveToFile(minotaur.prim(false));
                        else {
                            // Le labyrinthe n'a pas encore été généré !
                            throw new NullPointerException();
                        }
                        break;
                    }
                    case 4: {
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
                System.err.println("Veillez générer le labyrinthe en premier !");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("L'application s'est terminée avec succès");
    }
}
