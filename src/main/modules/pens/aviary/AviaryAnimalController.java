package main.modules.pens.aviary;

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
import main.classes.critters.Animal;
import main.classes.pens.Aviary;
import main.modules.critters.AnimalToPenController;

public class AviaryAnimalController {

    protected static ObservableList<Animal> aviaryAnimalTableViewItems = FXCollections.observableArrayList();

    protected static Label aviaryAnimalLabel;
    protected static TableView aviaryAnimalTableView;
    protected static TableColumn aviaryAnimalName;
    protected static TableColumn aviaryAnimalBreed;
    protected static TableColumn aviaryAnimalRequirements;
    protected static TableColumn aviaryAnimalID;

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
                    aviaryAnimalTableContextMenu(penRow);
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

    public static Label getAviaryAnimalLabel () {
        return aviaryAnimalLabel;
    }

    private static void aviaryAnimalTableContextMenu(TableRow<Animal> animalTableRow) {
        Animal selectedAnimal = animalTableRow.getItem();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem removePenMenuItem = new MenuItem("Remove "+selectedAnimal.getName() +" From Pen");
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
