//---------------------------------------------------------------------------------
//
// Snake.java
//

import java.awt.*;
import javax.swing.*;

public class Snake
{
    // Direction snake moves
    char direction;

    // Constants for each direction
    private final char UP = 'w';
    private final char DOWN = 's';
    private final char LEFT = 'a';
    private final char RIGHT = 'd';

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
        // Initial X position
        xArray = new int[MAX_LENGTH];
        xArray[0] = 300;
        xArray[1] = 300;
        xArray[2] = 300;
        xArray[3] = 300;

        // Initial Y position
        yArray = new int[MAX_LENGTH];
        yArray[0] = 250;
        yArray[1] = 240;
        yArray[2] = 230;
        yArray[3] = 220;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getSize()
    {
        return size;
    }

    public char getLeftDirection()
    {
        return LEFT;
    }

    public char getRightDirection()
    {
        return RIGHT;
    }

    public char getUpDirection()
    {
        return UP;
    }

    public char getDownDirection()
    {
        return DOWN;
    }

    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }

    public void updateSize()
    {
        size++;
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
                final int PIXEL_WIDTH = 10;
                final int PIXEL_HEIGHT = 10;
                g.fillRect(xArray[i], yArray[i], PIXEL_WIDTH, PIXEL_HEIGHT);
            }
        }
        else
            lose(g);
    }

    // Snake updated after each direction change
    public void updateSnake()
    {
        if(direction == LEFT || direction == DOWN || direction == RIGHT || direction == UP)
        {
            for(int i = size - 1; i > 0; i--)
            {
                xArray[i] = xArray[i - 1];
                yArray[i] = yArray[i - 1];
            }

            switch (direction)
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
        // Left/Right wall
        if(xArray[0] > 540 || xArray[0] < 50)
            gameOver = true;

        // Top/Bottom wall
        if(yArray[0] > 440 || yArray[0] < 50)
            gameOver = true;

        // If the snake collides with itself
        for(int i = 1; i < size; i++)
        {
            if(xArray[0] == xArray[i] && yArray[0] == yArray[i])
                gameOver = true;
        }
    }

    // Lose/game over message
    public void lose(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        String text = "Game Over";
        if(gameOver = true)
        {
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
            g2d.setFont(new Font("Arial",Font.PLAIN, 20));
            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(Color.WHITE);
            g2d.drawString("Press 'n' to play again", 205, 275);
        }
    }
}
