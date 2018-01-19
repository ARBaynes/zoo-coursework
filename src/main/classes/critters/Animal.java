package main.classes.critters;

import main.classes.pens.Aquarium;
import main.classes.pens.Aviary;
import main.classes.pens.Pen;
import main.modules.critters.models.AnimalModel;
import main.modules.pens.aquarium.AquariumModel;
import main.modules.pens.aviary.AviaryModel;

import java.io.Serializable;
import java.util.HashMap;

public class Animal implements Serializable{
    private String name;
    private Breed breed;
    private Integer ID;
    private String currentPenID;

    public Animal (String name, Breed breed) {
        this.name = name;
        this.breed = breed;
        this.currentPenID = null;
    }

    //SETTERS


    public void setCurrentPenID(String currentPenID) {
        this.currentPenID = currentPenID;
        AnimalModel.editAnimal(this);
    }

    public void setID(Integer ID) {this.ID = ID; }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    //GETTERS

    public Integer getID() { return ID; }

    public String getName() { return name; }

    public Breed getBreed() { return breed; }

    public String getCurrentPenID() { return currentPenID; }

    //BREED GETTERS

    public String getBreedName () {return breed.getName();}
    public String getBreedPenType  () {return breed.getPenType();}
    public HashMap<String, Double> getBreedRequirements () {return breed.getRequirements();}
    public String getBreedRequirementsToString () {return breed.requirementsToString();}

    //OTHER

    @Override
    public String toString() {
        return  getID() + " - " + getName();
    }

    public Boolean hasPen () {
        if (getCurrentPenID() != null && !getCurrentPenID().isEmpty()) {
            return true;
        } else if (getCurrentPenID() == null) {
            return false;
        }
        return null;
    }

    public void addToPen (String penID) {
        String typeID = penID.substring(0, 2);
        switch (typeID) {
            case "AQ":
                if (AquariumModel.getPenBy(penID) != null) {
                    AquariumModel.getPenBy(penID).addAnimalToPen(this);
                }
                break;
            case "AV":
                break;
            case "DR":
                break;
            case "PT":
                break;
            case "SA":
                break;
        }
    }

    public void removeFromPen () {
        currentPenID = null;
        AnimalModel.editAnimal(this);
    }
}
