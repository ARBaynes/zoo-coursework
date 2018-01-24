package models.critters;

import classes.critters.Breed;
import models.critters.BreedModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BreedModelTest {
    private Breed testBreed;

    public BreedModelTest () {
        this.testBreed = new Breed("Marinara Sauce", "dry", new Double(15), "area");
    }


    @Before
    public void addTestBreed () {
        BreedModel.addBreed(testBreed);
    }

    @After
    public void removeTestBreed () {
        BreedModel.removeBreed(testBreed);
    }


    // ASSERT-NOT-NULL TESTS


    @Test
    public void allBreedsNotNull () {
        Assert.assertNotNull(BreedModel.getAllBreeds());
    }

    @Test
    public void allBreedsByPenTypeNotNull () {
        Assert.assertNotNull(BreedModel.getAllBreedsWhere(testBreed.getPenType()));
    }

    @Test
    public void breedByNameNotNull () {
        Assert.assertNotNull(BreedModel.getABreedWhere(testBreed.getName()));
    }

    @Test
    public void allBreedNamesNotNull () {
        Assert.assertNotNull(BreedModel.getAllBreedNames());
    }

    // ASSERT-CONTAINS TESTS

    @Test
    public void allBreedsContainsTestBreed () {
        Assert.assertTrue(BreedModel.getAllBreeds().contains(testBreed));
    }

    @Test
    public void allBreedsByPenTypeContainsTestBreed () {
        Assert.assertTrue(BreedModel.getAllBreedsWhere(testBreed.getPenType()).contains(testBreed));
    }


    // ASSERT-EQUALS TESTS

    @Test
    public void breedByNameReturnsTestBreed () {
        Assert.assertEquals(testBreed, BreedModel.getABreedWhere(testBreed.getName()));
    }



}
