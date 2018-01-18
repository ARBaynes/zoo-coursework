package main.classes.pens;

import main.classes.critters.Animal;
import main.interfaces.Volume;
import main.interfaces.WaterTypes;
import main.modules.pens.aquarium.AquariumModel;

import java.io.Serializable;

public class Aquarium extends Pen implements WaterTypes, Volume, Serializable{
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

        AquariumModel.editAquarium(this);
        animal.setCurrentPenID(this.getPenID());
    }

    @Override
    public void removeAnimalFromPen (Animal animal) {
        getContainedAnimals().remove(animal);
        AquariumModel.editAquarium(this);
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
        AquariumModel.editAquarium(this);
    }

    @Override
    public void clearPen () {
        for (Animal animal : getContainedAnimals()) {
            animal.setCurrentPenID(null);
        }
        getContainedAnimals().clear();
        AquariumModel.editAquarium(this);
    }
}
