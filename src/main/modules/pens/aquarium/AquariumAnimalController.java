package main.modules.pens.aquarium;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.classes.critters.Animal;
import main.classes.pens.Aquarium;

public class AquariumAnimalController {

    protected static ObservableList<Animal> aquariumAnimalTableViewItems = FXCollections.observableArrayList();

    protected static Label aquariumAnimalLabel;
    protected static Button aquariumAddNewAnimalToPenButton;
    protected static TableView aquariumAnimalTableView;
    protected static TableColumn aquariumAnimalName;
    protected static TableColumn aquariumAnimalBreed;
    protected static TableColumn aquariumAnimalRequirements;
    protected static TableColumn aquariumAnimalID;

    public static void construct (Button addAnimalToPenButton, TableView tableView, TableColumn name, TableColumn breed,
                                  TableColumn requirements,TableColumn id) {
        aquariumAddNewAnimalToPenButton = addAnimalToPenButton;
        aquariumAnimalTableView = tableView;
        aquariumAnimalName = name;
        aquariumAnimalBreed = breed;
        aquariumAnimalRequirements = requirements;
        aquariumAnimalID = id;

        aquariumAnimalTableView.setItems(aquariumAnimalTableViewItems);
    }

    public static void outline () {
        aquariumAnimalID.setCellValueFactory( new PropertyValueFactory<Animal, Integer>("ID"));
        aquariumAnimalName.setCellValueFactory( new PropertyValueFactory<Animal, String>("name"));
        aquariumAnimalBreed.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedName());
            }
        });
        aquariumAnimalRequirements.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedRequirementsToString());
            }
        });
    }

    public static void refresh (Aquarium aquarium) {
        aquariumAnimalTableViewItems.clear();
        aquariumAnimalTableViewItems.addAll(aquarium.getContainedAnimals());
    }

    public static Label getAquariumAnimalLabel () {
        return aquariumAnimalLabel;
    }
}
