package models.pens;

import classes.critters.Animal;
import classes.critters.Breed;
import classes.pens.Petting;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class PettingModel extends PenModel{
    private static String filePath = "data/pen_data/petting_data/";
    private static ArrayList<Petting> allPens = new ArrayList<>();

    //SETTERS

    public static void setAllPens() {
        allPens.clear();
        File folder = new File(filePath);
        ArrayList<Petting> pens = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            pens.add(deserialize(fileEntry));
        }
        allPens.addAll(pens);
    }


    //GETTERS

    public static ArrayList<Petting> getAllPens() {
        if (allPens.isEmpty()) {setAllPens();}
        return allPens;
    }

    public static ArrayList<Petting> getAllAppropriatePens (Animal animal) {
        if (allPens.isEmpty()) { setAllPens(); }
        Double currentSpace = 0.0;
        ArrayList<Petting> appropriatePens = new ArrayList<>();
        for (Petting pen : allPens) {
            if ( animalsInPenDoNotDislikeAnimalToFit(pen, animal) && animalHasNoDislikesInPen(pen, animal) && spaceRemaining(pen, animal, currentSpace)) {
                appropriatePens.add(pen);
            }
        }
        return appropriatePens;
    }

    private static boolean spaceRemaining (Petting pen, Animal toFit, Double currentSpace) {
        currentSpace += pen.getCurrentArea();
        for (Map.Entry<String,Double> requirements : toFit.getBreedRequirements().entrySet()) {
            currentSpace -= requirements.getValue();
        }
        return currentSpace >= 0;
    }

    private static boolean animalHasNoDislikesInPen (Petting pen, Animal toFit) {
        for (Breed dislike : toFit.getBreedDislikes()) {
            System.out.println(dislike);
            if (pen.getContainedBreedNames().contains(dislike.getName())) {
                return false;
            }
        }
        return true;
    }

    private static boolean animalsInPenDoNotDislikeAnimalToFit (Petting pen, Animal toFit) {
        for (Animal containedAnimal : pen.getContainedAnimals()) {
            for (Breed dislikedBreed : containedAnimal.getBreedDislikes()) {
                if (dislikedBreed.getName().equals(toFit.getBreedName())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Petting getPenBy(String penID) {
        if (allPens.isEmpty()) { setAllPens(); }
        for (Petting pen : allPens) {
            if (pen.getPenID().equals(penID)) {
                return pen;
            }
        }
        return null;
    }

    //PUBLIC DATA MANIPULATION

    public static void addPen (Petting toAdd) {
        serialize(toAdd);
        setAllPens();
    }

    public static void editPen (Petting toEdit) {
        addPen(toEdit);
    }

    public static void removePen (Petting toFind) {
        try {
            Path pathToFile = Paths.get(filePath + toFind.getPenID() + ".petting");
            java.nio.file.Files.deleteIfExists(pathToFile);
            System.out.println("File Deleted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAllPens();
    }

    //PRIVATE DATA MANIPULATION

    private static void serialize (Petting pen) {
        if (pen.getPenID() == null) { pen.setPenID(createID(filePath, "PE")); }
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(filePath +pen.getPenID() +".petting");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(pen);
            out.close();
            fileOut.close();
            System.out.println("Serialized petting pen data is saved.");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private static Petting deserialize (File toRead) {
        Petting pen=null ;
        try
        {
            FileInputStream fileIn = new FileInputStream(toRead);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            pen = (Petting) in.readObject();
            fileIn.close();
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace(); }
        return pen;
    }
}
