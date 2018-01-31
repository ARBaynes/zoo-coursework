package controllers.pens.aviary;

import classes.critters.Animal;
import classes.pens.Aviary;
import controllers.critters.AnimalController;
import controllers.critters.BreedController;
import controllers.main.MainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import models.critters.AnimalModel;
import models.pens.AviaryModel;

import java.util.ArrayList;
import java.util.Optional;

public class AnimalToAviaryController {

    public static void animalFromAviary (Animal animal) {
        String currentPenID = animal.getCurrentPenID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText("Are you sure you wish to remove " +animal.getName()+ " from pen #" +currentPenID);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            if (currentPenID != null) {
                Aviary pen = AviaryModel.getPenBy(currentPenID);
                if (pen != null) {
                    pen.removeAnimalFromPen(animal);
                    AviaryModel.editPen(pen);

                    animal.setCurrentPenID(null);
                    AnimalModel.editAnimal(animal);

                    AnimalController.refresh(animal.getBreed());
                    AviaryAnimalController.refresh(pen);
                    AviaryController.refresh();
                    MainController.refresh();
                }
            }
        } else {
            System.out.println(animal.getName() + " will stay in this pen");
        }
    }

    public static void animalToAviary (Animal animal) {
        Dialog<Aviary> dialog = new Dialog<>();
        dialog.setTitle("Add "+ animal.getName() +" to an Aviary");
        dialog.setHeaderText("Add " + animal.getName() + " to " + animal.getBreedPenType());
        dialog.setResizable(true);
        Label desc = new Label("Aviaries: ");
        ObservableList<Aviary> pens = FXCollections.observableArrayList();
        ArrayList<Aviary> aviaries = AviaryModel.getAllAppropriatePens(animal);
        for (Aviary aviary: aviaries) {
            pens.addAll(aviary);
        }
        ChoiceBox<Aviary> penChoiceBox = new ChoiceBox<>(pens);

        TableView<Animal> currentAnimalsInPen = new TableView<>();
        TableColumn<Animal, Integer> animalIDCol = new TableColumn<>("ID");
        animalIDCol.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("ID"));
        TableColumn<Animal, String> animalNameCol = new TableColumn<>("Name");
        animalNameCol.setCellValueFactory(new PropertyValueFactory<Animal, String>("Name"));
        TableColumn<Animal, String> animalBreedCol = new TableColumn<>("Breed");
        animalBreedCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedName());
            }
        });

        TableColumn<Animal, String> animalRequirementsCol = new TableColumn<>("Requirements");
        animalRequirementsCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedRequirementsToString());
            }
        });

        currentAnimalsInPen.getColumns().addAll(animalIDCol, animalNameCol, animalBreedCol, animalRequirementsCol);

        ObservableList<Animal> animalsinPen = FXCollections.observableArrayList();
        currentAnimalsInPen.setItems(animalsinPen);

        penChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends Aviary> observable, Aviary oldValue, Aviary newValue) ->
                        refresh(animalsinPen, newValue)
                );

        GridPane animalDialogGridPane = new GridPane();
        animalDialogGridPane.add(desc, 1, 1);
        animalDialogGridPane.add(penChoiceBox, 2, 1);
        animalDialogGridPane.add(currentAnimalsInPen, 2, 2);
        dialog.getDialogPane().setContent(animalDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Aviary>() {
            @Override
            public Aviary call(ButtonType button) {
                if (button == buttonTypeOk) {
                    return penChoiceBox.getSelectionModel().getSelectedItem();
                }
                return null;
            }
        });

        Optional<Aviary> result = dialog.showAndWait();
        if (result.isPresent()) {

            animal.setCurrentPenID(result.get().getPenID());
            AnimalModel.editAnimal(animal);

            result.get().addAnimalToPen(animal);
            AviaryModel.editPen(result.get());

            AnimalController.refresh(animal.getBreed());
            BreedController.refresh();

            AviaryController.refresh();
            MainController.refresh();
        }
    }

    public static void autoAddAnimalToAviary (Animal animal) {
        ArrayList<Aviary> allSuitablePens = AviaryModel.getAllAppropriatePens(animal);
        if (allSuitablePens.isEmpty()) {
            noPensAlert(animal).showAndWait();
            return;
        }
        Aviary firstPenFree = allSuitablePens.get(0);
        animal.setCurrentPenID(firstPenFree.getPenID());
        AnimalModel.editAnimal(animal);

        firstPenFree.addAnimalToPen(animal);
        AviaryModel.editPen(firstPenFree);

        AnimalController.refresh(animal.getBreed());
        BreedController.refresh();

        AviaryController.refresh();
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

    private static void refresh (ObservableList<Animal> animalList, Aviary pen) {
        animalList.clear();
        animalList.addAll(pen.getContainedAnimals());
    }

    private static void animalToPenCellFactories (TableColumn<Animal, Integer> ID, TableColumn<Animal, String> name,
                                                  TableColumn<Animal, String> breed, TableColumn<Animal, String> requirements) {

        ID.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<Animal, String>("Name"));
        breed.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedName());
            }
        });
        requirements.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedRequirementsToString());
            }
        });
    }

}
