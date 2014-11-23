package com.horf.derp.testproject.gui;

import android.content.Context;
import android.util.Log;

import com.horf.derp.testproject.R;
import com.horf.derp.testproject.gui.ConcreteMapEntityGraphics;


/**
 * Created by Eridan on 11/14/2014.
 */
public class TankGraphics extends ConcreteMapEntityGraphics {
    public static int UP;
    public static int LEFT;
    public static int RIGHT;
    public static int DOWN;
    public static int PLAYER;
    public static int ENEMY;

    private int direction=UP;
    private static int imageUp;
    private static int imageDown;
    private static int imageLeft;
    private static int imageRight;

    private int playerOrEnemy;

    private final static String LOG_TAG="TankGraphics";

    public TankGraphics(Context context, int playerOrEnemy) {
        super(context);
        setDirections(context);
        setPlayerOrEnemy(playerOrEnemy);
    }
    public TankGraphics(Context context, int playerOrEnemy, int width, int height) {
        super(context, width, height);
        setDirections(context);
        setPlayerOrEnemy(playerOrEnemy);
    }
    private void setDirections(Context context) {
        UP=context.getResources().getInteger(R.integer.up);
        LEFT=context.getResources().getInteger(R.integer.left);
        DOWN=context.getResources().getInteger(R.integer.down);
        RIGHT=context.getResources().getInteger(R.integer.right);
        PLAYER=context.getResources().getInteger(R.integer.player);
        ENEMY=context.getResources().getInteger(R.integer.enemy);
    }
    private void setPlayerOrEnemy(int playerOrEnemy) {
        if(playerOrEnemy==PLAYER) {
            imageUp=R.drawable.playertankup;
            imageDown=R.drawable.playertankdown;
            imageLeft=R.drawable.playertankleft;
            imageRight=R.drawable.playertankright;
        }
        else {
            Log.d(LOG_TAG, "playerOrEnemy: "+playerOrEnemy);
            imageUp=R.drawable.enemytankup;
            imageDown=R.drawable.enemytankdown;
            imageLeft=R.drawable.enemytankleft;
            imageRight=R.drawable.enemytankright;
        }
        setImage(imageUp);
        this.playerOrEnemy=playerOrEnemy;
    }
    public int getPlayerOrEnemy() {return playerOrEnemy;}

    public int getDirection() { return direction; }

    public void setDirection(int direction) {
        if(direction==UP) {
            setImage(imageUp);
            this.direction=UP;
        }
        else if(direction==DOWN) {
            setImage(imageDown);
            this.direction=DOWN;
        }
        else if(direction==LEFT) {
            setImage(imageLeft);
            this.direction=LEFT;
        }
        else if(direction==RIGHT) {
            setImage(imageRight);
            this.direction=RIGHT;
        }
    }

    public void turn(int direction) {
        if(this.direction==UP) {
            if(direction==LEFT) {
                setDirection(LEFT);
            }
            else if(direction==RIGHT) {
                setDirection(RIGHT);
            }
            else Log.e(LOG_TAG, "Invalid direction: "+direction);
        }
        else if(this.direction==LEFT) {
            if(direction==LEFT) {
                setDirection(DOWN);
            }
            else if(direction==RIGHT) {
                setDirection(UP);
            }
            else Log.e(LOG_TAG, "Invalid direction: "+direction);
        }
        else if(this.direction==RIGHT) {
            if(direction==LEFT) {
                setDirection(UP);
            }
            else if(direction==RIGHT) {
                setDirection(DOWN);
            }
            else Log.e(LOG_TAG, "Invalid direction: "+direction);
        }
        else if(this.direction==DOWN) {
            if(direction==LEFT) {
                setDirection(RIGHT);
            }
            else if(direction==RIGHT) {
                setDirection(LEFT);
            }
            else Log.e(LOG_TAG, "Invalid direction: "+direction);
        }
    }
}
