package controllers.pens.dry;

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
import classes.pens.Dry;

public class DryAnimalController extends PenAnimalController {

    private static ObservableList<Animal> dryAnimalTableViewItems = FXCollections.observableArrayList();

    private static TableView dryAnimalTableView;
    private static TableColumn dryAnimalName;
    private static TableColumn dryAnimalBreed;
    private static TableColumn dryAnimalRequirements;
    private static TableColumn dryAnimalID;

    public static void construct (TableView tableView, TableColumn name, TableColumn breed,
                                  TableColumn requirements,TableColumn id) {
        dryAnimalTableView = tableView;
        dryAnimalName = name;
        dryAnimalBreed = breed;
        dryAnimalRequirements = requirements;
        dryAnimalID = id;

        dryAnimalTableView.setItems(dryAnimalTableViewItems);
    }

    public static void outline () {
        dryAnimalID.setCellValueFactory( new PropertyValueFactory<Animal, Integer>("ID"));
        dryAnimalName.setCellValueFactory( new PropertyValueFactory<Animal, String>("name"));
        dryAnimalBreed.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedName());
            }
        });
        dryAnimalRequirements.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedRequirementsToString());
            }
        });

        dryAnimalTableView.setRowFactory( tv -> {
            TableRow<Animal> penRow = new TableRow<>();
            penRow.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY  && (!penRow.isEmpty()) ) {
                    dryAnimalTableContextMenu(penRow);
                }
            });
            return penRow ;
        });
    }

    public static void refresh (Dry dry) {
        dryAnimalTableViewItems.clear();
        dryAnimalTableViewItems.addAll(dry.getContainedAnimals());
    }

    public static void refresh () {
        dryAnimalTableViewItems.clear();
    }

    private static void dryAnimalTableContextMenu(TableRow<Animal> animalTableRow) {
        setContextMenu(animalTableRow);
    }
}
