package controllers.pens.semiaquatic;

import controllers.main.MainController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import classes.pens.SemiAquatic;
import classes.staff.Staff;
import models.pens.SemiAquaticModel;
import controllers.critters.AnimalController;
import models.staff.StaffModel;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class SemiAquaticController {
    private static ObservableList<SemiAquatic> semiAquaticTableViewItems = FXCollections.observableArrayList();
    private static ToolBar semiAquaticToolbar;
    private static Button addSemiAquaticButton;
    private static TableView<SemiAquatic> semiAquaticPenTableView;
    private static TableColumn semiAquaticPenID;
    private static TableColumn semiAquaticTemp;
    private static TableColumn semiAquaticContainedAnimals;
    private static TableColumn semiAquaticWaterVolume;
    private static TableColumn semiAquaticCurrentVolume;
    private static TableColumn semiAquaticMaxArea;
    private static TableColumn semiAquaticCurrentArea;
    private static TableColumn semiAquaticWaterType;
    private static TableColumn semiAquaticKeeperID;

    private static String currentPenID;


    public static void construct (ToolBar toolBar, Button addButton, TableView<SemiAquatic> tableView, TableColumn id, TableColumn temp,
                                  TableColumn containedAnimals, TableColumn waterVolume, TableColumn currentVolume,
                                  TableColumn waterType, TableColumn maxArea, TableColumn currentArea, TableColumn keeperID) {
        addSemiAquaticButton = addButton;
        semiAquaticToolbar = toolBar;
        semiAquaticPenTableView = tableView;
        semiAquaticPenID = id;
        semiAquaticTemp = temp;
        semiAquaticContainedAnimals = containedAnimals;
        semiAquaticWaterVolume = waterVolume;
        semiAquaticCurrentVolume = currentVolume;
        semiAquaticWaterType = waterType;
        semiAquaticCurrentArea = currentArea;
        semiAquaticMaxArea = maxArea;
        semiAquaticKeeperID = keeperID;

        semiAquaticPenTableView.setItems(semiAquaticTableViewItems);
    }

    public static void outline () {
        semiAquaticPenID.setCellValueFactory( new PropertyValueFactory<SemiAquatic, Integer>("penID"));
        semiAquaticTemp.setCellValueFactory( new PropertyValueFactory<SemiAquatic, Double>("temperature"));
        semiAquaticWaterVolume.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<SemiAquatic, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<SemiAquatic, Double> p) {
                return new SimpleDoubleProperty(p.getValue().getWaterVolume()).asObject();
            }
        });
        semiAquaticCurrentVolume.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<SemiAquatic, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<SemiAquatic, Double> p) {
                return new SimpleDoubleProperty(p.getValue().getCurrentWaterVolume()).asObject();
            }
        });
        semiAquaticMaxArea.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<SemiAquatic, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<SemiAquatic, Double> p) {
                return new SimpleDoubleProperty(p.getValue().getArea()).asObject();
            }
        });
        semiAquaticCurrentArea.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<SemiAquatic, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<SemiAquatic, Double> p) {
                return new SimpleDoubleProperty(p.getValue().getCurrentArea()).asObject();
            }
        });
        semiAquaticContainedAnimals.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<SemiAquatic, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<SemiAquatic, Integer> p) {
                return new SimpleIntegerProperty(p.getValue().getContainedAnimalNumber()).asObject();
            }
        });
        semiAquaticKeeperID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SemiAquatic, Integer>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<SemiAquatic, Integer> p) {
                return new SimpleIntegerProperty(p.getValue().getKeeperID());
            }
        });

        semiAquaticWaterType.setCellValueFactory( new PropertyValueFactory<SemiAquatic, String>("waterType"));

        semiAquaticPenTableView.setRowFactory( tv -> {
            TableRow<SemiAquatic> penRow = new TableRow<>();
            penRow.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && (!penRow.isEmpty()) ) {
                    SemiAquatic pen = penRow.getItem();
                    currentPenID = pen.getPenID();
                    if (pen.getContainedAnimals().isEmpty() || pen.getContainedAnimals() == null ) {
                        Alert noAnimals = new Alert(Alert.AlertType.INFORMATION);
                        noAnimals.setHeaderText("Caution");
                        noAnimals.setContentText("There are no animals in pen #"+ pen.getPenID() + "!");
                        noAnimals.showAndWait();
                    } else {
                        SemiAquaticAnimalController.refresh(penRow.getItem());
                    }
                }
                if (event.getButton() == MouseButton.SECONDARY  && (!penRow.isEmpty()) ) {
                    semiAquaticTableContextMenu(penRow);
                }
            });
            return penRow ;
        });

        addSemiAquaticButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addSemiAquatic();
            }
        });
    }

    public static void refresh () {
        semiAquaticTableViewItems.clear();
        semiAquaticTableViewItems.addAll(SemiAquaticModel.getAllPens());
    }

    private static void semiAquaticTableContextMenu(TableRow<SemiAquatic> penRow) {
        SemiAquatic selectedPen = penRow.getItem();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem addNewPenMenuItem = new MenuItem("Add New SemiAquatic Pen");
        final MenuItem editPenMenuItem = new MenuItem("Edit SemiAquatic pen #" + selectedPen.getPenID());
        final MenuItem removePenMenuItem = new MenuItem("Remove SemiAquatic #" + selectedPen.getPenID());

        addNewPenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addSemiAquatic();
            }
        });
        editPenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editSemiAquatic(penRow.getItem());
            }
        });
        removePenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeSemiAquatic(penRow.getItem());
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

    public static void addSemiAquatic () {
        Dialog<SemiAquatic> dialog = new Dialog<>();
        dialog.setTitle("Add SemiAquatic Pen");
        dialog.setHeaderText("Add a new SemiAquatic pen: ");
        dialog.setResizable(true);

        Label lengthLabel= new Label("Length: ");
        TextField lengthTextField = new TextField();
        Label widthLabel = new Label("Width: ");
        TextField widthTextField =  new TextField();
        Label waterLengthLabel = new Label("Water Length: ");
        TextField waterLengthTextField =  new TextField();
        Label waterWidthLabel = new Label("Water Width: ");
        TextField waterWidthTextField =  new TextField();
        Label waterDepthLabel = new Label("Water Depth: ");
        TextField waterDepthTextField =  new TextField();


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
        allStaff.addAll(StaffModel.extractStaffIDs(StaffModel.getAllStaffBy("semiaquatic")));
        staffChoiceBox.setItems(allStaff);
        staffChoiceBox.getSelectionModel().selectFirst();

        Label autoAssignLabel = new Label("Automatically assign a staff member to this pen?");
        CheckBox autoAssignCheckBox = new CheckBox();
        autoAssignLabel.setLabelFor(autoAssignCheckBox);

        autoAssignCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (autoAssignCheckBox.isSelected()) {
                    staffLabel.setVisible(false);
                    staffChoiceBox.setVisible(false);
                } else {
                    staffLabel.setVisible(true);
                    staffChoiceBox.setVisible(true);
                }
            }
        });

        GridPane penDialogGridPane = new GridPane();
        penDialogGridPane.add(lengthLabel, 1, 1);
        penDialogGridPane.add(lengthTextField, 2, 1);
        penDialogGridPane.add(widthLabel, 1, 2);
        penDialogGridPane.add(widthTextField, 2, 2);
        penDialogGridPane.add(waterLengthLabel, 1, 3);
        penDialogGridPane.add(waterLengthTextField, 2, 3);
        penDialogGridPane.add(waterWidthLabel, 1, 4);
        penDialogGridPane.add(waterWidthTextField, 2, 4);
        penDialogGridPane.add(waterDepthLabel, 1, 5);
        penDialogGridPane.add(waterDepthTextField, 2, 5);
        penDialogGridPane.add(waterTypeLabel, 1, 6);
        penDialogGridPane.add(waterTypePane, 2, 6);
        penDialogGridPane.add(tempLabel, 1, 7);
        penDialogGridPane.add(tempTextField, 2, 7);
        penDialogGridPane.add(autoAssignLabel, 1, 8);
        penDialogGridPane.add(autoAssignCheckBox, 2, 8);
        penDialogGridPane.add(staffLabel, 1, 9);
        penDialogGridPane.add(staffChoiceBox, 2, 9);

        dialog.getDialogPane().setContent(penDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.setResultConverter(new Callback<ButtonType, SemiAquatic>() {
            @Override
            public SemiAquatic call(ButtonType button) {
                if (button == buttonTypeOk) {
                    SemiAquatic pen;
                    if (autoAssignCheckBox.isSelected()) {
                        ArrayList<Staff> allStaff = StaffModel.getAllStaffBy("semiaquatic");
                        Random randy = new Random();
                        pen = new SemiAquatic( Double.parseDouble(lengthTextField.getText()),
                                Double.parseDouble(widthTextField.getText()),
                                Double.parseDouble(tempTextField.getText()),
                                waterTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                                Double.parseDouble(waterDepthTextField.getText()),
                                Double.parseDouble(waterLengthTextField.getText()),
                                Double.parseDouble(waterWidthTextField.getText()),
                                allStaff.get(randy.nextInt(allStaff.size())).getStaffID()
                        );
                    } else {
                        pen = new SemiAquatic(
                                Double.parseDouble(lengthTextField.getText()),
                                Double.parseDouble(widthTextField.getText()),
                                Double.parseDouble(tempTextField.getText()),
                                waterTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                                Double.parseDouble(waterDepthTextField.getText()),
                                Double.parseDouble(waterLengthTextField.getText()),
                                Double.parseDouble(waterWidthTextField.getText()),
                                staffChoiceBox.getSelectionModel().getSelectedItem()
                        );
                    }
                    return pen;
                }
                return null;
            }
        });

        Optional<SemiAquatic> result = dialog.showAndWait();
        if (result.isPresent()) {
            SemiAquaticModel.addPen(result.get());
            refresh();
            MainController.refresh();
        }
    }

    public static void editSemiAquatic (SemiAquatic semiAquatic) {
        Dialog<SemiAquatic> dialog = new Dialog<>();
        dialog.setTitle("Edit SemiAquatic Pen");
        dialog.setHeaderText("Edit SemiAquatic Pen #" + semiAquatic.getPenID());
        dialog.setResizable(true);

        Label lengthLabel= new Label("Length: ");
        TextField lengthTextField = new TextField(semiAquatic.getLength().toString());
        Label widthLabel = new Label("Width: ");
        TextField widthTextField =  new TextField(semiAquatic.getWidth().toString());
        Label waterLengthLabel = new Label("Water Length: ");
        TextField waterLengthTextField =  new TextField(semiAquatic.getWaterLength().toString());
        Label waterWidthLabel = new Label("Water Width: ");
        TextField waterWidthTextField =  new TextField(semiAquatic.getWaterWidth().toString());
        Label waterDepthLabel = new Label("Water Depth: ");
        TextField waterDepthTextField =  new TextField(semiAquatic.getWaterDepth().toString());



        Label waterTypeLabel = new Label("Water Type: ");
        ChoiceBox<String> waterTypeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("salt", "fresh"));
        waterTypeChoiceBox.getSelectionModel().select(semiAquatic.getWaterType().toLowerCase());
        GridPane waterTypePane = new GridPane();
        waterTypePane.add(waterTypeChoiceBox, 1, 1);
        waterTypePane.add(new Label(" Water"), 2, 1);


        Label tempLabel = new Label("Temperature: ");
        TextField tempTextField =  new TextField(semiAquatic.getTemperature().toString());
        Label staffLabel = new Label("Staff Responsible: #");
        ChoiceBox<Integer> staffChoiceBox = new ChoiceBox<>();

        ObservableList<Integer> allStaff = FXCollections.observableArrayList();
        allStaff.addAll(StaffModel.extractStaffIDs(StaffModel.getAllStaffBy("semiaquatic")));
        staffChoiceBox.setItems(allStaff);
        staffChoiceBox.getSelectionModel().select(semiAquatic.getKeeperID());

        GridPane penDialogGridPane = new GridPane();
        penDialogGridPane.add(lengthLabel, 1, 1);
        penDialogGridPane.add(lengthTextField, 2, 1);
        penDialogGridPane.add(widthLabel, 1, 2);
        penDialogGridPane.add(widthTextField, 2, 2);
        penDialogGridPane.add(waterLengthLabel, 1, 3);
        penDialogGridPane.add(waterLengthTextField, 2, 3);
        penDialogGridPane.add(waterWidthLabel, 1, 4);
        penDialogGridPane.add(waterWidthTextField, 2, 4);
        penDialogGridPane.add(waterDepthLabel, 1, 5);
        penDialogGridPane.add(waterDepthTextField, 2, 5);
        penDialogGridPane.add(waterTypeLabel, 1, 6);
        penDialogGridPane.add(waterTypePane, 2, 6);
        penDialogGridPane.add(tempLabel, 1, 7);
        penDialogGridPane.add(tempTextField, 2, 7);
        penDialogGridPane.add(staffLabel, 1, 8);
        penDialogGridPane.add(staffChoiceBox, 2, 8);

        dialog.getDialogPane().setContent(penDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, SemiAquatic>() {
            @Override
            public SemiAquatic call(ButtonType button) {
                if (button == buttonTypeOk) {
                    SemiAquatic pen = new SemiAquatic(
                            Double.parseDouble(lengthTextField.getText()),
                            Double.parseDouble(widthTextField.getText()),
                            Double.parseDouble(tempTextField.getText()),
                            waterTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                            Double.parseDouble(waterDepthTextField.getText()),
                            Double.parseDouble(waterLengthTextField.getText()),
                            Double.parseDouble(waterWidthTextField.getText()),
                            staffChoiceBox.getSelectionModel().getSelectedItem()
                    );
                    pen.setPenID(semiAquatic.getPenID());
                    return pen;
                }
                return null;
            }
        });

        Optional<SemiAquatic> result = dialog.showAndWait();
        if (result.isPresent()) {
            SemiAquaticModel.editPen(result.get());
            refresh();
            MainController.refresh();
        }
    }

    public static void removeSemiAquatic(SemiAquatic semiAquatic) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you wish to delete " +semiAquatic.getPenID()+ "?");
        alert.setContentText("You cannot undo this action, and all animals within this pen will be released!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            semiAquatic.removeAllAnimalsFromPen();
            SemiAquaticModel.removePen(semiAquatic);
            refresh();
            AnimalController.refresh();
            SemiAquaticAnimalController.refresh();
            MainController.refresh();
        } else {
            System.out.println(semiAquatic.getPenID() + " will not be deleted");
        }
    }

}
