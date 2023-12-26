package com.studentjar.homework;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Gpud1 {
    RequestSpecification requestSpecification;
    Response response;
    ValidatableResponse validatableResponse;

    @Test //get employee
    public void getEmployee() {
        //given
        given()
                .get("http://localhost:8080/student/list")
                //then
                .then()
                .statusCode(200)
                .body("[3].programme", equalTo("Computer Science"));
    }
    @Test
    public void createStudentsInfo() {

        String data = "{ \"firstName\": \"kinjal\",\n" +
                "        \"lastName\": \"shah\",\n" +
                "        \"email\": \"ki1821n@yahoo.com\",\n" +
                "        \"programme\": \"Law\",\n" +
                "        \"courses\": [\n" +
                "            \"Criminal Law\",\n" +
                "            \"Constitutional Law\",\n" +
                "            \"Property Law\",\n" +
                "            \"Contracts\"\n" +
                "        ]\n" +
                "    }";
        response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(data)
                .post("http://localhost:8080/student");
        response.then().log().all().statusCode(201)
                .body("msg", equalTo("Student added"));

    }
    @Test
    public void updateStudentInfo(){
        String jsonData = "{\n" +
                "    \"id\": 113,\n" +
                "    \"firstName\": \"kinjal12\",\n" +
                "    \"lastName\": \"shah\",\n" +
                "    \"email\": \"ki1821n@yahoo.com\",\n" +
                "    \"programme\": \"Law\",\n" +
                "    \"courses\": [\n" +
                "        \"Criminal Law\",\n" +
                "        \"Constitutional Law\",\n" +
                "        \"Property Law\",\n" +
                "        \"Contracts\"\n" +
                "    ]\n" +
                "}";

        given()
                .baseUri("http://localhost:8080/student/113")
                .contentType(ContentType.JSON)
                .body(jsonData)
                .then().statusCode(200)
                .body("msg", equalTo("Student added"));
    }
    @Test // delete above id
    public void deleteStudentInfo() {
        given()
                .pathParam("id", 113)
                .when()
                .delete("http://localhost:8080/student/{id}")
                .then()
                .statusCode(204);
    }
    //verifydeletedinfo
    @Test
    public void verifyStudentInfoDeleted() {
        given()
                .pathParam("id", 101)
                .when()
                .get("http://localhost:8080/student/{id}")
                .then().statusCode(404);
    }
}


