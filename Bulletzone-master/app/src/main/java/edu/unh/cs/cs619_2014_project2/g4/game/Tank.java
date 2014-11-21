package edu.unh.cs.cs619_2014_project2.g4.game;

/**
 * Created by Tay on 11/8/2014.
 */


public class Tank extends Health
{
    public enum Direction {
        UP    ((byte)0),
        RIGHT ((byte)2),
        DOWN  ((byte)4),
        LEFT  ((byte)6);

        private final byte index;
        Direction (byte dir) {
            index = dir;
        }
        public byte code() {
            return index;
        }
    }
    //Variables----
    private int m_id;
    private Direction m_direction;

    /**
     * Class constructor.
     * @param xPos The x position of the tank on the grid
     * @param yPos The y position of the tank on the grid
     * @param _h The current health of the tank
     * @param id The tank's ID number
     * @param dir The direction the tank is facing
     */
    public Tank(int xPos, int yPos, int _h, int id, int dir)
    {
        super(xPos, yPos, _h);
        m_id = id;

        switch (dir) {
            case 0:
                m_direction = Direction.UP;
                break;
            case 2:
                m_direction = Direction.RIGHT;
                break;
            case 4:
                m_direction = Direction.DOWN;
                break;
            case 6:
                m_direction = Direction.LEFT;
                break;
            default:
                m_direction = Direction.UP;
        }
    }

    /**
     * @return Returns the tank ID number associated with this tank.
     */
    public int getID()
    {
        return m_id;
    }

    /**
     * The direction of a tank is 0 = Up, 2 = Right, 4 = Down, and
     * 6 = Left.
     * @return Direction as an integer.
     */
    public Direction getDirection()
    {
        return m_direction;
    }

    @Override
    public String toString()
    {
        return "W";
    }


}







/*
public class Tank extends Health{

    private final byte UP = 0;
    private final byte RIGHT = 2;
    private final byte DOWN = 4;
    private final byte LEFT = 6;

    public int id;

    public Tank(int x, int y, int health) {
        super(x, y, health);
    }
}
*/