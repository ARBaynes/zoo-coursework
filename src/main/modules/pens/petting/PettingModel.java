package main.modules.pens.petting;

import main.classes.pens.Dry;
import main.classes.pens.Petting;

import java.io.File;
import java.util.ArrayList;

public class PettingModel {
    protected static String filePath = "data/pen_data/petting_data/";
    protected static ArrayList<Petting> allPetting = new ArrayList<>();

    //SETTERS

    public static void setAllPetting() {
    }


    //GETTERS

    public static ArrayList<Petting> getAllPetting() {
        return allPetting;
    }


    //PUBLIC DATA MANIPULATION

    public static void addPetting (Petting petting) {

    }

    public static void editPetting (Petting toFind, Petting toReplaceWith) {

    }

    public static void removePetting (Petting toFind) {

    }

    //PRIVATE DATA MANIPULATION

    private static void serialize (Petting petting) {

    }

    private static Dry deserialize (File toRead) {
        return null;
    }

    //GENERAL PURPOSE
}
