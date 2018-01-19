package main.modules.pens.dry;

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
import main.classes.pens.Dry;
import main.modules.critters.AnimalToPenController;

public class DryAnimalController {

    protected static ObservableList<Animal> dryAnimalTableViewItems = FXCollections.observableArrayList();

    protected static Label dryAnimalLabel;
    protected static TableView dryAnimalTableView;
    protected static TableColumn dryAnimalName;
    protected static TableColumn dryAnimalBreed;
    protected static TableColumn dryAnimalRequirements;
    protected static TableColumn dryAnimalID;

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

    public static Label getDryAnimalLabel () {
        return dryAnimalLabel;
    }

    private static void dryAnimalTableContextMenu(TableRow<Animal> animalTableRow) {
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
