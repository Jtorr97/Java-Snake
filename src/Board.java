//---------------------------------------------------------------------------------
//
// Board.java
//

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Board extends JPanel
{
    private Snake snake = new Snake();
    private Food food = new Food();
    private final int SPEED = 60;
    private final int BOARD_WIDTH = 500;
    private final int BOARD_HEIGHT = 400;
    private static int playerScore;
    private boolean gameStarted = false;

    private final int BOX_HEIGHT = 10;
    private final int BOX_WIDTH = 10;
    private final int GRID_WIDTH = 50;
    private final int GRID_HEIGHT = 40;

    // Default constructor
    Board()
    {
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
            g2d.drawString("Press the Enter key to start!", BOARD_WIDTH/8, BOARD_HEIGHT/3);
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
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        //drawing an outside rect
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawRect(0, 0, GRID_WIDTH * BOX_WIDTH, GRID_HEIGHT * BOX_HEIGHT);

        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(0.1f));
        //drawing the vertical lines
        for (int x = BOX_WIDTH; x < GRID_WIDTH * BOX_WIDTH; x += BOX_WIDTH)
        {
            g2d.drawLine(x, 0, x, BOX_HEIGHT * GRID_HEIGHT);
        }

        //drawing the horizontal lines
        for (int y = BOX_HEIGHT; y < GRID_HEIGHT * BOX_HEIGHT; y += BOX_HEIGHT)
        {
            g2d.drawLine(0, y, GRID_WIDTH * BOX_WIDTH, y);
        }

    }

    public void drawWindow(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,Window.getWINDOW_WIDTH(),Window.getWINDOW_HEIGHT());
    }

    public void drawTitle(Graphics g)
    {
        // Title text
        Graphics2D g2d = (Graphics2D)g;
        g2d.setFont(Window.getFont1());
        g2d.setColor(Color.WHITE);
        g2d.drawString("Snake", 525, 40);
    }

    public void drawScoreBox(Graphics g)
    {
        // Score box/text
        Graphics2D g2d = (Graphics2D)g;
        g2d.setFont(Window.getFont2().deriveFont(Font.BOLD).deriveFont(40f));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Score: " + playerScore, 530, 75);
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