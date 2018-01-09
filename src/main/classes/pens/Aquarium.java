package main.classes.pens;

import main.interfaces.pens.*;

import java.io.Serializable;

public class Aquarium extends Pen implements HighPen, WaterPen, Serializable{
    private Integer height;
    private Integer waterVolume;
    private String waterType;

    public Aquarium () {
        setPenType();
    }

    @Override
    public void setPenType() { penType = "aquarium"; }

    @Override
    public void setHeight(Integer height) { this.height = height; }

    @Override
    public int getHeight() { return height; }

    @Override
    public void setWaterVolume(Integer waterVolume) { this.waterVolume = waterVolume; }

    @Override
    public Integer getWaterVolume() { return waterVolume; }

    @Override
    public void setWaterType(String waterType) { this.waterType = waterType;    }

    @Override
    public String getWaterType() { return waterType; }
}
