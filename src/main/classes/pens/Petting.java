package main.classes.pens;

import java.io.Serializable;

public class Petting extends Pen implements Serializable {
    public Petting (Double length, Double width, Double temperature) {
        setLength(length);
        setTemperature(temperature);
        setWidth(width);
        setPenType();
    }

    @Override
    public void setPenType() {
        penType = "petting";
    }
}
