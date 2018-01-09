package main.classes.pens;

import main.interfaces.HighPen;

import java.io.Serializable;

public class Aviary extends Pen implements HighPen, Serializable {
    private Double height;

    public Aviary () {
        setPenType();
    }

    @Override
    public void setPenType() { penType = "aviary"; }

    @Override
    public void setHeight(Double height) { this.height = height; }

    @Override
    public Double getHeight() { return height; }
}
