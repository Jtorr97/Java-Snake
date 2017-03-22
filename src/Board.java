//---------------------------------------------------------------------------------
//
// Board.java
//

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

public class Board extends JPanel implements KeyListener
{
    private volatile Snake gameSnake = new Snake();
    private JFrame gameFrame = new JFrame();
    private final int WINDOW_WIDTH = 700;
    private final int WINDOW_HEIGHT = 550;
    private int speed = 50;
    private int score;
    private int x = 10*(int)((50-5)*Math.random()+5), y =10*(int)((40-5)*Math.random()+5);


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

        // Various other buttons
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

        gameSnake.snakeTimer = new Timer(speed, reDrawSnake);
        gameSnake.snakeTimer.restart();
    }

    public void paint(Graphics g)
    {
        // The game board and window
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.setColor(Color.white);
        graphics2D.fillRect(0,0,700,500);

        // Title text
        Font font = new Font("Verdana", Font.BOLD, 40);
        graphics2D.setFont(font);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString("Snake!", 50, 40);

        // Score box/text
        graphics2D.setColor(Color.RED);
        graphics2D.drawRect(49, 49, 501, 401);
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(50, 50, 500, 400);
        graphics2D.drawRect(560, 50, 125, 40);
        graphics2D.setFont(new Font("Verdana", Font.PLAIN, 12));
        graphics2D.drawString("Score: " + score, 565, 75);

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