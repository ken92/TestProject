package com.horf.derp.testproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Toast;

import com.horf.derp.testproject.game.ShakeEventManager;
import com.horf.derp.testproject.gui.GridHandler;

/**
 * Created by Eridan on 11/20/2014.
 */
//@EActivity(R.layout.activity_servereventhandler)
public class ServerEventHandler extends Activity {//implements ShakeEventManager.ShakeListener {
    private static final String LOG_TAG="ServerEventHandler";
    private static int UP;
    private static int DOWN;
    private static int LEFT;
    private static int RIGHT;

    private GridHandler gridHandler;
    private ShakeEventManager shakeEventManager;

    long tankId = -1;
    /*@RestService
    BulletZoneRestClient restClient;
    @Bean
    Poller poller;*/

    float x1, x2, y1, y2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        UP=this.getResources().getInteger(R.integer.up);
        DOWN=this.getResources().getInteger(R.integer.down);
        LEFT=this.getResources().getInteger(R.integer.left);
        RIGHT=this.getResources().getInteger(R.integer.right);

        gridHandler = new GridHandler(this);
        setContentView(gridHandler.getCanvas());

        shakeEventManager = new ShakeEventManager();
        //shakeEventManager.setListener(this);
        shakeEventManager.init(this);

        //gridHandler.createTestSQLData();
        gridHandler.restoreTime(0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
        shakeEventManager.deregister();
       //BusProvider.getInstance().unregister(eventHandler);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
        shakeEventManager.register();
        //BusProvider.getInstance().register(eventHandler);
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
                    Toast.makeText(this, "Swipe right" + str, Toast.LENGTH_LONG).show();
                    //Log.d(LOG_TAG, "Swipe right"+str);
                    //entityHandler.turnPlayerTank(RIGHT);
                    //restClient.turn(tankId, (byte)RIGHT);
                }

                else if (x1>x2 && (((x1-x2) > (y1-y2))) && ((x1-x2) > (y2-y1))) {
                    Toast.makeText(this, "Swipe left"+str, Toast.LENGTH_SHORT).show();
                    //Log.d(LOG_TAG, "Swipe left" + str);
                    //entityHandler.turnPlayerTank(LEFT);
                    //restClient.turn(tankId, (byte)LEFT);
                }

                else if (y1<y2 && (((y2-y1) > (x1-x2))) && ((y2-y1) > (x2-x1))) {
                    Toast.makeText(this, "Swipe down"+str, Toast.LENGTH_SHORT).show();
                    //Log.d(LOG_TAG, "Swipe down"+str);
                    //entityHandler.movePlayerTank(DOWN);
                    //restClient.move(tankId, (byte)DOWN);
                }

                else if (y1>y2 && (((y1-y2) > (x1-x2))) && ((y1-y2) > (x2-x1))) {
                    Toast.makeText(this, "Swipe up"+str, Toast.LENGTH_SHORT).show();
                    //Log.d(LOG_TAG, "Swipe up" + str);
                    //entityHandler.movePlayerTank(UP);
                    //restClient.move(tankId, (byte)UP);
                }
                break;
            }
        }
        return false;
    }

    /*@Background
    @Override
    public void onShake() {
    //    Toast.makeText(this, "BANG!", Toast.LENGTH_SHORT).show();
        //if(tankId!=-1)
         //   restClient.fire(tankId);
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
        gridHandler.updateGrid(grid);
    }

    @Background
    void join(){
        try{
            //tankId = restClient.join().getResult();
            gridHandler.setPlayerTankID(tankId);
            Log.i(LOG_TAG, "Tank ID: "+tankId);
            //updateTankId();
            //poller.doPoll();
            //poller.pollOnce();
        } catch (Exception e){
            e.printStackTrace();
        }
    }*/




}
