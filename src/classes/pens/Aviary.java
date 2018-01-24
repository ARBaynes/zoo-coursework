package classes.pens;

import classes.critters.Animal;
import interfaces.Volume;
import models.pens.AviaryModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Aviary extends Pen implements Volume, Serializable {
    private Double height;

    public Aviary (Double length, Double width, Double temperature, Double height, Integer staffID) {
        setLength(length);
        setTemperature(temperature);
        setWidth(width);
        setHeight(height);
        setPenType();
        setKeeperID(staffID);
    }

    @Override
    public void setPenType() { penType = "aviary"; }

    @Override
    public void addAnimalToPen(Animal animal) {
        getContainedAnimals().add(animal);

        AviaryModel.editPen(this);
        animal.setCurrentPenID(this.getPenID());
    }

    @Override
    public void removeAnimalFromPen(Animal animal) {
        ArrayList<Animal> containedAnimals = getContainedAnimals();
        for (Iterator<Animal> iterator = containedAnimals.iterator(); iterator.hasNext(); ) {
            Animal value = iterator.next();
            if (value.getID().equals(animal.getID())) {
                iterator.remove();
            }
        }
        AviaryModel.editPen(this);
        animal.setCurrentPenID(null);
    }

    @Override
    public void editAnimalInPen(Animal animal) {
        for (int i = 0 ; i < getContainedAnimals().size(); i++) {
            if (getContainedAnimals().get(i).getID() == animal.getID()) {
                getContainedAnimals().remove(i);
                getContainedAnimals().add(animal);
            }
        }
        AviaryModel.editPen(this);
    }

    @Override
    public void clearPen() {
        removeAllAnimalsFromPen();
        AviaryModel.editPen(this);
    }

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

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass())) { return false; }
        Aviary a = (Aviary) obj;
        return a.getPenID().equals(this.getPenID()) &&
                a.getHeight().equals(this.getHeight()) &&
                a.getLength().equals(this.getLength()) &&
                a.getWidth().equals(this.getWidth()) &&
                a.getTemperature().equals(this.getTemperature()) &&
                a.getKeeperID().equals(this.getKeeperID()) &&
                a.getContainedAnimals().equals(this.getContainedAnimals());
    }
}
