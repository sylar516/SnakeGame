package snake;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

public class Pear extends Fruit {

    public Pear(int x, int y) {
        super(x, y);
    }

    private static final String PEAR_SIGN  = "\uD83C\uDF50";

    @Override
    public void draw(Game game) {
        game.setCellValueEx(x,y, Color.NONE,PEAR_SIGN,Color.BEIGE,75);
    }
}
