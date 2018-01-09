package main.classes.pens;

import java.io.Serializable;

public class Dry extends Pen implements Serializable {
    public Dry () {
        setPenType();
    }

    @Override
    public void setPenType() {
        penType = "dry";
    }
}
