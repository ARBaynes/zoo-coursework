package models.pens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import classes.pens.Pen;

import java.io.File;
import java.util.ArrayList;

public abstract class PenModel {
    protected static String createID (String filePath, String identifier) {
        Integer ID = 0;
        File folder = new File(filePath);
        if (folder.exists()) {
            ID = folder.listFiles().length + 1;
        }
        return identifier + ID;
    }


    public static ObservableList<String> getAllPenTypes () {
        return FXCollections.observableArrayList(
                "aquarium","aviary","dry","petting","dry or petting","semiaquatic"
        );
    }

    public static ArrayList<Pen> getEveryPen () {
        ArrayList<Pen> allPens = new ArrayList<>();

        allPens.addAll(AquariumModel.getAllPens());
        allPens.addAll(AviaryModel.getAllPens());
        allPens.addAll(DryModel.getAllPens());
        allPens.addAll(PettingModel.getAllPens());
        allPens.addAll(SemiAquaticModel.getAllPens());

        return allPens;
    }
}
