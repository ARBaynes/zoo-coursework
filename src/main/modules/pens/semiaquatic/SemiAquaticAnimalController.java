package main.modules.pens.semiaquatic;

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
import main.classes.pens.SemiAquatic;
import main.modules.critters.AnimalToPenController;

public class SemiAquaticAnimalController {

    protected static ObservableList<Animal> semiAquaticAnimalTableViewItems = FXCollections.observableArrayList();

    protected static Label semiAquaticAnimalLabel;
    protected static TableView semiAquaticAnimalTableView;
    protected static TableColumn semiAquaticAnimalName;
    protected static TableColumn semiAquaticAnimalBreed;
    protected static TableColumn semiAquaticAnimalSize;
    protected static TableColumn semiAquaticAnimalID;

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

    public static Label getSemiAquaticAnimalLabel () {
        return semiAquaticAnimalLabel;
    }

    private static void semiAquaticAnimalTableContextMenu(TableRow<Animal> animalTableRow) {
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
