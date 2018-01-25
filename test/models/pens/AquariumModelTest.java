package models.pens;

import classes.critters.Animal;
import classes.critters.Breed;
import classes.pens.Aquarium;
import classes.staff.Staff;
import models.critters.BreedModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AquariumModelTest {
    private Aquarium testPen;
    private Animal testBigAnimal;
    private Animal testSmallAnimal;

    public AquariumModelTest() {
        this.testPen = new Aquarium(new Double(50), new Double(50), new Double(71), new Double(12), "salt", 0);
        testPen.setPenID("AQ0");
        this.testBigAnimal= new Animal("Big Bronte", new Breed("Hungry Hungry Hippo", "aquarium", new Double (1400000000), "volume"));
        this.testSmallAnimal= new Animal("Small Selma", new Breed("B A B B Y", "aquarium", new Double (12), "volume"));
    }

    @Before
    public void addTestPen () {
        AquariumModel.addPen(testPen);
    }

    @After
    public void removeTestPen () {
        AquariumModel.removePen(testPen);
    }


    // ASSERT-NOT-NULL TESTS


    @Test
    public void allPensNotNull () {
        Assert.assertNotNull(AquariumModel.getAllPens());
    }

    @Test
    public void allPensBySpaceRemainingNotNull () {
        Assert.assertNotNull(AquariumModel.getAllAppropriatePens(testSmallAnimal));
    }

    @Test
    public void penByIDNotNull () {
        Assert.assertNotNull(AquariumModel.getPenBy(testPen.getPenID()));
    }

    // ASSERT-CONTAINS TESTS

    @Test
    public void allPensContainsTestPen () {
        Assert.assertTrue(AquariumModel.getAllPens().contains(testPen));
    }

    @Test
    public void allPensBySpaceRemainingContainsTestPen () {
        Assert.assertTrue(AquariumModel.getAllAppropriatePens(testSmallAnimal).contains(testPen));
    }

    @Test
    public void allPensBySpaceRemainingDoesNotContainTestPen () {
        Assert.assertFalse(AquariumModel.getAllAppropriatePens(testBigAnimal).contains(testPen));
    }

}
