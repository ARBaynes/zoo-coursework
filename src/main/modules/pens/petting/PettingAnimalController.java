package main.modules.pens.petting;

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
import main.classes.pens.Petting;
import main.modules.critters.AnimalToPenController;

public class PettingAnimalController {

    protected static ObservableList<Animal> pettingAnimalTableViewItems = FXCollections.observableArrayList();

    protected static Label pettingAnimalLabel;
    protected static TableView pettingAnimalTableView;
    protected static TableColumn pettingAnimalName;
    protected static TableColumn pettingAnimalBreed;
    protected static TableColumn pettingAnimalRequirements;
    protected static TableColumn pettingAnimalID;

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

    public static Label getPettingAnimalLabel () {
        return pettingAnimalLabel;
    }

    private static void pettingAnimalTableContextMenu(TableRow<Animal> animalTableRow) {
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
