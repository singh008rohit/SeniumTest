   package apitest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.io.IOException;
import utlity.JsonUtils;

public class Autorized {
   public static void main(String[] args) throws IOException {
      RestAssured.baseURI = "https://restful-booker.herokuapp.com";
      String req = ((ValidatableResponse)((ValidatableResponse)((ValidatableResponse)((Response)RestAssured.given().header("Content-Type", "application/json", new Object[0]).body("{\n    \"username\" : \"admin\",\n    \"password\" : \"password123\"\n}").when().post("auth", new Object[0])).then()).assertThat()).statusCode(200)).extract().asString();
      System.out.println(JsonUtils.getStringValueFromJson("/src/test/java/resource/Sample.json", "company.location"));
      System.out.println(JsonUtils.getListvalueFromJson("/src/test/java/resource/Sample.json", "company.location"));
   }
}