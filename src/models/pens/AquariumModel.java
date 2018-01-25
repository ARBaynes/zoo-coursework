package models.pens;

import classes.critters.Animal;
import classes.critters.Breed;
import classes.pens.Aquarium;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class AquariumModel extends PenModel{
    private static String filePath = "data/pen_data/aquarium_data/";
    private static ArrayList<Aquarium> allPens = new ArrayList<>();


    //SETTERS

    public static void setAllPens() {
        allPens.clear();
        File folder = new File(filePath);
        ArrayList<Aquarium> pens = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            pens.add(deserialize(fileEntry));
        }
        allPens.addAll(pens);
    }


    //GETTERS

    public static ArrayList<Aquarium> getAllPens() {
        if (allPens.isEmpty()) { setAllPens(); }
        return allPens;
    }

    public static ArrayList<Aquarium> getAllAppropriatePens (Animal animal) {
        if (allPens.isEmpty()) { setAllPens(); }
        Double currentSpace = 0.0;
        ArrayList<Aquarium> appropriatePens = new ArrayList<>();
        for (Aquarium pen : allPens) {
            if (noDislikeToFit(pen,animal) && noDislikedInPen(pen, animal) && spaceRemaining(pen, animal, currentSpace)) {
                appropriatePens.add(pen);
            }
        }
        return appropriatePens;
    }

    private static boolean spaceRemaining (Aquarium pen, Animal toFit, Double currentSpace) {
        currentSpace += pen.getCurrentVolume();
        for (Map.Entry<String,Double> requirements : toFit.getBreedRequirements().entrySet()) {
            currentSpace -= requirements.getValue();
        }
        return currentSpace >= 0;
    }

    private static boolean noDislikedInPen (Aquarium pen, Animal toFit) {
        ArrayList<Breed> cannotLiveWith = toFit.getBreed().getCannotLiveWith();
        Iterator<Breed> breedIterator = pen.getContainedBreeds().iterator();
        boolean penIsAppropriate;
        penIsAppropriate = true;
        while (breedIterator.hasNext() && penIsAppropriate) {
            if (cannotLiveWith.contains(breedIterator.next())) {
                penIsAppropriate = false;
            }
        }
        return penIsAppropriate;
    }

    private static boolean noDislikeToFit (Aquarium pen, Animal toFit) {
        Iterator<Breed> breedIterator = pen.getContainedBreeds().iterator();
        boolean penIsAppropriate;
        penIsAppropriate = true;
        while (breedIterator.hasNext() && penIsAppropriate) {
            if (breedIterator.next().getCannotLiveWith().contains(toFit.getBreed())) {
                penIsAppropriate = false;
            }
        }
        return penIsAppropriate;
    }



    /*public static ArrayList<Aquarium> getAllPensWithSpaceRemaining(Animal animalToFit) {
        if (allPens.isEmpty()) { setAllPens(); }
        ArrayList<Aquarium> aquariums = new ArrayList<>();
        Double calculation = 0.0;
        for (Aquarium pen : allPens) {
            calculation += pen.getCurrentVolume();
            for (Map.Entry<String,Double> requirements : animalToFit.getBreedRequirements().entrySet()) {
                 calculation -= requirements.getValue();
            }
            if (calculation >= 0) {
                aquariums.add(pen);
            }
        }
        return aquariums;
    }

    public static ArrayList<Aquarium> getAllPensWithNoDisliked (Animal animalToPutIn) {
        if (allPens.isEmpty()) { setAllPens(); }
        ArrayList<Breed> cannotLiveWith = animalToPutIn.getBreed().getCannotLiveWith();
        ArrayList<Aquarium> appropriatePens = new ArrayList<>();
        Boolean penIsAppropriate;
        Iterator<Breed> breedIterator;

        for (Aquarium pen : allPens) {
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
    }*/

    public static Aquarium getPenBy(String aquariumID) {
        if (allPens.isEmpty()) { setAllPens(); }
        for (Aquarium pen : allPens) {
            if (pen.getPenID().equals(aquariumID)) {
                return pen;
            }
        }
        return null;
    }


    //PUBLIC DATA MANIPULATION

    public static void addPen (Aquarium pen) {
        serialize(pen);
        setAllPens();
    }


    public static void editPen (Aquarium toEdit) {
        addPen(toEdit);
    }

    public static void removePen (Aquarium toFind) {
        try {
            Path pathToFile = Paths.get(filePath + toFind.getPenID() + ".aquarium");
            Files.deleteIfExists(pathToFile);
            System.out.println("File Deleted.");
        } catch(NoSuchFileException e) {
            System.out.println("File/directory does not exist");
        } catch(DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAllPens();
    }

    //PRIVATE DATA MANIPULATION

    private static void serialize (Aquarium pen) {
        if (pen.getPenID() == null) { pen.setPenID(createID(filePath, "AQ")); }
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(filePath +pen.getPenID() +".aquarium");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(pen);
            out.close();
            fileOut.close();
            System.out.println("Serialized aquarium pen data is saved.");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private static Aquarium deserialize (File toRead) {
        Aquarium pen=null ;
        try
        {
            FileInputStream fileIn = new FileInputStream(toRead);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            pen = (Aquarium) in.readObject();
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
