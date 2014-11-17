package com.horf.derp.testproject;

import android.content.Context;
import android.util.Log;


/**
 * Created by Eridan on 11/14/2014.
 */
public class TankGraphics extends ConcreteMapEntityGraphics {
    public final static int UP=R.integer.up;
    public final static int LEFT=R.integer.left;
    public final static int RIGHT=R.integer.right;
    public final static int DOWN=R.integer.down;
    public final static int PLAYER=R.integer.player;
    public final static int ENEMY=R.integer.enemy;

    private int direction=UP;
    private static int imageUp;
    private static int imageDown;
    private static int imageLeft;
    private static int imageRight;

    private int x, y;

    private final static String LOG_TAG="TankGraphics";

    public TankGraphics(Context context, int playerOrEnemy) {
        super(context);
        setPlayerOrEnemy(playerOrEnemy);
    }
    public TankGraphics(Context context, int playerOrEnemy, int width, int height) {
        super(context, width, height);
        setPlayerOrEnemy(playerOrEnemy);
    }
    private void setPlayerOrEnemy(int playerOrEnemy) {
        if(playerOrEnemy==PLAYER) {
            imageUp=R.drawable.playertankup;
            imageDown=R.drawable.playertankdown;
            imageLeft=R.drawable.playertankleft;
            imageRight=R.drawable.playertankright;
        }
        else {
            imageUp=R.drawable.enemytankup;
            imageDown=R.drawable.enemytankdown;
            imageLeft=R.drawable.enemytankleft;
            imageRight=R.drawable.enemytankright;
        }
        setImage(imageUp);
    }

    public int getDirection() { return direction; }

    public void setDirection(int direction) {
        switch (direction) {
            case UP:
                setImage(imageUp);
                this.direction=UP;
                break;
            case DOWN:
                setImage(imageDown);
                this.direction=DOWN;
                break;
            case LEFT:
                setImage(imageLeft);
                this.direction=LEFT;
                break;
            case RIGHT:
                setImage(imageRight);
                this.direction=RIGHT;
                break;
            default: return;
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
