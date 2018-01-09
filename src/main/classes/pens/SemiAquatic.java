package main.classes.pens;

import main.interfaces.WaterPen;

import java.io.Serializable;

public class SemiAquatic extends Pen implements WaterPen, Serializable {
    private Double waterVolume;
    private String waterType;

    @Override
    public void setPenType() {
        penType = "semiaquatic";
    }

    @Override
    public void setWaterVolume(Double waterVolume) {
        this.waterVolume = waterVolume;
    }

    @Override
    public Double getWaterVolume() {
        return waterVolume;
    }

    @Override
    public void setWaterType(String waterType) {
        this. waterType = waterType;
    }

    @Override
    public String getWaterType() {
        return waterType;
    }
}
