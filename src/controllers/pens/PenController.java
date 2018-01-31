package controllers.pens;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public abstract class PenController {
    public static void disableKeeper (CheckBox autoAssignCheckBox, Label staffLabel, ChoiceBox<Integer> staffChoiceBox) {
        if (autoAssignCheckBox.isSelected()) {
            staffLabel.setVisible(false);
            staffChoiceBox.setVisible(false);
        } else {
            staffLabel.setVisible(true);
            staffChoiceBox.setVisible(true);
        }
    }
}
