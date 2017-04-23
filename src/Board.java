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

    private InputHandler inputHandler = new InputHandler();

    private Food food = new Food();

    private final int SPEED = 45;

    private static int playerScore;

    private boolean gameStarted = false;

    // Default constructor
    Board()
    {
        // Init sounds
        Sound.SoundEffect.init();
        Sound.SoundEffect.volume = Sound.SoundEffect.Volume.PLAYING;
        Sound.Music.volume = Sound.Music.Volume.PLAYING;
        Sound.Music.LEVEL_THEME.play();
        Window.getGameFrame().addKeyListener(inputHandler);

        snake.setSize(4);

        snake.snakeTimer = new Timer(SPEED, reDrawSnake);
    }

    // Paint component
    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        drawWindow(g);
        drawTitle(g);
        drawScoreBox(g);

        if(!gameStarted)
        {
            g2d.setFont(Window.getFont2().deriveFont(Font.BOLD));
            g2d.setColor(Color.WHITE);
            g2d.drawString("Press the Enter key to start!", 500/4, 500/3);
            snake.snakeTimer.stop();
        }
        else
        {
            snake.snakeTimer.restart();
        }

        snake.drawSnake(g2d);
        food.spawnFood(g2d, snake);
    }

    public void drawWindow(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.white);
        g2d.fillRect(0,0,700,500);
    }

    public void drawTitle(Graphics g)
    {
        // Title text
        Graphics2D g2d = (Graphics2D)g;
        g2d.setFont(Window.getFont1());
        g2d.setColor(Color.BLACK);
        g2d.drawString("Snake", 50, 40);
    }

    public void drawScoreBox(Graphics g)
    {
        // Score box/text
        Graphics2D g2d = (Graphics2D)g;
        g2d.setFont(Window.getFont2().deriveFont(Font.BOLD));
        g2d.setColor(Color.RED);
        g2d.drawRect(49, 49, 501, 401);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(50, 50, 500, 400);
        g2d.drawRect(560, 50, 125, 40);
        g2d.drawString("Score: " + playerScore, 565, 75);
    }

    // Reset the snake, score, position and game status
    public void restartGame()
    {
        playerScore = 0;
        snake.direction = snake.getDownDirection();
        snake.snakeTimer.start();
        snake.setSize(4);
        snake.setGameOver(false);
        gameStarted = false;

        snake.initalCoords();
    }

    public static void updateScore()
    {
        playerScore += 10;
    }

    public void move(char inputDirection)
    {
        snake.snakeTimer.stop();
        snake.direction = inputDirection;
        repaint();
        snake.snakeTimer = new Timer(SPEED, reDrawSnake);
        snake.snakeTimer.start();
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
                        move(snake.getLeftDirection());
                        break;
                    }

                    case KeyEvent.VK_RIGHT:
                    {
                        move(snake.getRightDirection());
                        break;
                    }

                    case KeyEvent.VK_DOWN:
                    {
                        move(snake.getDownDirection());
                        break;
                    }

                    case KeyEvent.VK_UP:
                    {
                        move(snake.getUpDirection());
                        break;
                    }
                }
            }

            // Start the game
            switch(e.getKeyCode())
            {

                case KeyEvent.VK_ENTER:
                {
                    Sound.SoundEffect.GAMESTART.play();
                    //snake.direction = snake.getDownDirection();
                    gameStarted = true;
                    repaint();
                    break;
                }

                // Esc pressed to exit and close window
                case KeyEvent.VK_ESCAPE:
                {
                    Sound.SoundEffect.GAMERESTART.play();
                    System.exit(0);
                }

                // New game/window
                case KeyEvent.VK_N:
                {
                    Sound.SoundEffect.GAMERESTART.play();
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