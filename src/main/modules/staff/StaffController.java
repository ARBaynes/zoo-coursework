package main.modules.staff;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
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
import main.classes.critters.Animal;
import main.classes.staff.Staff;
import main.modules.pens.PenModel;

import java.util.ArrayList;
import java.util.Optional;

public class StaffController {
    private static TableView<Staff> staffTableView;
    private static Button addNewStaffButton;
    private static TableColumn staffID;
    private static TableColumn staffName;
    private static TableColumn staffResponsibleFor;
    private static ObservableList<Staff> staffTableViewItems = FXCollections.observableArrayList();

    public static void construct (TableView<Staff> tableView, Button button, TableColumn ID, TableColumn name, TableColumn responsibleFor) {
        staffTableView = tableView;
        addNewStaffButton = button;
        staffID = ID;
        staffName = name;
        staffResponsibleFor = responsibleFor;

        staffTableView.setItems(staffTableViewItems);
    }

    public static void outline () {
        staffID.setCellValueFactory( new PropertyValueFactory<Staff, Integer>("staffID"));
        staffName.setCellValueFactory( new PropertyValueFactory<Staff, String>("name"));
        staffResponsibleFor.setCellValueFactory( new PropertyValueFactory<Staff, ArrayList<String>>("penTypes"));

        staffTableView.setRowFactory( tv -> {
            TableRow<Staff> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY  && (!row.isEmpty()) ) {
                    contextMenu(row);
                }
            });
            return row ;
        });

        addNewStaffButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addStaff();
            }
        });
    }

    public static void refresh () {
        staffTableViewItems.clear();
        staffTableViewItems.addAll(StaffModel.getAllStaff());
    }

    private static void contextMenu (TableRow<Staff> row) {
        Staff selectedStaff = row.getItem();
        final ContextMenu contextMenu = new ContextMenu();

        final MenuItem addStaffMenuItem = new MenuItem("Add new Staff");
        final MenuItem editStaffMenuItem = new MenuItem("Edit " + selectedStaff.getName());
        final MenuItem removeStaffMenuItem = new MenuItem("Remove " + selectedStaff.getName());

        addStaffMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addStaff();
            }
        });
        editStaffMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editStaff(selectedStaff);
            }
        });
        removeStaffMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteStaff(selectedStaff);
            }
        });

        contextMenu.getItems().add(addStaffMenuItem);
        contextMenu.getItems().add(editStaffMenuItem);
        contextMenu.getItems().add(removeStaffMenuItem);

        row.contextMenuProperty().bind(
                Bindings.when(row.emptyProperty())
                        .then((ContextMenu)null)
                        .otherwise(contextMenu)
        );
    }

    public static void addStaff () {
        Dialog<Staff> dialog = new Dialog<>();
        dialog.setTitle("Add Staff");
        dialog.setHeaderText("Add a new staff member: ");
        dialog.setResizable(true);

        Label nameLabel= new Label("Name: ");
        TextField nameTextField = new TextField();
        Label responsibleLabel = new Label("Responsible For: ");

        GridPane checkboxGridPane = new GridPane();

        CheckBox aquariumCheckBox = new CheckBox("aquarium");
        CheckBox aviaryCheckBox = new CheckBox("aviary");
        CheckBox dryCheckBox = new CheckBox("dry");
        CheckBox pettingCheckBox = new CheckBox("petting");
        CheckBox semiAquaticCheckBox = new CheckBox("semiaquatic");


        checkboxGridPane.add( aquariumCheckBox, 1, 1);
        checkboxGridPane.add( aviaryCheckBox, 2, 1);
        checkboxGridPane.add( dryCheckBox, 1, 2);
        checkboxGridPane.add( pettingCheckBox, 2, 2);
        checkboxGridPane.add( semiAquaticCheckBox, 1, 3);

        GridPane staffDialogGridPane = new GridPane();
        staffDialogGridPane.add(nameLabel, 1, 1);
        staffDialogGridPane.add(nameTextField, 2, 1);
        staffDialogGridPane.add(responsibleLabel, 1, 2);
        staffDialogGridPane.add(checkboxGridPane, 2, 2);
        dialog.getDialogPane().setContent(staffDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Staff>() {
            @Override
            public Staff call(ButtonType button) {
                if (button == buttonTypeOk) {
                    ArrayList<String> responsibleFor = new ArrayList<>();
                    if (aquariumCheckBox.isSelected()) {
                        responsibleFor.add("aquarium");
                    }
                    if (aviaryCheckBox.isSelected()) {
                        responsibleFor.add("aviary");
                    }
                    if (dryCheckBox.isSelected()) {
                        responsibleFor.add("dry");
                    }
                    if (pettingCheckBox.isSelected()) {
                        responsibleFor.add("petting");
                    }
                    if (semiAquaticCheckBox.isSelected()) {
                        responsibleFor.add("semiaquatic");
                    }
                    return new Staff(nameTextField.getText(), responsibleFor);

                }
                return null;
            }
        });
        Optional<Staff> result = dialog.showAndWait();
        if (result.isPresent()) {
            StaffModel.addStaff(result.get());
            refresh();
        }
    }

    public static void editStaff (Staff staff) {
        Dialog<Staff> dialog = new Dialog<>();
        dialog.setTitle("Edit Staff");
        dialog.setHeaderText("Edit #" + staff.getStaffID() + " - "+ staff.getName() +": ");
        dialog.setResizable(true);

        Label nameLabel= new Label("Name: ");
        TextField nameTextField = new TextField();
        nameTextField.setText(staff.getName());
        Label responsibleLabel = new Label("Responsible For: ");

        GridPane checkboxGridPane = new GridPane();

        CheckBox aquariumCheckBox = new CheckBox("aquarium");
        CheckBox aviaryCheckBox = new CheckBox("aviary");
        CheckBox dryCheckBox = new CheckBox("dry");
        CheckBox pettingCheckBox = new CheckBox("petting");
        CheckBox semiAquaticCheckBox = new CheckBox("semiaquatic");

        if (staff.getPenTypes().contains("aquarium")) {
            aquariumCheckBox.selectedProperty().setValue(true);
        }
        if (staff.getPenTypes().contains("aviary")) {
            aviaryCheckBox.selectedProperty().setValue(true);
        }
        if (staff.getPenTypes().contains("dry")) {
            dryCheckBox.selectedProperty().setValue(true);
        }
        if (staff.getPenTypes().contains("petting")) {
            pettingCheckBox.selectedProperty().setValue(true);
        }
        if (staff.getPenTypes().contains("semiaquatic")) {
            semiAquaticCheckBox.selectedProperty().setValue(true);
        }

        checkboxGridPane.add( aquariumCheckBox, 1, 1);
        checkboxGridPane.add( aviaryCheckBox, 2, 1);
        checkboxGridPane.add( dryCheckBox, 1, 2);
        checkboxGridPane.add( pettingCheckBox, 2, 2);
        checkboxGridPane.add( semiAquaticCheckBox, 1, 3);

        GridPane staffDialogGridPane = new GridPane();
        staffDialogGridPane.add(nameLabel, 1, 1);
        staffDialogGridPane.add(nameTextField, 2, 1);
        staffDialogGridPane.add(responsibleLabel, 1, 2);
        staffDialogGridPane.add(checkboxGridPane, 2, 2);
        dialog.getDialogPane().setContent(staffDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Staff>() {
            @Override
            public Staff call(ButtonType button) {
                if (button == buttonTypeOk) {
                    ArrayList<String> responsibleFor = new ArrayList<>();
                    if (aquariumCheckBox.isSelected()) {
                        responsibleFor.add("aquarium");
                    }
                    if (aviaryCheckBox.isSelected()) {
                        responsibleFor.add("aviary");
                    }
                    if (dryCheckBox.isSelected()) {
                        responsibleFor.add("dry");
                    }
                    if (pettingCheckBox.isSelected()) {
                        responsibleFor.add("petting");
                    }
                    if (semiAquaticCheckBox.isSelected()) {
                        responsibleFor.add("semiaquatic");
                    }
                    return new Staff(nameTextField.getText(), responsibleFor);

                }
                return null;
            }
        });
        Optional<Staff> result = dialog.showAndWait();
        if (result.isPresent()) {
            Staff edited = result.get();
            edited.setStaffID(staff.getStaffID());
            StaffModel.editStaff(edited);
            refresh();
        }
    }

    public static void deleteStaff (Staff staff) {

    }
}
