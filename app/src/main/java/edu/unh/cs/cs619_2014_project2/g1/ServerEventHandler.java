package edu.unh.cs.cs619_2014_project2.g1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.common.eventbus.Subscribe;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import edu.unh.cs.cs619_2014_project2.g1.game.GridHandler;
import edu.unh.cs.cs619_2014_project2.g1.game.ShakeEventManager;
import edu.unh.cs.cs619_2014_project2.g1.server.BulletZoneRestClient;
import edu.unh.cs.cs619_2014_project2.g1.server.BusProvider;
import edu.unh.cs.cs619_2014_project2.g1.server.GridUpdateEvent;
import edu.unh.cs.cs619_2014_project2.g1.server.Poller;

/**
 * Created by Eridan on 11/20/2014.
 */
@EActivity(R.layout.activity_servereventhandler)
public class ServerEventHandler extends Activity implements ShakeEventManager.ShakeListener {
    private static final String LOG_TAG="ServerEventHandler";
    private static final int UP=R.integer.up;
    private static final int DOWN=R.integer.down;
    private static final int LEFT=R.integer.left;
    private static final int RIGHT=R.integer.right;

    private GridHandler gridHandler;
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
        gridHandler = new GridHandler(this);
        setContentView(gridHandler.getCanvas());

        shakeEventManager = new ShakeEventManager();
        shakeEventManager.setListener(this);
        shakeEventManager.init(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
        shakeEventManager.deregister();
        BusProvider.getInstance().unregister(eventHandler);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
        shakeEventManager.register();
        BusProvider.getInstance().register(eventHandler);
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

    @Background
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
    }




}
