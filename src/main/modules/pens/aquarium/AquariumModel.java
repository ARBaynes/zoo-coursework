package main.modules.pens.aquarium;

import main.classes.critters.Animal;
import main.classes.pens.Aquarium;
import main.classes.pens.Pen;
import main.classes.staff.Staff;
import main.modules.pens.PenModel;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    public static ArrayList<Aquarium> getAllPensWithSpaceRemaining(Animal animalToFit) {
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
            java.nio.file.Files.deleteIfExists(pathToFile);
            System.out.println("File Deleted.");
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
            FileInputStream fileInputStream = new FileInputStream(toRead);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            pen = (Aquarium) objectInputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace(); }
        return pen;
    }

    //GENERAL PURPOSE
}
