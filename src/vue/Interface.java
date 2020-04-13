package vue;

import model.Case;
import model.Labyrinthe;
import model.Liaison;
import model.observer.Observer;

import javax.swing.*;
import java.awt.*;

/**
 * @author Nicolas Landry
 * @author Sviatoslav Besnard
 */
public class Interface extends JFrame{

    private JPanel container = new JPanel();
    private JLabel[][] lab;
    private GridLayout grille;

    private Labyrinthe labyrinthe;

    public Interface(String name, int longueur, int largeur, Labyrinthe labyrinthe){
        try{
            this.labyrinthe = labyrinthe;
            this.setTitle(name);
            this.setSize(longueur*45, largeur*45);
            this.setResizable(true);

            initComposant(longueur, largeur);
            this.setContentPane(container);

            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.setVisible(true);

        /*
        A chaque nouvelle liaison trouvée par l'algo de prim
        update le label correspondant dans le UI
         */
            labyrinthe.setObserver(new Observer() {
                @Override
                public void onEvent1(Liaison liaison) {
                    // nouvelle liaison solution
                    JLabel label = lab[liaison.getCoordY()][liaison.getCoordX()];
                    label.setBackground(Color.darkGray);
                    label.setForeground(Color.lightGray);
                }

                @Override
                public void onEvent2(Liaison liaison) {
                    // nouvelle liaison possible
                    JLabel label = lab[liaison.getCoordY()][liaison.getCoordX()];
                    label.setBackground(Color.cyan);
                }

                @Override
                public void onEvent3(Liaison liaison) {
                    // liaison possible supprimé
                    JLabel label = lab[liaison.getCoordY()][liaison.getCoordX()];
                    label.setBackground(Color.white);
                }

                @Override
                public void onEvent4(Liaison liaison) {
                    // liaison possible supprimé
                    JLabel label = lab[liaison.getCoordY()][liaison.getCoordX()];
                    label.setBackground(Color.red);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void initComposant(int longueur, int largeur){
        Font police = new Font("Arial", Font.BOLD, 20);
        lab = new JLabel[largeur*2-1][longueur*2-1];
        grille = new GridLayout(largeur*2-1, longueur*2-1);
        container.setLayout(grille);
        for(int y=0; y<((largeur*2)-1); y++){
            for(int x=0; x<((longueur*2)-1); x++){

                if(y%2==0 && x%2==0){
                    lab[y][x] = new JLabel();
                    container.add(lab[y][x]);
                    lab[y][x].setOpaque(true);
                    lab[y][x].setBackground(Color.BLACK);
                }else{
                    if(y%2==1 && x%2==0){
                        lab[y][x] = new JLabel(
                                String.valueOf(labyrinthe.getCase(x/2, y/2).getValeurLiaison(Case.Direction.BAS))
                        );
                        container.add(lab[y][x]);
                        lab[y][x].setOpaque(true);
                    }
                    if(x%2==1 && y%2==0){
                        lab[y][x] = new JLabel(
                                String.valueOf(labyrinthe.getCase(x/2, y/2).getValeurLiaison(Case.Direction.DROITE))
                        );
                        container.add(lab[y][x]);
                        lab[y][x].setOpaque(true);
                    }
                    if(y%2==1 && x%2==1){
                        lab[y][x] = new JLabel();
                        container.add(lab[y][x]);
                        lab[y][x].setOpaque(true);
                    }
                }
                lab[y][x].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }
    }

    /*public static void main(String[] args){
        Interface i = new Interface("Labyrinthe", 7, 7);
    }
*/
}
