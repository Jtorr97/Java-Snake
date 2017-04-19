//---------------------------------------------------------------------------------
// 
// Created by Josh on 4/19/2017.
//


import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Window extends JPanel
{
    // Main gameframe
    private static JFrame gameFrame = new JFrame();

    // App version
    private final double VERSION = 1.0;

    // Custom fonts used for this program
    private static Font font1;
    private static Font font2;

    public static void main(String[] args)
    {
        new Window();
    }

    Window()
    {
        // Create the main window
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setTitle("Snake");
        gameFrame.setSize(700, 550);
        gameFrame.setResizable(false);
        gameFrame.add(new Board());
        JMenuBar bar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        // Initialize fonts
        try {
            // Create the font to use. Specify the size!
            font1 = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/8-BIT WONDER.TTF")).deriveFont(40f);
            font2 = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/thin_pixel-7.ttf")).deriveFont(35f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            // Register the font
            ge.registerFont(font1);
            ge.registerFont(font2);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            System.out.println("Error: Font not found.");
        }

        // New game button
        JMenuItem newgame = new JMenuItem("New Game");
        //newgame.addActionListener(e -> restartGame());

        // create a jframe
        JFrame frame = new JFrame("Example");

        // Various other buttons
        JMenu helpMenu = new JMenu("Help");
        JMenuItem howToPlay = new JMenuItem ("How to play");
        JMenuItem controls = new JMenuItem("Controls");
        JMenuItem about = new JMenuItem("About");

        howToPlay.addActionListener(e -> JOptionPane.showMessageDialog(frame, "To play the game use the arrow keys to move the snake" +
                        " around and eat the apples." +
                        " \nThe bigger your snake the higher your score! " +
                        "\nWatch out for walls and your tail, hit these and the game is over!",
                "How to play",JOptionPane.INFORMATION_MESSAGE));
        controls.addActionListener(e -> JOptionPane.showMessageDialog(frame,
                "Up Key: Move Up\n" +
                        "Left Key: Move Left\n" +
                        "Right Key: Move Right\n" +
                        "Down Key: Move Down\n" +
                        "'n': New Game\n" +
                        "'p': Pause Screen\n" +
                        "'c': Exit Pause Screen\n" +
                        "Esc: Close window","Controls", JOptionPane.INFORMATION_MESSAGE));
        about.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Snake: version " + VERSION +
                        "\nwritten by Joshua Torres.\n" +
                        "Victoria College\n" +
                        "COSC 1436 Spring 2017 Final Project\n"
                , "About", JOptionPane.PLAIN_MESSAGE));

        // Add helpmenu elements
        helpMenu.add(howToPlay);
        helpMenu.add(controls);
        helpMenu.add(about);

        // Add gamemenu elements
        gameMenu.add(newgame);

        // Add game and help menu bar
        bar.add(gameMenu);
        bar.add(helpMenu);
        gameFrame.setJMenuBar(bar);
        gameFrame.setVisible(true);
    }

    public static JFrame getGameFrame()
    {
        return gameFrame;
    }

    public static Font getFont1()
    {
        return font1;
    }

    public static Font getFont2()
    {
        return font2;
    }
}
