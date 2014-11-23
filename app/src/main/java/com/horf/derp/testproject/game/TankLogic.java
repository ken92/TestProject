package com.horf.derp.testproject.game;

import android.content.Context;
import android.util.Log;

import com.horf.derp.testproject.game.BreakableEntityLogic;
import com.horf.derp.testproject.gui.TankGraphics;

/**
 * Created by Eridan on 11/15/2014.
 */
public class TankLogic extends BreakableEntityLogic {
    private static final String LOG_TAG = "TankLogic";

    private int id;
    private TankGraphics graphics;

    public TankLogic(int x, int y, int hp, TankGraphics graphics, Context context) {
        super(x, y, hp, graphics, context);
        this.graphics=graphics;
    }
    public TankLogic(int x, int y, int hp, int direction, TankGraphics graphics, Context context) {
        super(x, y, hp, graphics, context);
        this.graphics=graphics;
        setDirection(direction);
    }
    public TankLogic(int x, int y, TankGraphics graphics, Context context) {
        super(x,y,graphics, context);
        this.graphics=graphics;
    }
    public void setID(int id) { this.id=id; }
    public int getID() { return id; }

    public int getPlayerOrEnemy() {
        return graphics.getPlayerOrEnemy();
    }

    @Override
    public void setDirection(int direction) {
        super.setDirection(direction);
        if(graphics==null) Log.e(LOG_TAG, "graphics is null");
        graphics.setDirection(direction);
    }

    public void turn(int direction) {
        graphics.turn(direction);
    }
}
