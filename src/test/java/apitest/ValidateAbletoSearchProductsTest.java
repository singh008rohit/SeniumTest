package apitest;

import base.APITestBase;
import enums.AuthorType;
import enums.CategoryType;
import io.restassured.response.Response;
import listeners.SeleniumListener;
import reportManager.ExtentManager;
import reportManager.ExtentReportManager;
import specs.RequestSpecBuilderFactory;
import specs.ResponseSpecBuilderFactory;

import static io.restassured.RestAssured.*;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import annotation.FrameworkAnnotation;
//@Listeners({SeleniumListener.class})
public class ValidateAbletoSearchProductsTest extends APITestBase{
	
	
	
	@FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT},category = { CategoryType.SANITY,CategoryType.SMOKE,CategoryType.REGRESSION })
	
	
	
	@Test(enabled =true, description= "Validate able to get all product list",groups = {"SANITY","SMOKE","REGRESSION"})
	
	void ValidateAbletoSearchProducts(){
	      ExtentManager.getExtentTest().log(Status.INFO,"Get all product list from get api request");
	      ExtentManager.getExtentTest().log(Status.INFO, "------Execution started -----");
  
		
	Response re=	given().log().all().spec(RequestSpecBuilderFactory.getPostRequestForgetProduct("jean")).when()
	.post("api/searchProduct").then().spec(ResponseSpecBuilderFactory.getSearchProductResponse()).log().all().extract().response();
	
	ExtentManager.getExtentTest().log(Status.INFO, "response"+re.asPrettyString());
	
	System.out.println(re.body().asPrettyString());
	System.out.println("================  Headers==================="+re.getHeaders());
	}
	
	
		
		
		
	
	
	
	

}
