package main;
import java.util.*;
public class menu {
    private int number;
    Scanner scan;
    public menu()
    {
        scan =new Scanner(System.in);
    }
    private void setNum(String localisation) {
        String location=localisation;
        System.out.println("Veilliez entrer un chiffre entier et positif pour la "+location);
        number = scan.nextInt();
    }
    public int getnumber(String localisation)
    {
        boolean bool=true;
        while(bool)
        {
            setNum(localisation);
            if(number>0&&number<100)
            {
                bool=false;
            }
        }
    return number;
    }
}
