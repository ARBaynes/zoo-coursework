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
    public Label animalTypeLabel;



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
    public TableColumn aquariumMaxArea;
    public TableColumn aquariumCurrentArea;
    public TableColumn aquariumContainedAnimals;
    public TableColumn aquariumHeight;
    public TableColumn aquariumWaterVolume;
    public TableColumn aquariumWaterType;

    public Label aquariumAnimalLabel;
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

        //MAIN TAB

        //ANIMAL TAB

        //PEN TAB

        //AQUARIUM PANE

        AquariumController aquariumTab = new AquariumController();

        //Outline table data

        aquariumTab.outlinePenTableData(
                aquariumPenTableView, aquariumPenID, aquariumTemp,
                aquariumMaxArea, aquariumCurrentArea, aquariumContainedAnimals,
                aquariumHeight, aquariumWaterVolume, aquariumWaterType
        );

        //Outline table row actions



        //Load table items

        aquariumTab.setTableItems(aquariumPenTableView);

        //Set button action

        aquariumTab.setAddAquariumButtonAction(addAquariumButton, aquariumPenTableView);








        //AVIARY PANE

        //DRY PEN PANE

        //PETTING PANE

        //SEMI AQUATIC PANE

        //STAFF TAB

    }
}
