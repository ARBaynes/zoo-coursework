package controllers.critters;

import controllers.main.MainController;
import controllers.pens.aquarium.AnimalToAquariumController;
import controllers.pens.aviary.AnimalToAviaryController;
import controllers.pens.dry.AnimalToDryController;
import controllers.pens.petting.AnimalToPettingController;
import controllers.pens.semiaquatic.AnimalToSemiAquatic;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import classes.critters.Animal;
import classes.pens.*;
import models.critters.AnimalModel;
import controllers.pens.aquarium.AquariumAnimalController;
import controllers.pens.aquarium.AquariumController;
import models.pens.AquariumModel;
import controllers.pens.aviary.AviaryAnimalController;
import controllers.pens.aviary.AviaryController;
import models.pens.AviaryModel;
import controllers.pens.dry.DryAnimalController;
import controllers.pens.dry.DryController;
import models.pens.DryModel;
import controllers.pens.petting.PettingAnimalController;
import controllers.pens.petting.PettingController;
import models.pens.PettingModel;
import controllers.pens.semiaquatic.SemiAquaticAnimalController;
import controllers.pens.semiaquatic.SemiAquaticController;
import models.pens.SemiAquaticModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

public class AnimalToPenController {
    public static void addAnimalToPen (Animal animal) {
        switch (animal.getBreedPenType().toLowerCase()) {
            case "aquarium":
                AnimalToAquariumController.animalToAquarium(animal);
                break;
            case "aviary":
                AnimalToAviaryController.animalToAviary(animal);
                break;
            case "dry":
                AnimalToDryController.animalToDry(animal);
                break;
            case "petting":
                AnimalToPettingController.animalToPetting(animal);
                break;
            case "semiaquatic":
                AnimalToSemiAquatic.animalToSemiAquatic(animal);
                break;
            default:
                AnimalToDryOrPettingController.animalToDryOrPetting(animal);
        }
    }

    public static void autoAddAnimalToPen (Animal animal) {
        switch (animal.getBreedPenType().toLowerCase()) {
            case "aquarium":
                AnimalToAquariumController.autoAddAnimalToAquarium(animal);
                break;
            case "aviary":
                AnimalToAviaryController.autoAddAnimalToAviary(animal);
                break;
            case "dry":
                AnimalToDryController.autoAddAnimalToDry(animal);
                break;
            case "petting":
                AnimalToPettingController.autoAddAnimalToPetting(animal);
                break;
            case "semiaquatic":
                AnimalToSemiAquatic.autoAddAnimalToSemiAquatic(animal);
                break;
            default:
                AnimalToDryOrPettingController.autoAddAnimalToDryOrPetting(animal);
        }
    }

    public static void removeAnimalFromPen (Animal animal) {
        switch (animal.getBreedPenType().toLowerCase()) {
            case "aquarium":
                System.out.println("removeAnimalFromPen: animal, " + animal);
                AnimalToAquariumController.animalFromAquarium(animal);
                break;
            case "aviary":
                AnimalToAviaryController.animalFromAviary(animal);
                break;
            case "dry":
                AnimalToDryController.animalFromDry(animal);
                break;
            case "petting":
                AnimalToPettingController.animalFromPetting(animal);
                break;
            case "semiaquatic":
                AnimalToSemiAquatic.animalFromSemiAquatic(animal);
                break;
            default:
                AnimalToDryOrPettingController.animalFromDryOrPetting(animal);
        }
    }




    private static class AnimalToDryOrPettingController {
        public static void animalFromDryOrPetting(Animal animal) {
            if (animal.getCurrentPenID().contains("DR")) {
                AnimalToDryController.animalFromDry(animal);
            } else if (animal.getCurrentPenID().contains("PE")) {
                AnimalToPettingController.animalFromPetting(animal);
            }
        }

        public static void animalToDryOrPetting(Animal animal) {
            List<String> choices = new ArrayList<>();
            choices.add("dry");
            choices.add("petting");

            ChoiceDialog<String> dialog = new ChoiceDialog<>("dry", choices);
            dialog.setTitle("Choose A Pen Type");
            dialog.setHeaderText("Choose A Pen Type:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                if (result.get().equals("dry")) {
                    AnimalToDryController.animalToDry(animal);
                } else {
                    AnimalToPettingController.animalToPetting(animal);
                }
            }
        }

        private static void autoAddAnimalToDryOrPetting (Animal animal) {
            ArrayList<Dry> allSuitableDryPens = DryModel.getAllAppropriatePens(animal);
            ArrayList<Petting> allSuitablePettingPens = PettingModel.getAllAppropriatePens(animal);
            if (allSuitableDryPens.isEmpty() && allSuitablePettingPens.isEmpty()) {
                noPensAlert(animal).showAndWait();
                return;
            }
            if (!allSuitablePettingPens.isEmpty() && allSuitableDryPens.isEmpty()) {
                AnimalToPettingController.autoAddAnimalToPetting(animal);
            }
            if (allSuitablePettingPens.isEmpty()&& !allSuitableDryPens.isEmpty() ) {
                AnimalToDryController.autoAddAnimalToDry(animal);
            }
            Random rand = new Random();
            if (rand.nextInt(1 )== 0) {
                AnimalToDryController.autoAddAnimalToDry(animal);
            } else {
                AnimalToPettingController.autoAddAnimalToPetting(animal);
            }
        }

        private static Alert noPensAlert (Animal animal) {
            Alert noPensAlert = new Alert(Alert.AlertType.ERROR);
            noPensAlert.setHeaderText("Error");
            noPensAlert.setContentText("No Free "+ animal.getBreedPenType() + " Available, " + animal.getName() + " cannot be automatically assigned a pen!");
            return noPensAlert;
        }

    }

}
