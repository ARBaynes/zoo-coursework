package main.classes.pens;

import java.io.Serializable;

public class Dry extends Pen implements Serializable {

    public Dry (Double length, Double width, Double temperature) {
        setLength(length);
        setTemperature(temperature);
        setWidth(width);
        setPenType();
    }

    @Override
    public void setPenType() {
        penType = "dry";
    }
}
