package com.horf.derp.testproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.common.eventbus.Subscribe;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

/**
 * Created by Eridan on 11/14/2014.
 */
public class GridHandler extends Activity implements ShakeEventManager.ShakeListener {
    private static final String LOG_TAG="GridHandler";
    private static final int UP=R.integer.up;
    private static final int DOWN=R.integer.down;
    private static final int LEFT=R.integer.left;
    private static final int RIGHT=R.integer.right;

    private static CanvasGrid canvas=null;
    private EntityHandler entityHandler;
    private ShakeEventManager shakeEventManager;

    long tankId = -1;
    @RestService
    BulletZoneRestClient restClient;
    @Bean
    Poller poller;



    float x1, x2, y1, y2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(canvas==null)
            canvas=new CanvasGrid(this);
        setContentView(canvas);
        entityHandler=new EntityHandler(this);

        shakeEventManager = new ShakeEventManager();
        shakeEventManager.setListener(this);
        shakeEventManager.init(this);
    }

    public void placeEntity(int x, int y, MapEntityGraphics entity) {
        canvas.putEntity(x, y, entity);
    }

    public void moveEntityAt(int fromX, int fromY, int toX, int toY) {
        canvas.moveEntityAt(fromX, fromY, toX, toY);
    }
    public int getCanvasReadyState() {
        return canvas.getReadyState();
    }

    public CanvasGrid getCanvas() {return canvas;}


    @Override
    protected void onPause() {
        super.onPause();
        canvas.pauseThread();
        shakeEventManager.deregister();
        BusProvider.getInstance().unregister(eventHandler);
        Log.i(LOG_TAG, "onPause");
    }
    @Override
    protected void onResume() {
        super.onResume();
        canvas.resumeThread();
        shakeEventManager.register();
        BusProvider.getInstance().register(eventHandler);
        Log.i(LOG_TAG, "onResume");
    }




    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction())
        {
            // when user first touches the screen we get x and y coordinate
            case MotionEvent.ACTION_DOWN:
            {
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                //String str="  x1: "+x1+"  x2: "+x2+"  y1: "+y1+"  y2: "+y2;
                String str="";

                if (x1<x2 && ((x2-x1) > (y1-y2)) && ((x2-x1) > (y2-y1))) {
                    Toast.makeText(this, "Swipe right"+str, Toast.LENGTH_LONG).show();
                    //Log.d(LOG_TAG, "Swipe right"+str);
                    entityHandler.turnPlayerTank(RIGHT);
                    restClient.turn(tankId, (byte)2);
                }

                else if (x1>x2 && (((x1-x2) > (y1-y2))) && ((x1-x2) > (y2-y1))) {
                    Toast.makeText(this, "Swipe left"+str, Toast.LENGTH_SHORT).show();
                    //Log.d(LOG_TAG, "Swipe left" + str);
                    entityHandler.turnPlayerTank(LEFT);
                    restClient.turn(tankId, (byte)6);
                }

                else if (y1<y2 && (((y2-y1) > (x1-x2))) && ((y2-y1) > (x2-x1))) {
                    Toast.makeText(this, "Swipe down"+str, Toast.LENGTH_SHORT).show();
                    //Log.d(LOG_TAG, "Swipe down"+str);
                    entityHandler.movePlayerTank(DOWN);
                    restClient.move(tankId, (byte)4);
                }

                else if (y1>y2 && (((y1-y2) > (x1-x2))) && ((y1-y2) > (x2-x1))) {
                    Toast.makeText(this, "Swipe up"+str, Toast.LENGTH_SHORT).show();
                    //Log.d(LOG_TAG, "Swipe up" + str);
                    entityHandler.movePlayerTank(UP);
                    restClient.move(tankId, (byte)0);
                }
                break;
            }
        }
        return false;
    }

    @Override
    public void onShake() {
        Toast.makeText(this, "BANG!", Toast.LENGTH_SHORT).show();
        restClient.fire(tankId);
    }





























    private Object eventHandler = new Object()
    {
        @Subscribe
        public void onUpdateGrid(GridUpdateEvent event) {
            updateGrid(event);
        }
    };


    @UiThread
    void updateGrid(GridUpdateEvent event){
        int [][] grid = event.getGrid();

        StringBuilder builder = new StringBuilder();

        builder.append("TimeStamp=").append(event.getTimestamp()).append("\n");
        for (int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                //~~PARSE THE NUMBERS HERE
                //  [0]     Empty field entity
                //  [1000]  Indestructible wall
                //  [1000 - 2000]   Destructible wall. HP = number - 1000
                //  [2,000,000 - 3,000,000] Bullet. If value is 2ABCDEF, then the ID of the tank who fired the bullet is ABC and the damage of the bullet is DEF
                //  [10,000,000 - 20,000,000]   Tank. If the value is 1ABCDEFG, then the ID of the tank is ABC, it has DEF life and its direction is G.
                //                                 (E.G., value = 12220071, tankId=222, life=007, direction=2) Directions: UP=0, RIGHT=2, DOWN=4, LEFT=6

                //bullet class doesn't exist yet so I guess it'll have BulletGraphics and BulletLogic even though that still sounds stupid to me.
                //  it's not breakable so have it extend ConcreteMapEntityGraphics/Logic. the actual image is "bullet.png," predictably.

                //the idea is that the entities are handled via EntityHandler so have that thing create the actual bullets and walls.
                //  there's code in there for creating a test tank so copy dat shit and yeah that's how it works. tried to make it as simple as possible.

                //the walls are "indestructiblewall.png" and "destructiblewall.png" why am I putting that here the files are right there

                //I lost my train of thought so yeah if you have any questions let me know






                builder.append(grid[i][j]).append(":");
            }
            builder.append("\n");
        }
        //TextView textViewGrid = (TextView) findViewById(R.id.textViewTankId);
        //textViewGrid.setText(builder.toString());
        Log.d(LOG_TAG, builder.toString());
    }

    /*@UiThread
    void updateTankId(){
        //textViewTankId.setText("TankId=" + tankId);
    }*/

    @Background
    void join(){
        try{
            tankId = restClient.join().getResult();
            Log.i(LOG_TAG, "Tank ID: "+tankId);
            //updateTankId();
            poller.doPoll();
            //poller.pollOnce();
        } catch (Exception e){
            e.printStackTrace();
        }
    }




}
