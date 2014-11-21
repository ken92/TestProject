package edu.unh.cs.cs619_2014_project2.g4.game;

/**
 * Created by Tay on 11/8/2014.
 */

/**
 * Created by Tay on 11/8/2014.
 */
public class Bullet extends Entity
{

    private int m_damage;
    private int m_tank;


    public Bullet(int xPos, int yPos, int damage, int tankID)
    {
        super(xPos, yPos);
        m_damage = damage;
        m_tank = tankID;
    }


    public int getTank()
    {
        return m_tank;
    }


    public int getDamage()
    {
        return m_damage;
    }


    @Override
    public String toString()
    {
      return "B";
    }
}