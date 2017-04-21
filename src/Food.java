//---------------------------------------------------------------------------------
// 
// Food.java
//

import java.awt.*;

public class Food
{
    // Random x and y coordinates for the food spawn location
    private int foodX = 10*(int)((50-5)*Math.random()+5), foodY =10*(int)((40-5)*Math.random()+5);

    public void spawnFood(Graphics g, Snake snake)
    {
        if(foodEaten(foodX, foodY, snake)) {
            for(int i = 0; i < snake.getSize(); i++){
                foodX = 10*(int)((50-5)*Math.random()+5);
                //System.out.println(x);

                foodY = 10*(int)((40-5)*Math.random()+5);
                //System.out.println(y);

                if(foodX == snake.xArray[i] && foodY == snake.yArray[i])
                {
                    foodX = 10*(int)((50-5)*Math.random()+5);
                    //System.out.println(x);

                    foodY = 10*(int)((40-5)*Math.random()+5);
                    //System.out.println(y);

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
        graphics2D.fillOval(foodX, foodY, 10, 10);
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
                System.out.println("Food eaten: " + foodX + " " + foodY);
                Sound.SoundEffect.SCOREPOINT.play();
                return true;
            }
        }
        return false;
    }
}
