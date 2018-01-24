package classes.pens;

import classes.critters.Animal;
import interfaces.Area;
import interfaces.WaterTypes;
import interfaces.WaterVolume;
import models.pens.SemiAquaticModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class SemiAquatic extends Pen implements WaterVolume, Area, WaterTypes, Serializable {
    private String waterType;
    private Double waterDepth;
    private Double waterLength;
    private Double waterWidth;

    public SemiAquatic (Double length, Double width, Double temperature, String waterType, Double waterDepth, Double waterLength, Double waterWidth, Integer staffID) {
        setLength(length);
        setTemperature(temperature);
        setWidth(width);
        setWaterType(waterType);
        setWaterDepth(waterDepth);
        setWaterLength(waterLength);
        setWaterWidth(waterWidth);
        setPenType();
        setKeeperID(staffID);
    }

    @Override
    public void setPenType() {
        penType = "semiaquatic";
    }

    @Override
    public void addAnimalToPen (Animal animal) {
        getContainedAnimals().add(animal);

        SemiAquaticModel.editPen(this);
        animal.setCurrentPenID(this.getPenID());
    }

    @Override
    public void removeAnimalFromPen (Animal animal) {
        ArrayList<Animal> containedAnimals = getContainedAnimals();
        for (Iterator<Animal> iterator = containedAnimals.iterator(); iterator.hasNext(); ) {
            Animal value = iterator.next();
            if (value.getID().equals(animal.getID())) {
                iterator.remove();
            }
        }
        SemiAquaticModel.editPen(this);
        animal.setCurrentPenID(null);
    }

    @Override
    public void editAnimalInPen (Animal animal) {
        for (int i = 0 ; i < getContainedAnimals().size(); i++) {
            if (getContainedAnimals().get(i).getID() == animal.getID()) {
                getContainedAnimals().remove(i);
                getContainedAnimals().add(animal);
            }
        }
        SemiAquaticModel.editPen(this);
    }

    @Override
    public void clearPen () {
        removeAllAnimalsFromPen();
        SemiAquaticModel.editPen(this);
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
