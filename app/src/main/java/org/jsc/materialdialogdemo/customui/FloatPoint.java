package org.jsc.materialdialogdemo.customui;

import java.io.Serializable;

/**
 * Created by jsc on 2016/9/30.
 */

public class FloatPoint implements Serializable{

    private float x;
    private float y;

    public FloatPoint() {
    }

    public FloatPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
