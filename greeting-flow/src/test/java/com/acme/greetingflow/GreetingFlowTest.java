package com.acme.greetingflow;


import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
@QuarkusTestResource(InternationalGreetingServiceMock.class)
class GreetingFlowTest {

    @BeforeAll
    static void init() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void testEnglish() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"name\" : \"Helber\", \"country\":\"United States\"}").when()
                .post("/greeting")
                .then()
                .statusCode(201)
                .body("workflowdata.greeting", containsString("Greetings from Serverless Workflow, Helber!"));
    }
}
