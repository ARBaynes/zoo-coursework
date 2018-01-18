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
import main.modules.critters.CritterController;
import main.modules.pens.aquarium.AquariumController;

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



    //TABLE ITEMS
    ObservableList<Breed> breedTableViewItems = FXCollections.observableArrayList();
    ObservableList<Animal> animalsObservableList = FXCollections.observableArrayList();

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
    public TableColumn aquariumAnimalID;

    public Label aquariumAnimalLabel;
    public Button aquariumAddNewAnimalToPenButton;
    public TableView aquariumAnimalTableView = new TableView<Animal>();
    public TableColumn aquariumAnimalName;
    public TableColumn aquariumAnimalBreed;
    public TableColumn aquariumAnimalSize;

    //AVIARY JANK
    public ToolBar aviaryToolbar;
    public Button addAviaryButton;
    public TableView aviaryPenTableView = new TableView<Aviary>();
    public TableColumn aviaryPenID;
    public TableColumn aviaryTemp;
    public TableColumn aviaryMaxArea;
    public TableColumn aviaryCurrentArea;
    public TableColumn aviaryContainedAnimals;
    public TableColumn aviaryHeight;

    public Label aviaryAnimalLabel;
    public Button aviaryAddNewAnimalToPenButton;
    public TableView aviaryAnimalTableView = new TableView<Animal>();
    public TableColumn aviaryAnimalName;
    public TableColumn aviaryAnimalBreed;
    public TableColumn aviaryAnimalSize;

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
    public Button dryAddNewAnimalToPenButton;
    public TableView dryAnimalTableView = new TableView<Animal>();
    public TableColumn dryAnimalName;
    public TableColumn dryAnimalBreed;
    public TableColumn dryAnimalSize;

    //SEMI AQUATIC JANK
    public ToolBar SemiAquaticToolbar;
    public Button addSemiAquaticButton;
    public TableView SemiAquaticPenTableView = new TableView<SemiAquatic>();
    public TableColumn SemiAquaticPenID;
    public TableColumn SemiAquaticTemp;
    public TableColumn SemiAquaticMaxArea;
    public TableColumn SemiAquaticCurrentArea;
    public TableColumn SemiAquaticContainedAnimals;
    public TableColumn SemiAquaticWaterVolume;
    public TableColumn SemiAquaticWaterType;

    public Label SemiAquaticAnimalLabel;
    public Button SemiAquaticAddNewAnimalToPenButton;
    public TableView SemiAquaticAnimalTableView = new TableView<Animal>();
    public TableColumn SemiAquaticAnimalName;
    public TableColumn SemiAquaticAnimalBreed;
    public TableColumn SemiAquaticAnimalSize;

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
    public Button pettingAddNewAnimalToPenButton;
    public TableView pettingAnimalTableView = new TableView<Animal>();
    public TableColumn pettingAnimalName;
    public TableColumn pettingAnimalBreed;
    public TableColumn pettingAnimalSize;

    //TABLE ITEMS

    //PENS

    ObservableList<Aviary> aviaryTableViewItems = FXCollections.observableArrayList();
    ObservableList<Dry> dryTableViewItems = FXCollections.observableArrayList();
    ObservableList<SemiAquatic> SemiAquaticTableViewItems = FXCollections.observableArrayList();
    ObservableList<Petting> pettingTableViewItems = FXCollections.observableArrayList();

    //PENANIMALS
    ObservableList<Animal> aquariumAnimalTableViewItems = FXCollections.observableArrayList();
    ObservableList<Animal> aviaryAnimalTableViewItems = FXCollections.observableArrayList();
    ObservableList<Animal> dryAnimalTableViewItems = FXCollections.observableArrayList();
    ObservableList<Animal> SemiAquaticAnimalTableViewItems = FXCollections.observableArrayList();
    ObservableList<Animal> pettingAnimalTableViewItems = FXCollections.observableArrayList();

    // -----------------------------------------

    //STAFF TAB ------------------------------

    //TABLE ITEMS
    ObservableList<Staff> staffTableViewItems = FXCollections.observableArrayList();

    // -----------------------------------------

    @FXML
    public void initialize()  throws IOException {

        //MAIN TAB --------------------------------------------------------------------------------------------------------------------------------------

        //CRITTER TAB --------------------------------------------------------------------------------------------------------------------------------------
        CritterController critterTab = new CritterController();

        //BREED PANE ------------------------------------------------------------------------
        //Outline table data

        /*critterTab.outlineBreedTableData(
                breedName, breedPenType, breedRequirements
        );

        critterTab.outlineAnimalTableData(animalTableColID, animalTableColName, animalTableColBreed, animalTableColHasPen);*/

        //Outline table row actions

        /*critterTab.outlineBreedTableRows(breedTableView, animalTableView, animalTypeLabel);

        critterTab.outlineAnimalTableRows(animalTableView, animalTypeLabel,
                aquariumAnimalTableView, aquariumPenTableView,
                aviaryAnimalTableView, aviaryPenTableView,
                dryAnimalTableView, dryPenTableView,
                pettingAnimalTableView, pettingPenTableView,
                SemiAquaticPenTableView, SemiAquaticPenTableView);

        //Load table items

        critterTab.setBreedTableItems(breedTableView);
        critterTab.setAnimalTableItems(animalTableView);*/

        //Set button action

        /*critterTab.setAddBreedButtonAction(addBreedButton, breedTableView);*/

        //ANIMAL PANE ------------------------------------------------------------------------

        AnimalController.construct(animalTableView, animalTableColBreed, animalTableColID, animalTableColName, animalTableColHasPen, animalTypeLabel);
        BreedController.construct(breedTableView, breedName, breedPenType, breedRequirements, addBreedButton);

        AnimalController.outline();
        BreedController.outline();

        BreedController.refresh();
        AnimalController.refresh();

        //PEN TAB --------------------------------------------------------------------------------------------------------------------------------------

        //AQUARIUM PANE ------------------------------------------------------------------------

        AquariumController aquariumTab = new AquariumController();

        //Outline table data

        aquariumTab.outlinePenTableData(
                aquariumPenID, aquariumTemp, aquariumWaterVolume,
                aquariumCurrentVolume, aquariumContainedAnimals,
                aquariumHeight, aquariumWaterType
        );
        aquariumTab.outlineAnimalPenTableData(
                aquariumAnimalID, aquariumAnimalName, aquariumAnimalBreed, aquariumAnimalSize
        );

        //Outline table row actions

        aquariumTab.outlineTableRows(aquariumPenTableView, aquariumAnimalTableView, aquariumAnimalLabel);
        aquariumTab.outlineAnimalPenTableRows(aquariumAnimalTableView, aquariumPenTableView);


        //Load table items

        aquariumTab.setTableItems(aquariumPenTableView);
        aquariumTab.setAnimalTableItems(aquariumAnimalTableView);

        //Set button action

        aquariumTab.setAddAquariumButtonAction(addAquariumButton, aquariumPenTableView);








        //AVIARY PANE ------------------------------------------------------------------------

        //DRY PEN PANE ------------------------------------------------------------------------

        //PETTING PANE ------------------------------------------------------------------------

        //SEMI AQUATIC PANE ------------------------------------------------------------------------

        //STAFF TAB --------------------------------------------------------------------------------------------------------------------------------------

    }
}
