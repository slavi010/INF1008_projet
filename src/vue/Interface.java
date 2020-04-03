package vue;

import model.Case;
import model.Labyrinthe;

import javax.swing.*;
import java.awt.*;

public class Interface extends JFrame{

    private JPanel container = new JPanel();
    private JLabel[][] lab;
    private GridLayout grille;

    private Labyrinthe labyrinthe;

    public Interface(String name, int longeur, int largeur, Labyrinthe labyrinthe){
        this.labyrinthe = labyrinthe;
        this.setTitle(name);
        this.setSize(longeur*45, largeur*45);
        this.setResizable(true);

        initComposant(longeur, largeur);
        this.setContentPane(container);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        labyrinthe.setObserver((liaison) -> {
            JLabel label = lab[liaison.getCase1().getX()*2 + (liaison.getCase2().getX() - liaison.getCase1().getX())]
                    [liaison.getCase1().getY()*2 + (liaison.getCase2().getY() - liaison.getCase1().getY())];
            label.setBackground(Color.darkGray);
            label.setForeground(Color.lightGray);
        });
    }

    private void initComposant(int longeur, int largeur){
        Font police = new Font("Arial", Font.BOLD, 20);
        lab = new JLabel[longeur*2-1][largeur*2-1];
        grille = new GridLayout(longeur*2-1, largeur*2-1);
        container.setLayout(grille);
        for(int i=0; i<((longeur*2)-1); i++){
            for(int j=0; j<((largeur*2)-1); j++){

                if(i%2==0 && j%2==0){
                    lab[i][j] = new JLabel();
                    container.add(lab[i][j]);
                    lab[i][j].setOpaque(true);
                    lab[i][j].setBackground(Color.BLACK);
                }else{
                    if(i%2==1 && j%2==0){
                        lab[i][j] = new JLabel(
                                String.valueOf(labyrinthe.getCase(j/2, i/2).getValeurLiaison(Case.Direction.BAS))
                        );
                        container.add(lab[i][j]);
                        lab[i][j].setOpaque(true);
                    }
                    if(j%2==1 && i%2==0){
                        lab[i][j] = new JLabel(
                                String.valueOf(labyrinthe.getCase(j/2, i/2).getValeurLiaison(Case.Direction.DROITE))
                        );
                        container.add(lab[i][j]);
                        lab[i][j].setOpaque(true);
                    }
                    if(i%2==1 && j%2==1){
                        lab[i][j] = new JLabel();
                        container.add(lab[i][j]);
                        lab[i][j].setOpaque(true);
                    }
                }
                lab[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }
    }

    /*public static void main(String[] args){
        Interface i = new Interface("Labyrinthe", 7, 7);
    }
*/
}
