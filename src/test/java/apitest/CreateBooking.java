   package apitest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class CreateBooking {
   public static void main(String[] args) {
   /*   RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
      Response req = (Response)((ValidatableResponse)((ValidatableResponse)((ValidatableResponse)((Response)RestAssured.given().header("Content-Type", "application/json", new Object[0]).body("{\n    \"firstname\" : \"Jim\",\n    \"lastname\" : \"Brown\",\n    \"totalprice\" : 111,\n    \"depositpaid\" : true,\n    \"bookingdates\" : {\n        \"checkin\" : \"2018-01-01\",\n        \"checkout\" : \"2019-01-01\"\n    },\n    \"additionalneeds\" : \"Breakfast\"\n}").when().post("booking", new Object[0])).then()).statusCode(200)).assertThat()).extract().response();
      System.out.println(req.asString());
      System.out.println(req.body().asString());
      System.out.println(req.getSessionId());
      System.out.println(req.sessionId());
     System.out.println(req.headers());
  */  }
}