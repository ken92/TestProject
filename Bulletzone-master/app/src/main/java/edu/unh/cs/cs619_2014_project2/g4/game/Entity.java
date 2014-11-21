package edu.unh.cs.cs619_2014_project2.g4.game;

/**
 * Created by Tay on 11/8/2014.
 */
public abstract class Entity
{

    private int m_xPos;
    private int m_yPos;


    public Entity(int xPos, int yPos)
    {
        m_xPos = xPos;
        m_yPos = yPos;
    }


    public int getX()
    {
        return m_xPos;
    }


    public int getY()
    {
        return m_yPos;
    }

    @Override
    public String toString()
    {
        return "E";
    }
}