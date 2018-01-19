package main.modules.critters;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import main.classes.critters.Animal;
import main.classes.critters.Breed;
import main.modules.critters.models.AnimalModel;
import main.modules.critters.models.BreedModel;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;


public class AnimalController {
    private static TableView<Animal> animalTable;
    private static TableColumn animalBreed;
    private static TableColumn animalID;
    private static TableColumn animalName;
    private static TableColumn animalHasPen;
    private static Label animalTypeLabel;
    private static ObservableList<Animal> animalTableItems = FXCollections.observableArrayList();

    public static Label getAnimalTypeLabel() {
        return animalTypeLabel;
    }

    public static void construct(TableView<Animal> table, TableColumn breed, TableColumn id, TableColumn name, TableColumn hasPen, Label typeLabel) {
        animalTable = table;
        animalBreed = breed;
        animalID = id;
        animalName = name;
        animalHasPen = hasPen;
        animalTypeLabel = typeLabel;

        animalTable.setItems(animalTableItems);
    }

    public static void outline () {
        //CELL VALUE FACTORIES
        animalID.setCellValueFactory( new PropertyValueFactory<Animal, Integer>("ID"));
        animalName.setCellValueFactory( new PropertyValueFactory<Animal, String>("name"));
        animalBreed.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedName());
            }
        });
        animalHasPen.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                if (p.getValue().hasPen()) {
                    return new SimpleStringProperty("Yes");
                } else {
                    return new SimpleStringProperty("No");
                }

            }
        });
        animalTable.setRowFactory( tv -> {
            TableRow<Animal> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY  && (!row.isEmpty()) ) {
                    contextMenu(row);
                }
            });
            return row ;
        });
    }

    public static void refresh (Breed breed) {
        ArrayList<Animal> allAnimalsWhere = AnimalModel.getAllAnimalsWhere(breed);
        if (allAnimalsWhere == null) {
            animalTableItems.clear();
            Alert noAnimals = new Alert(Alert.AlertType.INFORMATION);
            noAnimals.setHeaderText("Caution");
            noAnimals.setContentText("There are no animals of the "+ breed.getName() + " type!");
            noAnimals.showAndWait();

        } else {
            animalTableItems.clear();
            animalTableItems.addAll(allAnimalsWhere);
        }
    }

    public static void refresh () {
        animalTableItems.clear();
        animalTableItems.addAll(AnimalModel.getAllAnimals());
    }

    private static void contextMenu (TableRow<Animal> animalRow) {
        Animal selectedAnimal = animalRow.getItem();
        final ContextMenu contextMenu = new ContextMenu();

        final MenuItem editAnimalMenuItem = new MenuItem("Edit " + selectedAnimal.getName());
        final MenuItem removeAnimalMenuItem = new MenuItem("Remove " + selectedAnimal.getName());
        final MenuItem addAnimaltoPenMenuItem = new MenuItem("Add " + selectedAnimal.getName() + " to Pen");
        final MenuItem removeAnimalfromPenMenuItem = new MenuItem("Remove " + selectedAnimal.getName() + " from Pen");
        final MenuItem addAnimalMenuItem = new MenuItem("Add New " + selectedAnimal.getBreedName() + " Animal");
        editAnimalMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editAnimal(selectedAnimal);
            }
        });
        removeAnimalMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeAnimal(selectedAnimal);
            }
        });
        addAnimaltoPenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AnimalToPenController.addAnimalToPen(selectedAnimal);
            }
        });
        removeAnimalfromPenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AnimalToPenController.removeAnimalFromPen(selectedAnimal);
            }
        });
        addAnimalMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addAnimal(selectedAnimal.getBreed());
            }
        });
        contextMenu.getItems().add(editAnimalMenuItem);
        contextMenu.getItems().add(removeAnimalMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        if (animalRow.getItem().hasPen()) {
            contextMenu.getItems().add(removeAnimalfromPenMenuItem);
        } else {
            contextMenu.getItems().add(addAnimaltoPenMenuItem);
        }
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(addAnimalMenuItem);

        animalRow.contextMenuProperty().bind(
                Bindings.when(animalRow.emptyProperty())
                        .then((ContextMenu)null)
                        .otherwise(contextMenu)
        );
    }

    public static void addAnimal (Breed breed) {
        Dialog<Animal> dialog = new Dialog<>();
        dialog.setTitle("Add Animal");
        dialog.setHeaderText("Add a new animal: ");
        dialog.setResizable(true);

        Label nameLabel= new Label("Name: ");
        TextField nameTextField = new TextField();
        Label breedLabel = new Label("Breed: ");
        ObservableList<String> breedNames = FXCollections.observableArrayList();
        breedNames.addAll(BreedModel.getAllBreedNames());
        ChoiceBox<String> breedChoiceBox =  new ChoiceBox<>(breedNames);

        if (breed != null) {
            breedChoiceBox.getSelectionModel().select(breed.getName());
        }


        GridPane animalDialogGridPane = new GridPane();
        animalDialogGridPane.add(nameLabel, 1, 1);
        animalDialogGridPane.add(nameTextField, 2, 1);
        animalDialogGridPane.add(breedLabel, 1, 2);
        animalDialogGridPane.add(breedChoiceBox, 2, 2);
        dialog.getDialogPane().setContent(animalDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Animal>() {
            @Override
            public Animal call(ButtonType button) {
                if (button == buttonTypeOk) {
                    return new Animal(
                            nameTextField.getText(),
                            BreedModel.getABreedWhere(breedChoiceBox.getSelectionModel().getSelectedItem())
                    );
                }
                return null;
            }
        });

        Optional<Animal> result = dialog.showAndWait();
        if (result.isPresent()) {
            AnimalModel.addAnimal(result.get());
            refresh(result.get().getBreed());
        }
    }

    public static void editAnimal (Animal animal) {
        Dialog<Animal> dialog = new Dialog<>();
        dialog.setTitle("Edit Animal");
        dialog.setHeaderText("Edit " + animal.getName() + ": ");
        dialog.setResizable(true);

        Label nameLabel = new Label("Name: ");
        Label breedLabel = new Label("Breed: ");

        TextField nameTextField = new TextField();
        ChoiceBox<String> breedChoiceBox = new ChoiceBox<>();
        ObservableList<String> breedNames = FXCollections.observableArrayList(BreedModel.getAllBreedNames());
        breedChoiceBox.setItems(breedNames);
        breedChoiceBox.getSelectionModel().select(animal.getBreedName());

        nameTextField.setText(animal.getName());

        nameTextField.setOnKeyPressed(new EventHandler<KeyEvent> () {
            @Override
            public void handle(KeyEvent event) {
                dialog.setHeaderText("Edit " + nameTextField.getText() + ": ");
            }
        } );


        GridPane grid = new GridPane();
        grid.add(nameLabel, 1, 1);
        grid.add(nameTextField, 2, 1);
        grid.add(breedLabel, 1, 2);
        grid.add(breedChoiceBox, 2, 2);

        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Animal>() {
            @Override
            public Animal call(ButtonType button) {
                if (button == buttonTypeOk) {
                    Animal tempAnimal = new Animal (
                            nameTextField.getText(),
                            BreedModel.getABreedWhere(breedChoiceBox.getSelectionModel().getSelectedItem())
                    );
                    tempAnimal.setID(animal.getID());
                    return tempAnimal;
                }
                return null;
            }
        });

        Optional<Animal> result = dialog.showAndWait();
        if (result.isPresent()) {
            AnimalModel.editAnimal(result.get());
            refresh(result.get().getBreed());
        }
    }

    public static void removeAnimal (Animal animal) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you wish to delete " +animal.getName()+ "?");
        alert.setContentText("You cannot undo this action!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            AnimalModel.removeAnimal(animal);
            refresh(animal.getBreed());
        } else {
            System.out.println(animal.getName() + " will not be deleted");
        }
    }
}
