package TestScripts;

import Helpers.PetHelpers;
import POJO.Category;
import POJO.Pet;
import POJO.Status;
import POJO.Tag;
import ResponsePOJO.PetResponse;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

public class PetAPITests extends BaseTest{
    private static final String PHOTO_URL = "https://fakeimg.pl/300/";
    PetHelpers petHelpers;
    Pet pet = new Pet.Builder()
            .withId(RandomStringUtils.randomNumeric(2))
            .withName("My pet")
            .withPhotoUrls(Collections.singletonList(PHOTO_URL))
            .withStatus(Status.available)
            .withTags(Collections.singletonList(new Tag(1, "golden-retriever")))
            .inCategory(new Category(1, "dogs")).build();

    @BeforeClass
    public void beforeClass() {
        petHelpers = new PetHelpers();
    }

    @Test(priority = 0)
    public void addNewPet() {
        Response response = petHelpers.addNewPet(pet, captor);
        PetResponse petResponse = response.as(PetResponse.class);
        System.out.println("============== Response Is =============");
        System.out.println(response.prettyPrint());
        Assert.assertEquals(petResponse.getId(), pet.getId());

        writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

    }

    @Test(priority = 1,description = "Verify new pet is added")
    public void verifyNewPet() {
        PetResponse petResponse = petHelpers.findPet(pet);
        Assert.assertEquals(petResponse.getName(), pet.getName());
    }

    @Test(priority = 2,description = "Update pet name and status")
    public void updatePet() {
        pet.setName("New name for my pet");
        pet.setStatus(Status.pending);
        Response response = petHelpers.updatePet(pet, captor);
        PetResponse petResponse = response.as(PetResponse.class);
        Assert.assertEquals(petResponse.getName(), pet.getName());
        Assert.assertEquals(petResponse.getStatus(), pet.getStatus().toString());
        writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());
    }

    @Test(priority = 3)
    public void verifyUpdatedPet() {
        PetResponse petResponse = petHelpers.findPet(pet);
        Assert.assertEquals(petResponse.getName(), pet.getName());
    }
}
