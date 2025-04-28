   package apitest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class GetBooking {
   public static void main(String[] st) {
      RestAssured.baseURI = "https://restful-booker.herokuapp.com";
      String req = ((Response)((ValidatableResponse)((ValidatableResponse)((ValidatableResponse)((ValidatableResponse)((Response)((RequestSpecification)RestAssured.given().log().all()).auth().none().when().get("booking/4", new Object[0])).then()).log().all()).assertThat()).statusCode(200)).extract().response()).asString();
      System.out.println(req);
   }
}