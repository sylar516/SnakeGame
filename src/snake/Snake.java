package snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Snake {

    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public Snake(int x, int y) {
        snakeParts.add(new GameObject(x,y));
        snakeParts.add(new GameObject(x+1,y));
        snakeParts.add(new GameObject(x+2,y));
    }

    private List<GameObject> snakeParts = new ArrayList<>();

    public void draw(Game game) {

        Color color = isAlive ? Color.BLACK : Color.RED;

        for (GameObject gameObject : snakeParts) {
            if (gameObject == snakeParts.get(0)) {
                game.setCellValueEx(gameObject.x, gameObject.y, Color.NONE, HEAD_SIGN, color, 75);
            } else game.setCellValueEx(gameObject.x, gameObject.y, Color.NONE, BODY_SIGN, color, 75);
        }
    }

    public void setDirection(Direction direction) {
        if (snakeParts.get(0).x == snakeParts.get(1).x & (this.direction.equals(Direction.LEFT) | this.direction.equals(Direction.RIGHT))) {
            return;
        }
        if (snakeParts.get(0).y == snakeParts.get(1).y & (this.direction.equals(Direction.UP) | this.direction.equals(Direction.DOWN))) {
            return;
        }

        if (direction.equals(Direction.LEFT) && this.direction.equals(Direction.RIGHT)) {
            return;
        } else if (direction.equals(Direction.RIGHT) && this.direction.equals(Direction.LEFT)) {
            return;
        } else if (direction.equals(Direction.UP) && this.direction.equals(Direction.DOWN)) {
            return;
        } else if (direction.equals(Direction.DOWN) && this.direction.equals(Direction.UP)) {
            return;
        } else this.direction = direction;
    }

    public void move(Fruit fruit) {
        GameObject newHead = createNewHead();
        if (newHead.x < 0 | newHead.x > SnakeGame.WIDTH - 1 | newHead.y < 0 | newHead.y > SnakeGame.HEIGHT - 1) {
            isAlive = false;
        } else if (checkCollision(newHead)) {
            isAlive = false;
        } else if (newHead.x == fruit.x && newHead.y == fruit.y) {
            fruit.isAlive = false;
            snakeParts.add(0, newHead);
        } else {
            snakeParts.add(0, newHead);
            removeTail();
        }
    }

    public boolean checkCollision(GameObject newHead) {
        for (GameObject snakePart : snakeParts) {
            if (snakePart.x == newHead.x && snakePart.y == newHead.y)
                return true;
        }
        return false;
    }

    public GameObject createNewHead() {
        GameObject gameObject = snakeParts.get(0);
        switch (direction) {
            case LEFT:
                gameObject = new GameObject(snakeParts.get(0).x - 1, snakeParts.get(0).y);
                break;
            case RIGHT:
                gameObject = new GameObject(snakeParts.get(0).x + 1, snakeParts.get(0).y);
                break;
            case DOWN:
                gameObject = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
                break;
            case UP:
                gameObject = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
                break;
        }
        return gameObject;
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.get(snakeParts.size()-1));
    }

    public int getLength() {
        return snakeParts.size();
    }
}
