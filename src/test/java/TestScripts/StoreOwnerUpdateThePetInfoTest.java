package TestScripts;

import Helpers.PetHelpers;
import POJO.Category;
import POJO.Pet;
import POJO.Tag;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoreOwnerUpdateThePetInfoTest extends BaseTest
{
    private static final Logger logger = LoggerFactory.getLogger(E2ETest.class);
    private static final String PHOTO_URL = "https://fakeimg.pl/300/";
    PetHelpers petHelpers;
    @BeforeClass
    public void beforeClass() {
        petHelpers = new PetHelpers();
    }

    Pet updatedPet = new Pet.Builder()
            .withName("kurikuri")
            .inCategory(new Category(123, "Pomeranian")) // Assuming category ID is 123
            .withTags(Collections.singletonList(new Tag(456, "Super Cute"))) // Assuming tag ID is 456
            .build();
    @Test(description ="Store owner update the pet info")
    public void storeOwnerUpdateThePetInfoTest()
    {
        Response updateResponse = petHelpers.updatePet(updatedPet, captor);
        Assert.assertEquals(updateResponse.getStatusCode(), 200, "Unexpected status code");
        Pet updatedPetResponse = updateResponse.as(Pet.class);
        logger.info("Updated pet info: " + updatedPetResponse);
    }
}
