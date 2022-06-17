package snake;;

import com.javarush.engine.cell.Game;

public abstract class Fruit extends GameObject{

    public Fruit(int x, int y) {
        super(x, y);
    }

    public boolean isAlive = true;

    public abstract void draw(Game game);
}
