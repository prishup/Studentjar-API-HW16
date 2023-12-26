package com.studentjar.homework;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Gpud2 {
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
                " \"firstName\": \"shikha\",\n" +
                "        \"lastName\": \"kapoor\",\n" +
                "        \"email\": \"shikhakapoor@yahoo.co.uk\",\n" +
                "        \"programme\": \"Medicine\",\n" +
                "        \"courses\": [\n" +
                "            \"Anatomy\",\n" +
                "            \"Biochemistry\",\n" +
                "            \"Genetics\",\n" +
                "            \"Human Behavior.\"\n" +
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
       String jsonData = "{\n" +
               "    \"id\": 114,\n" +
               "    \"firstName\": \"shikha1\",\n" +
               "    \"lastName\": \"patel\",\n" +
               "    \"email\": \"shikhakapoor@yahoo.co.uk\",\n" +
               "    \"programme\": \"Medicine\",\n" +
               "    \"courses\": [\n" +
               "        \"Anatomy\",\n" +
               "        \"Biochemistry\",\n" +
               "        \"Genetics\",\n" +
               "        \"Human Behavior.\"\n" +
               "    ]\n" +
               "}";
       given()
               .baseUri("http://localhost:8080/student/114")
               .contentType(ContentType.JSON)
               .body(jsonData)
               .when()
               .then().statusCode(200)
               .body("msg", equalTo("Updated"));



    }
    @Test //changealldetails
    public void putStudentInfo() {

        String jsonData = "{\"email\": \"shikhakapoor23@yahoo.co.uk\"}";

        given()
                .baseUri("http://localhost:8080/student/114")
                .contentType(ContentType.JSON)
                .body(jsonData)
                .then().statusCode(200)
                .body("msg", equalTo("Updated"));
    }
    @Test //deleteStudentInfo
    public void deleteStudentInfo1() {
        given()
                .pathParam("id", 114)
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
