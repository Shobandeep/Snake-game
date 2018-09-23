package root;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class Scene extends Canvas implements Runnable{

    /*
        VARIABLES
     */
    // Graphics
    private boolean running = true;
    private Thread thread;
    private BufferStrategy bufferStrategy;
    // Game
    private int boundingY, boundingX;
    private Snake snake;
    private boolean gameOverFlag = false;


    Scene(int boundingX, int boundingY) {
        super();
        this.boundingX = boundingX;
        this.boundingY = boundingY;

        // initialize Snake Object
        snake = new Snake(new Block(0, 0), boundingX, boundingY, this);
        this.addKeyListener(snake);
    }


    // Draw calls
    public void draw() {
        // for smoother animation
        bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(2);
            return;
        }
        // prep board for drawing
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, boundingX, boundingY);

        /*
            GRAPHICS CODE HERE
        */

        // game over screen
        if(gameOverFlag) {
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.DIALOG, Font.PLAIN, 50));
            g.drawString("Game Over!", boundingX/2-135, boundingY/2);
        }
        // game
        else
            snake.draw(g);


        /*
            END GRAPHICS CODE
        */

        //buffer
        bufferStrategy.show();
        g.dispose();
        return;
    }


    public void update() {
        // do not play if game is over
        if(gameOverFlag)
            return;
        snake.update();
    }

    // start new thread
    public void start() {
        if(thread != null)
            return;
        thread = new Thread(this);
        thread.start();
    }


    @Override
    public void run() {
        // main game loop
        while(running) {
            try {
                // speed/difficulty
                Thread.sleep(200);
                update();
                draw();
            }
            catch (InterruptedException e) {

            }
        }
    }

    // setup fram
    public static JFrame setFrame(int x, int y) {
        JFrame frame = new JFrame("Snake Game");
        frame.setSize(x, y);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        return frame;
    }

    // called from Snake class when game is over
    public void gameOver(boolean done) {
        this.gameOverFlag = done;
    }

}


