package main.modules.pens.dry;

import main.classes.critters.Animal;
import main.classes.pens.Aquarium;
import main.classes.pens.Dry;
import main.modules.pens.PenModel;

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

    public static ArrayList<Dry> getAllPensWithSpaceRemaining(Animal animalToFit) {
        if (allPens.isEmpty()) { setAllPens(); }
        ArrayList<Dry> pens = new ArrayList<>();
        Double calculation = 0.0;
        for (Dry pen : allPens) {
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
            FileInputStream fileInputStream = new FileInputStream(toRead);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            pen = (Dry) objectInputStream.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace(); }
        return pen;
    }
}
