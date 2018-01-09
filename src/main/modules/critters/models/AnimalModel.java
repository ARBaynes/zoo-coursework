package main.modules.critters.models;

import main.classes.critters.Animal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;

public class AnimalModel {
    protected static String filePath = "data/animal_data/";
    protected static ArrayList<Animal> allAnimals = new ArrayList<>();

    //SETTERS

    public static void setAllAnimals() {
        allAnimals.clear();
        File folder = new File(filePath);
        ArrayList<Animal> animals = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            animals.add(deserialize(fileEntry));
        }
        allAnimals.addAll(animals);
    }

    //GETTERS

    public static ArrayList<Animal> getAllAnimals () { return allAnimals; }


    //PUBLIC DATA MANIPULATION

    public static void addAnimal () {

    }

    public static void editAnimal () {

    }

    public static void removeAnimal () {

    }

    //PRIVATE DATA MANIPULATION

    private static void serialize (Animal animal) {
        if (animal.getID() == null) { animal.setID(createAnimalID()); }
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(filePath + animal.getID()+".animal");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(animal);
            out.close();
            fileOut.close();

        } catch (IOException i) {
            i.printStackTrace();
        }
        setAllAnimals();
    }

    private static Animal deserialize (File toRead) {
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
