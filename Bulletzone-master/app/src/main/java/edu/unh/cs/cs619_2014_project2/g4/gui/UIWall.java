package edu.unh.cs.cs619_2014_project2.g4.gui;

import android.graphics.Canvas;

import edu.unh.cs.cs619_2014_project2.g4.game.IWall;
import edu.unh.cs.cs619_2014_project2.g4.game.Wall;

/**
 * Created by Tay on 11/8/2014.
 */
public class UIWall extends UIEntity {
    private Wall wall;
    private IWall iWall;

    public UIWall(Wall wall) {
        this.wall = wall;
    }
    public UIWall(IWall iwall) {
        this.iWall = iWall;
    }


    @Override
    public void draw(Canvas canvas) {

    }
    @Override
    public String toString()
    {
        return "W";
    }

}

