package main.classes.pens;

import main.classes.critters.Animal;
import main.classes.staff.Staff;
import main.interfaces.Volume;
import main.interfaces.WaterTypes;
import main.modules.pens.aquarium.AquariumModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Aquarium extends Pen implements WaterTypes, Volume, Serializable{
    private Double height;
    private String waterType;

    public Aquarium (Double length, Double width, Double temperature, Double height, String waterType, Integer staffID ) {
        setLength(length);
        setTemperature(temperature);
        setWidth(width);
        setWaterType(waterType);
        setHeight(height);
        setPenType();
        setKeeperID(staffID);
    }

    @Override
    public void setPenType() { penType = "aquarium"; }

    @Override
    public void setHeight(Double height) { this.height = height; }

    @Override
    public Double getHeight() { return height; }

    @Override
    public Double getVolume() {
        return getLength() * getWidth() * getHeight();
    }

    @Override
    public void setWaterType(String waterType) { this.waterType = waterType;    }

    @Override
    public String getWaterType() { return waterType; }


    @Override
    public Double getCurrentVolume() {
        Double volume = getVolume();
        for ( Animal containedAnimal : this.getContainedAnimals()) {
            volume -= containedAnimal.getBreed().getRequirements().get("volume");
        }
        return volume;
    }

    @Override
    public void addAnimalToPen (Animal animal) {
        getContainedAnimals().add(animal);

        AquariumModel.editPen(this);
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
        AquariumModel.editPen(this);
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
        AquariumModel.editPen(this);
    }

    @Override
    public void clearPen () {
        removeAllAnimalsFromPen();
        AquariumModel.editPen(this);
    }
}
