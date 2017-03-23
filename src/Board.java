//---------------------------------------------------------------------------------
//
// Board.java
//

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class Board extends JPanel implements KeyListener
{
    private volatile Snake gameSnake = new Snake();
    private JFrame gameFrame = new JFrame();
    private final int WINDOW_WIDTH = 700;
    private final int WINDOW_HEIGHT = 550;
    private int speed = 70;
    private int score;
    private int x = 10*(int)((50-5)*Math.random()+5), y =10*(int)((40-5)*Math.random()+5);
    private boolean isPaused;
    private boolean gameStarted = false;

    private final double VERSION = 1.0;

    Board()
    {
        // Create the main window
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.addKeyListener(this);
        gameFrame.setTitle("Snake");
        gameFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        gameFrame.setResizable(false);
        gameFrame.add(this);
        JMenuBar bar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        // New game button
        JMenuItem newgame = new JMenuItem("New Game");
        newgame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                gameFrame.dispose();
                Board board = new Board();
            }
        } );

        // create a jframe
        JFrame frame = new JFrame("Example");

        // Various other buttons
        JMenu helpMenu = new JMenu("Help");
        JMenuItem controls = new JMenuItem ("How to play");
        JMenuItem about = new JMenuItem("About");

        controls.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(frame, "To play the game use the arrow keys to move the snake" +
                                " around and eat the apples." +
                                " \nThe bigger your snake the higher your score! " +
                                "\nWatch out for walls and your tail, hit these and the game is over!",
                        "How to play",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Snake version " + VERSION +
                        "\nwritten by Joshua Torres.\nVictoria College\nCOSC 1436 Spring 2017 Final Project\n"
                        , "About", JOptionPane.PLAIN_MESSAGE);
            }
        });

        helpMenu.add(controls);
        helpMenu.add(about);
        gameMenu.add(newgame);

        bar.add(gameMenu);
        bar.add(helpMenu);
        gameFrame.setJMenuBar(bar);
        gameFrame.setVisible(true);

        gameSnake.snakeTimer = new Timer(speed, reDrawSnake);
    }

    public void paint(Graphics g)
    {
        // The game board and window
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.setColor(Color.white);
        graphics2D.fillRect(0,0,700,500);

        try {
            //create the font to use. Specify the size!
            Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts\\8-BIT WONDER.TTF")).deriveFont(40f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts\\8-BIT WONDER.TTF")));

            // Title text
            graphics2D.setFont(titleFont);
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString("Snake", 50, 40);

            //create the font to use. Specify the size!
            Font scoreFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts\\thin_pixel-7.ttf")).deriveFont(35f);
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts\\thin_pixel-7.ttf")));

            // Score box/text
            graphics2D.setFont(scoreFont);
            graphics2D.setColor(Color.RED);
            graphics2D.drawRect(49, 49, 501, 401);
            graphics2D.setColor(Color.BLACK);
            graphics2D.fillRect(50, 50, 500, 400);
            graphics2D.drawRect(560, 50, 125, 40);
            graphics2D.drawString("Score: " + score, 565, 75);

            // Pause screen
            if(isPaused)
            {
                graphics2D.setFont(scoreFont.deriveFont(Font.BOLD));
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
                graphics2D.setFont(scoreFont.deriveFont(Font.BOLD));
                graphics2D.setColor(Color.WHITE);
                graphics2D.drawString("Press Enter to start!", 200, 200);
                gameSnake.snakeTimer.stop();
            }

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

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

            // Press enter to start the game
            case KeyEvent.VK_ENTER:
            {
                gameStarted = true;
                gameSnake.snakeTimer.start();
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
                gameFrame.dispose();
                Board board = new Board();
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
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}