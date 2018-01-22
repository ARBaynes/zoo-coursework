package main.modules.pens.aviary;

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
import main.classes.pens.Aviary;
import main.classes.staff.Staff;
import main.modules.critters.AnimalController;
import main.modules.staff.StaffModel;

import java.util.Optional;

public class AviaryController {
    private static ObservableList<Aviary> aviaryTableViewItems = FXCollections.observableArrayList();
    private static ToolBar aviaryToolbar;
    private static Button addAviaryButton;
    private static TableView<Aviary> aviaryPenTableView;
    private static TableColumn aviaryPenID;
    private static TableColumn aviaryTemp;
    private static TableColumn aviaryContainedAnimals;
    private static TableColumn aviaryHeight;
    private static TableColumn aviaryMaxVolume;
    private static TableColumn aviaryCurrentVolume;
    private static TableColumn aviaryKeeperID;

    private static String currentPenID;


    public static void construct (ToolBar toolBar, Button addButton, TableView<Aviary> tableView, TableColumn id, TableColumn temp,
                                  TableColumn containedAnimals, TableColumn height, TableColumn maxVolume, TableColumn currentVolume, TableColumn keeperID) {
        aviaryToolbar = toolBar;
        addAviaryButton = addButton;
        aviaryPenTableView = tableView;
        aviaryPenID = id;
        aviaryTemp = temp;
        aviaryContainedAnimals = containedAnimals;
        aviaryHeight = height;
        aviaryMaxVolume = maxVolume;
        aviaryCurrentVolume = currentVolume;
        aviaryKeeperID = keeperID;

        aviaryPenTableView.setItems(aviaryTableViewItems);
    }

    public static void outline () {
        aviaryPenID.setCellValueFactory( new PropertyValueFactory<Aviary, Integer>("penID"));
        aviaryTemp.setCellValueFactory( new PropertyValueFactory<Aviary, Double>("temperature"));
        aviaryMaxVolume.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Aviary, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Aviary, Double> p) {
                return new SimpleDoubleProperty(p.getValue().getVolume()).asObject();
            }
        });
        aviaryCurrentVolume.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Aviary, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Aviary, Double> p) {
                return new SimpleDoubleProperty(p.getValue().getCurrentVolume()).asObject();
            }
        });
        aviaryContainedAnimals.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Aviary, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Aviary, Integer> p) {
                return new SimpleIntegerProperty(p.getValue().getContainedAnimalNumber()).asObject();
            }
        });
        aviaryKeeperID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Aviary, Integer>, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Aviary, Integer> p) {
                return new SimpleIntegerProperty(p.getValue().getKeeperID());
            }
        });
        aviaryHeight.setCellValueFactory( new PropertyValueFactory<Aviary, Double>("height"));


        aviaryPenTableView.setRowFactory( tv -> {
            TableRow<Aviary> penRow = new TableRow<>();
            penRow.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && (!penRow.isEmpty()) ) {
                    Aviary pen = penRow.getItem();
                    currentPenID = pen.getPenID();
                    if (pen.getContainedAnimals().isEmpty() || pen.getContainedAnimals() == null ) {
                        Alert noAnimals = new Alert(Alert.AlertType.INFORMATION);
                        noAnimals.setHeaderText("Caution");
                        noAnimals.setContentText("There are no animals in pen #"+ pen.getPenID() + "!");
                        noAnimals.showAndWait();
                    } else {
                        AviaryAnimalController.refresh(penRow.getItem());
                    }
                }
                if (event.getButton() == MouseButton.SECONDARY  && (!penRow.isEmpty()) ) {
                    aviaryTableContextMenu(penRow);
                }
            });
            return penRow ;
        });

        addAviaryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addAviary();
            }
        });
    }

    public static void refresh () {
        aviaryTableViewItems.clear();
        aviaryTableViewItems.addAll(AviaryModel.getAllPens());
    }

    private static void aviaryTableContextMenu(TableRow<Aviary> penRow) {
        Aviary selectedPen = penRow.getItem();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem addNewPenMenuItem = new MenuItem("Add New Aviary");
        final MenuItem editPenMenuItem = new MenuItem("Edit Aviary #" + selectedPen.getPenID());
        final MenuItem removePenMenuItem = new MenuItem("Remove Aviary #" + selectedPen.getPenID());

        addNewPenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addAviary();
            }
        });
        editPenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editAviary(penRow.getItem());
            }
        });
        removePenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeAviary(penRow.getItem());
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

    public static void addAviary () {
        Dialog<Aviary> dialog = new Dialog<>();
        dialog.setTitle("Add Aviary");
        dialog.setHeaderText("Add a new aviary: ");
        dialog.setResizable(true);

        Label lengthLabel= new Label("Length: ");
        TextField lengthTextField = new TextField();
        Label widthLabel = new Label("Width: ");
        TextField widthTextField =  new TextField();
        Label heightLabel = new Label("Height: ");
        TextField heightTextField =  new TextField();
        Label tempLabel = new Label("Temperature: ");
        TextField tempTextField =  new TextField();
        Label staffLabel = new Label("Staff Responsible: #");
        ChoiceBox<Integer> staffChoiceBox = new ChoiceBox<>();

        ObservableList<Integer> allStaff = FXCollections.observableArrayList();
        allStaff.addAll(StaffModel.extractStaffIDs(StaffModel.getAllStaffBy("aviary")));
        staffChoiceBox.setItems(allStaff);
        staffChoiceBox.getSelectionModel().selectFirst();


        GridPane penDialogGridPane = new GridPane();
        penDialogGridPane.add(lengthLabel, 1, 1);
        penDialogGridPane.add(lengthTextField, 2, 1);
        penDialogGridPane.add(widthLabel, 1, 2);
        penDialogGridPane.add(widthTextField, 2, 2);
        penDialogGridPane.add(heightLabel, 1, 3);
        penDialogGridPane.add(heightTextField, 2, 3);
        penDialogGridPane.add(tempLabel, 1, 4);
        penDialogGridPane.add(tempTextField, 2, 4);
        penDialogGridPane.add(staffLabel, 1, 5);
        penDialogGridPane.add(staffChoiceBox, 2, 5);

        dialog.getDialogPane().setContent(penDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Aviary>() {
            @Override
            public Aviary call(ButtonType button) {
                if (button == buttonTypeOk) {
                    Aviary pen = new Aviary(
                            Double.parseDouble(lengthTextField.getText()),
                            Double.parseDouble(widthTextField.getText()),
                            Double.parseDouble(heightTextField.getText()),
                            Double.parseDouble(tempTextField.getText()),
                            staffChoiceBox.getSelectionModel().getSelectedItem()
                    );
                    return pen;
                }
                return null;
            }
        });

        Optional<Aviary> result = dialog.showAndWait();
        if (result.isPresent()) {
            AviaryModel.addPen(result.get());
            refresh();
        }
    }

    public static void editAviary(Aviary aviary) {
        Dialog<Aviary> dialog = new Dialog<>();
        dialog.setTitle("Edit Aviary");
        dialog.setHeaderText("Edit Aviary #" + aviary.getPenID());
        dialog.setResizable(true);

        Label lengthLabel= new Label("Length: ");
        TextField lengthTextField = new TextField(aviary.getLength().toString());
        Label widthLabel = new Label("Width: ");
        TextField widthTextField =  new TextField(aviary.getWidth().toString());
        Label heightLabel = new Label("Height: ");
        TextField heightTextField =  new TextField(aviary.getHeight().toString());

        Label tempLabel = new Label("Temperature: ");
        TextField tempTextField =  new TextField(aviary.getTemperature().toString());
        Label staffLabel = new Label("Staff Responsible: #");
        ChoiceBox<Integer> staffChoiceBox = new ChoiceBox<>();

        ObservableList<Integer> allStaff = FXCollections.observableArrayList();
        allStaff.addAll(StaffModel.extractStaffIDs(StaffModel.getAllStaffBy("aviary")));
        staffChoiceBox.setItems(allStaff);
        staffChoiceBox.getSelectionModel().select(aviary.getKeeperID());

        GridPane aquariumDialogGridPane = new GridPane();
        aquariumDialogGridPane.add(lengthLabel, 1, 1);
        aquariumDialogGridPane.add(lengthTextField, 2, 1);
        aquariumDialogGridPane.add(widthLabel, 1, 2);
        aquariumDialogGridPane.add(widthTextField, 2, 2);
        aquariumDialogGridPane.add(heightLabel, 1, 3);
        aquariumDialogGridPane.add(heightTextField, 2, 3);
        aquariumDialogGridPane.add(tempLabel, 1, 4);
        aquariumDialogGridPane.add(tempTextField, 2, 4);
        aquariumDialogGridPane.add(staffLabel, 1, 5);
        aquariumDialogGridPane.add(staffChoiceBox, 2, 5);

        dialog.getDialogPane().setContent(aquariumDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Aviary>() {
            @Override
            public Aviary call(ButtonType button) {
                if (button == buttonTypeOk) {
                    Aviary pen = new Aviary(
                            Double.parseDouble(lengthTextField.getText()),
                            Double.parseDouble(widthTextField.getText()),
                            Double.parseDouble(heightTextField.getText()),
                            Double.parseDouble(tempTextField.getText()),
                            staffChoiceBox.getSelectionModel().getSelectedItem()
                    );
                    pen.setPenID(aviary.getPenID());
                    return pen;
                }
                return null;
            }
        });

        Optional<Aviary> result = dialog.showAndWait();
        if (result.isPresent()) {
            AviaryModel.editPen(result.get());
            refresh();
        }
    }

    public static void removeAviary(Aviary aviary) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you wish to delete " +aviary.getPenID()+ "?");
        alert.setContentText("You cannot undo this action, and all animals within this pen will be released!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            aviary.removeAllAnimalsFromPen();
            AviaryModel.removePen(aviary);
            refresh();
            AnimalController.refresh();
            AviaryAnimalController.refresh();

        } else {
            System.out.println(aviary.getPenID() + " will not be deleted");
        }
    }

}
