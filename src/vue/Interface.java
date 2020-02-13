package vue;

import javax.swing.*;
import java.awt.*;

public class Interface extends JFrame{
    private JPanel pan = new JPanel();
    private JButton b = new JButton("test");

    public Interface(){
        this.setTitle("Labyrinthe");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new GridLayout(2,1));
        this.getContentPane().add(new JButton("test1"));
        this.getContentPane().add(new JButton("test2"));
        this.setVisible(true);
    }

    public static void main(String[] args){
            Interface i = new Interface();
    }
}