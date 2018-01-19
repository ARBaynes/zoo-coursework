package main.modules.pens.petting;

import main.classes.critters.Animal;
import main.classes.pens.Dry;
import main.classes.pens.Petting;
import main.modules.pens.PenModel;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class PettingModel extends PenModel{
    protected static String filePath = "data/pen_data/petting_data/";
    protected static ArrayList<Petting> allPens = new ArrayList<>();

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

    public static ArrayList<Petting> getAllPensWithSpaceRemaining(Animal animalToFit) {
        if (allPens.isEmpty()) { setAllPens(); }
        ArrayList<Petting> pens = new ArrayList<>();
        Double calculation = 0.0;
        for (Petting pen : allPens) {
            calculation += pen.getCurrentArea();
            for (Map.Entry<String,Double> requirements : animalToFit.getBreedRequirements().entrySet()) {
                calculation -= requirements.getValue();
            }
            if (calculation >= 0) {
                pens.add(pen);
            }
        }
        return pens;
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
            FileInputStream fileInputStream = new FileInputStream(toRead);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            pen = (Petting) objectInputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace(); }
        return pen;
    }
}
