package com.horf.derp.testproject.game;

import android.content.Context;
import android.graphics.Canvas;

import com.horf.derp.testproject.gui.MapEntityGraphics;
import com.horf.derp.testproject.R;

/**
 * Created by Eridan on 11/15/2014.
 */
public class ConcreteMapEntityLogic implements MapEntityLogic {
    private static int UP;
    private static int DOWN;
    private static int LEFT;
    private static int RIGHT;

    private MapEntityGraphics graphics;
    private int x, y;
    private int direction=UP;

    public ConcreteMapEntityLogic(int x, int y, MapEntityGraphics graphics, Context context) {
        setX(x);
        setY(y);
        this.graphics=graphics;
        UP=context.getResources().getInteger(R.integer.up);
        LEFT=context.getResources().getInteger(R.integer.left);
        DOWN=context.getResources().getInteger(R.integer.down);
        RIGHT=context.getResources().getInteger(R.integer.right);
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
