package edu.unh.cs.cs619_2014_project2.g1.game;

import edu.unh.cs.cs619_2014_project2.g1.R;
import edu.unh.cs.cs619_2014_project2.g1.gui.MapEntityGraphics;

/**
 * Created by Eridan on 11/15/2014.
 */
public class WallLogic extends BreakableEntityLogic {
    private int hp=-1; //if hp is -1, it's indestructible

    private int ALIVE = R.integer.state_alive;
    private int DEAD = R.integer.state_dead;

    public WallLogic(int x, int y, MapEntityGraphics graphics) {
        super(x, y, graphics);
    }
    public WallLogic(int x, int y, int hp, MapEntityGraphics graphics) {
        super(x, y, hp, graphics);
    }

    @Override
    public int getState() {
        if(hp==-1) return ALIVE;
        else return super.getState();
    }
}
