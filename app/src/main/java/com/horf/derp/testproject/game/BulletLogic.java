package com.horf.derp.testproject.game;

import android.content.Context;

import com.horf.derp.testproject.gui.MapEntityGraphics;

/**
 * Created by Eridan on 11/22/2014.
 */
public class BulletLogic extends ConcreteMapEntityLogic {
    private int damage=0;
    private int tankID=0;

    public BulletLogic(int x, int y, int damage, int tankID, MapEntityGraphics graphics, Context context) {
        super(x, y, graphics, context);
        this.damage=damage;
        this.tankID=tankID;
    }

    public int getDamage() {return damage;}
    public void setDamage(int damage) {this.damage=damage;}
    public int getTankID() {return tankID;}
    public void setTankID(int tankID) {this.tankID=tankID;}
}
