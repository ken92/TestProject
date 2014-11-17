package com.horf.derp.testproject;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Eridan on 10/31/2014.
 */
public class CanvasGrid extends SurfaceView {
    private static final String LOG_TAG = "CanvasGrid";

    private int gridWidth, gridHeight;
    private GridBlock[][] theGrid;
    private SurfaceHolder holder;
    private CanvasThread thread;
    private int ready=0;

    public CanvasGrid(Context context) {
        super(context);
        gridHeight = context.getResources().getInteger(R.integer.grid_height);
        gridWidth = context.getResources().getInteger(R.integer.grid_width);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int parentWidth = displayMetrics.widthPixels;
        int parentHeight = displayMetrics.heightPixels - (gridHeight * 6);
        theGrid = new GridBlock[gridWidth][gridHeight];

        int blockWidth = parentWidth/gridWidth;
        int blockHeight = parentHeight/gridHeight;
        int blockX = 0;
        int blockY = 0;

        int blockNumber=0;

        for(int i=0; i<gridWidth; i++) {
            for(int j=0; j<gridHeight; j++) {
                theGrid[i][j] = new GridBlock(blockX, blockY, blockWidth, blockHeight, i, j);
                theGrid[i][j].setBlockNumber(blockNumber);
                //Log.d(LOG_TAG, "New block at (" + blockX + "," + blockY + ")");
                blockY+=blockHeight;
                blockNumber++;
            }
            blockY=0;
            blockX+=blockWidth;
        }

        //theGrid[0][0] = new GridBlock(blockX, blockY, blockWidth, blockHeight);
        //theGrid[0][0].setBlockNumber(0);
        //Log.d(LOG_TAG, "New block at (" + blockX + "," + blockY + ")");

        thread = new CanvasThread(this, theGrid, gridWidth, gridHeight);







        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                thread.setRunning(false);
                while (retry) {
                    try {
                        thread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }



            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                thread.setRunning(true);
                thread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
        ready=1;
    }

    public int getReadyState() {
        return ready;
    }

    public void putEntity(int x, int y, MapEntityGraphics entity) {
        theGrid[x][y].setObject(entity);
    }
    public GridBlock[][] getTheGrid() { return theGrid; }
    public void moveEntityAt(int fromX, int fromY, int toX, int toY) {
        //Log.i(LOG_TAG, "fromX: "+fromX+"  fromY: "+fromY+"  toX: "+toX+"  toY: "+toY);
        if(theGrid[fromX][fromY].getObject()==null || theGrid[toX][toY].getObject()!=null) return;
        theGrid[toX][toY].setObject(theGrid[fromX][fromY].getObject());
        theGrid[fromX][fromY].setObject(null);
    }

    public void resumeThread() {
        thread.setRunning(true);
        Log.d("CanvasGrid", "Resume Thread");
    }
    public void pauseThread() {
        thread.setRunning(false);
        Log.d("CanvasGrid", "Pause Thread");
    }

    public void moveObjectUp(int x, int y) {
        if(theGrid[x][y].getObject()==null || y-1<0) return;
        moveEntityAt(x, y, x, y-1);
    }
    public void moveObjectDown(int x, int y) {
        if(theGrid[x][y].getObject()==null || y+1<=gridHeight) return;
        moveEntityAt(x, y, x, y+1);
    }
    public void moveObjectLeft(int x, int y) {
        if(theGrid[x][y].getObject()==null || x-1<0) return;
        moveEntityAt(x, y, x-1, y);
    }
    public void moveObjectRight(int x, int y) {
        if(theGrid[x][y].getObject()==null || x+1<=gridWidth) return;
        moveEntityAt(x, y, x+1, y);
    }
}
