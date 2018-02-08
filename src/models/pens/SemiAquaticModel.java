package models.pens;

import classes.critters.Animal;
import classes.critters.Breed;
import classes.pens.SemiAquatic;
import models.critters.BreedModel;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class SemiAquaticModel extends PenModel{
    private static String filePath = "data/pen_data/semiaquatic_data/";
    private static ArrayList<SemiAquatic> allPens = new ArrayList<>();

    //SETTERS

    public static void setAllPens() {
        allPens.clear();
        File folder = new File(filePath);
        ArrayList<SemiAquatic> pens = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            pens.add(deserialize(fileEntry));
        }
        allPens.addAll(pens);
    }


    //GETTERS

    public static ArrayList<SemiAquatic> getAllPens() {
        if (allPens.isEmpty()) {setAllPens();}
        return allPens;
    }

    public static ArrayList<SemiAquatic> getAllAppropriatePens (Animal animal) {
        if (allPens.isEmpty()) { setAllPens(); }
        Double landSpace = 0.0;
        Double waterSpace = 0.0;
        ArrayList<SemiAquatic> appropriatePens = new ArrayList<>();
        for (SemiAquatic pen : allPens) {
            if ( animalsInPenDoNotDislikeAnimalToFit(pen, animal) && animalHasNoDislikesInPen(pen, animal) && spaceRemaining(pen, animal, landSpace, waterSpace)) {
                appropriatePens.add(pen);
            }
        }
        return appropriatePens;
    }

    private static boolean spaceRemaining (SemiAquatic pen, Animal toFit, Double landSpace, Double waterSpace) {
        landSpace += pen.getCurrentArea();
        waterSpace += pen.getCurrentWaterVolume();
        for (Map.Entry<String,Double> requirements : toFit.getBreedRequirements().entrySet()) {
            if (requirements.getKey().equals("land")) {
                landSpace -= requirements.getValue();
            } else if (requirements.getKey().equals("water")) {
                waterSpace -= requirements.getValue();
            }
        }
        return landSpace >= 0 && waterSpace >= 0;
    }

    private static boolean animalHasNoDislikesInPen (SemiAquatic pen, Animal toFit) {
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

    private static boolean animalsInPenDoNotDislikeAnimalToFit (SemiAquatic pen, Animal toFit) {
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

    public static SemiAquatic getPenBy(String penID) {
        if (allPens.isEmpty()) { setAllPens(); }
        for (SemiAquatic pen : allPens) {
            if (pen.getPenID().equals(penID)) {
                return pen;
            }
        }
        return null;
    }

    //PUBLIC DATA MANIPULATION

    public static void addPen (SemiAquatic toAdd) {
        serialize(toAdd);
        setAllPens();
    }

    public static void editPen (SemiAquatic toEdit) {
        addPen(toEdit);
    }

    public static void removePen (SemiAquatic toFind) {
        try {
            Path pathToFile = Paths.get(filePath + toFind.getPenID() + ".semiaquatic");
            java.nio.file.Files.deleteIfExists(pathToFile);
            System.out.println("File Deleted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAllPens();
    }

    //PRIVATE DATA MANIPULATION

    private static void serialize (SemiAquatic pen) {
        if (pen.getPenID() == null) { pen.setPenID(createID(filePath, "SA")); }
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(filePath +pen.getPenID() +".semiaquatic");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(pen);
            out.close();
            fileOut.close();
            System.out.println("Serialized SemiAquatic pen data is saved.");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private static SemiAquatic deserialize (File toRead) {
        SemiAquatic pen=null ;
        try
        {
            FileInputStream fileIn = new FileInputStream(toRead);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            pen = (SemiAquatic) in.readObject();
            fileIn.close();
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace(); }
        return pen;
    }
}
