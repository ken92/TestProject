package com.horf.derp.testproject;

import android.content.Context;

import java.util.Map;

/**
 * Created by Eridan on 11/14/2014.
 */
public class EntityHandler {
    private GridHandler gridHandler;
    private TankGraphics playerTankGraphics;
    private TankGraphics enemyTankGraphics;
    private WallGraphics indestructibleWallGraphics;
    private WallGraphics normalWallGraphics;
    private TankLogic playerTankLogic;

    Map<String, TankLogic> tanks;
    Map<String, WallLogic> walls;

    private static final int UP=R.integer.up;
    private static final int DOWN=R.integer.down;
    private static final int LEFT=R.integer.left;
    private static final int RIGHT=R.integer.right;

    public EntityHandler(GridHandler gridHandler, Context context) {
        this.gridHandler = gridHandler;
        playerTankGraphics = new TankGraphics(context, R.integer.player, 80, 80);
        enemyTankGraphics = new TankGraphics(context, R.integer.enemy, 80, 80);
        indestructibleWallGraphics = new WallGraphics(context, R.drawable.indestructiblewall, 80, 80);
        normalWallGraphics = new WallGraphics(context, R.drawable.destructiblewall, 80, 80);

        playerTankLogic = new TankLogic(0,0,100,playerTankGraphics);
    }

    public void turnPlayerTank(int direction) {
        playerTankLogic.turn(direction);
    }
    public void movePlayerTank(int direction) {
        int tankDirection = playerTankLogic.getDirection();
        int tankX=playerTankLogic.getX();
        int tankY=playerTankLogic.getY();
        if(direction==UP) {
            switch(tankDirection) {
                case UP:
                    gridHandler.moveEntityAt(tankX, tankY, tankX, tankY-1);
                    break;
                case DOWN:
                    gridHandler.moveEntityAt(tankX, tankY, tankX, tankY+1);
                    break;
                case LEFT:
                    gridHandler.moveEntityAt(tankX, tankY, tankX-1, tankY);
                    break;
                case RIGHT:
                    gridHandler.moveEntityAt(tankX, tankY, tankX+1, tankY);
                    break;
                default: return;
            }
        }
        else if(direction==DOWN) {
            switch(tankDirection) {
                case UP:
                    gridHandler.moveEntityAt(tankX, tankY, tankX, tankY+1);
                    break;
                case DOWN:
                    gridHandler.moveEntityAt(tankX, tankY, tankX, tankY-1);
                    break;
                case LEFT:
                    gridHandler.moveEntityAt(tankX, tankY, tankX+1, tankY);
                    break;
                case RIGHT:
                    gridHandler.moveEntityAt(tankX, tankY, tankX-1, tankY);
                    break;
                default: return;
            }
        }
    }

    //if it returns a wall, add it to the GridCanvas
    public WallLogic indestructibleWallAt(int x, int y) {
        String coords = makeCoords(x,y);
        if(walls.containsKey(coords))
            return null;
        else {
            WallLogic newWall = new WallLogic(x,y,indestructibleWallGraphics);
            walls.put(coords, newWall);
            return newWall;
        }
    }
    //if it returns a wall, add it to the GridCanvas
    public WallLogic normalWallAt(int x, int y, int hp) {
        String coords = makeCoords(x,y);
        if(walls.containsKey(coords))
            return null;
        else {
            WallLogic newWall = new WallLogic(x,y,hp,normalWallGraphics);
            walls.put(coords, newWall);
            return newWall;
        }
    }
    public TankLogic tankAt(int x, int y, String tankID, int tankHealth, int tankDirection) {
        if(tanks.containsKey(tankID)) {
            TankLogic tank = tanks.get(tankID);
            if(tank.getX()!=x || tank.getY()!=y) {
                gridHandler.moveEntityAt(tank.getX(), tank.getY(), x, y);
                tank.setCoordinates(x,y);
            }
            if(tankDirection!=tank.getDirection())
                tank.turn(tankDirection);

            return null;
        }
        else {
            TankLogic newTank;
            if(Integer.parseInt(tankID)==playerTankLogic.getID()) {
                //newTank = new TankLogic(x, y, tankHealth, tankDirection, playerTankGraphics);
                playerTankLogic.setHP(tankHealth);
                playerTankLogic.setDirection(tankDirection);
                playerTankLogic.setCoordinates(x,y);
                newTank = playerTankLogic;
            }
            else {
                newTank = new TankLogic(x, y, tankHealth, tankDirection, enemyTankGraphics);
            }
            tanks.put(tankID,newTank);
            return newTank;
        }
    }


    private String makeCoords(int x, int y) {
        return x+","+y;
    }

    public void setPlayerTankID(long id) {
        playerTankLogic.setID(safeLongToInt(id));
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }
}
