//---------------------------------------------------------------------------------
//
// Snake.java
//

import java.awt.*;
import javax.swing.*;

public class Snake
{
    public enum Direction {
        LEFT {
            Direction opposite() {
                return RIGHT;
            }
        },
        RIGHT {
            Direction opposite() {
                return LEFT;
            }
        },
        UP {
            Direction opposite() {
                return DOWN;
            }
        },
        DOWN {
            Direction opposite() {
                return UP;
            }
        };

        abstract Direction opposite();
    }

    public Direction headDirection;

    private final int PIXEL_WIDTH = 10;
    private final int PIXEL_HEIGHT = 10;

    // Stores x and y coordinates
    public int[] xArray;
    public int[] yArray;

    // Snake's size
    private int size;

    // Max Snake size
    private int MAX_LENGTH = 560;

    // Determines whether game is over or not
    private boolean gameOver = false;

    // Timer used for snake's movement
    Timer snakeTimer;

    // Default constructor
    Snake()
    {
        xArray = new int[MAX_LENGTH];
        yArray = new int[MAX_LENGTH];
        initalCoords();
    }

    public void initalCoords()
    {
        // Initial X position
        xArray[0] = 300;
        xArray[1] = 300;
        xArray[2] = 300;
        xArray[3] = 300;

        // Initial Y position
        yArray[0] = 250;
        yArray[1] = 240;
        yArray[2] = 230;
        yArray[3] = 220;
    }

    // Draws the snake onto the screen
    public void drawSnake(Graphics2D g)
    {
        updateSnake();
        checkCollision();
        if(!gameOver)
        {
            g.setColor(Color.GREEN);
            for(int i = 0; i < size; i++)
            {
                g.fillRect(xArray[i], yArray[i], PIXEL_WIDTH, PIXEL_HEIGHT);
            }
        }
        else
            gameOverScreen(g);
    }

    // Snake updated after each direction change
    public void updateSnake()
    {
        if(headDirection == Direction.LEFT || headDirection == Direction.DOWN || headDirection == Direction.RIGHT || headDirection == Direction.UP)
        {
            for(int i = size - 1; i > 0; i--)
            {
                xArray[i] = xArray[i - 1];
                yArray[i] = yArray[i - 1];
            }

            switch (headDirection)
            {
                case LEFT:
                    xArray[0] -= 10;
                    break;

                case DOWN:
                    yArray[0] += 10;
                    break;

                case RIGHT:
                    xArray[0] += 10;
                    break;

                case UP:
                    yArray[0] -= 10;
                    break;
            }
        }
    }

    // Collision checker
    public void checkCollision()
    {
        // Left/Right wall OR Top/Bottom wall
        if((xArray[0] > 540 || xArray[0] < 50) || (yArray[0] > 440 || yArray[0] < 50))
            gameOver = true;

        // If the snake collides with itself
        for(int i = 1; i < size; i++)
        {
            if(xArray[0] == xArray[i] && yArray[0] == yArray[i])
                gameOver = true;
        }
    }

    // Lose/game over message
    public void gameOverScreen(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        String text = "Game Over";
        if(gameOver = true)
        {
            Sound.SoundEffect.COLLISION.play();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            snakeTimer.stop();
            // Game over text
            g2d.setFont(Window.getFont1());
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.BLACK);
            g2d.fillRect(50,50,500,400);
            g2d.setColor(Color.WHITE);
            g2d.drawString(text, 500/4, 500/2);

            // Try again text
            g2d.setFont(Window.getFont2());
            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(Color.WHITE);
            g2d.drawString("Press 'n' to play again", 205, 275);
        }
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getSize()
    {
        return size;
    }

    public void setDirection(Direction headDirection)
    {
        this.headDirection = headDirection;
    }

    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }

    public void updateSize()
    {
        size++;
    }
}
