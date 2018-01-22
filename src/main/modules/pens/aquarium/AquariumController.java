package main.modules.pens.aquarium;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import main.classes.pens.Aquarium;
import main.classes.staff.Staff;
import main.modules.critters.AnimalController;
import main.modules.staff.StaffModel;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class AquariumController {
    private static ObservableList<Aquarium> aquariumTableViewItems = FXCollections.observableArrayList();
    private static ToolBar aquariumToolbar;
    private static Button addAquariumButton;
    private static TableView aquariumPenTableView;
    private static TableColumn aquariumPenID;
    private static TableColumn aquariumTemp;
    private static TableColumn aquariumContainedAnimals;
    private static TableColumn aquariumHeight;
    private static TableColumn aquariumWaterVolume;
    private static TableColumn aquariumCurrentVolume;
    private static TableColumn aquariumWaterType;
    private static TableColumn aquariumKeeperID;

    private static String currentPenID;


    public static void construct (ToolBar toolBar, Button addButton, TableView tableView, TableColumn id, TableColumn temp,
                                  TableColumn containedAnimals, TableColumn height, TableColumn waterVolume, TableColumn currentVolume,
                                  TableColumn waterType, TableColumn keeperID) {
        aquariumToolbar = toolBar;
        addAquariumButton = addButton;
        aquariumPenTableView = tableView;
        aquariumPenID = id;
        aquariumTemp = temp;
        aquariumContainedAnimals = containedAnimals;
        aquariumHeight = height;
        aquariumWaterVolume = waterVolume;
        aquariumCurrentVolume = currentVolume;
        aquariumWaterType = waterType;
        aquariumKeeperID = keeperID;


        aquariumPenTableView.setItems(aquariumTableViewItems);
    }

    public static void outline () {
        aquariumPenID.setCellValueFactory( new PropertyValueFactory<Aquarium, Integer>("penID"));
        aquariumTemp.setCellValueFactory( new PropertyValueFactory<Aquarium, Double>("temperature"));
        aquariumWaterVolume.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Aquarium, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Aquarium, Double> p) {
                return new SimpleDoubleProperty(p.getValue().getVolume()).asObject();
            }
        });
        aquariumCurrentVolume.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Aquarium, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Aquarium, Double> p) {
                return new SimpleDoubleProperty(p.getValue().getCurrentVolume()).asObject();
            }
        });
        aquariumContainedAnimals.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Aquarium, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Aquarium, Integer> p) {
                return new SimpleIntegerProperty(p.getValue().getContainedAnimalNumber()).asObject();
            }
        });
        aquariumHeight.setCellValueFactory( new PropertyValueFactory<Aquarium, Double>("height"));

        aquariumWaterType.setCellValueFactory( new PropertyValueFactory<Aquarium, String>("waterType"));

        aquariumKeeperID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Aquarium, Integer>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Aquarium, Integer> p) {
                return new SimpleIntegerProperty(p.getValue().getKeeperID());
            }
        });

        aquariumPenTableView.setRowFactory( tv -> {
            TableRow<Aquarium> penRow = new TableRow<>();
            penRow.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && (!penRow.isEmpty()) ) {
                    Aquarium pen = penRow.getItem();
                    currentPenID = pen.getPenID();
                    //AquariumAnimalController.getAquariumAnimalLabel().setText("Aquarium #" + penRow.getItem().getPenID() + ": ");
                    if (pen.getContainedAnimals().isEmpty() || pen.getContainedAnimals() == null ) {
                        Alert noAnimals = new Alert(Alert.AlertType.INFORMATION);
                        noAnimals.setHeaderText("Caution");
                        noAnimals.setContentText("There are no animals in pen #"+ pen.getPenID() + "!");
                        noAnimals.showAndWait();
                    } else {
                        AquariumAnimalController.refresh(penRow.getItem());
                    }
                }
                if (event.getButton() == MouseButton.SECONDARY  && (!penRow.isEmpty()) ) {
                    aquariumTableContextMenu(penRow);
                }
            });
            return penRow ;
        });

        addAquariumButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addAquarium();
            }
        });
    }

    public static void refresh () {
        aquariumTableViewItems.clear();
        aquariumTableViewItems.addAll(AquariumModel.getAllPens());
    }

    private static void aquariumTableContextMenu(TableRow<Aquarium> penRow) {
        Aquarium selectedPen = penRow.getItem();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem addNewPenMenuItem = new MenuItem("Add New Aquarium");
        final MenuItem editPenMenuItem = new MenuItem("Edit Aquarium #" + selectedPen.getPenID());
        final MenuItem removePenMenuItem = new MenuItem("Remove Aquarium #" + selectedPen.getPenID());

        addNewPenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addAquarium();
            }
        });
        editPenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editAquarium(penRow.getItem());
            }
        });
        removePenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeAquarium(penRow.getItem());
            }
        });
        contextMenu.getItems().add(addNewPenMenuItem);
        contextMenu.getItems().add(editPenMenuItem);
        contextMenu.getItems().add(removePenMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        // Set context menu on row, but use a binding to make it only show for non-empty rows:
        penRow.contextMenuProperty().bind(
                Bindings.when(penRow.emptyProperty())
                        .then((ContextMenu)null)
                        .otherwise(contextMenu)
        );
    }

    public static void addAquarium () {
        Dialog<Aquarium> dialog = new Dialog<>();
        dialog.setTitle("Add Aquarium");
        dialog.setHeaderText("Add a new aquarium: ");
        dialog.setResizable(true);

        Label lengthLabel= new Label("Length: ");
        TextField lengthTextField = new TextField();
        Label widthLabel = new Label("Width: ");
        TextField widthTextField =  new TextField();
        Label heightLabel = new Label("Height: ");
        TextField heightTextField =  new TextField();


        Label waterTypeLabel = new Label("Water Type: ");
        ChoiceBox<String> waterTypeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("salt", "fresh"));
        GridPane waterTypePane = new GridPane();
        waterTypePane.add(waterTypeChoiceBox, 1, 1);
        waterTypePane.add(new Label(" Water"), 2, 1);


        Label tempLabel = new Label("Temperature: ");
        TextField tempTextField =  new TextField();
        Label staffLabel = new Label("Staff Responsible: #");
        ChoiceBox<Integer> staffChoiceBox = new ChoiceBox<>();

        ObservableList<Integer> allStaff = FXCollections.observableArrayList();
        allStaff.addAll(StaffModel.extractStaffIDs(StaffModel.getAllStaffBy("aquarium")));
        staffChoiceBox.setItems(allStaff);
        staffChoiceBox.getSelectionModel().selectFirst();

        GridPane aquariumDialogGridPane = new GridPane();
        aquariumDialogGridPane.add(lengthLabel, 1, 1);
        aquariumDialogGridPane.add(lengthTextField, 2, 1);
        aquariumDialogGridPane.add(widthLabel, 1, 2);
        aquariumDialogGridPane.add(widthTextField, 2, 2);
        aquariumDialogGridPane.add(heightLabel, 1, 3);
        aquariumDialogGridPane.add(heightTextField, 2, 3);
        aquariumDialogGridPane.add(waterTypeLabel, 1, 4);
        aquariumDialogGridPane.add(waterTypePane, 2, 4);
        aquariumDialogGridPane.add(tempLabel, 1, 5);
        aquariumDialogGridPane.add(tempTextField, 2, 5);
        aquariumDialogGridPane.add(staffLabel, 1, 6);
        aquariumDialogGridPane.add(staffChoiceBox, 2, 6);

        dialog.getDialogPane().setContent(aquariumDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Aquarium>() {
            @Override
            public Aquarium call(ButtonType button) {
                if (button == buttonTypeOk) {
                    Aquarium pen = new Aquarium(
                            Double.parseDouble(lengthTextField.getText()),
                            Double.parseDouble(widthTextField.getText()),
                            Double.parseDouble(heightTextField.getText()),
                            Double.parseDouble(tempTextField.getText()),
                            waterTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                            staffChoiceBox.getSelectionModel().getSelectedItem()
                    );
                    return pen;
                }
                return null;
            }
        });

        Optional<Aquarium> result = dialog.showAndWait();
        if (result.isPresent()) {
            AquariumModel.addPen(result.get());
            refresh();
        }
    }

    public static void editAquarium (Aquarium aquarium) {
        Dialog<Aquarium> dialog = new Dialog<>();
        dialog.setTitle("Edit Aquarium");
        dialog.setHeaderText("Edit Aquarium #" + aquarium.getPenID());
        dialog.setResizable(true);

        Label lengthLabel= new Label("Length: ");
        TextField lengthTextField = new TextField(aquarium.getLength().toString());
        Label widthLabel = new Label("Width: ");
        TextField widthTextField =  new TextField(aquarium.getWidth().toString());
        Label heightLabel = new Label("Height: ");
        TextField heightTextField =  new TextField(aquarium.getHeight().toString());


        Label waterTypeLabel = new Label("Water Type: ");
        ChoiceBox<String> waterTypeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("salt", "fresh"));
        waterTypeChoiceBox.getSelectionModel().select(aquarium.getWaterType().toLowerCase());
        GridPane waterTypePane = new GridPane();
        waterTypePane.add(waterTypeChoiceBox, 1, 1);
        waterTypePane.add(new Label(" Water"), 2, 1);


        Label tempLabel = new Label("Temperature: ");
        TextField tempTextField =  new TextField(aquarium.getTemperature().toString());
        Label staffLabel = new Label("Staff Responsible: #");
        ChoiceBox<Integer> staffChoiceBox = new ChoiceBox<>();

        ObservableList<Integer> allStaff = FXCollections.observableArrayList();
        allStaff.addAll(StaffModel.extractStaffIDs(StaffModel.getAllStaffBy("aquarium")));
        staffChoiceBox.setItems(allStaff);
        staffChoiceBox.getSelectionModel().select(aquarium.getKeeperID());

        GridPane aquariumDialogGridPane = new GridPane();
        aquariumDialogGridPane.add(lengthLabel, 1, 1);
        aquariumDialogGridPane.add(lengthTextField, 2, 1);
        aquariumDialogGridPane.add(widthLabel, 1, 2);
        aquariumDialogGridPane.add(widthTextField, 2, 2);
        aquariumDialogGridPane.add(heightLabel, 1, 3);
        aquariumDialogGridPane.add(heightTextField, 2, 3);
        aquariumDialogGridPane.add(waterTypeLabel, 1, 4);
        aquariumDialogGridPane.add(waterTypePane, 2, 4);
        aquariumDialogGridPane.add(tempLabel, 1, 5);
        aquariumDialogGridPane.add(tempTextField, 2, 5);
        aquariumDialogGridPane.add(staffLabel, 1, 6);
        aquariumDialogGridPane.add(staffChoiceBox, 2, 6);

        dialog.getDialogPane().setContent(aquariumDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Aquarium>() {
            @Override
            public Aquarium call(ButtonType button) {
                if (button == buttonTypeOk) {
                    Aquarium pen = new Aquarium(
                            Double.parseDouble(lengthTextField.getText()),
                            Double.parseDouble(widthTextField.getText()),
                            Double.parseDouble(heightTextField.getText()),
                            Double.parseDouble(tempTextField.getText()),
                            waterTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                            staffChoiceBox.getSelectionModel().getSelectedItem()
                    );
                    pen.setPenID(aquarium.getPenID());
                    return pen;
                }
                return null;
            }
        });

        Optional<Aquarium> result = dialog.showAndWait();
        if (result.isPresent()) {
            AquariumModel.editPen(result.get());
            refresh();
        }
    }

    public static void removeAquarium(Aquarium aquarium) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you wish to delete " +aquarium.getPenID()+ "?");
        alert.setContentText("You cannot undo this action, and all animals within this pen will be released!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            aquarium.removeAllAnimalsFromPen();
            AquariumModel.removePen(aquarium);
            refresh();
            AnimalController.refresh();
            AquariumAnimalController.refresh();

        } else {
            System.out.println(aquarium.getPenID() + " will not be deleted");
        }
    }

}
