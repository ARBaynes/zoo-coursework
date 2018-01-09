package main.classes.pens;

import java.io.Serializable;

public class Petting extends Pen implements Serializable {
    public Petting () {
        setPenType();
    }

    @Override
    public void setPenType() {
        penType = "petting";
    }
}
