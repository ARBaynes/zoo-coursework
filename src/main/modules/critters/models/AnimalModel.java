package main.modules.critters.models;

import main.classes.critters.Animal;

import java.util.ArrayList;
import java.util.Comparator;

public class AnimalModel {
    protected static String filePath = "data/Animal_Data/";
    protected static ArrayList<Animal> allAnimals = new ArrayList<>();

    //SETTERS

    public static void setAllAnimals() {

    }

    //GETTERS

    public static ArrayList<Animal> getAllAnimals () {
        return null;
    }


    //PUBLIC DATA MANIPULATION

    public static void addAnimal () {

    }

    public static void editAnimal () {

    }

    public static void removeAnimal () {

    }

    //PRIVATE DATA MANIPULATION

    private static void serialize () {

    }

    private static Animal deserialize () {
        return null;
    }

    //GENERAL PURPOSE

    public static Integer createAnimalID () {
        return null;
    }

    public static Comparator<Animal> animalComparator () {
        return null;
    }
}
