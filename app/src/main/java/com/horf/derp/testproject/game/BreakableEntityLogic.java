package com.horf.derp.testproject.game;

import android.content.Context;

import com.horf.derp.testproject.gui.MapEntityGraphics;
import com.horf.derp.testproject.R;

/**
 * Created by Eridan on 11/15/2014.
 */
public class BreakableEntityLogic extends ConcreteMapEntityLogic {
    private static int DEAD;
    private static int ALIVE;

    private int HP;
    private int state;

    public BreakableEntityLogic(int x, int y, MapEntityGraphics graphics, Context context) {
        super(x, y, graphics, context);
        setStates(context);
    }
    public BreakableEntityLogic(int x, int y, int hp, MapEntityGraphics graphics, Context context) {
        super(x, y, graphics, context);
        HP=hp;
        setStates(context);
    }
    private void setStates(Context context) {
        DEAD=context.getResources().getInteger(R.integer.state_dead);
        ALIVE=context.getResources().getInteger(R.integer.state_alive);
        HP=context.getResources().getInteger(R.integer.default_tank_hp);
        state=ALIVE;
    }


    //returns true if it's still alive
    public boolean setHP(int hp) {
        if(hp<=0) {
            HP = 0;
            setState(DEAD);
            return false;
        }
        else {
            HP = hp;
            return true;
        }
    }

    //returns true if it's still alive
    public boolean subHealth(int healthLost) {
        return setHP(HP-healthLost);
    }


    private void setState(int state) {
        if(state==ALIVE || state==DEAD) this.state=state;
    }
    public int getState() { return state; }
    public int getHP() {return HP;}
}
