package com.horf.derp.testproject;

import android.graphics.Canvas;

/**
 * Created by Eridan on 11/19/2014.
 */
public class ConcreteMapEntity implements MapEntity {
    private MapEntityGraphics graphics;
    private MapEntityLogic logic;

    public ConcreteMapEntity(MapEntityGraphics graphics, MapEntityLogic logic) {
        this.graphics=graphics;
        this.logic=logic;
    }

    public void setGraphics(MapEntityGraphics graphics) { this.graphics=graphics; }
    public void setLogic(MapEntityLogic logic) { this.logic=logic; }
    public void draw(Canvas canvas, int x, int y) {
        graphics.draw(canvas, x, y);
    }
}
