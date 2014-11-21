package com.horf.derp.testproject;

import android.graphics.Canvas;

/**
 * Created by Eridan on 11/19/2014.
 */
public interface MapEntity {
    public void draw(Canvas canvas, int x, int y);
    public void setGraphics(MapEntityGraphics graphics);
    public void setLogic(MapEntityLogic logic);
}
