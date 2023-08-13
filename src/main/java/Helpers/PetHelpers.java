package Helpers;

import POJO.Pet;
import POJO.PlaceOrder;
import POJO.Status;
import ResponsePOJO.OrderResponse;
import ResponsePOJO.PetResponse;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import java.io.PrintStream;
import java.util.List;

import static Utilities.property.baseUri;
import static io.restassured.RestAssured.given;

public class PetHelpers {
    public static String PET_ENDPOINT = baseUri + "/pet";
    private RequestSpecification requestSpecification;
    PrintStream captor;

    public PetHelpers() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(baseUri);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();
    }

    /**
     * Add a new pet to the store
     */
    public Response addNewPet(Pet pet, PrintStream captor) {
        return given(requestSpecification).filter(new RequestLoggingFilter(captor))
                .body(pet)
                .post(PET_ENDPOINT);

    }

    public List<Pet> getPetsByStatus(Status status) {
        return given(requestSpecification).filter(new RequestLoggingFilter(captor))
                .queryParam("status", Status.available.toString())
                .get(PET_ENDPOINT + "/findByStatus")
                .then().log().all()
                .extract().body()
                .jsonPath().getList("", Pet.class);

    }

    public Response updatePet(Pet pet,PrintStream captor) {
        return given(requestSpecification).filter(new RequestLoggingFilter(captor))
                .body(pet)
                .put(PET_ENDPOINT);
    }

    public PetResponse findPet(Pet pet) {
        return given(requestSpecification)
                .pathParam("petId", pet.getId())
                .get(PET_ENDPOINT + "/{petId}").as(PetResponse.class);
    }
    public Response placeOrderForPet(PlaceOrder placeOrder, PrintStream captor) {
        return given(requestSpecification).filter(new RequestLoggingFilter(captor))
                .body(placeOrder)
                .post("/store/order");
    }


    public Response findOrder(int orderId) {
        return given(requestSpecification)
                .pathParam("orderId", orderId)
                .get("/store/order/{orderId}");
//        String endpoint = "/store/order/" + orderId;
//
//        Response response = RestAssured.given()
//                .baseUri(PET_ENDPOINT)
//                .when()
//                .get(endpoint);
//
//        return response.as(OrderResponse.class);
    }


}
