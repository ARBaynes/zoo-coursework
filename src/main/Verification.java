package main;

import javafx.scene.control.ChoiceBox;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Verification {

    public static boolean isNumeric (TextArea textArea) {
        if (textArea.getText().matches("\\d+(\\.\\d+)?")) {
            return true;
        } else {
            textArea.setBackground(new Color(15706537));
            return false;
        }
    }

    public static Boolean isName (String string) {
        return null;
    }

    public static Boolean hasSelected (ChoiceBox choiceBox) {
        return null;
    }

    public static Boolean uniqueID (ArrayList<Integer> currentIDArrayList, Integer possibleID) {
        return !currentIDArrayList.contains(possibleID);
    }

    public static Boolean noMissingSequence (ArrayList<Integer> integerArrayList) {

        return false;
    }

    public static Integer getMissingSequence (ArrayList<Integer> integerArrayList) {
        Integer i;
        Integer length = integerArrayList.size();
        Integer total = (length+1)*(length +2)/2;
        for ( i = 0; i< length; i++) {
            total -= integerArrayList.get(i);
        }
        return total;
    }

}
