package main.classes.pens;

import main.interfaces.WaterPen;

import java.io.Serializable;

public class SemiAquatic extends Pen implements WaterPen, Serializable {
    private Integer waterVolume;
    private String waterType;

    @Override
    public void setPenType() {
        penType = "semiaquatic";
    }

    @Override
    public void setWaterVolume(Integer waterVolume) {
        this.waterVolume = waterVolume;
    }

    @Override
    public Integer getWaterVolume() {
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
