package com.horf.derp.testproject;

/**
 * Created by Eridan on 11/13/2014.
 */
public class GridUpdateEvent {
    int [][] grid;
    long timestamp;

    public GridUpdateEvent(int[][] grid, long timestamp) {
        this.grid = grid;
        this.timestamp = timestamp;
    }

    public int[][] getGrid() {
        return grid;
    }

    public long getTimestamp() {
        return timestamp;
    }
}