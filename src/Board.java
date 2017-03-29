//---------------------------------------------------------------------------------
//
// Board.java
//

import javafx.beans.property.adapter.JavaBeanObjectProperty;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class Board extends JPanel
{
    // Snake object
    private volatile Snake gameSnake = new Snake();

    // Main game frame
    private JFrame gameFrame = new JFrame();

    // InputHandler object
    private InputHandler inputHandler = new InputHandler();

    // Custom fonts used for this program
    private Font font1;
    private Font font2;

    // Speed of which the snakes moves at, lower = faster
    private final int speed = 70;

    // The game score
    private int score;

    // Random x and y coordinates for the food spawn location
    private int x = 10*(int)((50-5)*Math.random()+5), y =10*(int)((40-5)*Math.random()+5);

    // If the game is paused or not
    private boolean isPaused;

    // If the game has started or not
    private boolean gameStarted = false;

    // App version
    private final double VERSION = 1.0;

    // Default constructor
    private Board()
    {
        initGUI();

        gameSnake.snakeTimer = new Timer(speed, reDrawSnake);
    }

    // Initialize various elements for the game's GUI
    private void initGUI()
    {
        // Create the main window
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.addKeyListener(inputHandler);
        gameFrame.setTitle("Snake");
        gameFrame.setSize(700, 550);
        gameFrame.setResizable(false);
        gameFrame.add(this);
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
        newgame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                restartGame();
            }
        } );

        // create a jframe
        JFrame frame = new JFrame("Example");

        // Various other buttons
        JMenu helpMenu = new JMenu("Help");
        JMenuItem howToPlay = new JMenuItem ("How to play");
        JMenuItem controls = new JMenuItem("Controls");
        JMenuItem about = new JMenuItem("About");

        howToPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(frame, "To play the game use the arrow keys to move the snake" +
                                " around and eat the apples." +
                                " \nThe bigger your snake the higher your score! " +
                                "\nWatch out for walls and your tail, hit these and the game is over!",
                        "How to play",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        controls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,
                        "Up Key: Move Up\n" +
                        "Left Key: Move Left\n" +
                        "Right Key: Move Right\n" +
                                "Down Key: Move Down\n" +
                                "'n': New Game\n" +
                                "'p': Pause Screen\n" +
                                "'c': Exit Pause Screen\n" +
                                "Esc: Close window","Controls", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Snake: version " + VERSION +
                                "\nwritten by Joshua Torres.\n" +
                                "Victoria College\n" +
                                "COSC 1436 Spring 2017 Final Project\n"
                        , "About", JOptionPane.PLAIN_MESSAGE);
            }
        });

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

    private void restartGame()
    {
        gameFrame.dispose();
        new Board();
    }

    // Paint component
    public void paint(Graphics g)
    {
        // The game board and window
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.setColor(Color.white);
        graphics2D.fillRect(0,0,700,500);

        // Title text
        graphics2D.setFont(font1);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString("Snake", 50, 40);

        // Score box/text
        graphics2D.setFont(font2);
        graphics2D.setColor(Color.RED);
        graphics2D.drawRect(49, 49, 501, 401);
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(50, 50, 500, 400);
        graphics2D.drawRect(560, 50, 125, 40);
        graphics2D.drawString("Score: " + score, 565, 75);

        // Pause screen
        if(isPaused)
        {
            graphics2D.setFont(font2.deriveFont(Font.BOLD));
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("Paused - Press 'c' to continue",150,200);
            gameSnake.snakeTimer.stop();
        }
        else
        {
            gameSnake.snakeTimer.restart();
        }

        if(!gameStarted)
        {
            graphics2D.setFont(font2.deriveFont(Font.BOLD));
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("Press the up or down key to start!", 120, 200);
        }
        else
        {
            gameSnake.snakeTimer.restart();
        }

        // Draw the snake
        gameSnake.drawSnake(graphics2D);

        // Spawn the food
        spawnFood(graphics2D);
    }

    private Action reDrawSnake = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    };

    public void spawnFood(Graphics g)
    {
        drawFood(x, y, g);
    }

    public void drawFood(int x, int y, Graphics g)
    {
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setColor(Color.RED);
        graphics2D.fillOval(x, y, 10, 10);
    }

    //***************************************************************
    // Handle various input for the program
    //***************************************************************

    public class InputHandler implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e)
        {
            // Handle input: Use arrow keys to move the snake
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                {
                    if(gameSnake.direction == 'a')
                        break;
                    if(gameSnake.direction != 'd') {
                        gameSnake.snakeTimer.stop();
                        gameSnake.direction = 'a';
                        repaint();
                        gameSnake.snakeTimer = new Timer(speed, reDrawSnake);
                        gameSnake.snakeTimer.start();
                    }
                    break;
                }

                case KeyEvent.VK_RIGHT:
                {
                    if(gameSnake.direction == 'd')
                        break;
                    if(gameSnake.direction != 'a') {
                        gameSnake.snakeTimer.stop();
                        gameSnake.direction = 'd';
                        repaint();
                        gameSnake.snakeTimer = new Timer(speed, reDrawSnake);
                        gameSnake.snakeTimer.start();
                    }
                    break;
                }

                case KeyEvent.VK_DOWN:
                {
                    if(gameSnake.direction == 's')
                        break;
                    if(gameSnake.direction != 'w') {
                        gameSnake.snakeTimer.stop();
                        gameSnake.direction = 's';
                        repaint();
                        gameSnake.snakeTimer = new Timer(speed, reDrawSnake);
                        gameSnake.snakeTimer.start();
                    }
                    break;
                }

                case KeyEvent.VK_UP:
                {
                    if(gameSnake.direction == 'w')
                        break;
                    if(gameSnake.direction != 's') {
                        gameSnake.snakeTimer.stop();
                        gameSnake.direction = 'w';
                        repaint();
                        gameSnake.snakeTimer = new Timer(speed, reDrawSnake);
                        gameSnake.snakeTimer.start();
                    }
                    break;

                }

                case KeyEvent.VK_ENTER:
                {
                    gameSnake.direction = 'a';
                    gameStarted = true;
                    break;
                }

                // 'p' pressed, pause game
                case KeyEvent.VK_P:
                {
                    isPaused = true;
                    break;
                }

                // 'c' pressed, continue game
                case KeyEvent.VK_C:
                {
                    isPaused = false;
                    repaint();
                    break;
                }

                // Esc pressed to exit and close window
                case KeyEvent.VK_ESCAPE:
                {
                    System.exit(0);
                }

                case KeyEvent.VK_N:
                {
                    restartGame();
                    break;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    //***************************************************************
    // Main method
    //***************************************************************

    public static void main(String[] args)
    {
       new Board();
    }
}