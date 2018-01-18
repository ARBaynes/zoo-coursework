package main.modules.pens.aviary;

import main.classes.critters.Animal;
import main.classes.pens.Aquarium;
import main.classes.pens.Aviary;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class AviaryModel {
    protected static String filePath = "data/pen_data/aviary_data/";
    protected static ArrayList<Aviary> allAviaries = new ArrayList<>();

    //SETTERS

    public static void setAllAviaries() {
        allAviaries.clear();
        File folder = new File(filePath);
        ArrayList<Aviary> aviaries = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            aviaries.add(deserialize(fileEntry));
        }
        allAviaries.addAll(aviaries);
    }


    //GETTERS

    public static ArrayList<Aviary> getAllAviaries() {
        return allAviaries;
    }

    public static ArrayList<Aviary> getAllAviariesWithSpaceRemaining(Animal animalToFit) {
        if (allAviaries.isEmpty()) { setAllAviaries(); }
        ArrayList<Aviary> aviaries = new ArrayList<>();
        Double calculation = 0.0;
        for (Aviary aviary : allAviaries) {
            calculation += aviary.getCurrentVolume();
            for (Map.Entry<String,Double> requirements : animalToFit.getBreedRequirements().entrySet()) {
                calculation -= requirements.getValue();
            }
            if (calculation >= 0) {
                aviaries.add(aviary);
            }
        }
        return aviaries;
    }


    //PUBLIC DATA MANIPULATION

    public static void addAviary (Aviary aviary) {

    }

    public static void editAviary (Aviary toEdit) {

    }

    public static void removeAviary (Aviary toFind) {

    }

    //PRIVATE DATA MANIPULATION

    private static void serialize (Aviary aviary) {

    }

    private static Aviary deserialize (File toRead) {
        return null;
    }

    //GENERAL PURPOSE
}
