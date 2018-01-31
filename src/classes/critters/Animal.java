package classes.critters;

import models.critters.AnimalModel;

import java.io.Serializable;
import java.util.ArrayList;
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
    public ArrayList<Breed> getBreedDislikes () {return breed.getCannotLiveWith();}
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

    public void removeFromPen () {
        currentPenID = null;
        AnimalModel.editAnimal(this);
    }

}
