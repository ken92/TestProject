package com.horf.derp.testproject;

import android.os.SystemClock;

import com.horf.derp.testproject.game.GridUpdateEvent;
import com.horf.derp.testproject.server.BulletZoneRestClient;
import com.horf.derp.testproject.server.BusProvider;
import com.horf.derp.testproject.server.GridWrapper;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

@EBean
public class Poller {
    private static final String TAG = "PollServer";

    @RestService
    BulletZoneRestClient restClient;

    @Background(id = "grid_poller_task")
    public void doPoll() {
        while (true) {
            onGridUpdate(restClient.grid());

            // poll server every 100ms
            SystemClock.sleep(100);
        }
    }

    public void pollOnce() {
        onGridUpdate(restClient.grid());
    }

    @UiThread
    public void onGridUpdate(GridWrapper gw) {
        BusProvider.getInstance().post(new GridUpdateEvent(gw.getGrid(), gw.getTimeStamp()));
    }
}