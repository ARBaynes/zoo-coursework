package main.classes.pens;

import main.classes.critters.Animal;
import main.classes.staff.Staff;
import main.interfaces.Area;
import main.modules.pens.petting.PettingModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Petting extends Pen implements Area, Serializable {
    public Petting (Double length, Double width, Double temperature, Staff staff) {
        setLength(length);
        setTemperature(temperature);
        setWidth(width);
        setPenType();
        setStaffResponsible(staff);
    }

    @Override
    public void setPenType() {
        penType = "petting";
    }

    @Override
    public void addAnimalToPen (Animal animal) {
        getContainedAnimals().add(animal);

        PettingModel.editPen(this);
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
        PettingModel.editPen(this);
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
        PettingModel.editPen(this);
    }

    @Override
    public void clearPen () {
        removeAllAnimalsFromPen();
        PettingModel.editPen(this);
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
