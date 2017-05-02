//---------------------------------------------------------------------------------
//
// Board.java
//

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class Board extends JPanel
{
    // Snake Object
    private static Snake snake = new Snake();

    // Food object
    private Food food = new Food();

    // Snake's speed
    private final int SPEED = 70;

    // Board width and height
    private final int BOARD_WIDTH = 600;
    private final int BOARD_HEIGHT = 600;

    // Initial/Starting score
    private static int playerScore = 0;

    // Highscore variable
    private static String highScore = "";

    // Determines if game is started
    private static boolean gameStarted = false;

    // Constants used for drawing grid
    private final int BOX_HEIGHT = 20;
    private final int BOX_WIDTH = 20;
    private static final int GRID_WIDTH = 30;
    private static final int GRID_HEIGHT = 30;

    // Default constructor
    Board()
    {
        Sound.Music.LEVEL_THEME.play();
        InputHandler inputHandler = new InputHandler();
        Window.getGameFrame().addKeyListener(inputHandler);
        Action reDrawSnake = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        };
        snake.snakeTimer = new Timer(SPEED, reDrawSnake);

        if(highScore.equals(""))
        {
            // Init the highscore
            highScore = this.getHighScore();
        }
    }

    // Paint component
    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        drawWindow(g);
        drawBoard(g);
        drawGrid(g);
        drawTitle(g);
        drawScoreBox(g);

        if(!gameStarted)
        {
            g2d.setFont(Window.getFont2().deriveFont(Font.BOLD).deriveFont(50f));
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

        if(highScore.equals(""))
        {
            // Init the highscore
            highScore = this.getHighScore();
        }
    }

    public void drawBoard(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
    }

    public void drawGrid(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        // Drawing an outside rect
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawRect(0, 0, GRID_WIDTH * BOX_WIDTH, GRID_HEIGHT * BOX_HEIGHT);

        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(0.1f));
        // Drawing the vertical lines
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
        g2d.setFont(Window.getFont1().deriveFont(50f));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Snake", 625, 50);
    }

    public void drawScoreBox(Graphics g)
    {
        // Score box/text
        Graphics2D g2d = (Graphics2D)g;
        g2d.setFont(Window.getFont2().deriveFont(Font.BOLD).deriveFont(50f));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Score: " + playerScore, 0, 625);
        g2d.drawString("Highscore: " + highScore, 0, 650);
    }

    // Reset the snake, score, position and game status
    public static void restartGame()
    {
        Sound.SoundEffect.GAME_OVER.stop();
        Sound.Music.LEVEL_THEME.play();
        playerScore = 0;
        snake.setDirection(Direction.RIGHT);
        snake.snakeTimer.start();
        snake.setSize(4);
        snake.setGameOver(false);
        gameStarted = false;
        snake.initCoordinates();
    }

    public static void checkScore()
    {
        // Format:  Name/:/Score
        if(playerScore > Integer.parseInt(highScore.split(":")[1]))
        {
            try
            {
                Thread.sleep(5500);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            // User has set a new record
            Sound.SoundEffect.NEW_HIGH_SCORE.play();
            String name = JOptionPane.showInputDialog("You set a new highscore! What is your name?");
            highScore = name + ":" + playerScore;
            restartGame();

            File scoreFile = new File("src/data/highscore.dat");
            if(!scoreFile.exists())
            {
                try
                {
                    scoreFile.createNewFile();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            FileWriter writeFile = null;
            BufferedWriter writer = null;

            try
            {
                writeFile = new FileWriter(scoreFile);
                writer = new BufferedWriter(writeFile);
                writer.write(highScore);
            }
            catch (Exception e)
            {
                // Errors
            }
            finally
            {
                if(writer != null)
                    try
                    {
                        writer.close();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
            }
        }
    }

    public String getHighScore()
    {
        // Format:  Name:Score
        FileReader readFile = null;
        BufferedReader reader = null;
        try
        {
            readFile = new FileReader("src/data/highscore.dat");
            reader = new BufferedReader(readFile);
            return reader.readLine();
        }
        catch (Exception e)
        {
            System.out.println("Not found!");
            return "Nobody:0";
        }
        finally
        {
            try
            {
                if(reader != null)
                reader.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static int getGRID_WIDTH()
    {
        return GRID_WIDTH;
    }

    public static int getGRID_HEIGHT()
    {
        return GRID_HEIGHT;
    }

    public static void updateScore()
    {
        playerScore += 10;
    }

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
                        snake.setDirection(Direction.LEFT);
                        break;
                    }
                    case KeyEvent.VK_RIGHT:
                    {
                        snake.setDirection(Direction.RIGHT);
                        break;
                    }

                    case KeyEvent.VK_DOWN:
                    {
                        snake.setDirection(Direction.DOWN);
                        break;
                    }

                    case KeyEvent.VK_UP:
                    {
                        snake.setDirection(Direction.UP);
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
                    snake.setDirection(Direction.DOWN);
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