package com.horf.derp.testproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_guitest)
public class GUITest extends Activity {
    /*@ViewById
    TextView textViewTankId;*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        join();
    }

    @Background
    void join(){
        try{
            //tankId = restClient.join().getResult();
            //updateTankId();
            //poller.doPoll();
            //poller.pollOnce();
            Intent intent = new Intent(this, GridHandler.class);
            startActivity(intent);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /*@Click(R.id.btnJoin)
    public void joinClicked(){
        join();
    }*/
}


