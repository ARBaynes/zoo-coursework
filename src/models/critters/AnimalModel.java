package models.critters;

import classes.critters.Animal;
import classes.critters.Breed;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

public class AnimalModel {
    private static String filePath = "data/animal_data/";
    private static ArrayList<Animal> allAnimals = new ArrayList<>();

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

    public static ArrayList<Animal> getAllAnimals () {
        if (allAnimals.isEmpty()) { setAllAnimals(); }
        return allAnimals;
    }

    public static  ArrayList<Animal> getAllAnimalsWhere (Breed breed) {
        if (allAnimals.isEmpty()) { setAllAnimals(); }
        ArrayList<Animal> allAnimalsWhere = new ArrayList<>();
        for (Animal animal : allAnimals) {
            if (animal.getBreedName().toLowerCase().equals(breed.getName().toLowerCase())) {
                allAnimalsWhere.add(animal);
            }
        }

        if (allAnimalsWhere.isEmpty()) {
            return null;
        } else {
            return allAnimalsWhere;
        }
    }

    public static  ArrayList<Animal> getAllAnimalsWhere (String penType) {
        if (allAnimals.isEmpty()) { setAllAnimals(); }
        ArrayList<Animal> allAnimalsWhere = new ArrayList<>();
        for (Animal animal : allAnimals) {
            if (animal.getBreedPenType().equals(penType.toLowerCase()) || animal.getBreedPenType().contains(penType.toLowerCase()) && !animal.hasPen()) {
                allAnimalsWhere.add(animal);
            }
        }
        return allAnimalsWhere;
    }

    public static  Animal getAnAnimalWhere (Integer ID) {
        if (allAnimals.isEmpty()) { setAllAnimals(); }
        for (Animal animal : allAnimals) {
            if (animal.getID() == ID) {
                return animal;
            }
        }
        return null;
    }


    //PUBLIC DATA MANIPULATION

    public static void addAnimal (Animal animal) {
        serialize(animal);
        setAllAnimals();
    }

    public static void editAnimal (Animal toEdit) {
        addAnimal(toEdit);
    }

    public static void removeAnimal (Animal toFind) {
        try {
            Path pathToFile = Paths.get(filePath + toFind.getID() + ".animal");
            java.nio.file.Files.deleteIfExists(pathToFile);
            System.out.println("File Deleted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAllAnimals();
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
        Animal animal;
        try {
            FileInputStream fileIn = new FileInputStream(toRead);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            animal = (Animal) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Animal class not found");
            c.printStackTrace();
            return null;
        }
        return animal;
    }

    //GENERAL PURPOSE

    public static Integer createAnimalID () {
        File folder = new File(filePath);
        Integer ID = folder.listFiles().length + 1;
        return ID;
    }

    public static Comparator<Animal> animalComparator () {
        return new Comparator<Animal>() {
            @Override
            public int compare(Animal animal1, Animal animal2) {
                return animal1.getID().compareTo(animal2.getID());
            }
        };
    }
}
