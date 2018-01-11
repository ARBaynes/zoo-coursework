package main.classes.pens;

import main.classes.critters.Animal;
import main.interfaces.Area;

import java.io.Serializable;

public class Petting extends Pen implements Area, Serializable {
    public Petting (Double length, Double width, Double temperature) {
        setLength(length);
        setTemperature(temperature);
        setWidth(width);
        setPenType();
    }

    @Override
    public void setPenType() {
        penType = "petting";
    }

    @Override
    public Double getArea() {
        return getLength() * getWidth();
    }

    @Override
    public Double getCurrentArea() {
        Double area = getArea();
        for ( Animal containedAnimal : this.getContainedAnimals()) {
            area -= containedAnimal.getBreed().getRequirements().get("area");
        }
        return area;
    }
}
