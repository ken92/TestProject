package com.horf.derp.testproject.game;

import android.content.Context;

import com.horf.derp.testproject.R;
import com.horf.derp.testproject.game.BreakableEntityLogic;
import com.horf.derp.testproject.gui.MapEntityGraphics;

/**
 * Created by Eridan on 11/15/2014.
 */
public class WallLogic extends BreakableEntityLogic {
    private int hp=-1; //if hp is -1, it's indestructible

    private int ALIVE;
    private int DEAD;

    public WallLogic(int x, int y, MapEntityGraphics graphics, Context context) {
        super(x, y, graphics, context);
        ALIVE=context.getResources().getInteger(R.integer.state_alive);
        DEAD=context.getResources().getInteger(R.integer.state_dead);
    }
    public WallLogic(int x, int y, int hp, MapEntityGraphics graphics, Context context) {
        super(x, y, hp, graphics, context);
    }

    @Override
    public int getState() {
        if(hp==-1) return ALIVE;
        else return super.getState();
    }
}
