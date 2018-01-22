package main;

import javafx.scene.control.ChoiceBox;

import java.awt.*;

public class Verification {

    public static boolean isNumeric (TextArea textArea) {
        if (textArea.getText().matches("-?\\d+(\\.\\d+)?")) {
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


}
