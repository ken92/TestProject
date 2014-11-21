package edu.unh.cs.cs619_2014_project2.g4.game;

/**
 * Created by Tay on 11/8/2014.
 */

/**
 * Created by Tay on 11/8/2014.
 */
public class Wall extends Health{

    public Wall(int x, int y, int health){
        super(x, y, health);
    }

    @Override
    public String toString()
    {
        return "W";
    }


}
