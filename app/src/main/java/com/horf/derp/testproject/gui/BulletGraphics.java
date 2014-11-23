package com.horf.derp.testproject.gui;

import android.content.Context;

/**
 * Created by Eridan on 11/22/2014.
 */
public class BulletGraphics extends ConcreteMapEntityGraphics {
    public BulletGraphics(Context context) {
        super(context);
    }

    public BulletGraphics(Context context, int path, int width, int height) {
        super(context, path, width, height);
    }
}
