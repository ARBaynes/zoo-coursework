package main.modules.pens.dry;

import main.classes.pens.Aquarium;
import main.classes.pens.Dry;

import java.io.File;
import java.util.ArrayList;

public class DryModel {

    protected static String filePath = "data/pen_data/dry_data/";
    protected static ArrayList<Dry> allDryPens = new ArrayList<>();

    //SETTERS

    public static void setAllDryPens() {
        allDryPens.clear();
        File folder = new File(filePath);
        ArrayList<Dry> dry = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            dry.add(deserialize(fileEntry));
        }
        allDryPens.addAll(dry);
    }


    //GETTERS

    public static ArrayList<Dry> getAllDryPens() {
        return allDryPens;
    }


    //PUBLIC DATA MANIPULATION

    public static void addDryPen (Dry dry) {

    }

    public static void editDryPen (Dry toFind, Dry toReplaceWith) {

    }

    public static void removeDryPen (Dry toFind) {

    }

    //PRIVATE DATA MANIPULATION

    private static void serialize (Dry dry) {

    }

    private static Dry deserialize (File toRead) {
        return null;
    }

    //GENERAL PURPOSE
}
