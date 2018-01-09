package main.modules.pens.aquarium;

import main.classes.pens.Aquarium;
import main.classes.staff.Staff;

import java.io.File;
import java.util.ArrayList;

public class AquariumModel {
    protected static String filePath = "data/pen_data/aquarium_data/";
    protected static ArrayList<Aquarium> allAquariums = new ArrayList<>();

    //SETTERS

    public static void setAllAquariums() {
        allAquariums.clear();
        File folder = new File(filePath);
        ArrayList<Aquarium> aquariums = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            aquariums.add(deserialize(fileEntry));
        }
        allAquariums.addAll(aquariums);
    }


    //GETTERS

    public static ArrayList<Aquarium> getAllAquariums() {
        return allAquariums;
    }


    //PUBLIC DATA MANIPULATION

    public static void addAquarium (Aquarium aquarium) {

    }

    public static void editAquarium (Aquarium toFind, Aquarium toReplaceWith) {

    }

    public static void removeAquarium (Aquarium toFind) {

    }

    //PRIVATE DATA MANIPULATION

    private static void serialize (Aquarium aquarium) {

    }

    private static Aquarium deserialize (File toRead) {
        return null;
    }

    //GENERAL PURPOSE
}
