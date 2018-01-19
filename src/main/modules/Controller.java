package main.modules;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import main.classes.critters.Animal;
import main.classes.critters.Breed;
import main.classes.pens.*;
import main.classes.staff.Staff;
import main.modules.critters.AnimalController;
import main.modules.critters.BreedController;
import main.modules.pens.aquarium.AquariumAnimalController;
import main.modules.pens.aquarium.AquariumController;
import main.modules.pens.aviary.AviaryAnimalController;
import main.modules.pens.aviary.AviaryController;
import main.modules.pens.dry.DryAnimalController;
import main.modules.pens.dry.DryController;
import main.modules.pens.petting.PettingAnimalController;
import main.modules.pens.petting.PettingController;
import main.modules.pens.semiaquatic.SemiAquaticAnimalController;
import main.modules.pens.semiaquatic.SemiAquaticController;
import main.modules.staff.StaffController;

import java.io.IOException;

public class Controller {

    //ANIMALS TAB ------------------------------

    public AnchorPane breedParentAnchorPane;
    public TableView breedTableView = new TableView<Breed>();
    public TableView animalTableView = new TableView<Animal>();
    public TableColumn breedName;
    public TableColumn breedPenType;
    public TableColumn breedRequirements;
    public TableColumn animalTableColID;
    public TableColumn animalTableColName;
    public TableColumn animalTableColHasPen;
    public Label animalTypeLabel;
    public Button addBreedButton;
    public ToolBar breedToolbar;
    public TableColumn animalTableColBreed;

    // -----------------------------------------

    //PENS TAB ------------------------------

    //AQUARIUM JANK
    public ToolBar aquariumToolbar;
    public Button addAquariumButton;
    public TableView aquariumPenTableView = new TableView<Aquarium>();
    public TableColumn aquariumPenID;
    public TableColumn aquariumTemp;
    public TableColumn aquariumContainedAnimals;
    public TableColumn aquariumHeight;
    public TableColumn aquariumWaterVolume;
    public TableColumn aquariumCurrentVolume;
    public TableColumn aquariumWaterType;


    public Label aquariumAnimalLabel;
    public TableView aquariumAnimalTableView = new TableView<Animal>();
    public TableColumn aquariumAnimalName;
    public TableColumn aquariumAnimalBreed;
    public TableColumn aquariumAnimalSize;
    public TableColumn aquariumAnimalID;

    //AVIARY JANK
    public ToolBar aviaryToolbar;
    public Button addAviaryButton;
    public TableView aviaryPenTableView = new TableView<Aviary>();
    public TableColumn aviaryPenID;
    public TableColumn aviaryTemp;
    public TableColumn aviaryMaxVolume;
    public TableColumn aviaryCurrentVolume;
    public TableColumn aviaryContainedAnimals;
    public TableColumn aviaryHeight;

    public Label aviaryAnimalLabel;
    public TableView aviaryAnimalTableView = new TableView<Animal>();
    public TableColumn aviaryAnimalName;
    public TableColumn aviaryAnimalBreed;
    public TableColumn aviaryAnimalSize;
    public TableColumn aviaryAnimalID;

    //DRY PEN JANK
    public ToolBar dryToolbar;
    public Button addDryButton;
    public TableView dryPenTableView = new TableView<Dry>();
    public TableColumn dryPenID;
    public TableColumn dryTemp;
    public TableColumn dryMaxArea;
    public TableColumn dryCurrentArea;
    public TableColumn dryContainedAnimals;

    public Label dryAnimalLabel;
    public TableView dryAnimalTableView = new TableView<Animal>();
    public TableColumn dryAnimalName;
    public TableColumn dryAnimalBreed;
    public TableColumn dryAnimalSize;
    public TableColumn dryAnimalID;

    //SEMI AQUATIC JANK
    public ToolBar semiAquaticToolbar;
    public Button addSemiAquaticButton;
    public TableView semiAquaticPenTableView = new TableView<SemiAquatic>();
    public TableColumn semiAquaticPenID;
    public TableColumn semiAquaticTemp;
    public TableColumn semiAquaticMaxArea;
    public TableColumn semiAquaticCurrentArea;
    public TableColumn semiAquaticContainedAnimals;
    public TableColumn semiAquaticMaxWaterVolume;
    public TableColumn semiAquaticCurrentWaterVolume;
    public TableColumn semiAquaticWaterType;

    public Label semiAquaticAnimalLabel;
    public TableView semiAquaticAnimalTableView = new TableView<Animal>();
    public TableColumn semiAquaticAnimalName;
    public TableColumn semiAquaticAnimalBreed;
    public TableColumn semiAquaticAnimalSize;
    public TableColumn semiAquaticAnimalID;

    //PETTING JANK
    public ToolBar pettingToolbar;
    public Button addPettingButton;
    public TableView pettingPenTableView = new TableView<Petting>();
    public TableColumn pettingPenID;
    public TableColumn pettingTemp;
    public TableColumn pettingMaxArea;
    public TableColumn pettingCurrentArea;
    public TableColumn pettingContainedAnimals;

    public Label pettingAnimalLabel;
    public TableView pettingAnimalTableView = new TableView<Animal>();
    public TableColumn pettingAnimalName;
    public TableColumn pettingAnimalBreed;
    public TableColumn pettingAnimalSize;
    public TableColumn pettingAnimalID;

    // -----------------------------------------

    //STAFF TAB ------------------------------

    public TableView staffTableView = new TableView<Staff>();
    public Button addNewStaffButton;
    public TableColumn staffID;
    public TableColumn staffName;
    public TableColumn staffResponsibleFor;


    // -----------------------------------------

    @FXML
    public void initialize()  throws IOException {

        StaffController.construct(staffTableView, addNewStaffButton, staffID, staffName, staffResponsibleFor);
        AnimalController.construct(animalTableView, animalTableColBreed, animalTableColID, animalTableColName, animalTableColHasPen, animalTypeLabel);
        BreedController.construct(breedTableView, breedName, breedPenType, breedRequirements, addBreedButton);
        AquariumController.construct(aquariumToolbar,addAquariumButton, aquariumPenTableView, aquariumPenID, aquariumTemp, aquariumContainedAnimals, aquariumHeight, aquariumWaterVolume, aquariumCurrentVolume, aquariumWaterType);
        AquariumAnimalController.construct(aquariumAnimalTableView, aquariumAnimalName, aquariumAnimalBreed, aquariumAnimalSize, aquariumAnimalID);
        AviaryController.construct(aviaryToolbar, addAviaryButton, aviaryPenTableView, aviaryPenID, aviaryTemp, aviaryContainedAnimals, aviaryHeight, aviaryMaxVolume, aviaryCurrentVolume);
        AviaryAnimalController.construct(aviaryAnimalTableView, aviaryAnimalName, aviaryAnimalBreed, aviaryAnimalSize, aviaryAnimalID);
        DryController.construct(dryToolbar, addDryButton, dryPenTableView, dryPenID, dryTemp, dryContainedAnimals, dryMaxArea, dryCurrentArea);
        DryAnimalController.construct(dryAnimalTableView, dryAnimalName, dryAnimalBreed, dryAnimalSize, dryAnimalID);
        PettingController.construct(pettingToolbar, addPettingButton, pettingPenTableView, pettingPenID, pettingTemp, pettingContainedAnimals, pettingMaxArea, pettingCurrentArea);
        PettingAnimalController.construct(pettingAnimalTableView, pettingAnimalName, pettingAnimalBreed, pettingAnimalSize, pettingAnimalID);
        SemiAquaticController.construct(semiAquaticToolbar, addSemiAquaticButton, semiAquaticPenTableView, semiAquaticPenID, semiAquaticTemp, semiAquaticContainedAnimals,
                semiAquaticMaxWaterVolume, semiAquaticCurrentWaterVolume, semiAquaticWaterType, semiAquaticMaxArea, semiAquaticCurrentArea);
        SemiAquaticAnimalController.construct(semiAquaticAnimalTableView, semiAquaticAnimalName, semiAquaticAnimalBreed, semiAquaticAnimalSize, semiAquaticAnimalID);



        StaffController.outline();
        AnimalController.outline();
        BreedController.outline();
        AquariumController.outline();
        AquariumAnimalController.outline();
        AviaryController.outline();
        AviaryAnimalController.outline();
        DryController.outline();
        DryAnimalController.outline();
        PettingController.outline();
        PettingAnimalController.outline();
        SemiAquaticController.outline();
        SemiAquaticAnimalController.outline();


        StaffController.refresh();
        BreedController.refresh();
        AnimalController.refresh();
        AquariumController.refresh();
        AviaryController.refresh();
        DryController.refresh();
        PettingController.refresh();
        SemiAquaticController.refresh();

    }
}
