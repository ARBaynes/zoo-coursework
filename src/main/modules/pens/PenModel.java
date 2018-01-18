package main.modules.pens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.classes.pens.Aquarium;
import main.classes.pens.Pen;

import java.io.File;

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
}
