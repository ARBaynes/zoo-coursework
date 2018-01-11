package main.classes.pens;

import main.classes.critters.Animal;
import main.interfaces.Area;
import main.interfaces.Volume;
import main.interfaces.WaterTypes;
import main.interfaces.WaterVolume;

import java.io.Serializable;

public class SemiAquatic extends Pen implements WaterVolume, Area, WaterTypes, Serializable {
    private String waterType;
    private Double waterDepth;
    private Double waterLength;
    private Double waterWidth;

    public SemiAquatic (Double length, Double width, Double temperature, String waterType, Double waterDepth) {
        setLength(length);
        setTemperature(temperature);
        setWidth(width);
        setWaterType(waterType);
        setWaterDepth(waterDepth);
        setPenType();
    }

    @Override
    public void setPenType() {
        penType = "semiaquatic";
    }

    //WATER VOLUME
    @Override
    public void setWaterDepth (Double waterDepth) {
        this.waterDepth = waterDepth;
    }

    @Override
    public Double getWaterDepth () { return waterDepth; }

    @Override
    public void setWaterLength(Double waterLength) {
        this.waterLength = waterLength;
    }

    @Override
    public Double getWaterLength() {
        return waterLength;
    }

    @Override
    public void setWaterWidth(Double waterWidth) {
        this.waterWidth = waterWidth;
    }

    @Override
    public Double getWaterWidth() {
        return waterWidth;
    }

    @Override
    public Double getWaterVolume() {
        return getWaterWidth() * getWaterLength() * getWaterDepth();
    }

    @Override
    public Double getCurrentWaterVolume() {
        Double waterVolume = getWaterVolume();
        for ( Animal containedAnimal : this.getContainedAnimals()) {
            waterVolume -= containedAnimal.getBreed().getRequirements().get("water");
        }
        return waterVolume;
    }

    //WATER TYPE

    @Override
    public void setWaterType(String waterType) {
        this. waterType = waterType;
    }

    @Override
    public String getWaterType() {
        return waterType;
    }

    //AREA

    @Override
    public Double getArea() {
        return getLength() * getWidth();
    }

    @Override
    public Double getCurrentArea() {
        Double area = getArea();
        for ( Animal containedAnimal : this.getContainedAnimals()) {
            area -= containedAnimal.getBreed().getRequirements().get("land");
        }
        return area;
    }

}
