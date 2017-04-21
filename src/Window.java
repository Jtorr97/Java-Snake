//---------------------------------------------------------------------------------
// 
// Window.java
//

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Window
{
    // Main gameframe
    private static JFrame gameFrame = new JFrame();

    private JMenuBar bar = new JMenuBar();
    private JMenu helpMenu = new JMenu("Help");

    // Various other buttons
    private JMenuItem howToPlay = new JMenuItem ("How to play");
    private JMenuItem controls = new JMenuItem("Controls");
    private JMenuItem about = new JMenuItem("About");

    // App version
    private final double VERSION = 1.0;

    // Window size
    private final int WINDOW_WIDTH = 700;
    private final int WINDOW_HEIGHT = 550;

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
        gameFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        gameFrame.setResizable(false);
        gameFrame.add(new Board());
        loadMenu();
        loadFonts();
        gameFrame.setVisible(true);
    }

    public void loadMenu()
    {
        howToPlay.addActionListener(e -> JOptionPane.showMessageDialog(gameFrame, "To play the game use the arrow keys to move the snake" +
                        " around and eat the apples." +
                        " \nThe bigger your snake the higher your score! " +
                        "\nWatch out for walls and your tail, hit these and the game is over!",
                "How to play",JOptionPane.INFORMATION_MESSAGE));
        controls.addActionListener(e -> JOptionPane.showMessageDialog(gameFrame,
                "Up Key: Move Up\n" +
                        "Left Key: Move Left\n" +
                        "Right Key: Move Right\n" +
                        "Down Key: Move Down\n" +
                        "'n': New Game\n" +
                        "'p': Pause Screen\n" +
                        "'c': Exit Pause Screen\n" +
                        "Esc: Close window","Controls", JOptionPane.INFORMATION_MESSAGE));
        about.addActionListener(e -> JOptionPane.showMessageDialog(gameFrame, "Snake: version " + VERSION +
                        "\nwritten by Joshua Torres.\n" +
                        "Victoria College\n" +
                        "COSC 1436 Spring 2017 Final Project\n"
                , "About", JOptionPane.PLAIN_MESSAGE));

        // Add helpmenu elements
        helpMenu.add(howToPlay);
        helpMenu.add(controls);
        helpMenu.add(about);

        // Add game and help menu bar
        bar.add(helpMenu);
        gameFrame.setJMenuBar(bar);
    }

    public void loadFonts()
    {
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
