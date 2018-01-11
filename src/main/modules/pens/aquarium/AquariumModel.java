package main.modules.pens.aquarium;

import main.classes.pens.Aquarium;
import main.classes.pens.Pen;
import main.classes.staff.Staff;
import main.modules.pens.PenModel;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AquariumModel extends PenModel{
    protected static String filePath = "data/pen_data/aquarium_data/";
    protected static ArrayList<Aquarium> allAquariums = new ArrayList<>();

    //SETTERS

    public static void setAllAquariums() {
        allAquariums.clear();
        File folder = new File(filePath);
        ArrayList<Aquarium> aquariums = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            aquariums.add(deserialize(fileEntry));
        }
        allAquariums.addAll(aquariums);
        System.out.println(allAquariums.toString());
    }


    //GETTERS

    public static ArrayList<Aquarium> getAllAquariums() {
        if (allAquariums.isEmpty()) { setAllAquariums(); }
        return allAquariums;
    }


    //PUBLIC DATA MANIPULATION

    public static void addAquarium (Aquarium pen) {
        serialize(pen);
        setAllAquariums();
    }

    public static void editAquarium (Aquarium toFind, Aquarium toReplaceWith) {
        removeAquarium(toFind);
        addAquarium(toReplaceWith);
    }

    public static void removeAquarium (Aquarium toFind) {
        try {
            Path pathToFile = Paths.get(filePath + toFind.getPenID() + ".aquarium");
            java.nio.file.Files.deleteIfExists(pathToFile);
            System.out.println("File Deleted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAllAquariums();
    }

    //PRIVATE DATA MANIPULATION

    private static void serialize (Aquarium pen) {
        if (pen.getPenID() == null) { pen.setPenID(createID(filePath)); }
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(filePath + pen.getPenID() +".aquarium");
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
