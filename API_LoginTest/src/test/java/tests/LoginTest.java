package tests;

import com.github.javafaker.Faker;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import utils.AuthRequest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class LoginTest {

    private static final Logger LOGGER = Logger.getLogger("Login Test");

    @Parameters({"email", "password"})
    @Test(priority = 1)
    public void LoginValidCredentials(final String email, final String password){

        AuthRequest authRequest = new AuthRequest(email, password);
        LOGGER.info("Submit auth POST request");

        given()
                .baseUri("https://demo.placelab.com")
                .contentType("application/json")
                .body(authRequest.requestBody.toString())

                .when()
                .post("/api/v2/sessions")

                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("api_token", notNullValue());
    }

    Faker faker = new Faker();
    private String fakeEmail = faker.internet().emailAddress().toString();
    private String fakePassword = faker.lordOfTheRings().toString();


    @Test(priority = 2)
    public void LoginWithInvalidCredentials(){

        AuthRequest authRequest = new AuthRequest(fakeEmail, fakePassword);
        LOGGER.info("Submit authorization POST request");

        given()
                .baseUri("https://demo.placelab.com")
                .contentType("application/json")
                .body(authRequest.requestBody.toString())

                .when()
                .post("/api/v2/sessions")

                .then()
                .statusCode(401)
                .contentType("application/json")
                .body("error", equalTo("Password mismatch."));
    }


    @Test(priority = 3)
    public void LoginWithEmptyCredentials(){
        AuthRequest authRequest = new AuthRequest("", "");
        LOGGER.info("Submit auth POST request");
        given()
                .baseUri("https://demo.placelab.com")
                .contentType("application/json")
                .body(authRequest.requestBody.toString())

                .when()
                .post("/api/v2/sessions")

                .then()
                .statusCode(401)
                .contentType("application/json")
                .body("error", equalTo("Password mismatch."));
    }


}
