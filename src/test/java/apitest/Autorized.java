   package apitest;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import io.restassured.RestAssured;

public class Autorized {
   public static void main(String[] args) throws IOException {
    //  RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    //  String req = given().header("Content-Type", "application/json").body("{\n    \"username\" : \"admin\",\n    \"password\" : \"password123\"\n}").when().post("auth").then().assertThat().statusCode(200).extract().asString();
    //  System.out.println(JsonUtils.getStringValueFromJson("/src/test/java/resource/Sample.json", "company.location"));
      //System.out.println(JsonUtils.getListvalueFromJson("/src/test/java/resource/Sample.json", "company.location"));
   }
}