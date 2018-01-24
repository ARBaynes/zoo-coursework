package models.critters;

import classes.critters.Animal;
import classes.critters.Breed;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class BreedModel {
    protected static String filePath = "data/breed_data/";
    protected static ArrayList<Breed> allBreeds = new ArrayList<>();

    //GETTERS

    public static ArrayList<Breed> getAllBreeds() {
        if (allBreeds.isEmpty()) { setAllBreeds(); }
        return allBreeds;
    }

    public static ArrayList<Breed> getAllBreedsWhere(String penType) {
        if (allBreeds.isEmpty()) { setAllBreeds(); }
        ArrayList<Breed> breedsWhere = new ArrayList<>();
        for (Breed breed : allBreeds) {
            if (breed.getPenType().equals(penType)) {
                breedsWhere.add(breed);
            }
        }
        return breedsWhere;
    }

    public static ArrayList<String> getAllBreedNames() {
        if (allBreeds.isEmpty()) { setAllBreeds(); }
        ArrayList<String> names = new ArrayList<>();
        for ( Breed breed : allBreeds) {
            names.add(breed.getName());
        }
        return names;
    }

    public static Breed getABreedWhere (String name) {
        if (allBreeds.isEmpty()) { setAllBreeds(); }
        for (Breed breed : allBreeds) {
            if (breed.getName().equals(name)) {
                return breed;
            }
        }
        return null;
    }

    //SETTERS

    public static void setAllBreeds() {
        allBreeds.clear();
        File folder = new File(filePath);
        ArrayList<Breed> breeds = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            breeds.add(deserialize(fileEntry));
        }
        allBreeds.addAll(breeds);
    }

    //PUBLIC DATA MANIPULATION

    public static void addBreed (Breed breed) {
        serialize(breed);
        setAllBreeds();
    }

    public static void editBreed (Breed toFind, Breed toReplaceWith) {
        removeBreed(toFind);
        addBreed(toReplaceWith);
    }

    public static void removeBreed (Breed toFind) {
        try {
            Path pathToFile = Paths.get(filePath + toFind.getName() + ".breed");
            java.nio.file.Files.deleteIfExists(pathToFile);
            System.out.println("File Deleted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAllBreeds();
    }

    //PRIVATE DATA MANIPULATION

    private static void serialize (Breed breed) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(filePath + breed.getName() +".breed");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(breed);
            out.close();
            fileOut.close();
            System.out.printf("Serialized breed data is saved!!");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private static Breed deserialize (File toRead) {
        Breed breed = null;
        try {
            FileInputStream fileIn = new FileInputStream(toRead);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            breed = (Breed) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Breed class not found");
            c.printStackTrace();
            return null;
        }
        return breed;
    }

    //GENERAL PURPOSE


}
