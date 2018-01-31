package controllers.pens.semiaquatic;

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
import classes.pens.SemiAquatic;

public class SemiAquaticAnimalController extends PenAnimalController {

    private static ObservableList<Animal> semiAquaticAnimalTableViewItems = FXCollections.observableArrayList();

    private static TableView semiAquaticAnimalTableView;
    private static TableColumn semiAquaticAnimalName;
    private static TableColumn semiAquaticAnimalBreed;
    private static TableColumn semiAquaticAnimalSize;
    private static TableColumn semiAquaticAnimalID;

    public static void construct (TableView tableView, TableColumn name, TableColumn breed,
                                  TableColumn requirements,TableColumn id) {
        semiAquaticAnimalTableView = tableView;
        semiAquaticAnimalName = name;
        semiAquaticAnimalBreed = breed;
        semiAquaticAnimalSize = requirements;
        semiAquaticAnimalID = id;

        semiAquaticAnimalTableView.setItems(semiAquaticAnimalTableViewItems);
    }

    public static void outline () {
        semiAquaticAnimalID.setCellValueFactory( new PropertyValueFactory<Animal, Integer>("ID"));
        semiAquaticAnimalName.setCellValueFactory( new PropertyValueFactory<Animal, String>("name"));
        semiAquaticAnimalBreed.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedName());
            }
        });
        semiAquaticAnimalSize.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedRequirementsToString());
            }
        });

        semiAquaticAnimalTableView.setRowFactory( tv -> {
            TableRow<Animal> penRow = new TableRow<>();
            penRow.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY  && (!penRow.isEmpty()) ) {
                    semiAquaticAnimalTableContextMenu(penRow);
                }
            });
            return penRow ;
        });
    }

    public static void refresh (SemiAquatic semiAquatic) {
        semiAquaticAnimalTableViewItems.clear();
        semiAquaticAnimalTableViewItems.addAll(semiAquatic.getContainedAnimals());
    }

    public static void refresh () {
        semiAquaticAnimalTableViewItems.clear();
    }

    private static void semiAquaticAnimalTableContextMenu(TableRow<Animal> animalTableRow) {
        setContextMenu(animalTableRow);
    }
}
