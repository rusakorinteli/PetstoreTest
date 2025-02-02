import Utils.Constants;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.testng.Assert;
import org.testng.annotations.Test;


public class UserTests extends Constants {

    private final String username = "Rusako";
    private final String firstName = "Rusudan";
    private  String lastName = "Korinteli";
    private final String email = "rusako@gmail.com";
    private final String password = "Rusako@123";
    private String phone = "5432345678";
    private final int userStatus = 1;

    @Test
    public void testCreateUser() {
        String requestBody = "{"
                + "\"id\": 1,"
                + "\"username\": \"Rusako\","
                + "\"firstName\": \"Rusudan\","
                + "\"lastName\": \"Korinteli\","
                + "\"email\": \"rusako@gmail.com\","
                + "\"password\": \"Rusako@123\","
                + "\"phone\": \"5432345678\","
                + "\"userStatus\": 1"
                + "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/user")
                .then()
                .log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code must be 200");
    }

    @Test
    public void testGetUserByUsername() {

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/user/" + username)
                .then()
                .log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code must be 200");
        Assert.assertEquals(response.jsonPath().getInt("id"), userId, "User ID must match");
        Assert.assertEquals(response.jsonPath().getString("username"), username, "Username must match");
        Assert.assertEquals(response.jsonPath().getString("firstName"), firstName, "First name must match");
        Assert.assertEquals(response.jsonPath().getString("lastName"), lastName, "Last name must match");
        Assert.assertEquals(response.jsonPath().getString("email"), email, "Email must match");
        Assert.assertEquals(response.jsonPath().getString("password"), password, "Password must match");
        Assert.assertEquals(response.jsonPath().getString("phone"), phone, "Phone must match");
        Assert.assertEquals(response.jsonPath().getInt("userStatus"), userStatus, "User status must match");
    }

    @Test
    public void testUpdateUser() {
        lastName = "UpdatedKorinteli";
        phone = "1234567890";

        String updatedRequestBody = String.format(
                "{ \"id\": %d, \"username\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\", " +
                        "\"email\": \"%s\", \"password\": \"%s\", \"phone\": \"%s\", \"userStatus\": %d }",
                1, username, firstName, lastName, email, password, phone, userStatus
        );

        Response response = given()
                .contentType(ContentType.JSON)
                .body(updatedRequestBody)
                .when()
                .put("/user/" + username)
                .then()
                .log().all()
                .extract().response();

        System.out.println("Update User Response: " + response.getBody().asString());

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code must be 200");
    }


    @Test
    public void testVerifyUpdatedUser() {
        Response response = given()
                .when()
                .get("/user/" + username)
                .then()
                .log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code must be 200");

        Assert.assertEquals(response.jsonPath().getString("lastName"), lastName, "Last name must be updated");
        Assert.assertEquals(response.jsonPath().getString("phone"), phone, "Phone must be updated");
    }

    @Test
    public void testLoginUser() {
        Response response = given()
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get("/user/login")
                .then()
                .log().all()
                .extract().response();


        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code must be 200");

        String message = response.getBody().asString();
        Assert.assertNotNull(message, "Response message must not be null");
        Assert.assertFalse(message.isEmpty(), "Response message must not be empty");

    }

    @Test
    public void testLogoutUser() {
        Response response = given()
                .when()
                .get("/user/logout")
                .then()
                .log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code must be 200");

        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "ok", "Response message must be 'ok'");

    }

    @Test
    public void testDeleteUser() {
        Response response = given()
                .when()
                .delete("/user/" + username)
                .then()
                .log().all()
                .extract().response();


        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code must be 200");

        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, username, "Response message must be the username");

    }

}
