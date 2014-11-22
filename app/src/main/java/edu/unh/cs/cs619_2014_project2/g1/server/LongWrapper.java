package edu.unh.cs.cs619_2014_project2.g1.server;

/**
 * Created by Eridan on 11/13/2014.
 */
public class LongWrapper {
    private long theLong;
    private long timeStamp;

    public LongWrapper() {
    }

    public LongWrapper(long newLong) {
        theLong=newLong;
    }
    public void setResult(long newLong) {
        theLong=newLong;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
    public long getResult() { return theLong; }
}
