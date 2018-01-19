package main.modules.pens.dry;

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
import main.classes.pens.Dry;
import main.classes.staff.Staff;
import main.modules.critters.AnimalController;
import main.modules.staff.StaffModel;

import java.util.Optional;

public class DryController {
    protected static ObservableList<Dry> dryTableViewItems = FXCollections.observableArrayList();
    protected static ToolBar dryToolbar;
    protected static Button addDryButton;
    protected static TableView<Dry> dryPenTableView;
    protected static TableColumn dryPenID;
    protected static TableColumn dryTemp;
    protected static TableColumn dryContainedAnimals;
    protected static TableColumn dryMaxArea;
    protected static TableColumn dryCurrentArea;

    private static String currentPenID;


    public static void construct (ToolBar toolBar, Button addButton, TableView<Dry> tableView, TableColumn id, TableColumn temp,
                                  TableColumn containedAnimals, TableColumn maxArea, TableColumn currentArea) {
        dryToolbar = toolBar;
        addDryButton = addButton;
        dryPenTableView = tableView;
        dryPenID = id;
        dryTemp = temp;
        dryContainedAnimals = containedAnimals;
        dryMaxArea = maxArea;
        dryCurrentArea = currentArea;

        dryPenTableView.setItems(dryTableViewItems);
    }

    public static void outline () {
        dryPenID.setCellValueFactory( new PropertyValueFactory<Dry, Integer>("penID"));
        dryTemp.setCellValueFactory( new PropertyValueFactory<Dry, Double>("temperature"));
        dryMaxArea.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Dry, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Dry, Double> p) {
                return new SimpleDoubleProperty(p.getValue().getArea()).asObject();
            }
        });
        dryCurrentArea.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Dry, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Dry, Double> p) {
                return new SimpleDoubleProperty(p.getValue().getCurrentArea()).asObject();
            }
        });
        dryContainedAnimals.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Dry, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Dry, Integer> p) {
                return new SimpleIntegerProperty(p.getValue().getContainedAnimalNumber()).asObject();
            }
        });


        dryPenTableView.setRowFactory( tv -> {
            TableRow<Dry> penRow = new TableRow<>();
            penRow.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && (!penRow.isEmpty()) ) {
                    Dry pen = penRow.getItem();
                    currentPenID = pen.getPenID();
                    if (pen.getContainedAnimals().isEmpty() || pen.getContainedAnimals() == null ) {
                        Alert noAnimals = new Alert(Alert.AlertType.INFORMATION);
                        noAnimals.setHeaderText("Caution");
                        noAnimals.setContentText("There are no animals in pen #"+ pen.getPenID() + "!");
                        noAnimals.showAndWait();
                    } else {
                        DryAnimalController.refresh(penRow.getItem());
                    }
                }
                if (event.getButton() == MouseButton.SECONDARY  && (!penRow.isEmpty()) ) {
                    dryTableContextMenu(penRow);
                }
            });
            return penRow ;
        });

        addDryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addDry();
            }
        });
    }

    public static void refresh () {
        dryTableViewItems.clear();
        dryTableViewItems.addAll(DryModel.getAllPens());
    }

    private static void dryTableContextMenu(TableRow<Dry> penRow) {
        Dry selectedPen = penRow.getItem();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem addNewPenMenuItem = new MenuItem("Add New Dry Pen");
        final MenuItem editPenMenuItem = new MenuItem("Edit Dry Pen #" + selectedPen.getPenID());
        final MenuItem removePenMenuItem = new MenuItem("Remove Dry Pen #" + selectedPen.getPenID());

        addNewPenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addDry();
            }
        });
        editPenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editDry(penRow.getItem());
            }
        });
        removePenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeDry(penRow.getItem());
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

    public static void addDry () {
        Dialog<Dry> dialog = new Dialog<>();
        dialog.setTitle("Add Dry Pen");
        dialog.setHeaderText("Add a new dry pen: ");
        dialog.setResizable(true);

        Label lengthLabel= new Label("Length: ");
        TextField lengthTextField = new TextField();
        Label widthLabel = new Label("Width: ");
        TextField widthTextField =  new TextField();
        Label tempLabel = new Label("Temperature: ");
        TextField tempTextField =  new TextField();
        Label staffLabel = new Label("Staff Responsible: ");
        ChoiceBox staffChoiceBox = new ChoiceBox();

        ObservableList<Staff> allStaff = FXCollections.observableArrayList();
        allStaff.addAll(StaffModel.getAllStaff());
        staffChoiceBox.setItems(allStaff);



        GridPane penDialogGridPane = new GridPane();
        penDialogGridPane.add(lengthLabel, 1, 1);
        penDialogGridPane.add(lengthTextField, 2, 1);
        penDialogGridPane.add(widthLabel, 1, 2);
        penDialogGridPane.add(widthTextField, 2, 2);
        penDialogGridPane.add(tempLabel, 1, 3);
        penDialogGridPane.add(tempTextField, 2, 3);
        penDialogGridPane.add(staffLabel, 1, 4);
        penDialogGridPane.add(staffChoiceBox, 2, 4);

        dialog.getDialogPane().setContent(penDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Dry>() {
            @Override
            public Dry call(ButtonType button) {
                if (button == buttonTypeOk) {
                    Dry pen = new Dry(
                            Double.parseDouble(lengthTextField.getText()),
                            Double.parseDouble(widthTextField.getText()),
                            Double.parseDouble(tempTextField.getText()),
                            (Staff) staffChoiceBox.getSelectionModel().getSelectedItem()
                    );
                    return pen;
                }
                return null;
            }
        });

        Optional<Dry> result = dialog.showAndWait();
        if (result.isPresent()) {
            DryModel.addPen(result.get());
            refresh();
        }
    }

    public static void editDry(Dry dry) {
        Dialog<Dry> dialog = new Dialog<>();
        dialog.setTitle("Edit Dry Pen");
        dialog.setHeaderText("Edit dry pen #" + dry.getPenID());
        dialog.setResizable(true);

        Label lengthLabel= new Label("Length: ");
        TextField lengthTextField = new TextField(dry.getLength().toString());
        Label widthLabel = new Label("Width: ");
        TextField widthTextField =  new TextField(dry.getWidth().toString());

        Label tempLabel = new Label("Temperature: ");
        TextField tempTextField =  new TextField(dry.getTemperature().toString());
        Label staffLabel = new Label("Staff Responsible: ");
        ChoiceBox staffChoiceBox = new ChoiceBox();

        ObservableList<Staff> allStaff = FXCollections.observableArrayList();
        allStaff.addAll(StaffModel.getAllStaff());
        staffChoiceBox.setItems(allStaff);
        staffChoiceBox.getSelectionModel().select(dry.getStaffResponsible());

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

        dialog.setResultConverter(new Callback<ButtonType, Dry>() {
            @Override
            public Dry call(ButtonType button) {
                if (button == buttonTypeOk) {
                    Dry pen = new Dry(
                            Double.parseDouble(lengthTextField.getText()),
                            Double.parseDouble(widthTextField.getText()),
                            Double.parseDouble(tempTextField.getText()),
                            (Staff) staffChoiceBox.getSelectionModel().getSelectedItem()
                    );
                    pen.setPenID(dry.getPenID());
                    return pen;
                }
                return null;
            }
        });

        Optional<Dry> result = dialog.showAndWait();
        if (result.isPresent()) {
            DryModel.editPen(result.get());
            refresh();
        }
    }

    public static void removeDry(Dry dry) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you wish to delete " +dry.getPenID()+ "?");
        alert.setContentText("You cannot undo this action, and all animals within this pen will be released!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            dry.removeAllAnimalsFromPen();
            DryModel.removePen(dry);
            refresh();
            AnimalController.refresh();
            DryAnimalController.refresh();

        } else {
            System.out.println(dry.getPenID() + " will not be deleted");
        }
    }

}
