//---------------------------------------------------------------------------------
//
// Snake.java
//

import java.awt.*;
import javax.swing.*;

public class Snake
{
    private Direction direction = Direction.LEFT;

    // Stores x and y coordinates for the snake
    public int[] xPos;
    public int[] yPos;

    // Snake's size
    private int size = 4;

    // Max Snake size
    private int MAX_LENGTH = 560;

    // Determines whether game is over or not
    private boolean gameOver = false;

    // Timer used for snake's movement
    Timer snakeTimer;

    // Default constructor
    Snake()
    {
        xPos = new int[MAX_LENGTH];
        yPos = new int[MAX_LENGTH];
        initCoordinates();
    }

    public void initCoordinates()
    {
        // Initial X position
        xPos[0] = 300;
        xPos[1] = 300;
        xPos[2] = 300;
        xPos[3] = 300;

        // Initial Y position
        yPos[0] = 280;
        yPos[1] = 260;
        yPos[2] = 240;
        yPos[3] = 220;
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
                g.fillRect(xPos[i], yPos[i], 20, 20);
            }
        }
        else
        {
            gameOverScreen(g);
        }

    }

    // Snake updated after each direction change
    public void updateSnake()
    {
        assert (direction != null);

            for(int i = size - 1; i > 0; i--)
            {
                xPos[i] = xPos[i - 1];
                yPos[i] = yPos[i - 1];
            }

            switch (direction)
            {
                case LEFT:
                    xPos[0] -= 20;
                    break;

                case DOWN:
                    yPos[0] += 20;
                    break;

                case RIGHT:
                    xPos[0] += 20;
                    break;

                case UP:
                    yPos[0] -= 20;
                    break;
            }

    }

    // Collision checker
    public void checkCollision()
    {
        // (Left OR Right wall) OR (Top OR Bottom wall)
        if((xPos[0] < 0 || xPos[0] > 580) || (yPos[0] < 0 || yPos[0] > 580))
            gameOver = true;

        // If the snake collides with itself
        for(int i = 1; i < size; i++)
        {
            if(xPos[0] == xPos[i] && yPos[0] == yPos[i])
                gameOver = true;
        }
    }

    // Lose/game over message
    public void gameOverScreen(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        String text = "Game Over";
        if(gameOver)
        {
            try {
                Sound.Music.LEVEL_THEME.stop();
                Sound.SoundEffect.COLLISION.play();
                Sound.SoundEffect.GAME_OVER.play();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            snakeTimer.stop();
            // Game over text
            g2d.setFont(Window.getFont1());
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.WHITE);
            g2d.drawString(text, 600/5, 600/3);

            // Try again text
            g2d.setFont(Window.getFont2().deriveFont(50f));
            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(Color.WHITE);
            g2d.drawString("Press 'n' to play again", 600/4, 600 - 370);
        }
        Board.checkScore();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    public void setDirection(Direction newDirection)
    {
        assert(direction != null);
        if (newDirection != direction.opposite())
        {
            direction = newDirection;
        }
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
