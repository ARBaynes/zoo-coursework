package controllers.pens.petting;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import classes.pens.Petting;
import classes.staff.Staff;
import models.pens.PettingModel;
import controllers.critters.AnimalController;
import models.staff.StaffModel;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class PettingController {
    private static ObservableList<Petting> pettingTableViewItems = FXCollections.observableArrayList();
    private static ToolBar pettingToolbar;
    private static Button addPettingButton;
    private static TableView<Petting> pettingPenTableView;
    private static TableColumn pettingPenID;
    private static TableColumn pettingTemp;
    private static TableColumn pettingContainedAnimals;
    private static TableColumn pettingMaxArea;
    private static TableColumn pettingCurrentArea;
    private static TableColumn pettingKeeperID;

    private static String currentPenID;


    public static void construct (ToolBar toolBar, Button addButton, TableView<Petting> tableView, TableColumn id, TableColumn temp,
                                  TableColumn containedAnimals, TableColumn maxArea, TableColumn currentArea, TableColumn keeperID) {
        pettingToolbar = toolBar;
        addPettingButton = addButton;
        pettingPenTableView = tableView;
        pettingPenID = id;
        pettingTemp = temp;
        pettingContainedAnimals = containedAnimals;
        pettingMaxArea = maxArea;
        pettingCurrentArea = currentArea;
        pettingKeeperID = keeperID;

        pettingPenTableView.setItems(pettingTableViewItems);
    }

    public static void outline () {
        pettingPenID.setCellValueFactory( new PropertyValueFactory<Petting, Integer>("penID"));
        pettingTemp.setCellValueFactory( new PropertyValueFactory<Petting, Double>("temperature"));
        pettingMaxArea.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Petting, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Petting, Double> p) {
                return new SimpleDoubleProperty(p.getValue().getArea()).asObject();
            }
        });
        pettingCurrentArea.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Petting, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Petting, Double> p) {
                return new SimpleDoubleProperty(p.getValue().getCurrentArea()).asObject();
            }
        });
        pettingContainedAnimals.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Petting, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Petting, Integer> p) {
                return new SimpleIntegerProperty(p.getValue().getContainedAnimalNumber()).asObject();
            }
        });
        pettingKeeperID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Petting, Integer>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Petting, Integer> p) {
                return new SimpleIntegerProperty(p.getValue().getKeeperID());
            }
        });

        pettingPenTableView.setRowFactory( tv -> {
            TableRow<Petting> penRow = new TableRow<>();
            penRow.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && (!penRow.isEmpty()) ) {
                    Petting pen = penRow.getItem();
                    currentPenID = pen.getPenID();
                    if (pen.getContainedAnimals().isEmpty() || pen.getContainedAnimals() == null ) {
                        Alert noAnimals = new Alert(Alert.AlertType.INFORMATION);
                        noAnimals.setHeaderText("Caution");
                        noAnimals.setContentText("There are no animals in pen #"+ pen.getPenID() + "!");
                        noAnimals.showAndWait();
                    } else {
                        PettingAnimalController.refresh(penRow.getItem());
                    }
                }
                if (event.getButton() == MouseButton.SECONDARY  && (!penRow.isEmpty()) ) {
                    dryTableContextMenu(penRow);
                }
            });
            return penRow ;
        });

        addPettingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addPetting();
            }
        });
    }

    public static void refresh () {
        pettingTableViewItems.clear();
        pettingTableViewItems.addAll(PettingModel.getAllPens());
    }

    private static void dryTableContextMenu(TableRow<Petting> penRow) {
        Petting selectedPen = penRow.getItem();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem addNewPenMenuItem = new MenuItem("Add New Petting Pen");
        final MenuItem editPenMenuItem = new MenuItem("Edit Petting Pen #" + selectedPen.getPenID());
        final MenuItem removePenMenuItem = new MenuItem("Remove Petting Pen #" + selectedPen.getPenID());

        addNewPenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addPetting();
            }
        });
        editPenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editPetting(penRow.getItem());
            }
        });
        removePenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removePetting(penRow.getItem());
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

    public static void addPetting () {
        if (StaffModel.getAllStaffBy("petting").isEmpty() || StaffModel.getAllStaffBy("petting") == null) {
            noKeepersAlert().showAndWait();
            return;
        }

        Dialog<Petting> dialog = new Dialog<>();
        dialog.setTitle("Add Petting Pen");
        dialog.setHeaderText("Add a new petting pen: ");
        dialog.setResizable(true);

        Label lengthLabel= new Label("Length: ");
        TextField lengthTextField = new TextField();
        Label widthLabel = new Label("Width: ");
        TextField widthTextField =  new TextField();
        Label tempLabel = new Label("Temperature: ");
        TextField tempTextField =  new TextField();
        Label staffLabel = new Label("Staff Responsible: #");
        ChoiceBox<Integer> staffChoiceBox = new ChoiceBox<>();

        ObservableList<Integer> allStaff = FXCollections.observableArrayList();
        allStaff.addAll(StaffModel.extractStaffIDs(StaffModel.getAllStaffBy("petting")));
        staffChoiceBox.setItems(allStaff);
        staffChoiceBox.getSelectionModel().selectFirst();

        Label autoAssignLabel = new Label("Automatically assign a staff member to this pen");
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
        penDialogGridPane.add(tempLabel, 1, 3);
        penDialogGridPane.add(tempTextField, 2, 3);
        penDialogGridPane.add(autoAssignLabel, 1, 8);
        penDialogGridPane.add(autoAssignCheckBox, 2, 8);
        penDialogGridPane.add(staffLabel, 1, 9);
        penDialogGridPane.add(staffChoiceBox, 2, 9);

        dialog.getDialogPane().setContent(penDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Petting>() {
            @Override
            public Petting call(ButtonType button) {
                if (button == buttonTypeOk) {
                    Petting pen;
                    if (autoAssignCheckBox.isSelected()) {
                        ArrayList<Staff> allStaff = StaffModel.getAllStaffBy("petting");
                        Random randy = new Random();
                        pen = new Petting( Double.parseDouble(lengthTextField.getText()),
                                Double.parseDouble(widthTextField.getText()),
                                Double.parseDouble(tempTextField.getText()),
                                allStaff.get(randy.nextInt(allStaff.size())).getStaffID()
                        );
                    } else {
                        pen = new Petting(
                                Double.parseDouble(lengthTextField.getText()),
                                Double.parseDouble(widthTextField.getText()),
                                Double.parseDouble(tempTextField.getText()),
                                staffChoiceBox.getSelectionModel().getSelectedItem()
                        );
                    }
                    return pen;
                }
                return null;
            }
        });

        Optional<Petting> result = dialog.showAndWait();
        if (result.isPresent()) {
            PettingModel.addPen(result.get());
            refresh();
            MainController.refresh();
        }
    }

    public static void editPetting(Petting petting) {
        if (StaffModel.getAllStaffBy("petting").isEmpty() || StaffModel.getAllStaffBy("petting") == null) {
            noKeepersAlert(petting).showAndWait();
            return;
        }

        Dialog<Petting> dialog = new Dialog<>();
        dialog.setTitle("Edit Petting Pen");
        dialog.setHeaderText("Edit petting pen #" + petting.getPenID());
        dialog.setResizable(true);

        Label lengthLabel= new Label("Length: ");
        TextField lengthTextField = new TextField(petting.getLength().toString());
        Label widthLabel = new Label("Width: ");
        TextField widthTextField =  new TextField(petting.getWidth().toString());

        Label tempLabel = new Label("Temperature: ");
        TextField tempTextField =  new TextField(petting.getTemperature().toString());
        Label staffLabel = new Label("Staff Responsible: #");
        ChoiceBox<Integer> staffChoiceBox = new ChoiceBox<>();

        ObservableList<Integer> allStaff = FXCollections.observableArrayList();
        allStaff.addAll(StaffModel.extractStaffIDs(StaffModel.getAllStaffBy("petting")));
        staffChoiceBox.setItems(allStaff);
        staffChoiceBox.getSelectionModel().select(petting.getKeeperID());

        GridPane aquariumDialogGridPane = new GridPane();
        aquariumDialogGridPane.add(lengthLabel, 1, 1);
        aquariumDialogGridPane.add(lengthTextField, 2, 1);
        aquariumDialogGridPane.add(widthLabel, 1, 2);
        aquariumDialogGridPane.add(widthTextField, 2, 2);
        aquariumDialogGridPane.add(tempLabel, 1, 3);
        aquariumDialogGridPane.add(tempTextField, 2, 3);
        aquariumDialogGridPane.add(staffLabel, 1, 4);
        aquariumDialogGridPane.add(staffChoiceBox, 2, 4);

        dialog.getDialogPane().setContent(aquariumDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Petting>() {
            @Override
            public Petting call(ButtonType button) {
                if (button == buttonTypeOk) {
                    Petting pen = new Petting(
                            Double.parseDouble(lengthTextField.getText()),
                            Double.parseDouble(widthTextField.getText()),
                            Double.parseDouble(tempTextField.getText()),
                            staffChoiceBox.getSelectionModel().getSelectedItem()
                    );
                    pen.setPenID(petting.getPenID());
                    return pen;
                }
                return null;
            }
        });

        Optional<Petting> result = dialog.showAndWait();
        if (result.isPresent()) {
            PettingModel.editPen(result.get());
            refresh();
            MainController.refresh();
        }
    }

    public static void removePetting(Petting petting) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you wish to delete " +petting.getPenID()+ "?");
        alert.setContentText("You cannot undo this action, and all animals within this pen will be released!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            petting.removeAllAnimalsFromPen();
            PettingModel.removePen(petting);
            refresh();
            AnimalController.refresh();
            PettingAnimalController.refresh();
            MainController.refresh();
        } else {
            System.out.println(petting.getPenID() + " will not be deleted");
        }
    }

    private static Alert noKeepersAlert (Petting pen) {
        Alert noKeepersAlert = new Alert(Alert.AlertType.ERROR);
        noKeepersAlert.setHeaderText("Error");
        noKeepersAlert.setContentText("No Keepers to Look After Pen #" + pen.getPenID() + ". " + System.lineSeparator() + "Please Create Keepers Before Pens. ");
        return noKeepersAlert;
    }

    private static Alert noKeepersAlert () {
        Alert noKeepersAlert = new Alert(Alert.AlertType.ERROR);
        noKeepersAlert.setHeaderText("Error");
        noKeepersAlert.setContentText("No Keepers to Look After a Petting Pen. " + System.lineSeparator() + "Please Create Keepers Before Pens. ");
        return noKeepersAlert;
    }

}
