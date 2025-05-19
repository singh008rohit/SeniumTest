  package apitest;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import static io.restassured.RestAssured.*;
import base.APITestBase;
import io.cucumber.java.en.Given;
import reportManager.ExtentReportManager;
import specs.RequestSpecBuilderFactory;
import specs.ResponseSpecBuilderFactory;

public class ApiTest extends APITestBase {
   @Test
   void getBookingId() {
      ExtentReportManager.createTest("Get Booking Details API");
      ExtentReportManager.getTest().log(Status.INFO, "API test execution started for get booking id ");
      
     
      String response = given().log().all().spec(RequestSpecBuilderFactory.getRequestSpecForGet()).when().get("booking").then().extract().response().toString();
      ExtentReportManager.getTest().log(Status.INFO, "Validate getting all booking ID: ");
      ExtentReportManager.getTest().log(Status.INFO, "API test execution started for get booking id are : " + response);
   }
}