package main;
import model.*;
import vue.*;
import java.util.*;

public class main {
    static void main(String[] args){
            int choice;
            boolean bool=true;
            while(true) {
                Scanner scan = new Scanner(System.in);
                try {
                    System.out.println("1-Généré le labyrinthe");
                    System.out.println("2-Afficher le labyrinthe");
                    System.out.println("3-Quitter");
                    choice = scan.nextInt();
                    switch(choice)
                    {
                        case 1:
                        {

                            break;
                        }
                        case 2:
                        {
                            break;
                        }
                        case 3:
                        {
                            break;
                        }
                        default:
                        {
                            System.out.println("Votre choix n'est pas compris dans le menu!");
                            break;
                        }
                    }


                } catch (Exception e) {
                    System.out.println("Une erreur c'est produit.");
                }
                finally {

                }
            }

    }
}
