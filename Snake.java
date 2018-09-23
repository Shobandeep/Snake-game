package root;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/*
    NOTES:
    - Change ArrayList to a custom Queue?
*/


public class Snake implements KeyListener {

    /*
    VARIABLES
    */
    private ArrayList<Block> snake;
    private enum Direction {
            UP, DOWN, LEFT, RIGHT
    }
    private Direction currentDirection;
    private int boundingX, boundingY, size;
    private Scene scene;
    private Block food;
    private boolean ateFood = false;


    public Snake(Block head, int boundingX, int boundingY, Scene scene) {
        // initialize snake
        snake = new ArrayList();
        snake.add(head);
        food = null;

        //set Bounds and Scene
        this.boundingX = boundingX;
        this.boundingY = boundingY;
        this.scene = scene;

        // size of blocks
        size = boundingX/ 20;

        //set starting direction
        currentDirection = Direction.RIGHT;
    }


    public void update() {
        move();
        placeNewFood();
        checkFoodEaten();
        return;
    }

    public void draw(Graphics2D g) {

        // draw snake
        g.setColor(Color.GREEN);
        for(Block block: snake) {
            g.fillRect(block.getX()+5, block.getY()+5, size-5, size-5);
        }
        // draw food
        if(food != null) {
            g.setColor(Color.WHITE);
            g.fillRect(food.getX()+5, food.getY()+5, size-5, size-5);
        }
        // draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        g.drawString("Score: " + (snake.size()-1), 10 , 20);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        // check player input
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                if(currentDirection != Direction.UP)
                    currentDirection = Direction.DOWN;
                break;
            case KeyEvent.VK_UP:
                if(currentDirection != Direction.DOWN)
                    currentDirection = Direction.UP;
                break;
            case KeyEvent.VK_LEFT:
                if(currentDirection != Direction.RIGHT)
                    currentDirection = Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                if(currentDirection != Direction.LEFT)
                    currentDirection = Direction.RIGHT;
                break;
        }

    }

    // handle movement, tail becomes new head unless food was eaten
    private void move() {
        Block newHead = Block.copyBlock(snake.get(0));
        if(ateFood)
            ateFood = false;
        else
            snake.remove(snake.size() - 1);


        // move snake
        switch (currentDirection) {
            case DOWN:
                newHead.setY(newHead.getY() + size);
                break;
            case UP:
                newHead.setY(newHead.getY() - size);
                break;
            case LEFT:
                newHead.setX(newHead.getX() - size);
                break;
            case RIGHT:
                newHead.setX(newHead.getX() + size);
                break;
        }

        // check edge cases
        if(newHead.getX() < 0)
            newHead.setX(boundingX-size);
        else if (newHead.getX() >= boundingX)
            newHead.setX(0);
        else if(newHead.getY() < 0)
            newHead.setY(boundingY-size);
        else if (newHead.getY() >= boundingY)
            newHead.setY(0);

        // check collision
        for(Block block: snake) {
            if(newHead.equals(block))
                scene.gameOver(true);
        }
        // add modified tail back to snake
        snake.add(0, newHead);
    }

    // place new food if it was eaten
    private void placeNewFood() {
        if(food == null) {
            int x = 0,y = 0;
            boolean done = false;
            while(!done) {
                x = (int)(Math.random()*19) * 25;
                y = (int)(Math.random()*19) * 25;
                done = true;
                // make sure food does not spawn inside the snake
                for(Block block: snake) {
                    if(x == block.getX() && y == block.getY())
                        done = false;
                }
            }
            food = new Block(x, y);
        }
        return;
    }

    // for use by move function to extend snake
    private void checkFoodEaten() {
        for(Block block: snake) {
            if(block.getX() == food.getX() && block.getY() == food.getY()) {
                food = null;
                ateFood = true;
                break;
            }
        }
    }





    /*
        NOT USED
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
