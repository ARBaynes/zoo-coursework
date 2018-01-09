package main.modules.pens.aquarium;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.classes.pens.Aquarium;

import javafx.scene.control.TableView;

public class AquariumController {
    protected ObservableList<Aquarium> aquariumTableViewItems = FXCollections.observableArrayList();

    public AquariumController () {
        updateObservableTableData();
    }


    //OUTLINE/ROUGH SKETCH TABLE DATA

    public void outlineTableData (TableView tableView, TableColumn IDCol, TableColumn tempCol,
                                  TableColumn areaCol, TableColumn currentSpaceCol, TableColumn containedAnimalNumberCol,
                                  TableColumn heightCol, TableColumn waterVolCol, TableColumn waterTypeCol) {
        IDCol.setCellValueFactory( new PropertyValueFactory<Aquarium, Integer>("penID"));
        tempCol.setCellValueFactory( new PropertyValueFactory<Aquarium, Double>("temperature"));
        areaCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Aquarium, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Aquarium, Double> p) {
                ObservableValue<Double> area = new SimpleDoubleProperty(p.getValue().getArea()).asObject();
                return area;
            }
        });
        currentSpaceCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Aquarium, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Aquarium, Double> p) {
                ObservableValue<Double> currentSpace = new SimpleDoubleProperty(p.getValue().getCurrentSpace()).asObject();
                return currentSpace;
            }
        });
        containedAnimalNumberCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Aquarium, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Aquarium, Integer> p) {
                ObservableValue<Integer> containedAnimalNumber = new SimpleIntegerProperty(p.getValue().getContainedAnimalNumber()).asObject();
                return containedAnimalNumber;
            }
        });
        heightCol.setCellValueFactory( new PropertyValueFactory<Aquarium, Double>("height"));
        waterVolCol.setCellValueFactory( new PropertyValueFactory<Aquarium, Double>("waterVolume"));
        waterTypeCol.setCellValueFactory( new PropertyValueFactory<Aquarium, String>("waterType"));
    }

    public void outlineTableRows () {

    }


    //LOAD AND UPDATE TABLE ITEMS

    public void setTableItems (TableView<Aquarium> tableView) {
        tableView.setItems(aquariumTableViewItems);
    }

    public void updateObservableTableData () {
        aquariumTableViewItems.clear();
        aquariumTableViewItems.addAll(AquariumModel.getAllAquariums());
    }

}
