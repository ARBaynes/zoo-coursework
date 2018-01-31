package controllers.pens.aviary;

import controllers.pens.PenAnimalController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import classes.critters.Animal;
import classes.pens.Aviary;

public class AviaryAnimalController extends PenAnimalController {

    private static ObservableList<Animal> aviaryAnimalTableViewItems = FXCollections.observableArrayList();

    private static TableView aviaryAnimalTableView;
    private static TableColumn aviaryAnimalName;
    private static TableColumn aviaryAnimalBreed;
    private static TableColumn aviaryAnimalRequirements;
    private static TableColumn aviaryAnimalID;

    public static void construct (TableView tableView, TableColumn name, TableColumn breed,
                                  TableColumn requirements,TableColumn id) {
        aviaryAnimalTableView = tableView;
        aviaryAnimalName = name;
        aviaryAnimalBreed = breed;
        aviaryAnimalRequirements = requirements;
        aviaryAnimalID = id;

        aviaryAnimalTableView.setItems(aviaryAnimalTableViewItems);
    }

    public static void outline () {
        aviaryAnimalID.setCellValueFactory( new PropertyValueFactory<Animal, Integer>("ID"));
        aviaryAnimalName.setCellValueFactory( new PropertyValueFactory<Animal, String>("name"));
        aviaryAnimalBreed.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedName());
            }
        });
        aviaryAnimalRequirements.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedRequirementsToString());
            }
        });

        aviaryAnimalTableView.setRowFactory( tv -> {
            TableRow<Animal> penRow = new TableRow<>();
            penRow.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY  && (!penRow.isEmpty()) ) {
                    setContextMenu(penRow);
                }
            });
            return penRow ;
        });
    }

    public static void refresh (Aviary aviary) {
        aviaryAnimalTableViewItems.clear();
        aviaryAnimalTableViewItems.addAll(aviary.getContainedAnimals());
    }

    public static void refresh () {
        aviaryAnimalTableViewItems.clear();
    }


}
