//---------------------------------------------------------------------------------
//
// Snake.java
//

import java.awt.*;
import javax.swing.*;

public class Snake implements Runnable
{
    // Direction snake moves
    char direction;

    // Stores x and y coordinates
    private int[] xArray;
    private int[] yArray;

    // Snake's initial size
    private int initialSize;

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

        // Initial snake size
        initialSize = 4;
    }

    // Draws the snake onto the screen
    public void drawSnake(Graphics2D g)
    {
        updateSnake();
        checkCollision();
        if(!gameOver)
        {
            g.setColor(Color.GREEN);
            for(int i = 0; i < initialSize; i++)
            {
                System.out.println("X pos: " + xArray[0] + " Y pos: " + yArray[0]);

                final int PIXEL_WIDTH = 10;
                final int PIXEL_HEIGHT = 10;
                g.fillOval(xArray[i], yArray[i], PIXEL_WIDTH, PIXEL_HEIGHT);
            }
        }
        else
            lose(g);
    }

    // Snake updated after each direction change
    public void updateSnake()
    {
        if(direction == 'a')
        {
            for(int i = initialSize - 1; i > 0; i--)
            {
                xArray[i] = xArray[i - 1];
                yArray[i] = yArray[i - 1];
            }

            xArray[0] -= 10;
        }

        if(direction == 's')
        {
            for(int i = initialSize - 1; i > 0; i--)
        {
            xArray[i] = xArray[i - 1];
            yArray[i] = yArray[i - 1];
        }

            yArray[0] += 10;
        }

        if(direction == 'd')
        {
            for(int i = initialSize - 1; i > 0; i--)
            {
                xArray[i] = xArray[i - 1];
                yArray[i] = yArray[i - 1];
            }

            xArray[0] += 10;
        }

        if(direction == 'w')
        {
            for(int i = initialSize - 1; i > 0; i--)
            {
                xArray[i] = xArray[i - 1];
                yArray[i] = yArray[i - 1];
            }

            yArray[0] -= 10;
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
        for(int i = 1; i < initialSize; i++)
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
            g2d.setFont(new Font("Arial Black", Font.BOLD, 40));
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.BLACK);
            g2d.fillRect(50,50,500,400);
            g2d.setColor(Color.WHITE);
            g2d.drawString(text, 174, 250);
        }
    }

    @Override
    public void run()
    {

    }
}
