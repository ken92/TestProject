package com.horf.derp.testproject;

import android.graphics.Canvas;

/**
 * Created by Eridan on 10/31/2014.
 */
public interface MapEntityGraphics {
    public void draw(Canvas canvas, int x, int y);
    public void setImage(int path);
    public void setDimensions(int width, int height);
    /*public void setCoordinates(int x, int y);
    public void setX(int x);
    public void setY(int y);
    public int getX();
    public int getY();*/
}
