package main.modules.critters;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import main.classes.critters.Animal;
import main.classes.critters.Breed;
import main.classes.pens.Aquarium;
import main.modules.critters.models.AnimalModel;
import main.modules.critters.models.BreedModel;
import main.modules.pens.PenModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class CritterController {

    //BREED PANEL

    protected ObservableList<Breed> breedTableViewItems = FXCollections.observableArrayList();
    protected ObservableList<Animal> animalTableViewItems = FXCollections.observableArrayList();

    public CritterController () {
        updateObservableBreedTableData();
    }

    //OUTLINE/ROUGH SKETCH TABLE DATA

    public void outlineBreedTableData (TableColumn nameCol, TableColumn penTypeCol, TableColumn requirementsCol) {
        nameCol.setCellValueFactory( new PropertyValueFactory<Breed, String>("name"));
        penTypeCol.setCellValueFactory( new PropertyValueFactory<Breed, String>("penType"));
        requirementsCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Breed, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Breed, String> p) {
                return new SimpleStringProperty(p.getValue().requirementsToString());
            }
        });
    }

    public void outlineBreedTableRows (TableView<Breed> breedTableView, TableView<Animal> animalTableView, Label animalLabel) {
        breedTableView.setRowFactory( tv -> {
            TableRow<Breed> breedRow = new TableRow<>();
            breedRow.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && (!breedRow.isEmpty()) ) {
                    animalLabel.setText(breedRow.getItem().getName() + "s: ");
                    updateObservableAnimalTableData(breedRow.getItem());
                }
                if (event.getButton() == MouseButton.SECONDARY  && (!breedRow.isEmpty()) ) {
                    breedTableContextMenu(breedRow, breedTableView, animalTableView);
                }
            });
            return breedRow ;
        });
    }


    //LOAD AND UPDATE TABLE ITEMS

    public void setBreedTableItems (TableView<Breed> tableView) {
        updateObservableBreedTableData();
        tableView.setItems(breedTableViewItems);
        tableView.refresh();
    }

    private void updateObservableBreedTableData() {
        breedTableViewItems.clear();
        breedTableViewItems.addAll(BreedModel.getAllBreeds());
    }

    private void refreshBreedTable(TableView<Breed> tableView) {
        tableView.refresh();
        updateObservableBreedTableData();
        tableView.refresh();
    }

    //AQUARIUM BUTTON MANIPULATION

    public void setAddBreedButtonAction (Button addBreedButton, TableView<Breed> tableView) {
        addBreedButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addBreedDialog(tableView);
            }
        });
    }


    //DIALOGS

    private void addBreedDialog(TableView<Breed> table) {
        Dialog<Breed> dialog = new Dialog<>();
        dialog.setTitle("Add Breed");
        dialog.setHeaderText("Add a new breed: ");
        dialog.setResizable(true);

        Label nameLabel= new Label("Name: ");
        TextField nameTextField = new TextField();


        Label penTypeLabel = new Label("Pen Type: ");
        ChoiceBox<String> penTypeChoiceBox =  new ChoiceBox<>(PenModel.getAllPenTypes());



        Label requirementsLabel = new Label("Requirements: ");

        TextField areaRequirementsTextField =  new TextField();
        areaRequirementsTextField.setPromptText("Area");
        TextField volumeRequirementsTextField =  new TextField();
        volumeRequirementsTextField.setPromptText("Volume");

        GridPane requirementsGridPane = new GridPane();
        requirementsGridPane.add(new Label ("Select a pen type to add requirements."), 1, 1 );

        penTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    String selectedpen = penTypeChoiceBox.getSelectionModel().getSelectedItem();
                    requirementsGridPane.getChildren().clear();
                    switch (selectedpen) {
                        case "SemiAquatic" :
                            requirementsGridPane.add(areaRequirementsTextField, 1, 1);
                            requirementsGridPane.add(new Label(" m. squared"), 2, 1);
                        case "Aviary" :
                        case "Aquarium" :
                            requirementsGridPane.add(volumeRequirementsTextField, 1, 2);
                            requirementsGridPane.add(new Label(" m. cubed"), 2, 2);
                            break;
                        default:
                            requirementsGridPane.add(areaRequirementsTextField, 1, 1);
                            requirementsGridPane.add(new Label(" m. squared"), 2, 1);
                    }
                });

        GridPane breedDialogGridPane = new GridPane();
        breedDialogGridPane.add(nameLabel, 1, 1);
        breedDialogGridPane.add(nameTextField, 2, 1);
        breedDialogGridPane.add(penTypeLabel, 1, 2);
        breedDialogGridPane.add(penTypeChoiceBox, 2, 2);
        breedDialogGridPane.add(requirementsLabel, 1, 3);
        breedDialogGridPane.add(requirementsGridPane, 2, 3);

        dialog.getDialogPane().setContent(breedDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Breed>() {
            @Override
            public Breed call(ButtonType button) {
                if (button == buttonTypeOk) {
                    ObservableList<Node> requirementNodes = requirementsGridPane.getChildren();
                    Breed breed = null;
                    if (requirementNodes.contains(areaRequirementsTextField) && requirementNodes.contains(volumeRequirementsTextField)) {
                        //SEMIAQUATIC
                        breed = new Breed(nameTextField.getText(),
                                penTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                                Double.parseDouble(areaRequirementsTextField.getText()),
                                Double.parseDouble(volumeRequirementsTextField.getText())
                        );
                        System.out.println("SEMIAQUATIC:" + breed.toString());
                    } else if (!requirementNodes.contains(areaRequirementsTextField) && requirementNodes.contains(volumeRequirementsTextField)) {
                        //AQUARIUM OR AVIARY
                        breed = new Breed(nameTextField.getText(),
                                penTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                                Double.parseDouble(volumeRequirementsTextField.getText()),
                                "volume"
                        );
                        System.out.println("AQUARIUM OR AVIARY: " +breed.toString());
                    } else {
                        //ALL OTHER TYPES
                        breed = new Breed(nameTextField.getText(),
                                penTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                                Double.parseDouble(areaRequirementsTextField.getText()),
                                "area"
                        );
                        System.out.println("ALL OTHER TYPES: " + breed.toString());
                    }
                    return breed;
                }
                return null;
            }
        });

        Optional<Breed> result = dialog.showAndWait();
        if (result.isPresent()) {
            BreedModel.addBreed(result.get());
            refreshBreedTable(table);
        }
    }

    private void editBreedDialog(TableRow<Breed> rowData, TableView<Breed> breedTable) {

    }

    private void removeBreedDialog(TableRow<Breed> rowData, TableView<Breed> breedTable) {

    }

    //CONTEXT MENUS

    private void breedTableContextMenu(TableRow<Breed> breedRow, TableView<Breed> breedTable,
                                       TableView<Animal> animalTableView) {

        Breed selectedBreed = breedRow.getItem();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem addBreedMenuItem = new MenuItem("Add New Breed");
        final MenuItem editBreedMenuItem = new MenuItem("Edit " + selectedBreed.getName());
        final MenuItem removeBreedMenuItem = new MenuItem("Remove " + selectedBreed.getName());
        final MenuItem addAnimalMenuItem = new MenuItem("Add New " + selectedBreed.getName() + " Animal");
        addBreedMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addBreedDialog(breedTable);
            }
        });
        editBreedMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editBreedDialog(breedRow, breedTable);
            }
        });
        removeBreedMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeBreedDialog(breedRow, breedTable);
            }
        });
        addAnimalMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addAnimalDialog(animalTableView, breedRow.getItem());
            }
        });
        contextMenu.getItems().add(addBreedMenuItem);
        contextMenu.getItems().add(editBreedMenuItem);
        contextMenu.getItems().add(removeBreedMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(addAnimalMenuItem);
        // Set context menu on row, but use a binding to make it only show for non-empty rows:
        breedRow.contextMenuProperty().bind(
                Bindings.when(breedRow.emptyProperty())
                        .then((ContextMenu)null)
                        .otherwise(contextMenu)
        );
    }

    //ANIMAL PANE

    //OUTLINE/ROUGH SKETCH TABLE DATA

    public void outlineAnimalTableData (TableColumn IDCol, TableColumn nameCol, TableColumn breedCol, TableColumn hasPenCol) {
        IDCol.setCellValueFactory( new PropertyValueFactory<Animal, Integer>("ID"));
        nameCol.setCellValueFactory( new PropertyValueFactory<Animal, String>("name"));
        breedCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedName());
            }
        });
        hasPenCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                if (p.getValue().hasPen()) {
                    return new SimpleStringProperty("Yes");
                } else {
                    return new SimpleStringProperty("No");
                }

            }
        });
    }

    public void outlineAnimalTableRows (TableView<Animal> animalTableView, Label animalTypeLabel) {
        animalTableView.setRowFactory( tv -> {
            TableRow<Animal> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY  && (!row.isEmpty()) ) {
                   animalTableContextMenu(row, animalTableView);
                }
            });
            return row ;
        });
    }


    //LOAD AND UPDATE TABLE ITEMS

    public void setAnimalTableItems (TableView<Animal> tableView) {
        updateObservableAnimalTableData();
        tableView.setItems(animalTableViewItems);
        tableView.refresh();
    }

    private void updateObservableAnimalTableData() {
        animalTableViewItems.clear();
        animalTableViewItems.addAll(AnimalModel.getAllAnimals());
    }

    private void updateObservableAnimalTableData(Breed breedToFind) {
        animalTableViewItems.clear();
        ArrayList<Animal> allAnimalsWhere = AnimalModel.getAllAnimalsWhere(breedToFind);
        if (allAnimalsWhere.isEmpty()) {
            Alert noAnimals = new Alert(Alert.AlertType.INFORMATION);
            noAnimals.setHeaderText("Caution");
            noAnimals.setContentText("There are no animals of the "+ breedToFind.getName() + " type!");
            noAnimals.showAndWait();
            return;

        }
        animalTableViewItems.addAll(allAnimalsWhere);
    }
    //DIALOGS

    public void addAnimalDialog (TableView<Animal> table) {
        addAnimalDialog(table, null);
    }

    private void addAnimalDialog(TableView<Animal> table, Breed breedData) {
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

        if (breedData != null) {
            breedChoiceBox.getSelectionModel().select(breedData.getName());
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
            table.refresh();
            updateObservableAnimalTableData(result.get().getBreed());
            table.refresh();
        }
    }


    private void editAnimalDialog(TableRow<Animal> animalRow, TableView<Animal> animalTable) {

    }

    private void removeAnimalDialog(TableRow<Animal> animalRow, TableView<Animal> animalTable) {

    }

    private void animalTableContextMenu(TableRow<Animal> animalRow, TableView<Animal> animalTable) {

        Animal selectedAnimal = animalRow.getItem();
        final ContextMenu contextMenu = new ContextMenu();

        final MenuItem editAnimalMenuItem = new MenuItem("Edit " + selectedAnimal.getName());
        final MenuItem removeAnimalMenuItem = new MenuItem("Remove " + selectedAnimal.getName());
        final MenuItem addAnimalMenuItem = new MenuItem("Add New " + selectedAnimal.getBreed() + " Animal");
        editAnimalMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editAnimalDialog(animalRow, animalTable);
            }
        });
        removeAnimalMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeAnimalDialog(animalRow, animalTable);
            }
        });
        addAnimalMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addAnimalDialog(animalTable, selectedAnimal.getBreed());
            }
        });
        contextMenu.getItems().add(editAnimalMenuItem);
        contextMenu.getItems().add(removeAnimalMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(addAnimalMenuItem);
        // Set context menu on row, but use a binding to make it only show for non-empty rows:
        animalRow.contextMenuProperty().bind(
                Bindings.when(animalRow.emptyProperty())
                        .then((ContextMenu)null)
                        .otherwise(contextMenu)
        );
    }
}
