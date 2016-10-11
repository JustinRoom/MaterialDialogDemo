package org.jsc.materialdialogdemo.customui;

import java.io.Serializable;

/**
 * Created by jsc on 2016/9/30.
 */

public class DataModel implements Serializable{

    float value;
    String label;

    public DataModel() {
    }

    public DataModel(float value, String label) {
        this.value = value;
        this.label = label;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
