package com.horf.derp.testproject;

import android.content.Context;

/**
 * Created by Eridan on 11/15/2014.
 */
public class WallGraphics extends ConcreteMapEntityGraphics {
    public WallGraphics(Context context, int path) {
        super(context, path);
    }

    public WallGraphics(Context context, int path, int width, int height) {
        super(context, path, width, height);
    }
}
