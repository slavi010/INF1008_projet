package main;

import model.Labyrinthe;
import vue.*;

import java.sql.Time;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        menu test = new menu();
        int[] storage = new int[3];
        String localisation;
        int choice;
        boolean bool = true;
        Interface i;
        Labyrinthe minotaur = null;
        while (bool) {
            Scanner scan = new Scanner(System.in);
            try {
                System.out.println("1-Généré le labyrinthe");
                System.out.println("2-Afficher le labyrinthe dans une fenêtre (ne pas faire si taille > 100x100)");
                System.out.println("3-Quitter");
                choice = scan.nextInt();
                switch (choice) {
                    case 1: {
                        localisation = "la longeur";
                        storage[0] = test.getnumber(localisation);
                        localisation = "la largeur";
                        storage[1] = test.getnumber(localisation);
                        localisation = "le poid maximal du labyrithe";
                        storage[2] =test.getnumber(localisation);
                        minotaur= new Labyrinthe(storage[1], storage[0], 1, storage[2]);
                        System.out.println("Labyrinthe a été créé avec succès.");
                        break;
                    }
                    case 2: {
                     //Test pour l'affichage. Evité de faire 100x100 sur un petit processeur.
                        i = new Interface("Labyrinthe", storage[0], storage[1], minotaur);
                        System.out.println("Interface chargé");
                        Thread.sleep(1);
                        System.out.println("Algo de PRIM...");
                        minotaur.prim();
                        System.out.println("Algo de PRIM fini");
                        break;
                    }
                    case 3: {
                        bool = false;
                        scan.close();
                        break;
                    }
                    default: {
                        System.out.println("Votre choix n'est pas compris dans le menu!");
                        break;
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("L'application s'est terminée avec succès");
    }
}
