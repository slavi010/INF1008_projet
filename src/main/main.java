package main;

//import model.*;

import model.Labyrinthe;
import vue.*;

import java.util.*;

public class main {
    public static void main(String[] args) {
        menu test = new menu();
        int[] storage = new int[2];
        String localisation = "";
        int choice;
        boolean bool = true;
        Interface i;
        Labyrinthe Minotaur;
        while (bool) {
            Scanner scan = new Scanner(System.in);
            try {
                System.out.println("1-Généré le labyrinthe");
                System.out.println("2-Afficher le labyrinthe");
                System.out.println("3-Quitter");
                choice = scan.nextInt();
                switch (choice) {
                    case 1: {
                        localisation = "longeur";
                        storage[0] = test.getnumber(localisation);
                        localisation = "largeur";
                        storage[1] = test.getnumber(localisation);
                        Minotaur= new Labyrinthe(storage[1],storage[0],10);
                       // i = new Interface("Labyrinthe", storage[0], storage[1]);
                        break;
                    }
                    case 2: {
                        break;
                    }
                    case 3: {
                        bool = !bool;
                        break;
                    }
                    default: {
                        System.out.println("Votre choix n'est pas compris dans le menu!");
                        break;
                    }
                }


            } catch (Exception e) {
                System.out.println("Une erreur c'est produit.");
            } finally {
                    System.out.println("Allo!");
            }
        }

    }
}