package main.classes.pens;

import main.classes.critters.Animal;
import main.interfaces.HighPen;
import main.interfaces.Volume;

import java.io.Serializable;

public class Aviary extends Pen implements HighPen, Volume, Serializable {
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
