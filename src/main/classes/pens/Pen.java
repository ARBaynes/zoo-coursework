package main.classes.pens;

import main.classes.critters.Animal;
import main.classes.staff.Staff;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Pen implements Serializable {

    private Integer penID;
    private Double length;
    private Double width;
    private Double temperature;
    private ArrayList<Animal> containedAnimals = new ArrayList<>();
    private Staff staffResponsible;
    public  String penType;

    //SETTERS

    public void setPenID (Integer penID) { this.penID = penID; }

    public void setWidth(Double width) {
        this.width = width;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setStaffResponsible(Staff staffResponsible) { this.staffResponsible = staffResponsible; }

    public abstract void setPenType();

    //GETTERS

    public Double getWidth() { return width; }

    public Double getLength() { return length; }

    public Double getTemperature() { return temperature; }

    public Staff staffResponsible() { return staffResponsible; }

    public ArrayList<Animal> getContainedAnimals() { return containedAnimals; }

    public String getPenType() { return penType; }

    public Integer getPenID() { return penID; }

    public Integer getContainedAnimalNumber () { return this.containedAnimals.size(); }

    //AREA GETTERS

    public Double getCurrentSpace () {
        Double area = getArea();
        for ( Animal containedAnimal : this.containedAnimals) {
            area -= containedAnimal.getBreed().getRequirements();
        }
        return area;
    }

    public Double getArea () {
        return getLength() * getWidth();
    }

    //CONTAINED ANIMAL MANIPULATION

    public void addAnimalToPen (Animal animal) {
        this.containedAnimals.add(animal);
    }

    public void removeAnimalFromPen (Animal animal) {
        this.containedAnimals.remove(animal);
    }

    public void editAnimalInPen (Animal animal) {
        for (int i = 0 ; i < containedAnimals.size(); i++) {
            if (containedAnimals.get(i).getID() == animal.getID()) {
                this.containedAnimals.remove(i);
                this.containedAnimals.add(animal);
            }
        }
    }

    public void clearPen () {
        containedAnimals.clear();
    }



    @Override
    public String toString() {
        return getPenID() + " - {Contained Animals: " + getContainedAnimalNumber() + "}" + System.lineSeparator() +
                "Length: " + getLength() + ", Width: "+ getWidth() + System.lineSeparator()+
                "Temperature: " + getTemperature();
    }
}
