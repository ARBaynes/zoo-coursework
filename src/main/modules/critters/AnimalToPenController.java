package main.modules.critters;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import main.classes.critters.Animal;
import main.classes.pens.Aquarium;
import main.classes.pens.Aviary;
import main.classes.pens.Pen;
import main.modules.critters.models.AnimalModel;
import main.modules.pens.aquarium.AquariumAnimalController;
import main.modules.pens.aquarium.AquariumController;
import main.modules.pens.aquarium.AquariumModel;
import main.modules.pens.aviary.AviaryModel;

import java.util.ArrayList;
import java.util.Optional;

public class AnimalToPenController {
    public static void addAnimalToPen (Animal animal) {
        switch (animal.getBreedPenType().toLowerCase()) {
            case "aquarium":
                animalToAquarium(animal);
                break;
            case "aviary":
                animalToAviary(animal);
                break;
            case "dry":
                animalToDry(animal);
                break;
            case "petting":
                animalToPetting(animal);
                break;
            case "semiaquatic":
                animalToSemiAquatic(animal);
                break;
            default:
                animalToDryOrPetting(animal);
        }
    }

    public static void removeAnimalFromPen (Animal animal) {
        switch (animal.getBreedPenType().toLowerCase()) {
            case "aquarium":
                animalFromAquarium(animal);
                break;
            case "aviary":
                animalFromAviary(animal);
                break;
            case "dry":
                animalFromDry(animal);
                break;
            case "petting":
                animalFromPetting(animal);
                break;
            case "semiaquatic":
                animalFromSemiAquatic(animal);
                break;
            default:
                animalFromDryOrPetting(animal);
        }
    }

    public static void animalFromAquarium (Animal animal) {
        String currentPenID = animal.getCurrentPenID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText("Are you sure you wish to remove " +animal.getName()+ " from pen #" +currentPenID);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            if (currentPenID != null) {
                Aquarium pen = AquariumModel.getAquariumBy(currentPenID);
                if (pen != null) {
                    pen.removeAnimalFromPen(animal);
                    AquariumModel.editAquarium(pen);

                    animal.setCurrentPenID(null);
                    AnimalModel.editAnimal(animal);

                    AnimalController.refresh(animal.getBreed());
                    AquariumAnimalController.refresh(pen);
                    AquariumController.refresh();
                }
            }
        } else {
            System.out.println(animal.getName() + " will stay in this pen");
        }
    }

    public static void animalFromAviary (Animal animal) {}
    public static void animalFromDry (Animal animal) {}
    public static void animalFromPetting (Animal animal) {}
    public static void animalFromSemiAquatic (Animal animal) {}
    public static void animalFromDryOrPetting(Animal animal) {}


    /*public static void animalFromAviary (Animal animal) {
        String currentPenID = animal.getCurrentPenID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText("Are you sure you wish to remove " +animal.getName()+ " from pen #" +currentPenID);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            if (currentPenID != null) {
                Aviary pen = AviaryModel.getAviaryBy(currentPenID);
                if (pen != null) {
                    pen.removeAnimalFromPen(animal);

                    animal.setCurrentPenID(null);
                    AnimalModel.editAnimal(animal);

                    AnimalController.refresh(animal.getBreed());
                    AquariumController.refresh();
                    AquariumAnimalController.refresh(pen);
                }
            }
        } else {
            System.out.println(animal.getName() + " will stay in this pen");
        }
    }

    public static void animalFromDry  (Animal animal) {
        String currentPenID = animal.getCurrentPenID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText("Are you sure you wish to remove " +animal.getName()+ " from pen #" +currentPenID);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            if (currentPenID != null) {
                Aquarium pen = AquariumModel.getAquariumBy(currentPenID);
                if (pen != null) {
                    pen.removeAnimalFromPen(animal);

                    animal.setCurrentPenID(null);
                    AnimalModel.editAnimal(animal);

                    AnimalController.refresh(animal.getBreed());
                    AquariumController.refresh();
                    AquariumAnimalController.refresh(pen);
                }
            }
        } else {
            System.out.println(animal.getName() + " will stay in this pen");
        }
    }

    public static void animalFromPetting(Animal animal) {
        String currentPenID = animal.getCurrentPenID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText("Are you sure you wish to remove " +animal.getName()+ " from pen #" +currentPenID);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            if (currentPenID != null) {
                Aquarium pen = AquariumModel.getAquariumBy(currentPenID);
                if (pen != null) {
                    pen.removeAnimalFromPen(animal);

                    animal.setCurrentPenID(null);
                    AnimalModel.editAnimal(animal);

                    AnimalController.refresh(animal.getBreed());
                    AquariumController.refresh();
                    AquariumAnimalController.refresh(pen);
                }
            }
        } else {
            System.out.println(animal.getName() + " will stay in this pen");
        }
    }

    public static void animalFromSemiAquatic (Animal animal) {
        String currentPenID = animal.getCurrentPenID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText("Are you sure you wish to remove " +animal.getName()+ " from pen #" +currentPenID);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            if (currentPenID != null) {
                Aquarium pen = AquariumModel.getAquariumBy(currentPenID);
                if (pen != null) {
                    pen.removeAnimalFromPen(animal);

                    animal.setCurrentPenID(null);
                    AnimalModel.editAnimal(animal);

                    AnimalController.refresh(animal.getBreed());
                    AquariumController.refresh();
                    AquariumAnimalController.refresh(pen);
                }
            }
        } else {
            System.out.println(animal.getName() + " will stay in this pen");
        }
    }

    public static void animalFromDryOrPetting (Animal animal) {
        String currentPenID = animal.getCurrentPenID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText("Are you sure you wish to remove " +animal.getName()+ " from pen #" +currentPenID);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            if (currentPenID != null) {
                Aquarium pen = AquariumModel.getAquariumBy(currentPenID);
                if (pen != null) {
                    pen.removeAnimalFromPen(animal);

                    animal.setCurrentPenID(null);
                    AnimalModel.editAnimal(animal);

                    AnimalController.refresh(animal.getBreed());
                    AquariumController.refresh();
                    AquariumAnimalController.refresh(pen);
                }
            }
        } else {
            System.out.println(animal.getName() + " will stay in this pen");
        }
    }*/

    private static void animalToAquarium (Animal animal) {
        Dialog<Aquarium> dialog = new Dialog<>();
        dialog.setTitle("Add "+ animal.getName() +" to an Aquarium");
        dialog.setHeaderText("Add " + animal.getName() + " to " + animal.getBreedPenType());
        dialog.setResizable(true);
        Label desc = new Label("Aquariums: ");
        ObservableList<Aquarium> pens = FXCollections.observableArrayList();
        ArrayList<Aquarium> aquariums = AquariumModel.getAllAquariumsWithSpaceRemaining(animal);
        for (Aquarium aquarium: aquariums) {
            pens.addAll(aquarium);
        }
        ChoiceBox<Aquarium> penChoiceBox = new ChoiceBox<>(pens);

        TableView<Animal> currentAnimalsInPen = new TableView<>();
        TableColumn<Animal, Integer> animalIDCol = new TableColumn<>("ID");
        animalIDCol.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("ID"));
        TableColumn<Animal, String> animalNameCol = new TableColumn<>("Name");
        animalNameCol.setCellValueFactory(new PropertyValueFactory<Animal, String>("Name"));
        TableColumn<Animal, String> animalBreedCol = new TableColumn<>("Breed");
        animalBreedCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedName());
            }
        });

        TableColumn<Animal, String> animalRequirementsCol = new TableColumn<>("Requirements");
        animalRequirementsCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedRequirementsToString());
            }
        });

        currentAnimalsInPen.getColumns().addAll(animalIDCol, animalNameCol, animalBreedCol, animalRequirementsCol);

        ObservableList<Animal> animalsinPen = FXCollections.observableArrayList();
        currentAnimalsInPen.setItems(animalsinPen);

        penChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends Aquarium> observable, Aquarium oldValue, Aquarium newValue) ->
                        refresh(animalsinPen, newValue)
                );

        GridPane animalDialogGridPane = new GridPane();
        animalDialogGridPane.add(desc, 1, 1);
        animalDialogGridPane.add(penChoiceBox, 2, 1);
        animalDialogGridPane.add(currentAnimalsInPen, 2, 2);
        dialog.getDialogPane().setContent(animalDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Aquarium>() {
            @Override
            public Aquarium call(ButtonType button) {
                if (button == buttonTypeOk) {
                    return penChoiceBox.getSelectionModel().getSelectedItem();
                }
                return null;
            }
        });

        Optional<Aquarium> result = dialog.showAndWait();
        if (result.isPresent()) {

            animal.setCurrentPenID(result.get().getPenID());
            AnimalModel.editAnimal(animal);

            result.get().addAnimalToPen(animal);
            AquariumModel.editAquarium(result.get());

            AnimalController.refresh(animal.getBreed());
            BreedController.refresh();

            AquariumController.refresh();
        }
    }

    private static void animalToAviary (Animal animal) {
        Dialog<Aviary> dialog = new Dialog<>();
        dialog.setTitle("Add "+ animal.getName() +" to an Aviary");
        dialog.setHeaderText("Add " + animal.getName() + " to " + animal.getBreedPenType());
        dialog.setResizable(true);
        Label desc = new Label("Aviaries: ");
        ObservableList<Aviary> pens = FXCollections.observableArrayList();
        ArrayList<Aviary> aviaries = AviaryModel.getAllAviariesWithSpaceRemaining(animal);
        for (Aviary aviary: aviaries) {
            pens.addAll(aviary);
        }
        ChoiceBox<Aviary> penChoiceBox = new ChoiceBox<>(pens);

        TableView<Animal> currentAnimalsInPen = new TableView<>();
        TableColumn<Animal, Integer> animalIDCol = new TableColumn<>("ID");
        animalIDCol.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("ID"));
        TableColumn<Animal, String> animalNameCol = new TableColumn<>("Name");
        animalNameCol.setCellValueFactory(new PropertyValueFactory<Animal, String>("Name"));
        TableColumn<Animal, String> animalBreedCol = new TableColumn<>("Breed");
        animalBreedCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedName());
            }
        });

        TableColumn<Animal, String> animalRequirementsCol = new TableColumn<>("Requirements");
        animalRequirementsCol.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Animal, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Animal, String> p) {
                return new SimpleStringProperty(p.getValue().getBreedRequirementsToString());
            }
        });

        currentAnimalsInPen.getColumns().addAll(animalIDCol, animalNameCol, animalBreedCol, animalRequirementsCol);

        ObservableList<Animal> animalsinPen = FXCollections.observableArrayList();
        currentAnimalsInPen.setItems(animalsinPen);

        penChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends Aviary> observable, Aviary oldValue, Aviary newValue) ->
                        refresh(animalsinPen, newValue)
                );

        GridPane animalDialogGridPane = new GridPane();
        animalDialogGridPane.add(desc, 1, 1);
        animalDialogGridPane.add(penChoiceBox, 2, 1);
        animalDialogGridPane.add(currentAnimalsInPen, 2, 2);
        dialog.getDialogPane().setContent(animalDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Aviary>() {
            @Override
            public Aviary call(ButtonType button) {
                if (button == buttonTypeOk) {
                    return penChoiceBox.getSelectionModel().getSelectedItem();
                }
                return null;
            }
        });

        Optional<Aviary> result = dialog.showAndWait();
        if (result.isPresent()) {

            animal.setCurrentPenID(result.get().getPenID());
            AnimalModel.editAnimal(animal);

            result.get().addAnimalToPen(animal);
            AviaryModel.editAviary(result.get());

            AnimalController.refresh(animal.getBreed());
            BreedController.refresh();

            //AviaryController.refresh();
        }
    }

    private static void animalToDry (Animal animal) {

    }

    private static void animalToPetting (Animal animal) {

    }

    private static void animalToSemiAquatic (Animal animal) {

    }

    private static void animalToDryOrPetting (Animal animal) {

    }

    public static void refresh (ObservableList<Animal> animalList, Pen pen) {
        animalList.clear();
        animalList.addAll(pen.getContainedAnimals());
    }
}
