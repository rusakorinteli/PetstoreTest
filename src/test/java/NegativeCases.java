import Utils.Constants;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class NegativeCases extends Constants {
    @Test
    public void testPlaceOrderWithInvalidId() {
        String requestBody = "{"
                + "\"id\": \"test\","  // Invalid ID (should be an integer)
                + "\"petId\": " + Constants.PET_ID + ","
                + "\"quantity\": " + Constants.QUANTITY + ","
                + "\"status\": \"" + Constants.STATUS + "\""
                + "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 500, "Status code must be 500");

        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "something bad happened", "Response message must be 'something bad happened'");

    }

    @Test
    public void testPlaceOrderWithInvalidPetId() {
        String requestBody = "{"
                + "\"id\": " + Constants.ORDER_ID + ","
                + "\"petId\": \"test\","  // Invalid petId (should be an integer)
                + "\"quantity\": " + Constants.QUANTITY + ","
                + "\"status\": \"" + Constants.STATUS + "\""
                + "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 500, "Status code must be 500");


        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "something bad happened", "Response message must be 'something bad happened'");

    }

    @Test
    public void testPlaceOrderWithInvalidQuantity() {
        String requestBody = "{"
                + "\"id\": " + Constants.ORDER_ID + ","
                + "\"petId\": " + Constants.PET_ID + ","
                + "\"quantity\": \"test\","  // Invalid quantity (should be an integer)
                + "\"status\": \"" + Constants.STATUS + "\""
                + "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 500, "Status code must be 500");

        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "something bad happened", "Response message must be 'something bad happened'");

    }
}
