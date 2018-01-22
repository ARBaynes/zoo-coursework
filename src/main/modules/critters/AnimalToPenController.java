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
import main.classes.pens.*;
import main.modules.critters.models.AnimalModel;
import main.modules.pens.aquarium.AquariumAnimalController;
import main.modules.pens.aquarium.AquariumController;
import main.modules.pens.aquarium.AquariumModel;
import main.modules.pens.aviary.AviaryAnimalController;
import main.modules.pens.aviary.AviaryController;
import main.modules.pens.aviary.AviaryModel;
import main.modules.pens.dry.DryAnimalController;
import main.modules.pens.dry.DryController;
import main.modules.pens.dry.DryModel;
import main.modules.pens.petting.PettingAnimalController;
import main.modules.pens.petting.PettingController;
import main.modules.pens.petting.PettingModel;
import main.modules.pens.semiaquatic.SemiAquaticAnimalController;
import main.modules.pens.semiaquatic.SemiAquaticController;
import main.modules.pens.semiaquatic.SemiAquaticModel;

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
                System.out.println("removeAnimalFromPen: animal, " + animal);
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
                Aquarium pen = AquariumModel.getPenBy(currentPenID);
                if (pen != null) {
                    pen.removeAnimalFromPen(animal);
                    AquariumModel.editPen(pen);

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

    public static void animalFromAviary (Animal animal) {
        String currentPenID = animal.getCurrentPenID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText("Are you sure you wish to remove " +animal.getName()+ " from pen #" +currentPenID);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            if (currentPenID != null) {
                Aviary pen = AviaryModel.getPenBy(currentPenID);
                if (pen != null) {
                    pen.removeAnimalFromPen(animal);
                    AviaryModel.editPen(pen);

                    animal.setCurrentPenID(null);
                    AnimalModel.editAnimal(animal);

                    AnimalController.refresh(animal.getBreed());
                    AviaryAnimalController.refresh(pen);
                    AviaryController.refresh();
                }
            }
        } else {
            System.out.println(animal.getName() + " will stay in this pen");
        }
    }

    public static void animalFromDry (Animal animal) {
        String currentPenID = animal.getCurrentPenID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText("Are you sure you wish to remove " +animal.getName()+ " from pen #" +currentPenID);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            if (currentPenID != null) {
                Dry pen = DryModel.getPenBy(currentPenID);
                if (pen != null) {
                    pen.removeAnimalFromPen(animal);
                    DryModel.editPen(pen);

                    animal.setCurrentPenID(null);
                    AnimalModel.editAnimal(animal);

                    AnimalController.refresh(animal.getBreed());
                    DryAnimalController.refresh(pen);
                    DryController.refresh();
                }
            }
        } else {
            System.out.println(animal.getName() + " will stay in this pen");
        }
    }

    public static void animalFromPetting (Animal animal) {
        String currentPenID = animal.getCurrentPenID();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText("Are you sure you wish to remove " +animal.getName()+ " from pen #" +currentPenID);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            if (currentPenID != null) {
                Petting pen = PettingModel.getPenBy(currentPenID);
                if (pen != null) {
                    pen.removeAnimalFromPen(animal);
                    PettingModel.editPen(pen);

                    animal.setCurrentPenID(null);
                    AnimalModel.editAnimal(animal);

                    AnimalController.refresh(animal.getBreed());
                    PettingAnimalController.refresh(pen);
                    PettingController.refresh();
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
                SemiAquatic pen = SemiAquaticModel.getPenBy(currentPenID);
                if (pen != null) {
                    pen.removeAnimalFromPen(animal);
                    SemiAquaticModel.editPen(pen);

                    animal.setCurrentPenID(null);
                    AnimalModel.editAnimal(animal);

                    AnimalController.refresh(animal.getBreed());
                    SemiAquaticAnimalController.refresh(pen);
                    SemiAquaticController.refresh();
                }
            }
        } else {
            System.out.println(animal.getName() + " will stay in this pen");
        }
    }

    public static void animalFromDryOrPetting(Animal animal) {
        if (animal.getCurrentPenID().contains("DR")) {
            animalFromDry(animal);
        } else if (animal.getCurrentPenID().contains("PE")) {
            animalFromPetting(animal);
        }
    }

    private static void animalToAquarium (Animal animal) {
        Dialog<Aquarium> dialog = new Dialog<>();
        dialog.setTitle("Add "+ animal.getName() +" to an Aquarium");
        dialog.setHeaderText("Add " + animal.getName() + " to " + animal.getBreedPenType());
        dialog.setResizable(true);
        Label desc = new Label("Aquariums: ");
        ObservableList<Aquarium> pens = FXCollections.observableArrayList();
        ArrayList<Aquarium> aquariums = AquariumModel.getAllPensWithSpaceRemaining(animal);
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
            AquariumModel.editPen(result.get());

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
        ArrayList<Aviary> aviaries = AviaryModel.getAllPensWithSpaceRemaining(animal);
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
            AviaryModel.editPen(result.get());

            AnimalController.refresh(animal.getBreed());
            BreedController.refresh();

            AviaryController.refresh();
        }
    }

    private static void animalToDry (Animal animal) {
        Dialog<Dry> dialog = new Dialog<>();
        dialog.setTitle("Add "+ animal.getName() +" to a Dry Pen");
        dialog.setHeaderText("Add " + animal.getName() + " to a dry pen: ");
        dialog.setResizable(true);
        Label desc = new Label("Dry Pens: ");
        ObservableList<Dry> pens = FXCollections.observableArrayList();
        ArrayList<Dry> drypens = DryModel.getAllPensWithSpaceRemaining(animal);
        for (Dry drypen: drypens) {
            pens.addAll(drypen);
        }
        ChoiceBox<Dry> penChoiceBox = new ChoiceBox<>(pens);

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
                .addListener((ObservableValue<? extends Dry> observable, Dry oldValue, Dry newValue) ->
                        refresh(animalsinPen, newValue)
                );

        GridPane animalDialogGridPane = new GridPane();
        animalDialogGridPane.add(desc, 1, 1);
        animalDialogGridPane.add(penChoiceBox, 2, 1);
        animalDialogGridPane.add(currentAnimalsInPen, 2, 2);
        dialog.getDialogPane().setContent(animalDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Dry>() {
            @Override
            public Dry call(ButtonType button) {
                if (button == buttonTypeOk) {
                    return penChoiceBox.getSelectionModel().getSelectedItem();
                }
                return null;
            }
        });

        Optional<Dry> result = dialog.showAndWait();
        if (result.isPresent()) {

            animal.setCurrentPenID(result.get().getPenID());
            AnimalModel.editAnimal(animal);

            result.get().addAnimalToPen(animal);
            DryModel.editPen(result.get());

            AnimalController.refresh(animal.getBreed());
            BreedController.refresh();

            DryController.refresh();
        }
    }

    private static void animalToPetting (Animal animal) {
        Dialog<Petting> dialog = new Dialog<>();
        dialog.setTitle("Add "+ animal.getName() +" to a Petting Pen");
        dialog.setHeaderText("Add " + animal.getName() + " to petting pen: ");
        dialog.setResizable(true);
        Label desc = new Label("Petting Pens: ");
        ObservableList<Petting> pens = FXCollections.observableArrayList();
        ArrayList<Petting> pettingPens = PettingModel.getAllPensWithSpaceRemaining(animal);
        for (Petting pettingPen : pettingPens) {
            pens.addAll(pettingPen);
        }
        ChoiceBox<Petting> penChoiceBox = new ChoiceBox<>(pens);

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
                .addListener((ObservableValue<? extends Petting> observable, Petting oldValue, Petting newValue) ->
                        refresh(animalsinPen, newValue)
                );

        GridPane animalDialogGridPane = new GridPane();
        animalDialogGridPane.add(desc, 1, 1);
        animalDialogGridPane.add(penChoiceBox, 2, 1);
        animalDialogGridPane.add(currentAnimalsInPen, 2, 2);
        dialog.getDialogPane().setContent(animalDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Petting>() {
            @Override
            public Petting call(ButtonType button) {
                if (button == buttonTypeOk) {
                    return penChoiceBox.getSelectionModel().getSelectedItem();
                }
                return null;
            }
        });

        Optional<Petting> result = dialog.showAndWait();
        if (result.isPresent()) {

            animal.setCurrentPenID(result.get().getPenID());
            AnimalModel.editAnimal(animal);

            result.get().addAnimalToPen(animal);
            PettingModel.editPen(result.get());

            AnimalController.refresh(animal.getBreed());
            BreedController.refresh();

            PettingController.refresh();
        }
    }

    private static void animalToSemiAquatic (Animal animal) {
        Dialog<SemiAquatic> dialog = new Dialog<>();
        dialog.setTitle("Add "+ animal.getName() +" to a SemiAquatic Pen");
        dialog.setHeaderText("Add " + animal.getName() + " to a semiaquatic pen: ");
        dialog.setResizable(true);
        Label desc = new Label("SemiAquatic Pens: ");
        ObservableList<SemiAquatic> pens = FXCollections.observableArrayList();
        ArrayList<SemiAquatic> semiAquatics = SemiAquaticModel.getAllPensWithSpaceRemaining(animal);
        for (SemiAquatic semiAquatic: semiAquatics) {
            pens.addAll(semiAquatic);
        }
        ChoiceBox<SemiAquatic> penChoiceBox = new ChoiceBox<>(pens);

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
                .addListener((ObservableValue<? extends SemiAquatic> observable, SemiAquatic oldValue, SemiAquatic newValue) ->
                        refresh(animalsinPen, newValue)
                );

        GridPane animalDialogGridPane = new GridPane();
        animalDialogGridPane.add(desc, 1, 1);
        animalDialogGridPane.add(penChoiceBox, 2, 1);
        animalDialogGridPane.add(currentAnimalsInPen, 2, 2);
        dialog.getDialogPane().setContent(animalDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, SemiAquatic>() {
            @Override
            public SemiAquatic call(ButtonType button) {
                if (button == buttonTypeOk) {
                    return penChoiceBox.getSelectionModel().getSelectedItem();
                }
                return null;
            }
        });

        Optional<SemiAquatic> result = dialog.showAndWait();
        if (result.isPresent()) {

            animal.setCurrentPenID(result.get().getPenID());
            AnimalModel.editAnimal(animal);

            result.get().addAnimalToPen(animal);
            SemiAquaticModel.editPen(result.get());

            AnimalController.refresh(animal.getBreed());
            BreedController.refresh();

            SemiAquaticController.refresh();
        }
    }

    private static void animalToDryOrPetting (Animal animal) {
        Dialog<Pen> dialog = new Dialog<>();
        dialog.setTitle("Add "+ animal.getName() +" to a Dry or Petting Pen");
        dialog.setHeaderText("Add " + animal.getName() + " to " + animal.getBreedPenType());
        dialog.setResizable(true);
        Label desc = new Label("Pens: ");
        ObservableList<Pen> pens = FXCollections.observableArrayList();
        ArrayList<Petting> pettingPens = PettingModel.getAllPensWithSpaceRemaining(animal);
        ArrayList<Dry> dryPens = DryModel.getAllPensWithSpaceRemaining(animal);

        for (Petting pettingPen : pettingPens) {
            pens.addAll(pettingPen);
        }
        for (Dry dryPen : dryPens) {
            pens.addAll(dryPen);
        }

        ChoiceBox<Pen> penChoiceBox = new ChoiceBox<>(pens);

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
                .addListener((ObservableValue<? extends Pen> observable, Pen oldValue, Pen newValue) ->
                        refresh(animalsinPen, newValue)
                );

        GridPane animalDialogGridPane = new GridPane();
        animalDialogGridPane.add(desc, 1, 1);
        animalDialogGridPane.add(penChoiceBox, 2, 1);
        animalDialogGridPane.add(currentAnimalsInPen, 2, 2);
        dialog.getDialogPane().setContent(animalDialogGridPane);

        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Pen>() {
            @Override
            public Pen call(ButtonType button) {
                if (button == buttonTypeOk) {
                    return penChoiceBox.getSelectionModel().getSelectedItem();
                }
                return null;
            }
        });

        Optional<Pen> result = dialog.showAndWait();
        if (result.isPresent()) {
            animal.setCurrentPenID(result.get().getPenID());
            AnimalModel.editAnimal(animal);

            if (result.get().getPenID().contains("DR")) {
                //DRY
                Dry pen = (Dry) result.get();
                pen.addAnimalToPen(animal);
                DryModel.editPen(pen);
                DryController.refresh();
            } else if (result.get().getPenID().contains("PE")) {
                //PETTING
                Petting pen = (Petting) result.get();
                pen.addAnimalToPen(animal);
                PettingModel.editPen(pen);
                PettingController.refresh();
            }

            AnimalController.refresh(animal.getBreed());
            BreedController.refresh();
        }
    }

    public static void refresh (ObservableList<Animal> animalList, Pen pen) {
        animalList.clear();
        animalList.addAll(pen.getContainedAnimals());
    }
}
