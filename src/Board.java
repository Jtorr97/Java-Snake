//---------------------------------------------------------------------------------
//
// Board.java
//

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Board extends JPanel
{
    private volatile Snake snake = new Snake();

    private Food food = new Food();

    private final int SPEED = 60;

    private static int playerScore;

    private boolean gameStarted = false;

    // Default constructor
    Board()
    {
        Sound.Music.init();
        Sound.SoundEffect.init();
        Sound.Music.LEVEL_THEME.play();

        InputHandler inputHandler = new InputHandler();
        Window.getGameFrame().addKeyListener(inputHandler);

        snake.setSize(4);

        snake.snakeTimer = new Timer(SPEED, reDrawSnake);
    }

    // Paint component
    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        drawWindow(g);
        drawBoard(g);
        drawTitle(g);
        drawScoreBox(g);

        if(!gameStarted)
        {
            g2d.setFont(Window.getFont2().deriveFont(Font.BOLD).deriveFont(45f));
            g2d.setColor(Color.WHITE);
            g2d.drawString("Press the Enter key to start!", 500/5, 500/3);
            snake.snakeTimer.stop();
        }
        else
        {
            snake.snakeTimer.restart();
        }

        snake.drawSnake(g2d);
        food.spawnFood(g2d, snake);
    }

    public void drawBoard(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.RED);
        g2d.drawRect(49, 49, 501, 401);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(50, 50, 500, 400);


    }

    public void drawWindow(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,700,500);
    }

    public void drawTitle(Graphics g)
    {
        // Title text
        Graphics2D g2d = (Graphics2D)g;
        g2d.setFont(Window.getFont1());
        g2d.setColor(Color.white);
        g2d.drawString("Snake", 50, 40);
    }

    public void drawScoreBox(Graphics g)
    {
        // Score box/text
        Graphics2D g2d = (Graphics2D)g;
        g2d.setFont(Window.getFont2().deriveFont(Font.BOLD));
        g2d.setColor(Color.red);
        g2d.drawRect(560, 50, 125, 40);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Score: " + playerScore, 565, 75);
    }

    // Reset the snake, score, position and game status
    public void restartGame()
    {
        playerScore = 0;
        snake.setDirection(Snake.Direction.DOWN);
        snake.snakeTimer.start();
        snake.setSize(4);
        snake.setGameOver(false);
        gameStarted = false;
        repaint();
        snake.initalCoords();
    }

    public static void updateScore()
    {
        playerScore += 10;
    }

    public void move(Snake.Direction inputDirection)
    {
        if(inputDirection != snake.headDirection.opposite())
        {
            snake.snakeTimer.stop();
            snake.setDirection(inputDirection);
            repaint();
            snake.snakeTimer = new Timer(SPEED, reDrawSnake);
            snake.snakeTimer.start();
        }
    }

    private Action reDrawSnake = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    };

    //***************************************************************
    // Inner class: Handle various input for the program
    //***************************************************************

    public class InputHandler implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e)
        {
            // Handle input: Use arrow keys to move the snake
            if(gameStarted)
            {
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_LEFT:
                    {
                        move(Snake.Direction.LEFT);
                        break;
                    }
                    case KeyEvent.VK_RIGHT:
                    {
                        move(Snake.Direction.RIGHT);
                        break;
                    }

                    case KeyEvent.VK_DOWN:
                    {
                        move(Snake.Direction.DOWN);
                        break;
                    }

                    case KeyEvent.VK_UP:
                    {
                        move(Snake.Direction.UP);
                        break;
                    }
                }
            }

            // Start the game
            switch(e.getKeyCode())
            {

                case KeyEvent.VK_ENTER:
                {
                    Sound.SoundEffect.GAME_START.play();
                    snake.setDirection(Snake.Direction.DOWN);
                    gameStarted = true;
                    repaint();
                    break;
                }

                // Esc pressed to exit and close window
                case KeyEvent.VK_ESCAPE:
                {
                    Sound.SoundEffect.GAME_RESTART.play();
                    System.exit(0);
                }

                // New game/window
                case KeyEvent.VK_N:
                {
                    Sound.SoundEffect.GAME_RESTART.play();
                    restartGame();
                    break;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}