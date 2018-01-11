package main.classes.critters;

import java.io.Serializable;

public class Breed implements Serializable {
    private String name;
    private String penType;
    private Double requirements;

    public Breed (String name, String penType, Double requirements) {
        this.setName(name);
        this.setPenType(penType);
        this.setRequirements(requirements);
    }

    //SETTERS

    public void setPenType(String penType) {
        this.penType = penType;
    }

    public void setRequirements(Double requirements) {
        this.requirements = requirements;
    }

    public void setName(String name) { this.name = name; }

    //GETTERS

    public String getPenType() {
        return penType;
    }

    public Double getRequirements() {
        return requirements;
    }

    public String getName() { return name; }

    //OTHER

    @Override
    public String toString() {
        return  "Breed Name: " + getName() + System.lineSeparator() +
                "Pen Type: " + getPenType() + System.lineSeparator() +
                "Area Requirements:  " + getRequirements() + System.lineSeparator();

    }
}
