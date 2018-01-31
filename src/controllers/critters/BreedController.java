package controllers.critters;

import controllers.main.MainController;
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
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import classes.critters.Animal;
import classes.critters.Breed;
import models.critters.AnimalModel;
import models.critters.BreedModel;
import models.pens.PenModel;

import java.util.ArrayList;
import java.util.Optional;

public class BreedController {
    private static ObservableList<Breed> breedTableViewItems = FXCollections.observableArrayList();

    private static TableView<Breed> breedTableView;
    private static TableColumn breedName;
    private static TableColumn breedPenType;
    private static TableColumn breedRequirements;
    private static TableColumn breedDislikes;
    private static Button addBreedButton;

    private static ObservableList<Breed> breedDislikesTableViewItems = FXCollections.observableArrayList();
    private static TableView<Breed> breedDislikesTableView = new TableView<>();
    private static TableColumn breedDislikesNames = new TableColumn("Name");

    public static void construct (TableView<Breed> tableView, TableColumn name, TableColumn penType, TableColumn requirements, TableColumn dislikes, Button addButton) {
        breedTableView = tableView;
        breedName = name;
        breedPenType = penType;
        breedRequirements = requirements;
        breedDislikes = dislikes;
        addBreedButton = addButton;

        breedDislikesTableView.getColumns().add(breedDislikesNames);
        breedDislikesTableView.setItems(breedDislikesTableViewItems);
        breedDislikesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        breedTableView.setItems(breedTableViewItems);
    }

    public static void outline () {
        breedName.setCellValueFactory( new PropertyValueFactory<Breed, String>("name"));
        breedPenType.setCellValueFactory( new PropertyValueFactory<Breed, String>("penType"));
        breedRequirements.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Breed, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Breed, String> p) {
                return new SimpleStringProperty(p.getValue().requirementsToString());
            }
        });

        breedDislikes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Breed, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Breed, String> p) {
                if (!p.getValue().getCannotLiveWith().isEmpty()) {
                    return new SimpleStringProperty(p.getValue().getCannotLiveWithToString());
                } else {
                    return new SimpleStringProperty("No Dislikes");
                }
            }
        });

        breedDislikesNames.setCellValueFactory( new PropertyValueFactory<Breed, String>("name"));

        breedTableView.setRowFactory( tv -> {
            TableRow<Breed> breedRow = new TableRow<>();
            breedRow.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && (!breedRow.isEmpty()) ) {
                    AnimalController.getAnimalTypeLabel().setText(breedRow.getItem().getName() + "s: ");
                    AnimalController.refresh(breedRow.getItem());
                }
                if (event.getButton() == MouseButton.SECONDARY  && (!breedRow.isEmpty()) ) {
                    contextMenu(breedRow);
                }
            });
            return breedRow ;
        });

        addBreedButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addBreed();
            }
        });
    }

    public static void refresh () {
        breedTableViewItems.clear();
        breedTableViewItems.addAll(BreedModel.getAllBreeds());
    }

    private static void contextMenu (TableRow<Breed> row) {
        Breed selectedBreed = row.getItem();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem addBreedMenuItem = new MenuItem("Add New Breed");
        final MenuItem editBreedMenuItem = new MenuItem("Edit " + selectedBreed.getName());
        final MenuItem removeBreedMenuItem = new MenuItem("Remove " + selectedBreed.getName());
        final MenuItem addAnimalMenuItem = new MenuItem("Add New " + selectedBreed.getName() + " Animal");
        final MenuItem configureBreedDislikes = new MenuItem("Configure " + selectedBreed.getName() + "'s Dislikes");
        addBreedMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addBreed();
            }
        });
        editBreedMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editBreed(selectedBreed);
            }
        });
        removeBreedMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeBreed(selectedBreed);
            }
        });
        addAnimalMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AnimalController.addAnimal(selectedBreed);
            }
        });
        configureBreedDislikes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                configureBreedDislikes(selectedBreed);
            }
        });
        contextMenu.getItems().add(addAnimalMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(editBreedMenuItem);
        contextMenu.getItems().add(removeBreedMenuItem);
        contextMenu.getItems().add(configureBreedDislikes);
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(addBreedMenuItem);

        row.contextMenuProperty().bind(
                Bindings.when(row.emptyProperty())
                        .then((ContextMenu)null)
                        .otherwise(contextMenu)
        );
    }

    public static void addBreed () {
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

        Label volRequirementsLabel = new Label(" m. cubed");
        Label areaRequirementsLabel = new Label(" m. squared");

        requirementsGridPane.add(volumeRequirementsTextField, 1,2);
        requirementsGridPane.add(volRequirementsLabel, 2, 2);
        requirementsGridPane.add(areaRequirementsTextField, 1,3);
        requirementsGridPane.add(areaRequirementsLabel, 2, 3);

        volumeRequirementsTextField.setVisible(false);
        volRequirementsLabel.setVisible(false);
        areaRequirementsTextField.setVisible(false);
        areaRequirementsLabel.setVisible(false);


        penTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    String selectedpen = penTypeChoiceBox.getSelectionModel().getSelectedItem();
                    switch (selectedpen) {
                        case "semiaquatic":
                            volumeRequirementsTextField.setVisible(true);
                            volRequirementsLabel.setVisible(true);
                            areaRequirementsTextField.setVisible(true);
                            areaRequirementsLabel.setVisible(true);
                            break;
                        case "aviary":
                        case "aquarium":
                            volumeRequirementsTextField.setVisible(true);
                            volRequirementsLabel.setVisible(true);
                            areaRequirementsTextField.setVisible(false);
                            areaRequirementsLabel.setVisible(false);
                            break;
                        default:
                            volumeRequirementsTextField.setVisible(false);
                            volRequirementsLabel.setVisible(false);
                            areaRequirementsTextField.setVisible(true);
                            areaRequirementsLabel.setVisible(true);
                            break;
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
                if (button == buttonTypeOk && !penTypeChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
                    Breed breed;
                    switch (penTypeChoiceBox.getSelectionModel().getSelectedItem()) {
                        case "aquarium":
                        case "aviary":
                            breed = new Breed(nameTextField.getText(),
                                    penTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                                    Double.parseDouble(volumeRequirementsTextField.getText()),
                                    "volume"
                            );
                            break;
                        case "dry":
                        case "petting":
                            breed = new Breed(nameTextField.getText(),
                                    penTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                                    Double.parseDouble(areaRequirementsTextField.getText()),
                                    "area"
                            );
                            break;
                        case "semiaquatic":
                            breed = new Breed(nameTextField.getText(),
                                penTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                                Double.parseDouble(areaRequirementsTextField.getText()),
                                Double.parseDouble(volumeRequirementsTextField.getText())
                            );
                            break;
                        default:
                            breed = new Breed(nameTextField.getText(),
                                    penTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                                    Double.parseDouble(areaRequirementsTextField.getText()),
                                    "area"
                            );
                    }
                    return breed;
                }
                return null;
            }
        });

        Optional<Breed> result = dialog.showAndWait();
        if (result.isPresent()) {
            BreedModel.addBreed(result.get());
            refresh();
            MainController.refresh();
        }
    }

    public static void editBreed (Breed currentBreed) {
        Dialog<Breed> dialog = new Dialog<>();
        dialog.setTitle("Edit Breed");
        dialog.setHeaderText("Edit " + currentBreed.getName() + ": ");
        dialog.setResizable(true);

        Label nameLabel= new Label("Name: ");
        TextField nameTextField = new TextField(currentBreed.getName());


        Label penTypeLabel = new Label("Pen Type: ");
        ChoiceBox<String> penTypeChoiceBox =  new ChoiceBox<>(PenModel.getAllPenTypes());



        Label requirementsLabel = new Label("Requirements: ");

        TextField areaRequirementsTextField =  new TextField();
        areaRequirementsTextField.setPromptText("Area");
        TextField volumeRequirementsTextField =  new TextField();
        volumeRequirementsTextField.setPromptText("Volume");

        GridPane requirementsGridPane = new GridPane();
        requirementsGridPane.add(new Label ("Select a pen type to add requirements."), 1, 1 );

        Label volRequirementsLabel = new Label(" m. cubed");
        Label areaRequirementsLabel = new Label(" m. squared");

        requirementsGridPane.add(volumeRequirementsTextField, 1,2);
        requirementsGridPane.add(volRequirementsLabel, 2, 2);
        requirementsGridPane.add(areaRequirementsTextField, 1,3);
        requirementsGridPane.add(areaRequirementsLabel, 2, 3);

        penTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    String selectedpen = penTypeChoiceBox.getSelectionModel().getSelectedItem();
                    switch (selectedpen) {
                        case "semiaquatic":
                            volumeRequirementsTextField.setVisible(true);
                            volRequirementsLabel.setVisible(true);
                            areaRequirementsTextField.setVisible(true);
                            areaRequirementsLabel.setVisible(true);
                            if (currentBreed.getRequirements().get("water") != null) {
                                volumeRequirementsTextField.setText(currentBreed.getRequirements().get("water").toString());
                            }
                            if (currentBreed.getRequirements().get("land") != null) {
                                areaRequirementsTextField.setText(currentBreed.getRequirements().get("land").toString());
                            }
                            break;
                        case "aviary":
                        case "aquarium":
                            volumeRequirementsTextField.setVisible(true);
                            volRequirementsLabel.setVisible(true);
                            areaRequirementsTextField.setVisible(false);
                            areaRequirementsLabel.setVisible(false);
                            if (currentBreed.getRequirements().get("volume") != null) {
                                volumeRequirementsTextField.setText(currentBreed.getRequirements().get("volume").toString());
                            }
                            break;
                        default:
                            volumeRequirementsTextField.setVisible(false);
                            volRequirementsLabel.setVisible(false);
                            areaRequirementsTextField.setVisible(true);
                            areaRequirementsLabel.setVisible(true);
                            if (currentBreed.getRequirements().get("area") != null) {
                                areaRequirementsTextField.setText(currentBreed.getRequirements().get("area").toString());
                            }
                            break;
                    }
                });

        penTypeChoiceBox.getSelectionModel().select(currentBreed.getPenType());

        penTypeChoiceBox.setValue(penTypeChoiceBox.getSelectionModel().getSelectedItem());

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
                    Breed breed;
                    switch (penTypeChoiceBox.getSelectionModel().getSelectedItem()) {
                        case "aquarium":
                        case "aviary":
                            breed = new Breed(nameTextField.getText(),
                                    penTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                                    Double.parseDouble(volumeRequirementsTextField.getText()),
                                    "volume"
                            );
                            break;
                        case "dry":
                        case "petting":
                            breed = new Breed(nameTextField.getText(),
                                    penTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                                    Double.parseDouble(areaRequirementsTextField.getText()),
                                    "area"
                            );
                            break;
                        case "semiaquatic":
                            breed = new Breed(nameTextField.getText(),
                                    penTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                                    Double.parseDouble(areaRequirementsTextField.getText()),
                                    Double.parseDouble(volumeRequirementsTextField.getText())
                            );
                            break;
                        default:
                            breed = new Breed(nameTextField.getText(),
                                    penTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                                    Double.parseDouble(areaRequirementsTextField.getText()),
                                    "area"
                            );
                    }
                    return breed;
                }
                return null;
            }
        });

        Optional<Breed> result = dialog.showAndWait();
        if (result.isPresent()) {
            BreedModel.editBreed(result.get());
            refresh();
            MainController.refresh();
        }

    }

    public static void removeBreed (Breed chosenBreed) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you wish to delete " +chosenBreed.getName()+ "?");
        alert.setContentText("This will delete all animals of this type!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            ArrayList<Animal> animals = AnimalModel.getAllAnimalsWhere(chosenBreed);
            if (animals != null && !animals.isEmpty()) {
                for ( Animal animal : animals ) {
                    AnimalModel.removeAnimal(animal);
                }
            }
            BreedModel.removeBreed(chosenBreed);
            refresh();
            AnimalController.refresh();
            MainController.refresh();
        } else {
            System.out.println(chosenBreed.getName() + " will not be deleted");
        }
    }


    private static void configureBreedDislikes (Breed currentBreed) {
        breedDislikesTableView.getSelectionModel().clearSelection();
        Dialog<ArrayList<Breed>> dialog = new Dialog<>();
        dialog.setTitle("Breed Dislikes");
        dialog.setHeaderText(currentBreed.getName() + "'s Dislikes: ");
        dialog.setResizable(true);
        GridPane breedDialogGridPane = new GridPane();

        refreshBreedDislikes(currentBreed);
        if (!currentBreed.getCannotLiveWith().isEmpty()) {
            for (Breed dislikes : currentBreed.getCannotLiveWith()) {
                breedDislikesTableView.getSelectionModel().select(dislikes);
            }
        }

        breedDialogGridPane.add(new Label(""), 1,1);
        breedDialogGridPane.add(breedDislikesTableView, 1, 2);

        dialog.getDialogPane().setContent(breedDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, ArrayList<Breed>>() {
            @Override
            public ArrayList<Breed> call(ButtonType button) {
                if (button == buttonTypeOk) {
                    return new ArrayList<Breed>(breedDislikesTableView.getSelectionModel().getSelectedItems());
                }
                return null;
            }
        });

        Optional<ArrayList<Breed>> result = dialog.showAndWait();
        if (result.isPresent()) {
            ArrayList<Breed> dislikedBreeds = result.get();
            currentBreed.setCannotLiveWith(dislikedBreeds);
            BreedModel.editBreed(currentBreed);
            refresh();
            MainController.refresh();
        }
    }

    private static void refreshBreedDislikes (Breed currentBreed) {
        breedDislikesTableViewItems.clear();
        breedDislikesTableViewItems.addAll(BreedModel.getAllBreedsExcept(currentBreed));
    }

}
