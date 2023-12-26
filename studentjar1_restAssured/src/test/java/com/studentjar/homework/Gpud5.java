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

public class Gpud5 {
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
        String jsonData = "{ \"firstName\": \"Ripti\",\n" +
                "        \"lastName\": \"patel\",\n" +
                "        \"email\": \"riptipatel145@yahoo.com\",\n" +
                "        \"programme\": \"Software Tester\",\n" +
                "        \"courses\": [\n" +
                "            \"Testing\",\n" +
                "            \"Developing\"\n" +
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
        String jsonData = "{firstName\": \"jiten}";
        given()
                .baseUri("http://localhost:8080/student/118")
                .contentType(ContentType.JSON)
                .body(jsonData)
                .when()
                .then().statusCode(200)
                .body("msg", equalTo("Updated"));

    }

    @Test //changealldetails
    public void putStudentInfo() {

        String jsonData = "{ \"firstName\": \"avani\",\n" +
                "        \"lastName\": \"pat\",\n" +
                "        \"email\": \"avani123@yahoo.com\",\n" +
                "        \"programme\": \"Business analusis\",\n" +
                "        \"courses\": [\n" +
                "            \"Testing\",\n" +
                "            \"Coding\"\n" +
                "        ]\n" +
                "    }";

        given()
                .baseUri("http://localhost:8080/student/118")
                .contentType(ContentType.JSON)
                .body(jsonData)
                .then().statusCode(200)
                .body("msg", equalTo("Student Updated"));

    }

    @Test //deleteStudentInfo
    public void deleteStudentInfo1() {
        given()
                .pathParam("id", 118)
                .when()
                .delete("http://localhost:8080/student/{id}")
                .then()
                .statusCode(204);

    }

    @Test //confirmdeletedornot
    public void verifyStudentInfoDeleted() {
        given()
                .pathParam("id", 118)
                .when()
                .get("http://localhost:8080/student/{id}")
                .then().statusCode(404);
    }

}
