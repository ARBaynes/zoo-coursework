package main.classes.critters;

import java.io.Serializable;

public class Breed implements Serializable {
    private String name;
    private String penType;
    private Integer requirements;

    public Breed (String name, String penType, Integer requirements) {
        this.setName(name);
        this.setPenType(penType);
        this.setRequirements(requirements);
    }

    //SETTERS

    public void setPenType(String penType) {
        this.penType = penType;
    }

    public void setRequirements(Integer requirements) {
        this.requirements = requirements;
    }

    public void setName(String name) { this.name = name; }

    //GETTERS

    public String getPenType() {
        return penType;
    }

    public Integer getRequirements() {
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
