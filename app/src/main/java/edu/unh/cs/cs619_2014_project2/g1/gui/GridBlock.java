package edu.unh.cs.cs619_2014_project2.g1.gui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import edu.unh.cs.cs619_2014_project2.g1.game.MapEntityLogic;

/**
 * Created by Eridan on 10/31/2014.
 */
public class GridBlock {
    private static final String LOG_TAG = "GridBlock";
    private int width, height;
    private int x, y;
    private int objectOffset=3; //buffer between edge of block and object
    private int objectX, objectY;
    private MapEntityGraphics object=null;
    private int blockNumber=-1;
    private int gridPositionX, gridPositionY;

    private int debug = 1;

    public GridBlock(int thisX, int thisY, int thisWidth, int thisHeight, int gridPositionX, int gridPositionY) {
        x=thisX;
        y=thisY;
        objectX=x+objectOffset;
        objectY=y+objectOffset;
        width=thisWidth;
        height=thisHeight;
        this.gridPositionX=gridPositionX;
        this.gridPositionY=gridPositionY;
    }

    public void draw(Canvas canvas, Paint fillPaint, Paint linePaint) {
        canvas.drawRect(x, y, width+x, height+y, fillPaint);

        canvas.drawLine(x, y, width+x, y, linePaint);
        canvas.drawLine(x, y, x, height+y, linePaint);
        canvas.drawLine(x+width, height+y, width+x, y, linePaint);
        canvas.drawLine(x+width, y+height, x, y+height, linePaint);

        if(object!=null) {
            object.draw(canvas, objectX, objectY);
        }

        if(debug==1) {
            Paint textPaint = new Paint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(30);
            canvas.drawText(Integer.toString(blockNumber), (x + (width / 2)) - (width / 3), y + (height / 2), textPaint);
        }
    }




    public void setBlockNumber(int b) { blockNumber=b; }
    public void setHeight(int h) { height=h; }
    public void setWidth(int w) { width=w; }
    public void setX(int x) { this.x=x; }
    public void setY(int y) { this.y=y; }
    public void setObject(MapEntityGraphics o) {
        object=o;
        /*if(o!=null) {
            //object.setX(gridPositionX);
            //object.setY(gridPositionY);
        }*/
    }
    public void setObject(MapEntityLogic o) {
        object=o.getGraphics();
        /*if(o!=null) {
            object.setX(gridPositionX);
            object.setY(gridPositionY);
        }*/
    }
    public void clearObject() {
        object=null;
    }

    public int getBlockHeight() { return height; }
    public int getBlockWidth() { return width; }
    public int getBlockX() { return x; }
    public int getBlockY() { return y; }
    public MapEntityGraphics getObject() { return object; }
}
