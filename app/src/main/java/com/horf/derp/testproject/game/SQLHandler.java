package com.horf.derp.testproject.game;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.horf.derp.testproject.R;

import org.json.JSONObject;

public class SQLHandler extends SQLiteOpenHelper {
    private static final String LOG_TAG="SQLHandler";

    private static int UP;
    private static int DOWN;
    private static int LEFT;
    private static int RIGHT;
    private static int ENEMY;
    private static int PLAYER;
    private static int WIDTH;
    private static int HEIGHT;
    private static int NORMAL_WALL;
    private static int INDESTRUCT_WALL;

    private static int DATABASE_VERSION=1;
    private static String DATABASE_NAME;
    private static final String TANK_TABLE="tank";
    private static final String WALL_TABLE="wall";
    private static final String BULLET_TABLE="bullet";

    private static final int TANK=1;
    private static final int BULLET=2;
    private static final int WALL=3;

    private Context context;

    public SQLHandler(Context context) {
        super(context, context.getString(R.string.database_name), null, DATABASE_VERSION);
        this.context=context;
        DATABASE_NAME=context.getString(R.string.database_name);

        //context.deleteDatabase(DATABASE_NAME);

        //DATABASE_VERSION=context.getResources().getInteger(R.integer.database_version);
        INDESTRUCT_WALL=context.getResources().getInteger(R.integer.wall_indestructible);
        NORMAL_WALL=context.getResources().getInteger(R.integer.wall_normal);
        HEIGHT=context.getResources().getInteger(R.integer.grid_height);
        WIDTH=context.getResources().getInteger(R.integer.grid_width);
        UP=context.getResources().getInteger(R.integer.up);
        DOWN=context.getResources().getInteger(R.integer.down);
        LEFT=context.getResources().getInteger(R.integer.left);
        RIGHT=context.getResources().getInteger(R.integer.right);
        ENEMY=context.getResources().getInteger(R.integer.enemy);
        PLAYER=context.getResources().getInteger(R.integer.player);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_TANK_TABLE = "CREATE TABLE IF NOT EXISTS "+TANK_TABLE+" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tankID INTEGER, "+
                "timestamp INTEGER, " +
                "direction INTEGER, " +
                "player INTEGER, "+ //0=no, 1=yes
                "x INTEGER, "+
                "y INTEGER"+
                ")";

        String CREATE_BULLET_TABLE = "CREATE TABLE IF NOT EXISTS "+BULLET_TABLE+" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                //"FOREIGN KEY(tankID) REFERENCES tank(tankID), " +
                "tankID INTEGER, " +
                "x INTEGER, " +
                "y INTEGER, " +
                "timestamp INTEGER" +
                ")";

        String CREATE_WALL_TABLE = "CREATE TABLE IF NOT EXISTS "+WALL_TABLE+" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "x INTEGER, " +
                "y INTEGER, " +
                "timestamp INTEGER, " +
                "indestructible INTEGER" + //0=no, 1=yes
                ")";

        String CREATE_REPLAY_TABLE = "";

        database.execSQL(CREATE_TANK_TABLE);
        database.execSQL(CREATE_BULLET_TABLE);
        database.execSQL(CREATE_WALL_TABLE);
    }

    public void remakeDatabase() {
        onUpgrade(this.getWritableDatabase(),1,1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS tank");
        database.execSQL("DROP TABLE IF EXISTS bullet");
        database.execSQL("DROP TABLE IF EXISTS wall");
        onCreate(database);
    }

    public void addTank(TankLogic tank, int timestamp) {
        int x=tank.getX();
        int y=tank.getY();
        int id=tank.getID();
        int direction=tank.getDirection();
        int playerOrEnemy=tank.getPlayerOrEnemy();
        addTank(id, direction, playerOrEnemy, x, y, timestamp);
    }
    public void addTank(int id, int direction, int playerOrEnemy, int x, int y, int timestamp) {
        SQLiteDatabase database = this.getWritableDatabase();
        int player;
        if(playerOrEnemy==PLAYER) player=1;
        else player=0;
        ContentValues values = new ContentValues();
        values.put("x",x);
        values.put("y",y);
        values.put("tankID",id);
        values.put("timestamp",timestamp);
        values.put("player",player);
        values.put("direction",direction);
        database.insert(TANK_TABLE, null, values);
        database.close();
    }

    public void addWall(WallLogic wall, int timestamp) {
        int x=wall.getX();
        int y=wall.getY();
        addWall(wall.getHP(), x, y, timestamp);
    }
    public void addWall(int hp, int x, int y, int timestamp) {
        SQLiteDatabase database = this.getWritableDatabase();
        int indestructible;
        if(hp == -1) indestructible=1;
        else indestructible=0;

        ContentValues values = new ContentValues();
        values.put("x",x);
        values.put("y",y);
        values.put("timestamp",timestamp);
        values.put("indestructible",indestructible);

        database.insert(WALL_TABLE, null, values);
        database.close();
    }

    public void addBullet(BulletLogic bullet, int timestamp) {
        int x=bullet.getX();
        int y=bullet.getY();
        int tankID=bullet.getTankID();
        addBullet(tankID, x, y, timestamp);
    }
    public void addBullet(int tankID, int x, int y, int timestamp) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("x",x);
        values.put("y",y);
        values.put("timestamp",timestamp);
        values.put("tankID",tankID);

        database.insert(BULLET_TABLE, null, values);
        database.close();
    }

    public JSONObject[][] getTimestamp(int timestamp) {
        int width = WIDTH;
        int height = HEIGHT;
        JSONObject[][] grid = new JSONObject[width][height];
        int x=0;
        int y=0;
        SQLiteDatabase database = this.getReadableDatabase();

        String timestampStr = Integer.toString(timestamp);
        String[] args={timestampStr};
        String[] tankColumns={"tankID", "direction", "player", "x", "y"};
        String[] bulletColumns={"tankID", "x", "y"};
        String[] wallColumns={"indestructible", "x", "y"};

        Cursor cursor = database.query(TANK_TABLE, //table
                tankColumns,        //column names
                " timestamp=? ",    //selections
                args,               //selection args
                null,               //group by
                null,               //having
                null,               //order by
                null);              //limit
        //Log.d(LOG_TAG, "Add tank to JSON");
        grid = addCursorToJSON(grid, cursor, TANK);

        cursor = database.query(BULLET_TABLE, //table
                bulletColumns,        //column names
                " timestamp=? ",    //selections
                args,               //selection args
                null,               //group by
                null,               //having
                null,               //order by
                null);              //limit
        //Log.d(LOG_TAG, "Add bullet to JSON");
        grid = addCursorToJSON(grid, cursor, BULLET);

        cursor = database.query(WALL_TABLE, //table
                wallColumns,        //column names
                " timestamp=? ",    //selections
                args,               //selection args
                null,               //group by
                null,               //having
                null,               //order by
                null);              //limit
        //Log.d(LOG_TAG, "Add wall to JSON");
        grid = addCursorToJSON(grid, cursor, WALL);

        database.close();
        return grid;
    }

    private JSONObject[][] addCursorToJSON(JSONObject[][] json, Cursor cursor, int method) {
        if(cursor!=null) {
            if(cursor.moveToFirst()){
                if(method==TANK) json=tankCursorMethod(json, cursor);
                else if(method==BULLET) json=bulletCursorMethod(json, cursor);
                else json=wallCursorMethod(json, cursor);
                while(cursor.moveToNext()) {
                    if(method==TANK) json=tankCursorMethod(json, cursor);
                    else if(method==BULLET) json=bulletCursorMethod(json, cursor);
                    else json=wallCursorMethod(json, cursor);
                }
            }
        }
        return json;
    }
    private JSONObject[][] tankCursorMethod(JSONObject[][] grid, Cursor cursor) {
        //Log.d(LOG_TAG, "tankCursor length: "+cursor.getCount()+"  ID: "+cursor.getString(0)+"  dir: "+cursor.getString(1)+"  player: "+
         //       cursor.getString(2)+"  x: "+cursor.getString(3)+"  y: "+cursor.getString(4));
        //String[] tankColumns={"tankID", "direction", "player", "x", "y"};
        JSONObject json = new JSONObject();
        try {
            json.put("object", "tank");
            json.put("tankID", Integer.parseInt(cursor.getString(0)));
            json.put("direction", Integer.parseInt(cursor.getString(1)));
            json.put("player", Integer.parseInt(cursor.getString(2)));
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Could not add tank to JSON grid");
        }
        int x = Integer.parseInt(cursor.getString(3));
        int y = Integer.parseInt(cursor.getString(4));
        grid[x][y]=json;
        return grid;
    }
    private JSONObject[][] bulletCursorMethod(JSONObject[][] grid, Cursor cursor) {
        //Log.d(LOG_TAG, "bulletCursor");
        //String[] bulletColumns={"tankID", "x", "y"};
        JSONObject json = new JSONObject();
        try {
            json.put("object", "bullet");
            json.put("tankID", Integer.parseInt(cursor.getString(0)));
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Could not add bullet to JSON grid");
        }
        int x = Integer.parseInt(cursor.getString(1));
        int y = Integer.parseInt(cursor.getString(2));
        grid[x][y]=json;
        return grid;
    }
    private JSONObject[][] wallCursorMethod(JSONObject[][] grid, Cursor cursor) {
        //Log.d(LOG_TAG, "wallCursor");
        //String[] wallColumns={"indestructible", "x", "y"};
        JSONObject json = new JSONObject();
        try {
            json.put("object", "wall");
            json.put("indestructible", Integer.parseInt(cursor.getString(0)));
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Could not add wall to JSON grid");
        }
        int x = Integer.parseInt(cursor.getString(1));
        int y = Integer.parseInt(cursor.getString(2));
        grid[x][y]=json;
        return grid;
    }

    private JSONObject tankToJSON(int id, int direction, int player, int x, int y) {
        JSONObject json=new JSONObject();
        int playerOrEnemy;
        if(player==1) playerOrEnemy=PLAYER;
        else playerOrEnemy=ENEMY;

        try {
            json.put("id", id);
            json.put("direction", direction);
            json.put("player", playerOrEnemy);
            json.put("x",x);
            json.put("y",y);
        }
        catch(Exception e) {
            Log.e(LOG_TAG, "Could not create tank JSON");
            return null;
        }
        return json;
    }

    private JSONObject bulletToJSON(int tankID, int x, int y) {
        JSONObject json=new JSONObject();
        try {
            json.put("tankID", tankID);
            json.put("x",x);
            json.put("y",y);
        }
        catch(Exception e) {
            Log.e(LOG_TAG, "Could not create bullet JSON");
            return null;
        }
        return json;
    }

    private JSONObject wallToJSON(int indestructible, int x, int y) {
        JSONObject json=new JSONObject();
        int wallType;
        if(indestructible==1) wallType=INDESTRUCT_WALL;
        else wallType=NORMAL_WALL;

        try {
            json.put("wallType", wallType);
            json.put("x",x);
            json.put("y",y);
        }
        catch(Exception e) {
            Log.e(LOG_TAG, "Could not create bullet JSON");
            return null;
        }
        return json;
    }
}
