package classes.pens;

import classes.critters.Animal;
import interfaces.Area;
import models.pens.DryModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Dry extends Pen implements Area, Serializable {

    public Dry (Double length, Double width, Double temperature, Integer staffID) {
        setLength(length);
        setTemperature(temperature);
        setWidth(width);
        setPenType();
        setKeeperID(staffID);
    }

    @Override
    public void setPenType() {
        penType = "dry";
    }

    @Override
    public void addAnimalToPen (Animal animal) {
        getContainedAnimals().add(animal);

        DryModel.editPen(this);
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
        DryModel.editPen(this);
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
        DryModel.editPen(this);
    }

    @Override
    public void clearPen () {
        removeAllAnimalsFromPen();
        DryModel.editPen(this);
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
