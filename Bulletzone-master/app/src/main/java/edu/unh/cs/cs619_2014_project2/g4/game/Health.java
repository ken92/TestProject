package edu.unh.cs.cs619_2014_project2.g4.game;

/**
 * Created by Tay on 11/8/2014.
 */

public abstract class Health extends Entity {

    int health;

    public Health(int x, int y, int health){
        super(x, y);
        this.health = health;
    }


    public int getHealth()
    {
        return health;
    }

    @Override
    public String toString()
    {
        return "H";
    }
}