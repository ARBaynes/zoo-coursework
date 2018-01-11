package main.classes.pens;

import main.classes.critters.Animal;
import main.interfaces.HighPen;
import main.interfaces.Volume;
import main.interfaces.WaterTypes;

import java.io.Serializable;

public class Aquarium extends Pen implements HighPen, WaterTypes, Volume, Serializable{
    private Double height;
    private String waterType;

    public Aquarium (Double length, Double width, Double temperature, Double height, String waterType) {
        setLength(length);
        setTemperature(temperature);
        setWidth(width);
        setWaterType(waterType);
        setHeight(height);
        setPenType();
    }

    @Override
    public void setPenType() { penType = "aquarium"; }

    @Override
    public void setHeight(Double height) { this.height = height; }

    @Override
    public Double getHeight() { return height; }

    @Override
    public void setWaterType(String waterType) { this.waterType = waterType;    }

    @Override
    public String getWaterType() { return waterType; }

    @Override
    public Double getVolume() {
        return getLength() * getWidth() * getHeight();
    }

    @Override
    public Double getCurrentVolume() {
        Double volume = getVolume();
        for ( Animal containedAnimal : this.getContainedAnimals()) {
            volume -= containedAnimal.getBreed().getRequirements().get("volume");
        }
        return volume;
    }
}
