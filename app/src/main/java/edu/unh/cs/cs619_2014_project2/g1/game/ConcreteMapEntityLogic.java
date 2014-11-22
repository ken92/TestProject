package edu.unh.cs.cs619_2014_project2.g1.game;


import android.graphics.Canvas;

import edu.unh.cs.cs619_2014_project2.g1.R;
import edu.unh.cs.cs619_2014_project2.g1.gui.MapEntityGraphics;

/**
 * Created by Eridan on 11/15/2014.
 */
public class ConcreteMapEntityLogic implements MapEntityLogic {
    private static final int UP= R.integer.up;
    private static final int DOWN=R.integer.down;
    private static final int LEFT=R.integer.left;
    private static final int RIGHT=R.integer.right;

    private MapEntityGraphics graphics;
    private int x, y;
    private int direction=UP;

    public ConcreteMapEntityLogic(int x, int y, MapEntityGraphics graphics) {
        setX(x);
        setY(y);
        this.graphics=graphics;
    }

    @Override
    public void setCoordinates(int x, int y) {
        setX(x);
        setY(y);
    }
    @Override
    public int getX() {
        return x;
    }
    @Override
    public int getY() {
        return y;
    }
    @Override
    public void setX(int x) {
        this.x=x;
    }
    @Override
    public void setY(int y) {
        this.y=y;
    }
    public int getDirection() {return direction;}
    public void setDirection(int direction) {
        if(direction==UP || direction==LEFT || direction==RIGHT || direction==DOWN)
            this.direction = direction;
    }

    public void draw(Canvas canvas, int x, int y) {
        graphics.draw(canvas, x, y);
    }
    public MapEntityGraphics getGraphics() { return graphics; }
}
