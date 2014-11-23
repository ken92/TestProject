package com.horf.derp.testproject.gui;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.horf.derp.testproject.R;

/**
 * Created by Eridan on 11/1/2014.
 */
public class CanvasThread extends Thread {
    //private GameView view;
    private GridBlock[][] gridView;
    private int gridWidth, gridHeight;
    private CanvasGrid canvasGrid;
    private boolean running = false;
    private Paint fillPaint, linePaint;

    /*public CanvasThread(GameView view) {
        this.view = view;
    }*/
    public CanvasThread(CanvasGrid canvasGrid, GridBlock[][] view, int gridWidth, int gridHeight) {
        this.canvasGrid = canvasGrid;
        this.gridView = view;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;

        fillPaint = new Paint();
        fillPaint.setStrokeWidth(canvasGrid.getContext().getResources().getInteger(R.integer.grid_block_stroke));
        fillPaint.setColor(canvasGrid.getContext().getResources().getColor(R.color.grid_block_colour));
        fillPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        linePaint = new Paint();
        linePaint.setStrokeWidth(canvasGrid.getContext().getResources().getInteger(R.integer.grid_block_stroke));
        linePaint.setColor(canvasGrid.getContext().getResources().getColor(R.color.grid_block_line_colour));
        linePaint.setStyle(Paint.Style.STROKE);
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        while (running) {
            Canvas c = null;
            try {
                c = canvasGrid.getHolder().lockCanvas();
                synchronized (canvasGrid.getHolder()) {
                    //gridView[0][0].draw(c, fillPaint, linePaint);
                    for(int i=0; i<gridWidth; i++) {
                        for(int j=0; j<gridHeight; j++) {
                            gridView[i][j].draw(c, fillPaint, linePaint);
                        }
                    }
                }
            } finally {
                if (c != null) {
                    canvasGrid.getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }


}
