package com.horf.derp.testproject.server;

/**
 * Created by sji363 on 11/12/14.
 */
public class GridWrapper {
    private int[][] grid;

    private long timeStamp;

    public GridWrapper() {
    }

    public GridWrapper(int[][] grid) {
        this.grid = grid;
    }

    public int[][] getGrid() {
        return this.grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}