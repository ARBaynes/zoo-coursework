package main.modules.pens.semiaquatic;

import main.classes.critters.Animal;
import main.classes.pens.SemiAquatic;
import main.modules.pens.PenModel;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class SemiAquaticModel extends PenModel{
    protected static String filePath = "data/pen_data/semiaquatic_data/";
    protected static ArrayList<SemiAquatic> allPens = new ArrayList<>();

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

    public static ArrayList<SemiAquatic> getAllPensWithSpaceRemaining(Animal animalToFit) {
        if (allPens.isEmpty()) { setAllPens(); }
        ArrayList<SemiAquatic> pens = new ArrayList<>();
        Double landCalculation = 0.0;
        Double waterCalculation = 0.0;
        for (SemiAquatic pen : allPens) {
            landCalculation += pen.getCurrentArea();
            waterCalculation += pen.getCurrentWaterVolume();
            for (Map.Entry<String,Double> requirements : animalToFit.getBreedRequirements().entrySet()) {
                if (requirements.getKey().equals("land")) {
                    landCalculation -= requirements.getValue();
                } else if (requirements.getKey().equals("water")) {
                    waterCalculation -= requirements.getValue();
                }
            }
            if (waterCalculation >= 0 && landCalculation >= 0) {
                pens.add(pen);
            }
        }
        return pens;
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
            FileInputStream fileInputStream = new FileInputStream(toRead);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            pen = (SemiAquatic) objectInputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace(); }
        return pen;
    }
}
