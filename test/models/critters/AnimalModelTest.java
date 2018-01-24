package models.critters;

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
        this.testBreed = new Breed("Friendly Shark", "aquarium", 40.5, "volume");
        Animal animal =  new Animal("Bruce", testBreed);
        animal.setID(0);
        this.testAnimal = animal;
    }


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
    public void allAnimalsNotNull () {
        Assert.assertNotNull(AnimalModel.getAllAnimals());
    }

    @Test
    public void allAnimalsByBreedNotNull () {
        Assert.assertNotNull(AnimalModel.getAllAnimalsWhere(testBreed));
    }

    @Test
    public void allAnimalsByPenTypeNotNull () {
        Assert.assertNotNull(AnimalModel.getAllAnimalsWhere(testAnimal.getBreedPenType()));
    }

    @Test
    public void animalByIDNotNull () {
        Assert.assertNotNull(AnimalModel.getAnAnimalWhere(0));
    }

    @Test
    public void animalIDCreatorNotNull () {
        Assert.assertNotNull(AnimalModel.createAnimalID());
    }

    // ASSERT-CONTAINS TESTS

    @Test
    public void allAnimalsByBreedContainsTestAnimal () {
        ArrayList<Animal> animalList = AnimalModel.getAllAnimalsWhere(testBreed);
        Assert.assertTrue(animalList.contains(testAnimal));
    }

    @Test
    public void allAnimalsByPenTypeContainsTestAnimal () {
        ArrayList<Animal> animalList = AnimalModel.getAllAnimalsWhere(testAnimal.getBreedPenType());
        Assert.assertTrue(animalList.contains(testAnimal));
    }


    // ASSERT-EQUALS TESTS

    @Test
    public void animalByIDReturnsTestAnimal () {
        Assert.assertEquals(testAnimal, AnimalModel.getAnAnimalWhere(0));
    }



}
