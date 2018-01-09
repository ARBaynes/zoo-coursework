package main.classes.pens;

import main.interfaces.HighPen;

import java.io.Serializable;

public class Aviary extends Pen implements HighPen, Serializable {
    private Double height;

    public Aviary (Double length, Double width, Double temperature, Double height) {
        setLength(length);
        setTemperature(temperature);
        setWidth(width);
        setHeight(height);
        setPenType();
    }

    @Override
    public void setPenType() { penType = "aviary"; }

    @Override
    public void setHeight(Double height) { this.height = height; }

    @Override
    public Double getHeight() { return height; }
}
