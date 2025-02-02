package Utils;

import DataObject.HeadersConfig;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class Constants {
        public HeadersConfig headConfig;
        @BeforeMethod
        public void SetBaseURL() {
            RestAssured.baseURI = "https://petstore.swagger.io/v2";
            headConfig = new HeadersConfig();
        }
    public static final int ORDER_ID = 12345;
    public static final int PET_ID = 67890;
    public static final int QUANTITY = 2;
    public static final String STATUS = "placed";
    public static final int userId = 1;

}
