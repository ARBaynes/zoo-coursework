package main.classes.pens;

import main.interfaces.pens.HighPen;

import java.io.Serializable;

public class Aviary extends Pen implements HighPen, Serializable {
    private Integer height;

    public Aviary () {
        setPenType();
    }

    @Override
    public void setPenType() { penType = "aviary"; }

    @Override
    public void setHeight(Integer height) { this.height = height; }

    @Override
    public int getHeight() { return height; }
}
