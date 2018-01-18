package main.modules.critters;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import main.classes.critters.Animal;
import main.classes.critters.Breed;
import main.classes.pens.*;
import main.modules.critters.models.AnimalModel;
import main.modules.critters.models.BreedModel;
import main.modules.pens.PenModel;
import main.modules.pens.aquarium.AquariumController;
import main.modules.pens.aquarium.AquariumModel;
import main.modules.pens.aviary.AviaryModel;
import main.modules.pens.dry.DryModel;
import main.modules.pens.petting.PettingModel;
import main.modules.pens.semiaquatic.SemiAquaticModel;

import java.lang.reflect.Array;
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

    public void outlineAnimalTableRows (TableView<Animal> animalTableView, Label animalTypeLabel,
                                        TableView<Animal> aquariumAnimalPenTableView, TableView<Aquarium> aquariumPenTableView,
                                        TableView<Animal> aviaryAnimalPenTableView, TableView<Aviary> aviaryPenTableView,
                                        TableView<Animal> dryAnimalPenTableView, TableView<Dry> dryPenTableView,
                                        TableView<Animal> pettingAnimalPenTableView, TableView<Petting> pettingPenTableView,
                                        TableView<Animal> semiAquaticAnimalPenTableView, TableView<SemiAquatic> semiAquaticPenTableView) {
        animalTableView.setRowFactory( tv -> {
            TableRow<Animal> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY  && (!row.isEmpty()) ) {
                   animalTableContextMenu(row, animalTableView,
                           aquariumAnimalPenTableView, aquariumPenTableView,
                           aviaryAnimalPenTableView, aviaryPenTableView,
                           dryAnimalPenTableView, dryPenTableView,
                           pettingAnimalPenTableView, pettingPenTableView,
                           semiAquaticAnimalPenTableView, semiAquaticPenTableView);
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
        if (allAnimalsWhere == null) {
            Alert noAnimals = new Alert(Alert.AlertType.INFORMATION);
            noAnimals.setHeaderText("Caution");
            noAnimals.setContentText("There are no animals of the "+ breedToFind.getName() + " type!");
            noAnimals.showAndWait();
            return;
        }
        animalTableViewItems.addAll(allAnimalsWhere);
    }
    //DIALOGS

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

    private void animalTableContextMenu(TableRow<Animal> animalRow, TableView<Animal> animalTable,
                                        TableView<Animal> aquariumAnimalPenTableView, TableView<Aquarium> aquariumPenTableView,
                                        TableView<Animal> aviaryAnimalPenTableView, TableView<Aviary> aviaryPenTableView,
                                        TableView<Animal> dryAnimalPenTableView, TableView<Dry> dryPenTableView,
                                        TableView<Animal> pettingAnimalPenTableView, TableView<Petting> pettingPenTableView,
                                        TableView<Animal> semiAquaticAnimalPenTableView, TableView<SemiAquatic> semiAquaticPenTableView) {

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
                editAnimalDialog(animalRow, animalTable);
            }
        });
        removeAnimalMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeAnimalDialog(animalRow, animalTable);
            }
        });
        addAnimaltoPenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addAnimaltoPen(animalRow.getItem(), animalTable, aquariumAnimalPenTableView, aviaryAnimalPenTableView, dryAnimalPenTableView, pettingAnimalPenTableView, semiAquaticAnimalPenTableView);
            }
        });
        removeAnimalfromPenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeAnimalfromPen(animalRow.getItem(), animalTable,
                        aquariumAnimalPenTableView, aquariumPenTableView,
                        aviaryAnimalPenTableView, aviaryPenTableView,
                        dryAnimalPenTableView, dryPenTableView,
                        pettingAnimalPenTableView, pettingPenTableView,
                        semiAquaticAnimalPenTableView, semiAquaticPenTableView);
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
        if (animalRow.getItem().hasPen()) {
            contextMenu.getItems().add(removeAnimalfromPenMenuItem);
        } else {
            contextMenu.getItems().add(addAnimaltoPenMenuItem);
        }
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(addAnimalMenuItem);
        // Set context menu on row, but use a binding to make it only show for non-empty rows:
        animalRow.contextMenuProperty().bind(
                Bindings.when(animalRow.emptyProperty())
                        .then((ContextMenu)null)
                        .otherwise(contextMenu)
        );
    }

    //ANIMAL TO PEN

    private void animalToAquarium (Animal animal, TableView<Animal> animalInPenTableView, TableView<Animal> animalTabTableView) {
        Dialog<Aquarium> dialog = new Dialog<>();
        dialog.setTitle("Add Animal To A Pen");
        dialog.setHeaderText("Add " + animal.getName() + " to " + animal.getBreedPenType());
        dialog.setResizable(true);
        Label desc = new Label("Pens: ");
        ObservableList<Aquarium> pens = FXCollections.observableArrayList();
        ArrayList<Aquarium> aquariums = AquariumModel.getAllAquariumsWithSpaceRemaining(animal);
        for (Aquarium aquarium: aquariums) {
            pens.addAll(aquarium);
        }
        ChoiceBox<Aquarium> penChoiceBox = new ChoiceBox<>(pens);

        TableView<Animal> currentAnimalsInPen = new TableView<>();
        TableColumn<Animal, Integer> animalIDCol = new TableColumn<>("ID");
        animalIDCol.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("ID"));
        TableColumn<Animal, String> animalNameCol = new TableColumn<>("Name");
        animalNameCol.setCellValueFactory(new PropertyValueFactory<Animal, String>("Name"));
        TableColumn<Animal, String> animalBreedCol = new TableColumn<>("Breed");
        animalBreedCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedName());
            }
        });
        TableColumn<Animal, String> animalRequirementsCol = new TableColumn<>("Requirements");
        animalRequirementsCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedRequirementsToString());
            }
        });

        currentAnimalsInPen.getColumns().addAll(animalIDCol, animalNameCol, animalBreedCol, animalRequirementsCol);

        ObservableList<Animal> animalsinPen = FXCollections.observableArrayList();
        currentAnimalsInPen.setItems(animalsinPen);

        penChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends Aquarium> observable, Aquarium oldValue, Aquarium newValue) ->
                        refreshAnimalsInPen(animalsinPen, newValue)
                );

        GridPane animalDialogGridPane = new GridPane();
        animalDialogGridPane.add(desc, 1, 1);
        animalDialogGridPane.add(penChoiceBox, 2, 1);
        animalDialogGridPane.add(currentAnimalsInPen, 2, 2);
        dialog.getDialogPane().setContent(animalDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Aquarium>() {
            @Override
            public Aquarium call(ButtonType button) {
                if (button == buttonTypeOk) {
                    return penChoiceBox.getSelectionModel().getSelectedItem();
                }
                return null;
            }
        });

        Optional<Aquarium> result = dialog.showAndWait();
        if (result.isPresent()) {

            animal.setCurrentPenID(result.get().getPenID());
            AnimalModel.editAnimal(animal);

            result.get().addAnimalToPen(animal);
            AquariumModel.editAquarium(result.get());

            updateObservableAnimalTableData(animal.getBreed());
            animalTabTableView.refresh();


            AquariumController aquariumController = new AquariumController();
            aquariumController.updateObservableTableData();
            animalInPenTableView.refresh();
        }
    }

    private void refreshAnimalsInPen (ObservableList<Animal> animalList, Aquarium pen ) {
        animalList.clear();
        animalList.addAll(pen.getContainedAnimals());
    }

    private void animalToAviary (Animal animal) {
        Dialog<Animal> dialog = new Dialog<>();
        dialog.setTitle("Add Animal To A Pen");
        dialog.setHeaderText("Add " + animal.getName() + " to " + animal.getBreedPenType());
        dialog.setResizable(true);
        Label desc = new Label("Pens: ");
        ArrayList<Aviary> aviaries = AviaryModel.getAllAviaries();
        ObservableList<String> pens = FXCollections.observableArrayList();
        for (Aviary aviary: aviaries) {
            pens.addAll(aviary.toString());
        }
    }

    private void animalToDry (Animal animal) {
        Dialog<Animal> dialog = new Dialog<>();
        dialog.setTitle("Add Animal To A Pen");
        dialog.setHeaderText("Add " + animal.getName() + " to " + animal.getBreedPenType());
        dialog.setResizable(true);
        Label desc = new Label("Pens: ");
        ObservableList<String> pens = FXCollections.observableArrayList();
        ArrayList<Dry> dryArrayList = DryModel.getAllDryPens();
        for (Dry dry : dryArrayList) {
            pens.addAll(dry.toString());
        }
    }

    private void animalToPetting (Animal animal) {
        Dialog<Animal> dialog = new Dialog<>();
        dialog.setTitle("Add Animal To A Pen");
        dialog.setHeaderText("Add " + animal.getName() + " to " + animal.getBreedPenType());
        dialog.setResizable(true);
        Label desc = new Label("Pens: ");
        ObservableList<String> pens = FXCollections.observableArrayList();
        ArrayList<Petting> pettings = PettingModel.getAllPetting();
        for (Petting petting: pettings) {
            pens.addAll(petting.toString());
        }
    }

    private void animalToSemiAquatic (Animal animal) {
        Dialog<Animal> dialog = new Dialog<>();
        dialog.setTitle("Add Animal To A Pen");
        dialog.setHeaderText("Add " + animal.getName() + " to " + animal.getBreedPenType());
        dialog.setResizable(true);
        Label desc = new Label("Pens: ");
        ObservableList<String> pens = FXCollections.observableArrayList();
        ArrayList<SemiAquatic> semiaquatics = SemiAquaticModel.getAllSemiAquatic();
        for (SemiAquatic semiAquatic: semiaquatics) {
            pens.addAll(semiAquatic.toString());
        }
    }

    private void animalToDryOrPetting (Animal animal) {
        Dialog<Animal> dialog = new Dialog<>();
        dialog.setTitle("Add Animal To A Pen");
        dialog.setHeaderText("Add " + animal.getName() + " to " + animal.getBreedPenType());
        dialog.setResizable(true);
        Label desc = new Label("Pens: ");
        ObservableList<Dry> dryPens = FXCollections.observableArrayList();
        ArrayList<Dry> dryArrayList = DryModel.getAllDryPens();
        for (Dry dry: dryArrayList) {
            dryPens.addAll(dry);
        }
        ObservableList<Petting> pettingPens = FXCollections.observableArrayList();
        ArrayList<Petting> pettingArrayList = PettingModel.getAllPetting();
        for (Petting petting: pettingArrayList) {
            pettingPens.addAll(petting);
        }
    }

    public void addAnimaltoPen (Animal animal, TableView<Animal> animalBreedTableView,
                                TableView<Animal> aquariumAnimalPenTableView,
                                TableView<Animal> aviaryAnimalPenTableView,
                                TableView<Animal> dryAnimalPenTableView,
                                TableView<Animal> pettingAnimalPenTableView,
                                TableView<Animal> semiAquaticAnimalPenTableView) {
        System.out.println(animal.getBreedPenType());
        if (!animal.getBreedPenType().toLowerCase().equals("dry or petting")) {
            switch (animal.getBreedPenType().toLowerCase()) {
                case "aquarium":
                    animalToAquarium(animal, animalBreedTableView, aquariumAnimalPenTableView);
                    break;
                case "aviary":
                    animalToAviary(animal);
                    break;
                case "dry":
                    animalToDry(animal);
                    break;
                case "petting":
                   animalToPetting(animal);
                    break;
                case "semiAquatic":
                    animalToSemiAquatic(animal);
                    break;
                default:

            }
        } else {

        }
    }

    public void removeAnimalfromPen (Animal animal, TableView<Animal> animalBreedTableView,
                                TableView<Animal> aquariumAnimalPenTableView, TableView<Aquarium> aquariumPenTableView,
                                TableView<Animal> aviaryAnimalPenTableView, TableView<Aviary> aviaryPenTableView,
                                TableView<Animal> dryAnimalPenTableView, TableView<Dry> dryPenTableView,
                                TableView<Animal> pettingAnimalPenTableView, TableView<Petting> pettingPenTableView,
                                TableView<Animal> semiAquaticAnimalPenTableView, TableView<SemiAquatic> semiAquaticPenTableView) {
        System.out.println(animal.getBreedPenType());
        if (!animal.getBreedPenType().toLowerCase().equals("dry or petting")) {
            switch (animal.getBreedPenType().toLowerCase()) {
                case "aquarium":
                    removeAnimalfromAquarium(animal, animalBreedTableView, aquariumAnimalPenTableView, aquariumPenTableView);
                    break;
                case "aviary":

                    break;
                case "dry":

                    break;
                case "petting":

                    break;
                case "semiAquatic":

                    break;
                default:

            }
        } else {

        }
    }

    public void removeAnimalfromAquarium (Animal animal, TableView<Animal> animalTableView, TableView<Animal> aquariumAnimalPenTableView , TableView<Aquarium> aquariumPenTableView) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText("Are you sure you wish to remove " +animal.getName()+ " from pen #" +animal.getCurrentPenID());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            if (animal.getCurrentPenID() != null) {
                Aquarium pen = AquariumModel.getAquariumBy(animal.getCurrentPenID());
                if (pen != null) {
                    pen.removeAnimalFromPen(animal);

                    animal.setCurrentPenID(null);
                    AnimalModel.editAnimal(animal);
                    updateObservableAnimalTableData(animal.getBreed());
                    animalTableView.refresh();

                    AquariumController aquariumController = new AquariumController();
                    aquariumController.updateAnimalObservableTableData(pen);
                    aquariumController.updateObservableTableData();

                    aquariumController.refreshAnimalPenTable(aquariumAnimalPenTableView);
                    aquariumController.refreshPenTable(aquariumPenTableView);
                }
            }
        } else {
            System.out.println(animal.getName() + " will stay in their pen");
        }

    }

}
