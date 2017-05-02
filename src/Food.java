//---------------------------------------------------------------------------------
// 
// Food.java
//

import java.awt.*;
import java.util.Random;

public class Food
{
    // Random generator
    private Random rng = new Random();

    // Random x and y coordinates for the food spawn location
    private int randomX =  20 * rng.nextInt(Board.getGRID_WIDTH());
    private int randomY =  20 * rng.nextInt(Board.getGRID_HEIGHT());

    // Creates the food
    public void spawnFood(Graphics g, Snake snake)
    {
        if(foodEaten(randomX, randomY, snake)) {
            for(int i = 0; i < snake.getSize(); i++)
            {
                randomX = 20 * rng.nextInt(Board.getGRID_WIDTH());
                randomY = 20 * rng.nextInt(Board.getGRID_HEIGHT());

                if(randomX == snake.xPos[i] && randomY == snake.yPos[i])
                {
                    randomX = 20 * rng.nextInt(Board.getGRID_WIDTH());
                    randomY = 20 * rng.nextInt(Board.getGRID_HEIGHT());
                    i = 0;
                }
            }
        }
        drawFood(randomX, randomY, g);
    }

    // Draws the food
    public void drawFood(int foodX, int foodY, Graphics g)
    {
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setColor(Color.RED);
        graphics2D.fillOval(foodX, foodY, 20, 20);
    }

    // When the snake eats the food
    public boolean foodEaten(int foodX, int foodY, Snake snake)
    {
        for(int i = 0; i < snake.getSize(); i++)
        {
            if(foodX == snake.xPos[i] && foodY == snake.yPos[i])
            {
                snake.updateSize();
                Board.updateScore();
                Sound.SoundEffect.POINT_SCORED.play();
                return true;
            }
        }
        return false;
    }
}
