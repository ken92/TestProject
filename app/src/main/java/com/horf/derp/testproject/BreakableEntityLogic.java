package com.horf.derp.testproject;

/**
 * Created by Eridan on 11/15/2014.
 */
public class BreakableEntityLogic extends ConcreteMapEntityLogic {
    private static final int DEAD=R.integer.state_dead;
    private static final int ALIVE=R.integer.state_alive;

    private int HP=R.integer.default_tank_hp;
    private int state=ALIVE;

    public BreakableEntityLogic(int x, int y) {
        super(x, y);
    }
    public BreakableEntityLogic(int x, int y, int hp) {
        super(x, y);
        HP=hp;
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
