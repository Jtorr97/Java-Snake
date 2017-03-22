//---------------------------------------------------------------------------------
//
// Snake.java
//

import java.awt.*;
import javax.swing.*;

public class Snake implements Runnable
{
    char direction = 'a';
    private int[] xArray;
    private int[] yArray;
    private int initialSize;
    private boolean gameOver = false;
    Timer snakeTimer;

    Snake()
    {
        xArray = new int[2000];

        xArray[0] = 300;
        xArray[1] = 310;
        xArray[2] = 320;
        xArray[3] = 330;


        yArray = new int[2000];
        yArray[0] = 250;
        yArray[1] = 250;
        yArray[2] = 250;
        yArray[3] = 250;


        initialSize = 10;
    }

    // Draws the snake onto the screen
    public void drawSnake(Graphics2D g2)
    {
        updateSnake();
        if(!gameOver)
        {
            g2.setColor(Color.GREEN);
            for(int i = 0; i < initialSize; i++)
            {
                g2.fillOval(xArray[i], yArray[i], 10, 10);
            }
        }
        else
            System.out.println("Lose"); // TODO: Draw a lose message when colliding with wall or self
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

            xArray[0] = xArray[0] - 10;
        }

        if(direction == 's')
        {
            for(int i = initialSize - 1; i > 0; i--)
        {
            xArray[i] = xArray[i - 1];
            yArray[i] = yArray[i - 1];
        }

            yArray[0] = yArray[0] + 10;
        }

        if(direction == 'd')
        {
            for(int i = initialSize - 1; i > 0; i--)
            {
                xArray[i] = xArray[i - 1];
                yArray[i] = yArray[i - 1];
            }

            xArray[0] = xArray[0] + 10;
        }

        if(direction == 'w')
        {
            for(int i = initialSize - 1; i > 0; i--)
            {
                xArray[i] = xArray[i - 1];
                yArray[i] = yArray[i - 1];
            }

            yArray[0] = yArray[0] - 10;
        }
    }

    // TODO: Create collision checker method, spawn food later

    @Override
    public void run()
    {

    }
}
