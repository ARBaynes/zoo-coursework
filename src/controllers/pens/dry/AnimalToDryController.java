package controllers.pens.dry;

import classes.critters.Animal;
import classes.pens.Dry;
import controllers.critters.AnimalController;
import controllers.critters.BreedController;
import controllers.main.MainController;
import controllers.pens.AnimalToSpecificPenController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import models.critters.AnimalModel;
import models.pens.DryModel;

import java.util.ArrayList;
import java.util.Optional;

public class AnimalToDryController extends AnimalToSpecificPenController {
    public static void animalFromDry (Animal animal) {
        String currentPenID = animal.getCurrentPenID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText("Are you sure you wish to remove " +animal.getName()+ " from pen #" +currentPenID);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            if (currentPenID != null) {
                Dry pen = DryModel.getPenBy(currentPenID);
                if (pen != null) {
                    pen.removeAnimalFromPen(animal);
                    DryModel.editPen(pen);

                    animal.setCurrentPenID(null);
                    AnimalModel.editAnimal(animal);

                    AnimalController.refresh(animal.getBreed());
                    DryAnimalController.refresh(pen);
                    DryController.refresh();
                    MainController.refresh();
                }
            }
        } else {
            System.out.println(animal.getName() + " will stay in this pen");
        }
    }

    public static void animalToDry (Animal animal) {
        Dialog<Dry> dialog = new Dialog<>();
        dialog.setTitle("Add "+ animal.getName() +" to a Dry Pen");
        dialog.setHeaderText("Add " + animal.getName() + " to a dry pen: ");
        dialog.setResizable(true);
        Label desc = new Label("Dry Pens: ");
        ObservableList<Dry> pens = FXCollections.observableArrayList();
        ArrayList<Dry> drypens = DryModel.getAllAppropriatePens(animal);
        for (Dry drypen: drypens) {
            pens.addAll(drypen);
        }
        ChoiceBox<Dry> penChoiceBox = new ChoiceBox<>(pens);

        TableView<Animal> currentAnimalsInPen = new TableView<>();
        TableColumn<Animal, Integer> animalIDCol = new TableColumn<>("ID");
        TableColumn<Animal, String> animalNameCol = new TableColumn<>("Name");
        TableColumn<Animal, String> animalBreedCol = new TableColumn<>("Breed");
        TableColumn<Animal, String> animalRequirementsCol = new TableColumn<>("Requirements");

        animalToPenCellFactories(animalIDCol, animalNameCol, animalBreedCol, animalRequirementsCol);
        currentAnimalsInPen.getColumns().addAll(animalIDCol, animalNameCol, animalBreedCol, animalRequirementsCol);

        ObservableList<Animal> animalsinPen = FXCollections.observableArrayList();
        currentAnimalsInPen.setItems(animalsinPen);

        penChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends Dry> observable, Dry oldValue, Dry newValue) ->
                        refresh(animalsinPen, newValue)
                );

        GridPane animalDialogGridPane = new GridPane();
        animalDialogGridPane.add(desc, 1, 1);
        animalDialogGridPane.add(penChoiceBox, 2, 1);
        animalDialogGridPane.add(currentAnimalsInPen, 2, 2);
        dialog.getDialogPane().setContent(animalDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Dry>() {
            @Override
            public Dry call(ButtonType button) {
                if (button == buttonTypeOk) {
                    return penChoiceBox.getSelectionModel().getSelectedItem();
                }
                return null;
            }
        });

        Optional<Dry> result = dialog.showAndWait();
        if (result.isPresent()) {

            animal.setCurrentPenID(result.get().getPenID());
            AnimalModel.editAnimal(animal);

            result.get().addAnimalToPen(animal);
            DryModel.editPen(result.get());

            AnimalController.refresh(animal.getBreed());
            BreedController.refresh();

            DryController.refresh();
            MainController.refresh();
        }
    }

    public static void autoAddAnimalToDry (Animal animal) {
        ArrayList<Dry> allSuitablePens = DryModel.getAllAppropriatePens(animal);
        if (allSuitablePens.isEmpty()) {
            noPensAlert(animal).showAndWait();
            return;
        }
        Dry firstPenFree = allSuitablePens.get(0);
        animal.setCurrentPenID(firstPenFree.getPenID());
        AnimalModel.editAnimal(animal);

        firstPenFree.addAnimalToPen(animal);
        DryModel.editPen(firstPenFree);

        AnimalController.refresh(animal.getBreed());
        BreedController.refresh();

        DryController.refresh();
        MainController.refresh();
        assignedToPenAlert(animal, firstPenFree.getPenID()).showAndWait();
    }

    private static Alert noPensAlert (Animal animal) {
        Alert noPensAlert = new Alert(Alert.AlertType.ERROR);
        noPensAlert.setHeaderText("Error");
        noPensAlert.setContentText("No Free "+ animal.getBreedPenType() + " Available, " + animal.getName() + " cannot be automatically assigned a pen!");
        return noPensAlert;
    }

    private static Alert assignedToPenAlert (Animal animal, String penID) {
        Alert assignedToPenAlert = new Alert(Alert.AlertType.INFORMATION);
        assignedToPenAlert.setHeaderText("Successfully Assigned");
        assignedToPenAlert.setContentText(animal.getName() + " successfully assigned to pen #" + penID + "!");
        return assignedToPenAlert;
    }
    public static void refresh (ObservableList<Animal> animalList, Dry pen) {
        animalList.clear();
        animalList.addAll(pen.getContainedAnimals());
    }
}
