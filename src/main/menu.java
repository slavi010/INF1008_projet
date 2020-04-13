package main;

import java.util.Scanner;

/**
 * @author Nicolas Landry
 * @author Marius Couet
 */
public class menu {
    private int number;
    Scanner scan;

    public menu()
    {
        scan = new Scanner(System.in);
    }

    private void setNum(String localisation) {
        System.out.println("Veuilliez entrer "+localisation+" (>0) :");
        number = scan.nextInt();
    }

    public int getnumber(String localisation)
    {
        do
        {
            setNum(localisation);
        }while(number<=0);
    return number;
    }
}
