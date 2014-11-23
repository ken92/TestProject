package com.horf.derp.testproject.game;

import android.content.Context;
import android.util.Log;

import com.horf.derp.testproject.gui.GridHandler;
import com.horf.derp.testproject.R;
import com.horf.derp.testproject.gui.TankGraphics;
import com.horf.derp.testproject.gui.WallGraphics;
import com.horf.derp.testproject.gui.BulletGraphics;

import java.util.HashMap;

/**
 * Created by Eridan on 11/14/2014.
 */
public class EntityHandler {
    private String LOG_TAG="EntityHandler";

    private GridHandler gridHandler;
    private TankGraphics playerTankGraphics;
    private TankGraphics enemyTankGraphics;
    private WallGraphics indestructibleWallGraphics;
    private WallGraphics normalWallGraphics;
    private BulletGraphics bulletGraphics;
    private TankLogic playerTankLogic;

    private Context context;

    private SQLHandler sqlHandler;

    HashMap<String, TankLogic> tanks;
    HashMap<String, WallLogic> walls;
    HashMap<String, BulletLogic> bullets;

    private static int UP;
    private static int DOWN;
    private static int LEFT;
    private static int RIGHT;
    private static int PLAYER;
    private static int ENEMY;

    public EntityHandler(GridHandler gridHandler, Context context, SQLHandler sqlHandler) {
        this.gridHandler = gridHandler;
        this.context=context;

        ENEMY=context.getResources().getInteger(R.integer.enemy);
        PLAYER=context.getResources().getInteger(R.integer.player);
        UP=context.getResources().getInteger(R.integer.up);
        LEFT=context.getResources().getInteger(R.integer.left);
        DOWN=context.getResources().getInteger(R.integer.down);
        RIGHT=context.getResources().getInteger(R.integer.right);

        tanks=new HashMap<String, TankLogic>();
        walls=new HashMap<String, WallLogic>();
        bullets=new HashMap<String, BulletLogic>();

        int default_tank_width=context.getResources().getInteger(R.integer.default_tank_width);
        int default_tank_height=context.getResources().getInteger(R.integer.default_tank_height);
        int default_wall_height=context.getResources().getInteger(R.integer.default_wall_height);
        int default_wall_width=context.getResources().getInteger(R.integer.default_wall_width);
        int default_bullet_width=context.getResources().getInteger(R.integer.default_bullet_width);
        int default_bullet_height=context.getResources().getInteger(R.integer.default_bullet_height);

        playerTankGraphics = new TankGraphics(context, PLAYER, default_tank_width, default_tank_height);
        enemyTankGraphics = new TankGraphics(context, ENEMY, default_tank_width, default_tank_height);
        //enemyTankGraphics = new TankGraphics(context, PLAYER, default_tank_width, default_tank_height);
        Log.d(LOG_TAG, "Created tank graphics");
        indestructibleWallGraphics = new WallGraphics(context, R.drawable.indestructiblewall, default_wall_width, default_wall_height);
        normalWallGraphics = new WallGraphics(context, R.drawable.destructiblewall, default_wall_width, default_wall_height);
        bulletGraphics = new BulletGraphics(context, R.drawable.bullet, default_bullet_width, default_bullet_height);
        this.sqlHandler=sqlHandler;

        playerTankLogic = new TankLogic(0,0,100,playerTankGraphics, context);
    }

    public void turnPlayerTank(int direction) {
        playerTankLogic.turn(direction);
    }
    public void movePlayerTank(int direction) {
        int tankDirection = playerTankLogic.getDirection();
        int tankX=playerTankLogic.getX();
        int tankY=playerTankLogic.getY();
        if(direction==UP) {
            if(tankDirection==UP)
                gridHandler.moveEntityAt(tankX, tankY, tankX, tankY-1);
            else if(tankDirection==DOWN)
                gridHandler.moveEntityAt(tankX, tankY, tankX, tankY+1);
            else if(tankDirection==LEFT)
                gridHandler.moveEntityAt(tankX, tankY, tankX-1, tankY);
            else if(tankDirection==RIGHT)
                gridHandler.moveEntityAt(tankX, tankY, tankX+1, tankY);
        }
        else if(direction==DOWN) {
            if(tankDirection==UP)
                gridHandler.moveEntityAt(tankX, tankY, tankX, tankY+1);
            else if(tankDirection==DOWN)
                gridHandler.moveEntityAt(tankX, tankY, tankX, tankY-1);
            else if(tankDirection==LEFT)
                gridHandler.moveEntityAt(tankX, tankY, tankX+1, tankY);
            else if(tankDirection==RIGHT)
                gridHandler.moveEntityAt(tankX, tankY, tankX-1, tankY);
        }
    }

    //if it returns a wall, add it to the GridCanvas
    public WallLogic indestructibleWallAt(int x, int y, int time) {
        String coords = makeCoords(x,y);
        if(walls==null) Log.e(LOG_TAG, "walls is null");
        if(walls.containsKey(coords)) {
            if(time!=-1)
                sqlHandler.addWall(walls.get(coords),time);
            return null;
        }
        else {
            WallLogic newWall = new WallLogic(x,y,indestructibleWallGraphics, context);
            walls.put(coords, newWall);
            if(time!=-1)
                sqlHandler.addWall(newWall, time);
            gridHandler.placeEntity(x, y, indestructibleWallGraphics);
            return newWall;
        }
    }
    //if it returns a wall, add it to the GridCanvas
    public WallLogic normalWallAt(int x, int y, int hp, int time) {
        String coords = makeCoords(x,y);
        if(walls==null) Log.e(LOG_TAG, "walls is null");
        if(walls.containsKey(coords)) {
            if(time!=-1)
                sqlHandler.addWall(walls.get(coords),time);
            return null;
        }
        else {
            WallLogic newWall = new WallLogic(x,y,hp,normalWallGraphics, context);
            walls.put(coords, newWall);
            if(time!=-1)
                sqlHandler.addWall(newWall, time);
            gridHandler.placeEntity(x, y, normalWallGraphics);
            return newWall;
        }
    }
    public TankLogic tankAt(int x, int y, String tankID, int tankHealth, int tankDirection, int time) {
        if(tanks.containsKey(tankID)) {
            TankLogic tank = tanks.get(tankID);
            if(tank.getX()!=x || tank.getY()!=y) {
                gridHandler.moveEntityAt(tank.getX(), tank.getY(), x, y);
                tank.setCoordinates(x,y);
            }
            if(tankDirection!=tank.getDirection())
                tank.turn(tankDirection);
            if(time!=-1)
                sqlHandler.addTank(tank, time);

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
                newTank = new TankLogic(x, y, tankHealth, tankDirection, enemyTankGraphics, context);
            }
            tanks.put(tankID,newTank);
            gridHandler.placeEntity(x, y, newTank);
            if(time!=-1)
                sqlHandler.addTank(newTank, time);
            return newTank;
        }
    }
    public BulletLogic bulletAt(int x, int y, int tankID, int damage, int time) {
        String coords = makeCoords(x,y);
        if(bullets.containsKey(coords)) {
            if(time!=-1)
                sqlHandler.addBullet(bullets.get(coords), time);
            return null;
        }
        else {
            BulletLogic newBullet = new BulletLogic(x, y, damage, tankID, bulletGraphics, context);
            bullets.put(coords, newBullet);
            if(time!=-1)
                sqlHandler.addBullet(newBullet, time);
            gridHandler.placeEntity(x, y, newBullet);
            return newBullet;
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
