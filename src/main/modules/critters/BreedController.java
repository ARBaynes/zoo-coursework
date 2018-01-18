package main.modules.critters;

import javafx.beans.binding.Bindings;
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
import main.modules.critters.models.AnimalModel;
import main.modules.critters.models.BreedModel;
import main.modules.pens.PenModel;

import java.util.ArrayList;
import java.util.Optional;

public class BreedController {
    protected static ObservableList<Breed> breedTableViewItems = FXCollections.observableArrayList();
    protected static TableView<Breed> breedTableView;
    protected static TableColumn breedName;
    protected static TableColumn breedPenType;
    protected static TableColumn breedRequirements;
    protected static Button addBreedButton;

    public static void construct (TableView<Breed> tableView, TableColumn name, TableColumn penType, TableColumn requirements, Button addButton) {
        breedTableView = tableView;
        breedName = name;
        breedPenType = penType;
        breedRequirements = requirements;
        addBreedButton = addButton;

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
        contextMenu.getItems().add(addBreedMenuItem);
        contextMenu.getItems().add(editBreedMenuItem);
        contextMenu.getItems().add(removeBreedMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        contextMenu.getItems().add(addAnimalMenuItem);
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

        penTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    String selectedpen = penTypeChoiceBox.getSelectionModel().getSelectedItem();
                    requirementsGridPane.getChildren().clear();
                    switch (selectedpen) {
                        case "semiaquatic" :
                            requirementsGridPane.add(areaRequirementsTextField, 1, 1);
                            requirementsGridPane.add(new Label(" m. squared"), 2, 1);
                        case "aviary" :
                        case "aquarium" :
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
            refresh();
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


        penTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    String selectedpen = penTypeChoiceBox.getSelectionModel().getSelectedItem();
                    requirementsGridPane.getChildren().clear();
                    switch (selectedpen) {
                        case "semiaquatic":
                            volumeRequirementsTextField.setText(currentBreed.getRequirements().get("water").toString());
                            areaRequirementsTextField.setText(currentBreed.getRequirements().get("land").toString());
                            requirementsGridPane.add(areaRequirementsTextField, 1, 1);
                            requirementsGridPane.add(new Label(" m. squared"), 2, 1);
                            requirementsGridPane.add(volumeRequirementsTextField, 1, 2);
                            requirementsGridPane.add(new Label(" m. cubed"), 2, 2);
                            break;
                        case "aviary":
                        case "aquarium":
                            volumeRequirementsTextField.setText(currentBreed.getRequirements().get("volume").toString());
                            requirementsGridPane.add(volumeRequirementsTextField, 1, 2);
                            requirementsGridPane.add(new Label(" m. cubed"), 2, 2);
                            break;
                        default:
                            areaRequirementsTextField.setText(currentBreed.getRequirements().get("area").toString());
                            requirementsGridPane.add(areaRequirementsTextField, 1, 1);
                            requirementsGridPane.add(new Label(" m. squared"), 2, 1);
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
            BreedModel.editBreed(currentBreed, result.get());
            refresh();
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
        } else {
            System.out.println(chosenBreed.getName() + " will not be deleted");
        }
    }


}
