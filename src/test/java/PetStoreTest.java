import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import Utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class PetStoreTest extends Constants{

    @Test
    public void testPlaceOrderForPet() {
        String requestBody = String.format(
                "{ \"id\": %d, \"petId\": %d, \"quantity\": %d, \"status\": \"%s\" }",
                Constants.ORDER_ID, Constants.PET_ID, Constants.QUANTITY, Constants.STATUS
        );
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .extract().response();


        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code must be 200");
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("id"), "Response must contain id");
        Assert.assertTrue(responseBody.contains("petId"), "Response must contain petId");
        Assert.assertTrue(responseBody.contains("status"), "Response must contain status");
    }

    @Test
    public void testGetOrderById() {
        Response response = given()
                .when()
                .get("/store/order/" + Constants.ORDER_ID)
                .then()
                .log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code must be 200");

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("id"), "Response must contain id");
        Assert.assertTrue(responseBody.contains("petId"), "Response must contain petId");
        Assert.assertTrue(responseBody.contains("status"), "Response must contain status");

        Assert.assertEquals(response.jsonPath().getInt("id"), Constants.ORDER_ID, "Order ID must match");
        Assert.assertEquals(response.jsonPath().getInt("petId"), Constants.PET_ID, "Pet ID must match");
        Assert.assertEquals(response.jsonPath().getInt("quantity"), Constants.QUANTITY, "Quantity must match");
        Assert.assertEquals(response.jsonPath().getString("status"), Constants.STATUS, "Status must match");
    }

    @Test
    public void testDeleteOrderById() {
        Response response = given()
                .when()
                .delete("/store/order/" + Constants.ORDER_ID)
                .then()
                .log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code must be 200");
    }

    @Test
    public void testDeleteNonExistentOrder() {
        Response response = given()
                .when()
                .delete("/store/order/" + Constants.ORDER_ID)
                .then()
                .log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 404, "Status code must be 404");

        String responseMessage = response.jsonPath().getString("message");
        Assert.assertEquals(responseMessage, "Order not found", "Error message must be 'Order not found'");
    }

    @Test
    public void testGetDeletedOrderById() {
        Response response = given()
                .when()
                .get("/store/order/" + Constants.ORDER_ID)
                .then()
                .log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 404, "Status code must be 404");

        String responseMessage = response.jsonPath().getString("message");
        Assert.assertEquals(responseMessage, "Order not found", "Error message must be 'Order not found'");
    }
}
