package main.modules.pens.aquarium;

import javafx.animation.Animation;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import main.classes.critters.Animal;
import main.classes.critters.Breed;
import main.classes.pens.Aquarium;
import main.modules.critters.models.AnimalModel;
import main.modules.critters.models.BreedModel;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class AquariumController {
    protected ObservableList<Aquarium> aquariumTableViewItems = FXCollections.observableArrayList();

    public AquariumController () {
        updateObservableTableData();
    }


    //OUTLINE/ROUGH SKETCH TABLE DATA

    public void outlinePenTableData (TableColumn IDCol, TableColumn tempCol, TableColumn waterVolCol,
                                     TableColumn currentVolumeCol, TableColumn containedAnimalNumberCol,
                                     TableColumn heightCol, TableColumn waterTypeCol) {
        IDCol.setCellValueFactory( new PropertyValueFactory<Aquarium, Integer>("penID"));
        tempCol.setCellValueFactory( new PropertyValueFactory<Aquarium, Double>("temperature"));
        waterVolCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Aquarium, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Aquarium, Double> p) {
                return new SimpleDoubleProperty(p.getValue().getVolume()).asObject();
            }
        });
        currentVolumeCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Aquarium, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Aquarium, Double> p) {
                return new SimpleDoubleProperty(p.getValue().getCurrentVolume()).asObject();
            }
        });
        containedAnimalNumberCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Aquarium, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Aquarium, Integer> p) {
                return new SimpleIntegerProperty(p.getValue().getContainedAnimalNumber()).asObject();
            }
        });
        heightCol.setCellValueFactory( new PropertyValueFactory<Aquarium, Double>("height"));

        waterTypeCol.setCellValueFactory( new PropertyValueFactory<Aquarium, String>("waterType"));
    }

    public void outlineTableRows (TableView<Aquarium> penTableView, TableView<Animal> penAnimalTableView, Label aquariumAnimalLabel) {
        penTableView.setRowFactory( tv -> {
            TableRow<Aquarium> penRow = new TableRow<>();
            penRow.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && (!penRow.isEmpty()) ) {
                    aquariumAnimalLabel.setText("Aquarium #" + penRow.getItem().getPenID().toString() + ": ");
                    updateObservableTableData();
                }
                if (event.getButton() == MouseButton.SECONDARY  && (!penRow.isEmpty()) ) {
                    aquariumTableContextMenu(penRow, penTableView, penAnimalTableView);
                }
            });
            return penRow ;
        });
    }


    //LOAD AND UPDATE TABLE ITEMS

    public void setTableItems (TableView<Aquarium> tableView) {
        updateObservableTableData();
        tableView.setItems(aquariumTableViewItems);
        tableView.refresh();
    }

    public void updateObservableTableData () {
        aquariumTableViewItems.clear();
        aquariumTableViewItems.addAll(AquariumModel.getAllAquariums());
    }

    public void refreshPenTable (TableView<Aquarium> tableView) {
        tableView.refresh();
        updateObservableTableData();
        tableView.refresh();
    }

    public void refreshAnimalTable (TableView<Animal> tableView) {
        tableView.refresh();
        updateObservableTableData();
        tableView.refresh();
    }

    //AQUARIUM BUTTON MANIPULATION

    public void setAddAquariumButtonAction (Button addAquariumButton, TableView<Aquarium> tableView) {
        addAquariumButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addAquariumDialog(tableView);
            }
        });
    }

    //DIALOGS

    public void addAquariumDialog (TableView table) {
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
        ChoiceBox<String> waterTypeChoiceBox = new ChoiceBox<>(waterTypes());
        GridPane waterTypePane =  waterTypeGridPane(waterTypeChoiceBox);


        Label tempLabel = new Label("Temperature: ");
        TextField tempTextField =  new TextField();
        Label staffLabel = new Label("Staff Responsible: ");
        ChoiceBox staffChoiceBox = new ChoiceBox<>();

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
                            waterTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase()
                    );
                    if (staffChoiceBox.getSelectionModel().getSelectedItem() != null) {
                        //pen.setStaffResponsible(staffChoiceBox.getSelectionModel().getSelectedItem());
                    }

                    return pen;
                }
                return null;
            }
        });

        Optional<Aquarium> result = dialog.showAndWait();
        if (result.isPresent()) {
            AquariumModel.addAquarium(result.get());
            refreshPenTable(table);
        }
    }

    private void editAquariumDialog(TableRow<Aquarium> penRow, TableView<Aquarium> penTable) {

    }

    private void removeAquariumDialog(TableRow<Aquarium> penRow, TableView<Aquarium> penTable) {

    }

    private GridPane waterTypeGridPane (ChoiceBox<String> waterTypeChoiceBox) {
        GridPane waterTypePane = new GridPane();
        waterTypePane.add(waterTypeChoiceBox, 1, 1);
        waterTypePane.add(new Label(" Water"), 2, 1);
        return waterTypePane;
    }

    private ObservableList<String> waterTypes () {
        return FXCollections.observableArrayList("Salt", "Fresh");
    }



    //CONTEXT MENUS

    private void aquariumTableContextMenu(TableRow<Aquarium> penRow, TableView<Aquarium> penTable,
                                       TableView<Animal> penAnimalTable) {

        Aquarium selectedPen = penRow.getItem();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem addNewPenMenuItem = new MenuItem("Add New Aquarium");
        final MenuItem editPenMenuItem = new MenuItem("Edit Aquarium #" + selectedPen.getPenID());
        final MenuItem removePenMenuItem = new MenuItem("Remove Aquarium #" + selectedPen.getPenID());
        addNewPenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addAquariumDialog(penTable);
            }
        });
        editPenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editAquariumDialog(penRow, penTable);
            }
        });
        removePenMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeAquariumDialog(penRow, penTable);
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
}
