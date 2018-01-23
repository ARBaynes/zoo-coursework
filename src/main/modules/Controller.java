package main.modules;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import main.classes.critters.Animal;
import main.classes.critters.Breed;
import main.classes.pens.*;
import main.classes.staff.Staff;
import main.modules.critters.AnimalController;
import main.modules.critters.BreedController;
import main.modules.main.MainController;
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
    public TableColumn aquariumKeeperID;


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
    public TableColumn aviaryKeeperID;

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
    public TableColumn dryKeeperID;

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
    public TableColumn semiAquaticKeeperID;

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
    public TableColumn pettingKeeperID;

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

    //MAIN TAB ------------------------------

    //WEATHER
    public HBox weatherBox;
    public Label weatherLabel;

    //ANIMALS
    public TableView animalTableMain = new TableView<Animal>();
    public TableColumn animalIDColMain;
    public TableColumn animalNameColMain;
    public TableColumn animalBreedColMain;


    //STAFF
    public TableView staffTableMain = new TableView<Staff>();
    public TableColumn staffIDColMain;
    public TableColumn staffNameColMain;
    public TableColumn staffResponsibilitiesColMain;


    //PENS
    public TableView penTableMain = new TableView<Pen>();
    public TableColumn penIDColMain;
    public TableColumn penTypeColMain;
    public TableColumn penContainedAnimalsColMain;

    // -----------------------------------------

    @FXML
    public void initialize() {

        MainController.construct(
                animalTableMain, animalIDColMain, animalNameColMain, animalBreedColMain,
                staffTableMain, staffIDColMain, staffNameColMain, staffResponsibilitiesColMain,
                penTableMain, penIDColMain, penTypeColMain, penContainedAnimalsColMain
        );
        StaffController.construct(
                staffTableView, addNewStaffButton, staffID, staffName, staffResponsibleFor
        );
        AnimalController.construct(
                animalTableView, animalTableColBreed, animalTableColID, animalTableColName, animalTableColHasPen, animalTypeLabel
        );
        BreedController.construct(
                breedTableView, breedName, breedPenType, breedRequirements, addBreedButton
        );
        AquariumController.construct(
                aquariumToolbar,addAquariumButton, aquariumPenTableView, aquariumPenID, aquariumTemp, aquariumContainedAnimals, aquariumHeight,
                aquariumWaterVolume, aquariumCurrentVolume, aquariumWaterType, aquariumKeeperID
        );
        AquariumAnimalController.construct(
                aquariumAnimalTableView, aquariumAnimalName, aquariumAnimalBreed, aquariumAnimalSize, aquariumAnimalID
        );
        AviaryController.construct(
                aviaryToolbar, addAviaryButton, aviaryPenTableView, aviaryPenID, aviaryTemp, aviaryContainedAnimals, aviaryHeight,
                aviaryMaxVolume, aviaryCurrentVolume, aviaryKeeperID
        );
        AviaryAnimalController.construct(
                aviaryAnimalTableView, aviaryAnimalName, aviaryAnimalBreed, aviaryAnimalSize, aviaryAnimalID
        );
        DryController.construct(
                dryToolbar, addDryButton, dryPenTableView, dryPenID, dryTemp, dryContainedAnimals, dryMaxArea, dryCurrentArea, dryKeeperID
        );
        DryAnimalController.construct(
                dryAnimalTableView, dryAnimalName, dryAnimalBreed, dryAnimalSize, dryAnimalID
        );
        PettingController.construct(
                pettingToolbar, addPettingButton, pettingPenTableView, pettingPenID, pettingTemp, pettingContainedAnimals, pettingMaxArea,
                pettingCurrentArea, pettingKeeperID
        );
        PettingAnimalController.construct(
                pettingAnimalTableView, pettingAnimalName, pettingAnimalBreed, pettingAnimalSize, pettingAnimalID
        );
        SemiAquaticController.construct(
                semiAquaticToolbar, addSemiAquaticButton, semiAquaticPenTableView, semiAquaticPenID, semiAquaticTemp, semiAquaticContainedAnimals,
                semiAquaticMaxWaterVolume, semiAquaticCurrentWaterVolume, semiAquaticWaterType, semiAquaticMaxArea, semiAquaticCurrentArea, semiAquaticKeeperID
        );
        SemiAquaticAnimalController.construct(
                semiAquaticAnimalTableView, semiAquaticAnimalName, semiAquaticAnimalBreed, semiAquaticAnimalSize, semiAquaticAnimalID
        );



        MainController.outline();
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


        MainController.refresh();
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
