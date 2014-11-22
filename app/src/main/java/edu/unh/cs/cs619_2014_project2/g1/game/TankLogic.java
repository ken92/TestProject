package edu.unh.cs.cs619_2014_project2.g1.game;

import edu.unh.cs.cs619_2014_project2.g1.gui.TankGraphics;

/**
 * Created by Eridan on 11/15/2014.
 */
public class TankLogic extends BreakableEntityLogic {
    private int id;
    private TankGraphics graphics;

    public TankLogic(int x, int y, int hp, TankGraphics graphics) {
        super(x, y, hp, graphics);
    }
    public TankLogic(int x, int y, int hp, int direction, TankGraphics graphics) {
        super(x, y, hp, graphics);
        setDirection(direction);
    }
    public TankLogic(int x, int y, TankGraphics graphics) {
        super(x,y,graphics);
    }
    public void setID(int id) { this.id=id; }
    public int getID() { return id; }

    @Override
    public void setDirection(int direction) {
        super.setDirection(direction);
        graphics.setDirection(direction);
    }

    public void turn(int direction) {
        graphics.turn(direction);
    }
}
