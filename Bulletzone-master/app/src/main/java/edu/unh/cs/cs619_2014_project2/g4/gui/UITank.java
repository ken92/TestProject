package edu.unh.cs.cs619_2014_project2.g4.gui;

import android.graphics.Canvas;

import edu.unh.cs.cs619_2014_project2.g4.game.Tank;

/**
 * Created by Tay on 11/8/2014.
 */


public class UITank extends UIEntity {
    private Tank m_tank;

    public UITank(Tank tank) {
        m_tank = tank;
    }


    @Override
    public void draw(Canvas canvas) {

    }

    @Override
      public String toString()
    {

        return "T";
    }

}
