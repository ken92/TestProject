package edu.unh.cs.cs619_2014_project2.g4.server;

/**
 * Created by Tay on 11/11/2014.
 */

import android.os.SystemClock;

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

    @UiThread
    public void onGridUpdate(GridWrapper gw) {
        BusProvider.getInstance().post(new GridUpdateEvent(gw.getGrid(), gw.getTimeStamp()));
    }

}