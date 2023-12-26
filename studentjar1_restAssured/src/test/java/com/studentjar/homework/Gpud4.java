package com.studentjar.homework;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Gpud4 {
    ValidatableResponse validatableResponse;
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    Response response;

    @Test //getallStudentInfo
    public void getAllStudentInfo() {
        given().log().all()
                .get("http://localhost:8080/student/list")
                .then().log().all()
                .statusCode(200);
    }

    @Test//CreateStudentInfo
    public void createStudentInfo() {
        String jsonData = "{ \"firstName\": \"khushbu\",\n" +
                "        \"lastName\": \"patel\",\n" +
                "        \"email\": \"khushbupatel12@yahoo.com\",\n" +
                "        \"programme\": \"Financial Analysis\",\n" +
                "        \"courses\": [\n" +
                "            \"Testing\",\n" +
                "            \"BusinessLaw\"\n" +
                "        ]\n" +
                "    }";
        response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(jsonData)
                .post("http://localhost:8080/student");
        response.then().log().all().statusCode(201)
                .body("msg", equalTo("Student added"));
    }

    @Test //patchStudentInfo
    public void patchStudentInfo() {
        String jsonData = "{lastName\": \"shah}";
        given()
                .baseUri("http://localhost:8080/student/117")
                .contentType(ContentType.JSON)
                .body(jsonData)
                .when()
                .then().statusCode(200)
                .body("msg", equalTo("Updated"));

    }

    @Test //changealldetails
    public void putStudentInfo() {

        String jsonData = "{ \"firstName\": \"khushbu11\",\n" +
                "        \"lastName\": \"patel11\",\n" +
                "        \"email\": \"khushbupatel1221@yahoo.com\",\n" +
                "        \"programme\": \"Financial Analysis\",\n" +
                "        \"courses\": [\n" +
                "            \"Accounting\",\n" +
                "            \"Stat\"\n" +
                "        ]\n" +
                "    }";

        given()
                .baseUri("http://localhost:8080/student/117")
                .contentType(ContentType.JSON)
                .body(jsonData)
                .then().statusCode(200)
                .body("msg", equalTo("Student Updated"));

    }

    @Test //deleteStudentInfo
    public void deleteStudentInfo1() {
        given()
                .pathParam("id", 117)
                .when()
                .delete("http://localhost:8080/student/{id}")
                .then()
                .statusCode(204);

    }

    @Test //confirmdeletedornot
    public void verifyStudentInfoDeleted() {
        given()
                .pathParam("id", 117)
                .when()
                .get("http://localhost:8080/student/{id}")
                .then().statusCode(404);
    }

}
