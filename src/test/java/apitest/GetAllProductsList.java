 package apitest;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import annotation.FrameworkAnnotation;
import base.APITestBase;
import enums.AuthorType;
import enums.CategoryType;
import io.restassured.response.Response;
import reportManager.ExtentManager;
import specs.RequestSpecBuilderFactory;
import utlity.JsonUtils;

public class GetAllProductsList extends APITestBase { 
	

@FrameworkAnnotation(author = { AuthorType.ROHIT},category = { CategoryType.SANITY,CategoryType.SMOKE,CategoryType.REGRESSION })
				
		
		
@Test(enabled =true, description= "Validate able to get all product list",groups = {"SANITY","SMOKE","REGRESSION"})
   void getAllProductList() throws IOException {
      ExtentManager.getExtentTest().log(Status.INFO, "------Execution started -----");
      Response response = given().spec(RequestSpecBuilderFactory.getRequestSpecForGet()).when().get("api/productsList").then().log().all().extract().response();
      ExtentManager.getExtentTest().log(Status.INFO, "Api executed with status code  " + response.getStatusCode());
      ExtentManager.getExtentTest().log(Status.INFO, "Api executed with response /n " + response.asPrettyString());
    List list=  JsonUtils.getListvalueFromJson(response.asString(), "$.products[?(@.category.usertype.usertype == 'Men')].name");
      ExtentManager.getExtentTest().log(Status.INFO," "+list);
      long responseTime=response.time();
      ExtentManager.getExtentTest().log(Status.INFO," Response time "+responseTime);
      Assert.assertTrue(responseTime < 5000, "Response time exceeded limit: " + responseTime);

      
      
   }
}