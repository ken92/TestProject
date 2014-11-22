package edu.unh.cs.cs619_2014_project2.g1.gui;

import android.graphics.Canvas;

import edu.unh.cs.cs619_2014_project2.g1.game.MapEntity;
import edu.unh.cs.cs619_2014_project2.g1.game.MapEntityLogic;

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
