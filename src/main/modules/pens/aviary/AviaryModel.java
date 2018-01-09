package main.modules.pens.aviary;

import main.classes.pens.Aquarium;
import main.classes.pens.Aviary;

import java.io.File;
import java.util.ArrayList;

public class AviaryModel {
    protected static String filePath = "data/pen_data/aviary_data/";
    protected static ArrayList<Aviary> allAviary = new ArrayList<>();

    //SETTERS

    public static void setAllAviary() {
    }


    //GETTERS

    public static ArrayList<Aviary> getAllAviary() {
        return allAviary;
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
