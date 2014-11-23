package com.horf.derp.testproject.game;

import android.graphics.Canvas;

import com.horf.derp.testproject.gui.MapEntityGraphics;

/**
 * Created by Eridan on 11/19/2014.
 */
public interface MapEntity {
    public void draw(Canvas canvas, int x, int y);
    public void setGraphics(MapEntityGraphics graphics);
    public void setLogic(MapEntityLogic logic);
}
