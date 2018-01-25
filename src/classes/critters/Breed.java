package classes.critters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Breed implements Serializable {
    private String name;
    private String penType;
    private HashMap<String, Double> requirements = new HashMap<>();
    private ArrayList<Breed> cannotLiveWith = new ArrayList<>();

    public Breed (String name, String penType, Double requirements, String areaOrVolume) {
        this.setName(name);
        this.setPenType(penType);
        this.setRequirements(areaOrVolume, requirements);
    }

    public Breed (String name, String penType, Double landRequirements, Double waterRequirements) {
        this.setName(name);
        this.setPenType(penType);
        this.setRequirements(landRequirements, waterRequirements);
    }
    //SETTERS

    public void setPenType(String penType) {
        this.penType = penType;
    }

    public void setRequirements(String key, Double requirements) {
        getRequirements().put(key, requirements);
    }

    public void setRequirements(Double landRequirements, Double waterRequirements) {
        getRequirements().put("water", waterRequirements);
        setRequirements("land", landRequirements);
    }

    public void setName(String name) { this.name = name; }

    public void setCannotLiveWith(ArrayList<Breed> cannotLiveWith) {
        this.cannotLiveWith = cannotLiveWith;
    }

    //GETTERS

    public String getPenType() {
        return penType;
    }

    public HashMap<String, Double> getRequirements() {
        return requirements;
    }

    public String getName() { return name; }

    public ArrayList<Breed> getCannotLiveWith() { return cannotLiveWith; }


    //OTHER

    public String requirementsToString () {
        StringBuilder stringBuilder = new StringBuilder();
        for ( Map.Entry<String, Double> entry : getRequirements().entrySet() ) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(" = ");
            stringBuilder.append(entry.getValue());
            if (entry.getKey().equals("area") || entry.getKey().equals("land")) {
                stringBuilder.append(" m^2. ");
            } else if (entry.getKey().equals("volume") || entry.getKey().equals("water")) {
                stringBuilder.append(" m^3. ");
            }
        }
        return stringBuilder.toString();
    }

    public String getCannotLiveWithToString () {
        StringBuilder cannotLiveWithStringBuilder = new StringBuilder();
        for (Breed breed: cannotLiveWith ) {
            cannotLiveWithStringBuilder.append(breed.getName()).append(" ");
        }
        return cannotLiveWithStringBuilder.toString();
    }

    @Override
    public String toString() {
        return  "Breed Name: " + getName() + System.lineSeparator() +
                "Pen Type: " + getPenType() + System.lineSeparator() +
                "Area Requirements:  " + getRequirements().toString() + System.lineSeparator();

    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass())) { return false; }
        Breed b = (Breed) obj;
        return b.getName().equals(this.getName()) &&
                b.getRequirements().equals(this.getRequirements()) &&
                b.getPenType().equals(this.getPenType());
    }
}
