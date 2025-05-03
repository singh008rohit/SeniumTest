package apitest;

import base.APITestBase;
import io.restassured.response.Response;
import listeners.SeleniumListener;
import reportManager.ExtentReportManager;
import specs.RequestSpecBuilderFactory;
import specs.ResponseSpecBuilderFactory;

import static io.restassured.RestAssured.*;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
//@Listeners({SeleniumListener.class})
public class ValidateAbletoSearchProductsTest extends APITestBase{
	
	
	
	@Test()
	
	void ValidateAbletoSearchProducts(){
	      ExtentReportManager.createTest("Get all product list from get api request");
	      ExtentReportManager.getTest().log(Status.INFO, "------Execution started -----");
  
		
	Response re=	given().log().all().spec(RequestSpecBuilderFactory.getPostRequestForgetProduct("jean")).when()
	.post("api/searchProduct").then().spec(ResponseSpecBuilderFactory.getSearchProductResponse()).log().all().extract().response();
	
	ExtentReportManager.getTest().log(Status.INFO, "response"+re.asPrettyString());
	}
	
	
		
		
		
	
	
	
	

}
