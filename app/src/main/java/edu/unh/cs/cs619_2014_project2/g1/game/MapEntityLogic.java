package edu.unh.cs.cs619_2014_project2.g1.game;


import android.graphics.Canvas;

import edu.unh.cs.cs619_2014_project2.g1.gui.MapEntityGraphics;

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
