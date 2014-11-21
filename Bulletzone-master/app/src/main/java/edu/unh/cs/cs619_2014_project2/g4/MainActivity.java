/**********************************************************************************************
 Main Activity
 **********************************************************************************************/
package edu.unh.cs.cs619_2014_project2.g4;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.common.eventbus.Subscribe;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import edu.unh.cs.cs619_2014_project2.g4.game.Entity;
import edu.unh.cs.cs619_2014_project2.g4.game.EntityFactory;
import edu.unh.cs.cs619_2014_project2.g4.game.Tank;
import edu.unh.cs.cs619_2014_project2.g4.gui.GridAdapter;
import edu.unh.cs.cs619_2014_project2.g4.gui.UIEntity;
import edu.unh.cs.cs619_2014_project2.g4.gui.UIEntityFactory;
import edu.unh.cs.cs619_2014_project2.g4.server.BulletZoneRestClient;
import edu.unh.cs.cs619_2014_project2.g4.server.BusProvider;
import edu.unh.cs.cs619_2014_project2.g4.server.GridUpdateEvent;
import edu.unh.cs.cs619_2014_project2.g4.server.Poller;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity  {




    Tank myTank;
    long tankId = -1;
    byte turn = 0;
    String[] gridString = new String[257];



    @ViewById
    GridView gridView;

    @ViewById
    TextView textViewTankID;

    @ViewById
    TextView textViewGrid;

    @RestService
    BulletZoneRestClient restClient;

    @ViewById
    Button bJoin;

    @Bean
    Poller poller;

    @UiThread
    void updateTankId(){
        textViewTankID.setText("TankId=" + tankId);
    }



    @Background
    void join(){
        try{
            tankId = restClient.join().result;
            updateTankId();
            poller.doPoll();

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Background
    void move(){
       System.err.println("move");
        restClient.move(tankId,turn );
    }




    @Background
    void fire(){
        System.err.println("fire");
        restClient.fire(tankId);


    }



    @Background
    void turnUp(){
        System.err.println("turn");
        turn = 0;
        restClient.turn(tankId, turn);
    }

    @Background
    void turnRight(){
        System.err.println("turn");
        turn = 2;
        restClient.turn(tankId, turn);
    }
    @Background
    void turnDown(){
        System.err.println("turn");
        turn = 4;
        restClient.turn(tankId, turn);
    }

    @Background
    void turnLeft(){
        System.err.println("turn");
        turn = 6;
        restClient.turn(tankId, turn);
    }






    @Background
    void leave(){
        System.err.println("leave");
        restClient.leave(tankId);
    }


    @Click(R.id.bJoin)
    public void joinClicked(){
        join();
    }

    @Click(R.id.bMove)
    public void moveClicked(){
        move();
    }

    @Click(R.id.bFire)
    public void fireClicked(){
        fire();
    }

    @Click(R.id.bUp)
    public void upClicked(){
        turnUp();
    }

    @Click(R.id.bDown)
    public void downClicked(){
        turnDown();
    }

    @Click(R.id.bLeft)
    public void leftClicked(){
         turnLeft();
    }

    @Click(R.id.bRight)
    public void rightClicked(){
        turnRight();
    }

    @Click(R.id.bLeave)
    public void leaveClicked(){
        leave();
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
                builder.append(grid[i][j]).append(":");
            }
            builder.append("\n");
        }
   //    this.textViewGrid.setText(builder.toString());


String[] gridString = new String[257];
int count = 0;


        EntityFactory entityFactory = new EntityFactory(grid, tankId);

        Entity arrayOfEntities[] = entityFactory.createEntities();

        UIEntityFactory uIEntityFactory = new UIEntityFactory();

        //System.out.println("ENTITY: " + entities[position]);
        UIEntity[] arrayOfUIEntities = uIEntityFactory.createUIEntities(arrayOfEntities);

        for (int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                if(grid[i][j] == 1000){
                    gridString[count] = "I";
                }else if(grid[i][j] > 1000 && grid[i][j] < 2000){
                    gridString[count] = "W";
                }else if(grid[i][j] > 2000000 && grid[i][j] < 3000000){
                    gridString[count] = "B";
                }else if(grid[i][j] > 10000000 && grid[i][j] < 20000000){
                    gridString[count] = "T";
                }
                count++;
               }
            }
            gridView.setAdapter(new GridAdapter(this, arrayOfUIEntities));
        }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(eventHandler);

    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(eventHandler);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
