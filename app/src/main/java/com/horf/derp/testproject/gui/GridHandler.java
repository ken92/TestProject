package com.horf.derp.testproject.gui;

import android.util.Log;

import com.horf.derp.testproject.game.GridParser;
import com.horf.derp.testproject.game.MapEntityLogic;
import com.horf.derp.testproject.R;
import com.horf.derp.testproject.game.SQLHandler;
import com.horf.derp.testproject.ServerEventHandler;
import com.horf.derp.testproject.game.ShakeEventManager;
import com.horf.derp.testproject.game.EntityHandler;

import org.json.JSONObject;

/**
 * Created by Eridan on 11/14/2014.
 */
public class GridHandler {
    private static final String LOG_TAG="GridHandler";
    private static int UP;
    private static int DOWN;
    private static int LEFT;
    private static int RIGHT;
    private static int ENEMY;
    private static int PLAYER;
    private static int WIDTH;
    private static int HEIGHT;

    private static CanvasGrid canvas=null;
    private EntityHandler entityHandler;
    private GridParser gridParser;
    private ShakeEventManager shakeEventManager;
    private SQLHandler sqlHandler;
    private ServerEventHandler eventHandler;

    float x1, x2, y1, y2;

    public GridHandler(ServerEventHandler eventHandler) {
        UP=eventHandler.getResources().getInteger(R.integer.up);
        DOWN=eventHandler.getResources().getInteger(R.integer.down);
        LEFT=eventHandler.getResources().getInteger(R.integer.left);
        RIGHT=eventHandler.getResources().getInteger(R.integer.right);
        ENEMY=eventHandler.getResources().getInteger(R.integer.enemy);
        PLAYER=eventHandler.getResources().getInteger(R.integer.player);
        HEIGHT=eventHandler.getResources().getInteger(R.integer.grid_height);
        WIDTH=eventHandler.getResources().getInteger(R.integer.grid_width);

        if(canvas==null)
            canvas=new CanvasGrid(eventHandler);
        //setContentView(canvas);
        sqlHandler=new SQLHandler(eventHandler);
        entityHandler=new EntityHandler(this, eventHandler, sqlHandler);
        gridParser=new GridParser(WIDTH, HEIGHT, this, entityHandler);
        this.eventHandler=eventHandler;

        /*shakeEventManager = new ShakeEventManager();
        shakeEventManager.setListener(this);
        shakeEventManager.init(this);*/
    }

    public void placeEntity(int x, int y, MapEntityGraphics entity) {
        canvas.putEntity(x, y, entity);
    }
    public void placeEntity(int x, int y, MapEntityLogic entity) {
        canvas.putEntity(x, y, entity.getGraphics());
    }

    public void moveEntityAt(int fromX, int fromY, int toX, int toY) {
        canvas.moveEntityAt(fromX, fromY, toX, toY);
    }
    public int getCanvasReadyState() {
        return canvas.getReadyState();
    }

    public CanvasGrid getCanvas() {return canvas;}


    protected void onPause() {
        canvas.pauseThread();
        //shakeEventManager.deregister();
        //BusProvider.getInstance().unregister(eventHandler);
        //Log.i(LOG_TAG, "onPause");
    }
    protected void onResume() {
        canvas.resumeThread();
        //shakeEventManager.register();
        //BusProvider.getInstance().register(eventHandler);
        //Log.i(LOG_TAG, "onResume");
    }

    public void updateGrid(int[][] grid){
        gridParser.parseGrid(grid);
    }

    public void setPlayerTankID(long id) {
        entityHandler.setPlayerTankID(id);
    }

    public void restoreTime(int time) {
        canvas.clearGrid();
        int width=WIDTH;
        int height=HEIGHT;
        JSONObject[][] grid = sqlHandler.getTimestamp(time);
        for(int i=0; i<width; i++) {
            for(int j=0; j<height; j++) {
                if(grid[i][j]!=null) {
                    try {
                        String object = (String) grid[i][j].get("object");
                        int x = i;
                        int y = j;
                        if (object.equals("tank")) {
                            String tankID = Integer.toString((Integer)grid[i][j].get("tankID"));
                            int direction = (Integer) grid[i][j].get("direction");
                            //int player=(Integer)grid[i][j].get("player");
                            entityHandler.tankAt(x, y, tankID, eventHandler.getResources().getInteger(R.integer.default_tank_hp), direction, -1);
                        } else if (object.equals("bullet")) {
                            int tankID = (Integer) grid[i][j].get("tankID");
                            entityHandler.bulletAt(x,y,tankID,10,-1);
                        }
                        else {
                            int temp = (Integer) grid[i][j].get("indestructible");
                            if(temp==eventHandler.getResources().getInteger(R.integer.wall_indestructible)) entityHandler.indestructibleWallAt(x, y, -1);
                            else entityHandler.normalWallAt(x, y, 10, -1);
                        }
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "Error while restoring timestamp");
                        e.printStackTrace();
                    }
                }
                else canvas.putEntity(i,j,null);
            }
        }
    }

    public void createTestSQLData() {
        sqlHandler.remakeDatabase();

        //F
        sqlHandler.addWall(-1, 1, 1, 0);
        sqlHandler.addWall(-1, 1, 2, 0);
        sqlHandler.addWall(-1, 1, 3, 0);
        sqlHandler.addWall(-1, 1, 4, 0);
        sqlHandler.addWall(-1, 1, 5, 0);
        sqlHandler.addWall(-1, 2, 1, 0);
        sqlHandler.addWall(-1, 2, 3, 0);
        sqlHandler.addWall(-1, 3, 1, 0);

        //U
        sqlHandler.addWall(10, 4, 2, 0);
        sqlHandler.addWall(10, 4, 3, 0);
        sqlHandler.addWall(10, 4, 4, 0);
        sqlHandler.addWall(10, 4, 5, 0);
        sqlHandler.addWall(10, 5, 5, 0);
        sqlHandler.addWall(10, 6, 2, 0);
        sqlHandler.addWall(10, 6, 3, 0);
        sqlHandler.addWall(10, 6, 4, 0);
        sqlHandler.addWall(10, 6, 5, 0);

        //C
        sqlHandler.addWall(-1, 8, 2, 0);
        sqlHandler.addWall(-1, 8, 3, 0);
        sqlHandler.addWall(-1, 8, 4, 0);
        sqlHandler.addWall(-1, 8, 5, 0);
        sqlHandler.addWall(-1, 9, 2, 0);
        sqlHandler.addWall(-1, 9, 5, 0);
        sqlHandler.addWall(-1, 10, 2, 0);
        sqlHandler.addWall(-1, 10, 5, 0);

        //K
        sqlHandler.addWall(10, 12, 1, 0);
        sqlHandler.addWall(10, 12, 2, 0);
        sqlHandler.addWall(10, 12, 3, 0);
        sqlHandler.addWall(10, 12, 4, 0);
        sqlHandler.addWall(10, 12, 5, 0);
        sqlHandler.addWall(10, 13, 3, 0);
        sqlHandler.addWall(10, 13, 4, 0);
        sqlHandler.addWall(10, 14, 2, 0);
        sqlHandler.addWall(10, 14, 5, 0);

        sqlHandler.addTank(10, UP, ENEMY, 3, 9, 0);
        sqlHandler.addTank(11, DOWN, PLAYER, 6, 9, 0);

        sqlHandler.addBullet(10, 2, 11, 0);
        sqlHandler.addBullet(10, 3, 12, 0);
        sqlHandler.addBullet(10, 4, 12, 0);
        sqlHandler.addBullet(11, 5, 12, 0);
        sqlHandler.addBullet(11, 6, 12, 0);
        sqlHandler.addBullet(11, 7, 11, 0);
    }
}
