package Utilities;

import io.restassured.response.ResponseBody;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class RequestSpecificationHelper {

    public static ResponseBody craetePostRequest(String endPoint, String payload) {
        ResponseBody response = given()
                .header("Content-Type", "application/json")
                .when()
                .body(payload)
                .post(endPoint).andReturn().then().
                statusCode(200).extract().response().getBody();

        return response;

    }

    public static void getResponse(String endPoint) {
        given()
                .when()
                .get(endPoint)
                .then()
                .assertThat()
                .statusCode(200);
    }
}
