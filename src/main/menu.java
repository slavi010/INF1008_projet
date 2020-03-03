package main;
import java.util.*;
public class menu {
    private int number;
    Scanner scan;
    public menu()
    {
        Scanner scan =new Scanner(System.in);
    }
    private void setNum() {
        System.out.println("Veuilliez entrer un chiffre positif ");
        number = scan.nextInt();
    }
    public int getnumber()
    {
        boolean bool=true;
        while(bool)
        {
            setNum();
            if(number>0)
            {
                bool=!bool;
            }
        }
    return number;
    }
}
