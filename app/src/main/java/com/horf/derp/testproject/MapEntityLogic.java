package com.horf.derp.testproject;

import android.graphics.Canvas;

/**
 * Created by Eridan on 11/15/2014.
 */
public interface MapEntityLogic {
    public void setCoordinates(int x, int y);
    public int getX();
    public int getY();
    public void setX(int x);
    public void setY(int y);
    public void draw(Canvas canvas, int x, int y);
    public MapEntityGraphics getGraphics();
}
