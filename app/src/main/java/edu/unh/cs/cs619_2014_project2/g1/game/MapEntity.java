package edu.unh.cs.cs619_2014_project2.g1.game;


import android.graphics.Canvas;

import edu.unh.cs.cs619_2014_project2.g1.gui.MapEntityGraphics;

/**
 * Created by Eridan on 11/19/2014.
 */
public interface MapEntity {
    public void draw(Canvas canvas, int x, int y);
    public void setGraphics(MapEntityGraphics graphics);
    public void setLogic(MapEntityLogic logic);
}
