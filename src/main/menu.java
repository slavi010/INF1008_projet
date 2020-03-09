package main;

import java.util.Scanner;

public class menu {
    private int number;
    Scanner scan;

    public menu()
    {
        Scanner scan = new Scanner(System.in);
    }

    private void setNum() {
        System.out.println("Veuilliez entrer un chiffre entier et  positif : ");
        number = scan.nextInt();
    }

    public int getnumber()
    {
        do
        {
            setNum();
        }while(number<=0);
    return number;
    }
}
