//---------------------------------------------------------------------------------
// 
// Food.java
//

import java.awt.*;

public class Food
{
    // Random x and y coordinates for the food spawn location
    private int foodX = 20*(int)((30-5)*Math.random()+5), foodY =20*(int)((30-5)*Math.random()+5);

    public void spawnFood(Graphics g, Snake snake)
    {
        System.out.println("Food X: " + foodX + " Food Y: " + foodY);
        if(foodEaten(foodX, foodY, snake)) {
            for(int i = 0; i < snake.getSize(); i++){
                foodX = 20*(int)((30-5)*Math.random()+5);

                foodY = 20*(int)((30-5)*Math.random()+5);

                if(foodX == snake.xArray[i] && foodY == snake.yArray[i])
                {
                    foodX = 20*(int)((30-5)*Math.random()+5);

                    foodY = 20*(int)((30-5)*Math.random()+5);

                    i = 0;
                }
            }
        }
        drawFood(foodX, foodY, g);
    }

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
            if(foodX == snake.xArray[i] && foodY == snake.yArray[i])
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
