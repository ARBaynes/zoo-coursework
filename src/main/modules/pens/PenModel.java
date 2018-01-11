package main.modules.pens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public abstract class PenModel {
    protected static Integer createID (String filePath) {
        Integer ID = 0;
        File folder = new File(filePath);
        if (folder.exists()) {
            ID = folder.listFiles().length + 1;
        }
        return ID;
    }


    public static ObservableList<String> getAllPenTypes () {
        return FXCollections.observableArrayList(
                "Aquarium","Aviary","Dry","Petting","Dry or Petting","SemiAquatic"
        );
    }
}
