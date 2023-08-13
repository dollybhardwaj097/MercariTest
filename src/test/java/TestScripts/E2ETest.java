package TestScripts;

import Helpers.PetHelpers;
import POJO.*;
import ResponsePOJO.OrderResponse;
import ResponsePOJO.PetResponse;
import com.relevantcodes.extentreports.ExtentTest;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import java.util.Collections;

import static Reporting.ExtentReportManager.extentReports;


public class E2ETest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(E2ETest.class);

    private ExtentTest test;
    private static final String PHOTO_URL = "https://fakeimg.pl/300/";
    PetHelpers petHelpers;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    String currentTimestamp = LocalDateTime.now().format(formatter);

    Pet pet = new Pet.Builder()
            .withId(RandomStringUtils.randomNumeric(1))
            .withName("pupo")
            .withPhotoUrls(Collections.singletonList(PHOTO_URL))
            .withStatus(Status.available)
            .withTags(Collections.singletonList(new Tag(1, "pajaro")))
            .inCategory(new Category(1, "pajaro")).build();

    PlaceOrder placeOrder = new PlaceOrder.Builder()
            .withId(Long.parseLong(RandomStringUtils.randomNumeric(1)))
            .withPetId(Long.parseLong(pet.getId()))
            .withQuantity(1)
            .withShipDate(currentTimestamp)
            .withStatus("placed")
            .withComplete(true).build();

    @BeforeClass
    public void beforeClass() {
        petHelpers = new PetHelpers();
    }

/*
Scenario:Buyer can check available pets named “pupo”
with category name “pajaro” and place an order for a pet
 */
    @Test()
    public void e2eTest() {
        com.aventstack.extentreports.ExtentTest extentTest = extentReports.createTest("E2E Test");
        extentTest.info("Additional logs for the test");
        // Step 1: Add a new pet
        Response addPetResponse = petHelpers.addNewPet(pet, captor);
        PetResponse addedPet = addPetResponse.as(PetResponse.class);
        logger.info("============== Response Is =============");
        logger.info(addPetResponse.prettyPrint());
        Assert.assertEquals(addedPet.getId(), pet.getId());
        //writeRequestAndResponseInReport(writer.toString(), addPetResponse.prettyPrint());

        // Step 2: Verify the new pet is added
        PetResponse verifyPetResponse = petHelpers.findPet(pet);
        logger.info("    ============== Pet Response Is =============");
        logger.info(verifyPetResponse.toString());
        Assert.assertEquals(verifyPetResponse.getName(), pet.getName());

        // Step 3: Buyer places an order for the pet
        Response orderResponse = petHelpers.placeOrderForPet(placeOrder, captor);
        OrderResponse placedOrder  = orderResponse.as(OrderResponse.class);
        logger.info("    ============== Places Order Response Is =============");
       logger.info(orderResponse.prettyPrint());
        String orderId = orderResponse.jsonPath().getString("id");
        //Assert.assertEquals(placedOrder.getId(), pet.getId());
        Assert.assertEquals(placedOrder.getStatus(), "placed");
       // writeRequestAndResponseInReport(writer.toString(), orderResponse.prettyPrint());

        // Step 4: Verify the order
        Response verifyOrderResponse = petHelpers.findOrder(Integer.parseInt(orderId));
        OrderResponse res = orderResponse.as(OrderResponse.class);
        logger.info("    ============== Verified Order Response Is =============");
        logger.info(verifyOrderResponse.prettyPrint());
        Assert.assertEquals(res.getStatus(), "placed");
        attachLogs("Additional logs for the test");
    }
    private void attachLogs(String logMessage) {
        Allure.step(logMessage);
    }
}
