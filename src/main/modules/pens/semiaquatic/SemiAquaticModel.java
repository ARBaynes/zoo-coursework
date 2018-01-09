package main.modules.pens.semiaquatic;

import main.classes.pens.Dry;
import main.classes.pens.Petting;
import main.classes.pens.SemiAquatic;

import java.io.File;
import java.util.ArrayList;

public class SemiAquaticModel {
    protected static String filePath = "data/pen_data/semiaquatic_data/";
    protected static ArrayList<SemiAquatic> allSemiAquatic = new ArrayList<>();

    //SETTERS

    public static void setAllSemiAquatic() {
    }


    //GETTERS

    public static ArrayList<SemiAquatic> getAllSemiAquatic() {
        return allSemiAquatic;
    }


    //PUBLIC DATA MANIPULATION

    public static void addSemiAquatic (SemiAquatic semiAquatic) {

    }

    public static void editSemiAquatic (SemiAquatic toFind, SemiAquatic toReplaceWith) {

    }

    public static void removeSemiAquatic (SemiAquatic toFind) {

    }

    //PRIVATE DATA MANIPULATION

    private static void serialize (SemiAquatic semiAquatic) {

    }

    private static SemiAquatic deserialize (File toRead) {
        return null;
    }

    //GENERAL PURPOSE
}
