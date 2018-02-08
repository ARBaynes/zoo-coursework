package models.pens;

import classes.critters.Animal;
import classes.critters.Breed;
import classes.pens.Dry;
import models.critters.BreedModel;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class DryModel extends PenModel {

    private static String filePath = "data/pen_data/dry_data/";
    private static ArrayList<Dry> allPens = new ArrayList<>();

    //SETTERS

    public static void setAllPens() {
        allPens.clear();
        File folder = new File(filePath);
        ArrayList<Dry> dry = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            dry.add(deserialize(fileEntry));
        }
        allPens.addAll(dry);
    }


    //GETTERS

    public static ArrayList<Dry> getAllPens() {
        if (allPens.isEmpty()) {setAllPens();}
        return allPens;
    }

    public static ArrayList<Dry> getAllAppropriatePens (Animal animal) {
        if (allPens.isEmpty()) { setAllPens(); }
        Double currentSpace = 0.0;
        ArrayList<Dry> appropriatePens = new ArrayList<>();
        for (Dry pen : allPens) {
            if ( animalsInPenDoNotDislikeAnimalToFit(pen, animal) && animalHasNoDislikesInPen(pen, animal) && spaceRemaining(pen, animal, currentSpace)) {
                appropriatePens.add(pen);
            }
        }
        return appropriatePens;
    }

    private static boolean spaceRemaining (Dry pen, Animal toFit, Double currentSpace) {
        currentSpace += pen.getCurrentArea();
        for (Map.Entry<String,Double> requirements : toFit.getBreedRequirements().entrySet()) {
            currentSpace -= requirements.getValue();
        }
        return currentSpace >= 0;
    }

    private static boolean animalHasNoDislikesInPen (Dry pen, Animal toFit) {
        Breed toFitBreed = BreedModel.getABreedWhere(toFit.getBreedName());
        if (toFitBreed != null) {
            for (Breed dislike : toFitBreed.getCannotLiveWith()) {
                if (pen.getContainedBreedNames().contains(dislike.getName())) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean animalsInPenDoNotDislikeAnimalToFit (Dry pen, Animal toFit) {
        Breed toFitBreed = BreedModel.getABreedWhere(toFit.getBreedName());
        Breed inPenBreed;
        if (toFitBreed != null) {
            for (Animal containedAnimal : pen.getContainedAnimals()) {
                inPenBreed = BreedModel.getABreedWhere(containedAnimal.getBreedName());
                if (inPenBreed != null) {
                    for (Breed dislikedBreed : inPenBreed.getCannotLiveWith()) {
                        if (dislikedBreed.getName().equals(toFitBreed.getName())) {
                            return false;
                        }
                    }
                }

            }
        }
        return true;
    }

    public static Dry getPenBy(String penID) {
        if (allPens.isEmpty()) { setAllPens(); }
        for (Dry pen : allPens) {
            if (pen.getPenID().equals(penID)) {
                return pen;
            }
        }
        return null;
    }

    //PUBLIC DATA MANIPULATION

    public static void addPen (Dry toAdd) {
        serialize(toAdd);
        setAllPens();
    }

    public static void editPen (Dry toEdit) {
        addPen(toEdit);
    }

    public static void removePen (Dry toFind) {
        try {
            Path pathToFile = Paths.get(filePath + toFind.getPenID() + ".dry");
            java.nio.file.Files.deleteIfExists(pathToFile);
            System.out.println("File Deleted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAllPens();
    }

    //PRIVATE DATA MANIPULATION

    private static void serialize (Dry pen) {
        if (pen.getPenID() == null) { pen.setPenID(createID(filePath, "DR")); }
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(filePath +pen.getPenID() +".dry");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(pen);
            out.close();
            fileOut.close();
            System.out.println("Serialized dry pen data is saved.");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private static Dry deserialize (File toRead) {
        Dry pen=null ;
        try
        {
            FileInputStream fileIn = new FileInputStream(toRead);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            pen = (Dry) in.readObject();
            fileIn.close();
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace(); }
        return pen;
    }
}
