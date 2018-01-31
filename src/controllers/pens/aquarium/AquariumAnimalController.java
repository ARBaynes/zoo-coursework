package controllers.pens.aquarium;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import classes.critters.Animal;
import classes.pens.Aquarium;
import controllers.critters.AnimalToPenController;

public class AquariumAnimalController {

    private static ObservableList<Animal> aquariumAnimalTableViewItems = FXCollections.observableArrayList();

    private static TableView<Animal> aquariumAnimalTableView;
    private static TableColumn aquariumAnimalName;
    private static TableColumn aquariumAnimalBreed;
    private static TableColumn aquariumAnimalRequirements;
    private static TableColumn aquariumAnimalID;

    public static void construct (TableView tableView, TableColumn name, TableColumn breed,
                                  TableColumn requirements,TableColumn id) {
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

        aquariumAnimalTableView.setRowFactory( tv -> {
            TableRow<Animal> penRow = new TableRow<>();
            penRow.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY  && (!penRow.isEmpty()) ) {
                    aquariumAnimalTableContextMenu(penRow);
                }
            });
            return penRow ;
        });
    }

    public static void refresh (Aquarium aquarium) {
        aquariumAnimalTableViewItems.clear();
        aquariumAnimalTableViewItems.addAll(aquarium.getContainedAnimals());
    }

    public static void refresh () {
        aquariumAnimalTableViewItems.clear();
    }

    private static void aquariumAnimalTableContextMenu(TableRow<Animal> animalTableRow) {
        Animal selectedAnimal = animalTableRow.getItem();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem removePenMenuItem = new MenuItem("Remove "+selectedAnimal.getName() +" From Pen #" + selectedAnimal.getCurrentPenID());
        removePenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AnimalToPenController.removeAnimalFromPen(selectedAnimal);
            }
        });
        contextMenu.getItems().add(removePenMenuItem);
        animalTableRow.contextMenuProperty().bind(
                Bindings.when(animalTableRow.emptyProperty())
                        .then((ContextMenu)null)
                        .otherwise(contextMenu)
        );
    }
}
