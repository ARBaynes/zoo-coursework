package main.classes.pens;

import main.classes.critters.Animal;
import main.classes.staff.Staff;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Pen implements Serializable {

    private String penID;
    private Double length;
    private Double width;
    private Double temperature;
    private ArrayList<Animal> containedAnimals = new ArrayList<>();
    private Staff staffResponsible;
    public  String penType;

    //SETTERS

    public void setPenID (String penID) { this.penID = penID; }

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

    public String getPenID() { return penID; }

    public Integer getContainedAnimalNumber () { return this.containedAnimals.size(); }

    public Staff getStaffResponsible () {return staffResponsible;}

    //CONTAINED ANIMAL MANIPULATION

    public abstract void addAnimalToPen (Animal animal) ;

    public abstract void removeAnimalFromPen (Animal animal) ;

    public abstract void editAnimalInPen (Animal animal) ;

    public abstract void clearPen ();

    public void removeAllAnimalsFromPen () {
        for (Animal containedAnimal : this.containedAnimals) {
            containedAnimal.removeFromPen();
        }
        this.containedAnimals.clear();
    }

    @Override
    public String toString() {
        return getPenID() + " - {Contained Animals: " + getContainedAnimalNumber() + "}" + System.lineSeparator() +
                "Length: " + getLength() + ", Width: "+ getWidth() + System.lineSeparator()+
                "Temperature: " + getTemperature();
    }
}
