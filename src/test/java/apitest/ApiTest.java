  package apitest;

import base.APITestBase;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import reportManager.ExtentReportManager;
import specs.RequestSpecBuilderFactory;
import specs.ResponseSpecBuilderFactory;

public class ApiTest extends APITestBase {
   @Test
   void getBookingId() {
      ExtentReportManager.createTest("Get Booking Details API");
      ExtentReportManager.getTest().log(Status.INFO, "API test execution started for get booking id ");
      String response = ((ValidatableResponse)((ValidatableResponse)((Response)((RequestSpecification)RestAssured.given().log().all()).spec(RequestSpecBuilderFactory.getRequestSpecForGet()).when().get("booking", new Object[0])).then()).spec(ResponseSpecBuilderFactory.getResponseForGet())).extract().asString();
      ExtentReportManager.getTest().log(Status.INFO, "Validate getting all booking ID: ");
      ExtentReportManager.getTest().log(Status.INFO, "API test execution started for get booking id are : " + response);
   }
}