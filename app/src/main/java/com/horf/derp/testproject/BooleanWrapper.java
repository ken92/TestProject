package com.horf.derp.testproject;

/**
 * Created by Eridan on 11/13/2014.
 */
public class BooleanWrapper {
    private boolean bool;
    private long timeStamp;

    public BooleanWrapper() {
    }

    public BooleanWrapper(boolean bool) {
        this.bool=bool;
    }

    public boolean getBoolean() {
        return bool;
    }

    public void setBoolean(boolean bool) {
        this.bool=bool;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
