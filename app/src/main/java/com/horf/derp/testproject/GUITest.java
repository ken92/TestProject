package com.horf.derp.testproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_guitest)
public class GUITest extends Activity {
    @ViewById
    TextView textViewTankId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.activity_guitest);

        join();
    }

    @Background
    void join(){
        try{
            //tankId = restClient.join().getResult();
            //updateTankId();
            //poller.doPoll();
            //poller.pollOnce();
            Intent intent = new Intent(this, ServerEventHandler_.class);
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


