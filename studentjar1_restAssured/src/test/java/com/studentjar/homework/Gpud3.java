package com.studentjar.homework;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Gpud3 {
    ValidatableResponse validatableResponse;
    Response response;
    RequestSpecification requestSpecification;

    @Test //getallStudentInfo
    public void getAllStudentInfo(){
        given().log().all()
                .get("http://localhost:8080/student/list")
                .then().log().all()
                .statusCode(200);
    }

    @Test//CreateStudentInfo
    public void createStudentInfo(){
        String jsonData = "{\n" +
                "  \"firstName\": \"kinnari\",\n" +
                "        \"lastName\": \"adhyaru\",\n" +
                "        \"email\": \"kinaariadhyaru45@yahoo.com\",\n" +
                "        \"programme\": \"Financial Analysis\",\n" +
                "        \"courses\": [\n" +
                "            \"Testing\",\n" +
                "            \"Statistics\"\n" +
                "        ]\n" +
                "    }";
        response= RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(jsonData)
                .post("http://localhost:8080/student");
        response.then().log().all().statusCode(201)
                .body("msg",equalTo("Student added"));
    }

    @Test //patchStudentInfo
    public void patchStudentInfo(){
        String jsonData = "{\"email\": \"kinaariadhyaru4589@yahoo.com\"}";
        given()
                .baseUri("http://localhost:8080/student/116")
                .contentType(ContentType.JSON)
                .body(jsonData)
                .when()
                .then().statusCode(200)
                .body("msg", equalTo("Updated"));



    }
    @Test //changealldetails
    public void putStudentInfo() {

        String jsonData = "{\n" +
                "    \"id\": 116,\n" +
                "    \"firstName\": \"kinnari1\",\n" +
                "    \"lastName\": \"adhyaru1\",\n" +
                "    \"email\": \"kinaariadhyaru45891@yahoo.com\",\n" +
                "    \"programme\": \"Financial Analysis\",\n" +
                "    \"courses\": [\n" +
                "        \"Testing\",\n" +
                "        \"Statistics\"\n" +
                "    ]\n" +
                "}";

        given()
                .baseUri("http://localhost:8080/student/116")
                .contentType(ContentType.JSON)
                .body(jsonData)
                .then().statusCode(200)
        .body("msg", equalTo("Student Updated"));

    }
    @Test //deleteStudentInfo
    public void deleteStudentInfo1() {
        given()
                .pathParam("id", 116)
                .when()
                .delete("http://localhost:8080/student/{id}")
                .then()
                .statusCode(204);

    }

    @Test //confirmdeletedornot
    public void verifyStudentInfoDeleted() {
        given()
                .pathParam("id", 114)
                .when()
                .get("http://localhost:8080/student/{id}")
                .then().statusCode(404);
    }
}

