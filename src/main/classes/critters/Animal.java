package main.classes.critters;

import java.io.Serializable;

public class Animal implements Serializable{
    private String name;
    private Breed breed;
    private Integer ID;
    private boolean hasPen;

    public Animal (String name, Breed breed) {
        this.name = name;
        this.breed = breed;
        setHasPen(false);
    }

    //SETTERS

    public void setHasPen(boolean hasPen) {
        this.hasPen = hasPen;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

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

    public boolean hasPen() { return hasPen; }

    //BREED GETTERS

    public String getBreedName () {return breed.getName();}
    public String getBreedPenType  () {return breed.getPenType();}
    public Double getBreedRequirements () {return breed.getRequirements();}

    //OTHER

    @Override
    public String toString() {
        return  getID() + " - " + getName();
    }
}
