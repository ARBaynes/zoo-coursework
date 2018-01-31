package controllers.pens.petting;

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
import classes.pens.Petting;

public class PettingAnimalController extends PenAnimalController {

    private static ObservableList<Animal> pettingAnimalTableViewItems = FXCollections.observableArrayList();

    private static TableView pettingAnimalTableView;
    private static TableColumn pettingAnimalName;
    private static TableColumn pettingAnimalBreed;
    private static TableColumn pettingAnimalRequirements;
    private static TableColumn pettingAnimalID;

    public static void construct (TableView tableView, TableColumn name, TableColumn breed,
                                  TableColumn requirements,TableColumn id) {
        pettingAnimalTableView = tableView;
        pettingAnimalName = name;
        pettingAnimalBreed = breed;
        pettingAnimalRequirements = requirements;
        pettingAnimalID = id;

        pettingAnimalTableView.setItems(pettingAnimalTableViewItems);
    }

    public static void outline () {
        pettingAnimalID.setCellValueFactory( new PropertyValueFactory<Animal, Integer>("ID"));
        pettingAnimalName.setCellValueFactory( new PropertyValueFactory<Animal, String>("name"));
        pettingAnimalBreed.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedName());
            }
        });
        pettingAnimalRequirements.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedRequirementsToString());
            }
        });

        pettingAnimalTableView.setRowFactory( tv -> {
            TableRow<Animal> penRow = new TableRow<>();
            penRow.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY  && (!penRow.isEmpty()) ) {
                    pettingAnimalTableContextMenu(penRow);
                }
            });
            return penRow ;
        });
    }

    public static void refresh (Petting petting) {
        pettingAnimalTableViewItems.clear();
        pettingAnimalTableViewItems.addAll(petting.getContainedAnimals());
    }

    public static void refresh () {
        pettingAnimalTableViewItems.clear();
    }


    private static void pettingAnimalTableContextMenu(TableRow<Animal> animalTableRow) {
        setContextMenu(animalTableRow);
    }
}
