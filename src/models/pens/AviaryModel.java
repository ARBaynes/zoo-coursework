package models.pens;

import classes.critters.Animal;
import classes.critters.Breed;
import classes.pens.Aviary;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class AviaryModel extends PenModel{
    private static String filePath = "data/pen_data/aviary_data/";
    private static ArrayList<Aviary> allPens = new ArrayList<>();

    //SETTERS

    public static void setAllPens() {
        allPens.clear();
        File folder = new File(filePath);
        ArrayList<Aviary> pens = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            pens.add(deserialize(fileEntry));
        }
        allPens.addAll(pens);
    }


    //GETTERS

    public static ArrayList<Aviary> getAllPens() {
        if (allPens.isEmpty()) {setAllPens();}
        return allPens;
    }

    public static ArrayList<Aviary> getAllPensWithSpaceRemaining(Animal animalToFit) {
        if (allPens.isEmpty()) { setAllPens(); }
        ArrayList<Aviary> pens = new ArrayList<>();
        Double calculation = 0.0;
        for (Aviary pen : allPens) {
            calculation += pen.getCurrentVolume();
            for (Map.Entry<String,Double> requirements : animalToFit.getBreedRequirements().entrySet()) {
                calculation -= requirements.getValue();
            }
            if (calculation >= 0) {
                pens.add(pen);
            }
        }
        return pens;
    }

    public static ArrayList<Aviary> getAllPensWithNoDisliked (Animal animalToPutIn) {
        if (allPens.isEmpty()) { setAllPens(); }
        ArrayList<Breed> cannotLiveWith = animalToPutIn.getBreed().getCannotLiveWith();
        ArrayList<Aviary> appropriatePens = new ArrayList<>();
        Boolean penIsAppropriate;
        Iterator<Breed> breedIterator;

        for (Aviary pen : allPens) {
            penIsAppropriate = true;
            breedIterator = pen.getContainedBreeds().iterator();
            while (breedIterator.hasNext() && penIsAppropriate) {
                if (cannotLiveWith.contains(breedIterator.next())) {
                    penIsAppropriate = false;
                }
            }
            if (penIsAppropriate) {
                appropriatePens.add(pen);
            }
        }
        return appropriatePens;
    }

    public static Aviary getPenBy(String penID) {
        if (allPens.isEmpty()) { setAllPens(); }
        for (Aviary pen : allPens) {
            if (pen.getPenID().equals(penID)) {
                return pen;
            }
        }
        return null;
    }

    //PUBLIC DATA MANIPULATION

    public static void addPen (Aviary toAdd) {
        serialize(toAdd);
        setAllPens();
    }

    public static void editPen (Aviary toEdit) {
        addPen(toEdit);
    }

    public static void removePen (Aviary toFind) {
        try {
            Path pathToFile = Paths.get(filePath + toFind.getPenID() + ".aviary");
            java.nio.file.Files.deleteIfExists(pathToFile);
            System.out.println("File Deleted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAllPens();
    }

    //PRIVATE DATA MANIPULATION

    private static void serialize (Aviary pen) {
        if (pen.getPenID() == null) { pen.setPenID(createID(filePath, "AV")); }
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(filePath +pen.getPenID() +".aviary");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(pen);
            out.close();
            fileOut.close();
            System.out.println("Serialized aviary pen data is saved.");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private static Aviary deserialize (File toRead) {
        Aviary pen=null ;
        try
        {
            FileInputStream fileIn = new FileInputStream(toRead);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            pen = (Aviary) in.readObject();
            fileIn.close();
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace(); }
        return pen;
    }

    //GENERAL PURPOSE
}
