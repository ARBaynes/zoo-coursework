package controllers.pens;

import classes.critters.Animal;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public abstract class AnimalToSpecificPenController {
    public static void animalToPenCellFactories (TableColumn<Animal, Integer> ID, TableColumn<Animal, String> name,
                                                  TableColumn<Animal, String> breed, TableColumn<Animal, String> requirements) {

        ID.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<Animal, String>("name"));
        breed.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedName());
            }
        });
        requirements.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedRequirementsToString());
            }
        });
    }
}
