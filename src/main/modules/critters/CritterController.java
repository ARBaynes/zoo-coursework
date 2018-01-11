package main.modules.critters;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import main.classes.critters.Breed;
import main.modules.critters.models.BreedModel;
import main.modules.pens.PenModel;

import java.util.Optional;

public class CritterController {

    //BREED PANEL

    protected ObservableList<Breed> breedTableViewItems = FXCollections.observableArrayList();

    public CritterController () {
        updateObservableBreedTableData();
    }

    //OUTLINE/ROUGH SKETCH TABLE DATA

    public void outlineBreedTableData (TableView<Breed> tableView, TableColumn nameCol, TableColumn penTypeCol, TableColumn requirementsCol) {
        nameCol.setCellValueFactory( new PropertyValueFactory<Breed, String>("name"));
        penTypeCol.setCellValueFactory( new PropertyValueFactory<Breed, String>("penType"));
        requirementsCol.setCellValueFactory( new PropertyValueFactory<Breed, Double>("requirements"));
    }

    public void outlineBreedTableRows () {

    }


    //LOAD AND UPDATE TABLE ITEMS

    public void setBreedTableItems (TableView<Breed> tableView) {
        updateObservableBreedTableData();
        tableView.setItems(breedTableViewItems);
        tableView.refresh();
    }

    public void updateObservableBreedTableData () {
        breedTableViewItems.clear();
        breedTableViewItems.addAll(BreedModel.getAllBreeds());
    }

    public void refreshBreedTable (TableView<Breed> tableView) {
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

    public void addBreedDialog (TableView<Breed> table) {
        Dialog<Breed> dialog = new Dialog<>();
        dialog.setTitle("Add Breed");
        dialog.setHeaderText("Add a new breed: ");
        dialog.setResizable(true);

        Label nameLabel= new Label("Name: ");
        TextField nameTextField = new TextField();
        Label penTypeLabel = new Label("Pen Type: ");
        ChoiceBox<String> penTypeChoiceBox =  new ChoiceBox<>(PenModel.getAllPenTypes());
        Label requirementsLabel = new Label("Requirements: ");
        TextField requirementsTextField =  new TextField();


        GridPane breedDialogGridPane = new GridPane();
        breedDialogGridPane.add(nameLabel, 1, 1);
        breedDialogGridPane.add(nameTextField, 2, 1);
        breedDialogGridPane.add(penTypeLabel, 1, 2);
        breedDialogGridPane.add(penTypeChoiceBox, 2, 2);
        breedDialogGridPane.add(requirementsLabel, 1, 3);
        breedDialogGridPane.add(requirementsTextField, 2, 3);

        dialog.getDialogPane().setContent(breedDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Breed>() {
            @Override
            public Breed call(ButtonType button) {
                if (button == buttonTypeOk) {
                    Breed breed = new Breed(nameTextField.getText(),
                            penTypeChoiceBox.getSelectionModel().getSelectedItem().toLowerCase(),
                            Double.parseDouble(requirementsTextField.getText())
                    );
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

    public void editBreedDialog () {

    }

    public void removeBreedDialog () {

    }
}
