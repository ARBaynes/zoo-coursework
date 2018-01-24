package controllers.main;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import classes.critters.Animal;
import classes.pens.Pen;
import classes.staff.Staff;
import models.critters.AnimalModel;
import models.pens.PenModel;
import models.staff.StaffModel;

import java.util.ArrayList;

public class MainController {

    private static TableView<Animal> animalTableMain;
    private static TableColumn animalIDColMain;
    private static TableColumn animalNameColMain;
    private static TableColumn animalBreedColMain;

    private static TableView<Staff> staffTableMain;
    private static TableColumn staffIDColMain;
    private static TableColumn staffNameColMain;
    private static TableColumn staffResponsibilitiesColMain;

    private static TableView<Pen> penTableMain;
    private static TableColumn penIDColMain;
    private static TableColumn penTypeColMain;
    private static TableColumn penContainedAnimalsColMain;

    private static ObservableList<Pen> allPens = FXCollections.observableArrayList();
    private static ObservableList<Animal> allAnimals = FXCollections.observableArrayList();
    private static ObservableList<Staff> allStaff = FXCollections.observableArrayList();

    public static void construct (TableView<Animal> animalTable, TableColumn animalID, TableColumn animalName, TableColumn animalBreed,
                                  TableView<Staff> staffTable, TableColumn staffID, TableColumn staffName, TableColumn staffResponsibilities,
                                  TableView<Pen> penTable, TableColumn penID, TableColumn penType, TableColumn penContainedAnimals) {

        animalTableMain = animalTable;
        animalIDColMain = animalID;
        animalNameColMain = animalName;
        animalBreedColMain = animalBreed;

        staffTableMain = staffTable;
        staffIDColMain = staffID;
        staffNameColMain = staffName;
        staffResponsibilitiesColMain = staffResponsibilities;

        penTableMain = penTable;
        penIDColMain = penID;
        penTypeColMain = penType;
        penContainedAnimalsColMain = penContainedAnimals;

        animalTableMain.setItems(allAnimals);
        staffTableMain.setItems(allStaff);
        penTableMain.setItems(allPens);
    }

    public static void outline () {
        //ANIMALS
        animalIDColMain.setCellValueFactory( new PropertyValueFactory<Animal, Integer>("ID"));
        animalNameColMain.setCellValueFactory( new PropertyValueFactory<Animal, String>("name"));
        animalBreedColMain.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedName());
            }
        });

        //STAFF
        staffIDColMain.setCellValueFactory( new PropertyValueFactory<Staff, Integer>("staffID"));
        staffNameColMain.setCellValueFactory( new PropertyValueFactory<Staff, String>("name"));
        staffResponsibilitiesColMain.setCellValueFactory( new PropertyValueFactory<Staff, ArrayList<String>>("penTypes"));


        //PENS
        penIDColMain.setCellValueFactory( new PropertyValueFactory<Pen, String>("penID"));
        penTypeColMain.setCellValueFactory( new PropertyValueFactory<Pen, String>("penType"));
        penContainedAnimalsColMain.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Pen, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Pen, Integer> p) {
                return new SimpleIntegerProperty(p.getValue().getContainedAnimalNumber()).asObject();
            }
        });
    }

    public static void refresh () {
        allAnimals.clear();
        allStaff.clear();
        allPens.clear();

        allAnimals.addAll(AnimalModel.getAllAnimals());
        allStaff.addAll(StaffModel.getAllStaff());
        allPens.addAll(PenModel.getEveryPen());
    }
}
