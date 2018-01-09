package main.modules.pens.aviary;

import main.classes.pens.Aquarium;
import main.classes.pens.Aviary;

import java.io.File;
import java.util.ArrayList;

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


    //PUBLIC DATA MANIPULATION

    public static void addAviary (Aviary aviary) {

    }

    public static void editAviary (Aviary toFind, Aviary toReplaceWith) {

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
