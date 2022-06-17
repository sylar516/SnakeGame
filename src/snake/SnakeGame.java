package snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {

    private static final int GOAL = 28;

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;

    private Snake snake;
    //private Apple apple;
    private Fruit fruit; //переменная вмест apple для отображения фруктов в общем и целом
    private int countFruits; //переменная-счётчик для чередования появляющихся на игровом поле фруктов

    private int turnDelay;

    private boolean isGameStopped;
    private int score;

    @Override
    public void initialize() {
        setScreenSize(WIDTH,HEIGHT);
        createGame();
    }

    private void createGame() {
        score = 0;
        setScore(score);
        snake = new Snake(WIDTH/2, HEIGHT/2);
        createNewFruit();
        isGameStopped = false;
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);
    }

    private void createNewFruit() {
        while(true) {
            if (countFruits > 1) {
                fruit = new Pear(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
                countFruits = 0;
            } else fruit = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));

            if (!snake.checkCollision(fruit)) {
                countFruits++;
                return;
            }
        }
    }

    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x,y,Color.DARKSEAGREEN,"");
            }
        }
        snake.draw(this);
        fruit.draw(this);
    }

    @Override
    public void onTurn(int step) {
        snake.move(fruit);
        if (!fruit.isAlive) {
            setScore(score += 1);
            setTurnTimer(turnDelay -= 10);
            createNewFruit();
        }

        if (!snake.isAlive) {
            gameOver();
        }

        if (snake.getLength() > GOAL) {
            win();
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {

        if (key == Key.SPACE && isGameStopped) {
            createGame();
        }

        switch (key) {
            case UP:
                snake.setDirection(Direction.UP);
                break;
            case DOWN:
                snake.setDirection(Direction.DOWN);
                break;
            case LEFT:
                snake.setDirection(Direction.LEFT);
                break;
            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                break;
        }
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "GAME OVER", Color.RED, 50);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "YOU WIN", Color.GREEN, 50);
    }
}
