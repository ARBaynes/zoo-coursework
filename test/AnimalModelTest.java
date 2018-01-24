import classes.critters.Animal;
import classes.critters.Breed;
import models.critters.AnimalModel;
import models.critters.BreedModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class AnimalModelTest {
    private Animal testAnimal;
    private Breed testBreed;

    public AnimalModelTest () {
        this.testBreed = new Breed("Shark", "aquarium", 40.5, "volume");

        Animal animal =  new Animal("Bruce", testBreed);
        animal.setID(0);
        this.testAnimal = animal;
    }



    /*@Before
    public void testData () {

    }*/

    @Before
    public void addTestAnimal () {
        AnimalModel.addAnimal(testAnimal);
    }

    @After
    public void removeTestAnimal () {
        AnimalModel.removeAnimal(testAnimal);
    }


    // ASSERT-NOT-NULL TESTS


    @Test
    public void getAllAnimalsNotNull () {
        Assert.assertNotNull(AnimalModel.getAllAnimals());
    }

    @Test
    public void getAllAnimalsByBreedNotNull () {
        Assert.assertNotNull(AnimalModel.getAllAnimalsWhere(testBreed));
    }

    @Test
    public void getAllAnimalsByPenTypeNotNull () {
        Assert.assertNotNull(AnimalModel.getAllAnimalsWhere("aquarium"));
    }

    @Test
    public void getAnimalByIDNotNull () {
        Assert.assertNotNull(AnimalModel.getAnAnimalWhere(0));
    }

    // ASSERT-CONTAINS TESTS

    @Test
    public void getAllAnimalsByBreedContains () {
        ArrayList<Animal> animalList = AnimalModel.getAllAnimalsWhere(testBreed);
        Assert.assertTrue(animalList.contains(testAnimal));
    }

    @Test
    public void getAllAnimalsByPenTypeContains () {
        ArrayList<Animal> animalList = AnimalModel.getAllAnimalsWhere("aquarium");
        Assert.assertTrue(animalList.contains(testAnimal));
    }


    // ASSERT-EQUALS TESTS


    @Test
    public void getAnimalByIDEquals () {
        Assert.assertEquals(testAnimal, AnimalModel.getAnAnimalWhere(0));
    }



}
