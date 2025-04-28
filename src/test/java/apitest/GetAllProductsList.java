 package apitest;

import base.APITestBase;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import org.testng.annotations.Test;
import reportManager.ExtentReportManager;
import specs.RequestSpecBuilderFactory;
import utlity.JsonUtils;

public class GetAllProductsList extends APITestBase {
   @Test
   void getAllProductList() throws IOException {
      ExtentReportManager.createTest("Get all product list from get api request");
      ExtentReportManager.getTest().log(Status.INFO, "------Execution started -----");
      Response response = (Response)((ValidatableResponse)((ValidatableResponse)((Response)((RequestSpecification)RestAssured.given().log().all()).spec(RequestSpecBuilderFactory.getRequestSpecForGet()).when().get("/api/productsList", new Object[0])).then()).log().all()).extract().response();
      ExtentReportManager.getTest().log(Status.INFO, "Api executed with status code  " + response.getStatusCode());
      ExtentReportManager.getTest().log(Status.INFO, "Api executed with response /n " + response.asPrettyString());
      System.out.println(JsonUtils.getListvalueFromJson(response.asString(), "$.products[?(@.category.usertype.usertype == 'Men')].name"));
   }
}