package com.horf.derp.testproject.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by Eridan on 11/13/2014.
 */
public class ConcreteMapEntityGraphics implements MapEntityGraphics {
    private static final String LOG_TAG="ConcreteMapEntityGraphics";

    private Context context;
    private Bitmap image;
    private int width=50;
    private int height=50;
    //private int x,y;

    public ConcreteMapEntityGraphics(Context context, int path) {
        this.context = context;
        setImage(path);
    }
    public ConcreteMapEntityGraphics(Context context, int path, int width, int height) {
        this.context = context;
        setImage(path);
        this.width=width;
        this.height=height;
    }
    public ConcreteMapEntityGraphics(Context context, int width, int height) {
        this.context = context;
        this.width=width;
        this.height=height;
    }
    public ConcreteMapEntityGraphics(Context context) {
        this.context = context;
    }

    @Override
    public void setImage(int path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inSampleSize = 2;
        Bitmap source = BitmapFactory.decodeResource(context.getResources(), path, options);
        image = resizeImage(source);
    }

    @Override
    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        image=resizeImage(image);
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        canvas.drawBitmap(image, x, y, null);
    }

    private Bitmap resizeImage(Bitmap image) {
        int maxWidth = width;
        int maxHeight = height;
        int ratio = 0;
        int newWidth = image.getWidth();
        int newHeight = image.getHeight();

        if(newWidth != maxWidth) {
            ratio = maxWidth / newWidth;
            newWidth = maxWidth;
            newHeight = newHeight*ratio;
        }
        if(newHeight > maxHeight) {
            ratio = maxHeight/newHeight;
            newHeight = maxHeight;
            newWidth = newWidth*ratio;
        }
        if(newWidth<0) {
            newWidth=50;
            Log.d(LOG_TAG, "Width less than 0");
        }
        if(newHeight<0) {
            newHeight=50;
            Log.d(LOG_TAG, "Height less than 0");
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
        //return Bitmap.createScaledBitmap(image, newWidth, newHeight, true);
    }

    /*public void setX(int x) { this.x=x;return; }
    public void setY(int y) { this.y=y; }
    public int getX() { return x; }
    public int getY() { return y; }
    public void setCoordinates(int x, int y) {
        setX(x);
        setY(y);
    }*/
}