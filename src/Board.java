//---------------------------------------------------------------------------------
//
// Board.java
//

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JPanel
{

    private JFrame gameFrame = new JFrame();

    private Board()
    {
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setTitle("Snake");
        gameFrame.setSize(800, 800);
        gameFrame.setResizable(false);
        JMenuBar bar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");


        JMenuItem newgame = new JMenuItem("New Game");
        newgame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                gameFrame.dispose();
                Board board = new Board();
            }
        } );

        JMenu aboutMenu = new JMenu("About");
        JMenuItem help = new JMenuItem ("Help");
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.out.println("Help-About was clicked");
                JOptionPane.showMessageDialog(null, "To use play game use the arrow keys to move the snake around and eat the apples. \nThe bigger your snake the higher your score! \nWatch out for walls and your tail, hit these and you die!");
            }
        });

        aboutMenu.add(help);
        gameMenu.add(newgame);

        bar.add(gameMenu);
        bar.add(aboutMenu);
        gameFrame.setJMenuBar(bar);
        gameFrame.setVisible(true);
    }

    public static void main(String[] args)
    {
        Board board = new Board();
    }
}