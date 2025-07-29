package apitest;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import annotation.FrameworkAnnotation;
import base.APITestBase;
import enums.AuthorType;
import enums.CategoryType;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojos.TestEmployee;
import reportManager.ExtentManager;
import reportManager.ExtentReportManager;

import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static io.restassured.RestAssured.given;

import java.io.File;

public class MockServicesTest extends APITestBase {
@FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT},category = { CategoryType.SANITY,CategoryType.SMOKE,CategoryType.REGRESSION })
	
	
	
	@Test(enabled =true, description= "Validate json schema with mock response",groups = {"SANITY","SMOKE","REGRESSION"})
	void testGetRequest() {
		RestAssured.baseURI="http://localhost:8081";
		ExtentManager.getExtentTest().log(Status.INFO, "Validate json schema with mock response");
	Response res=	given().log().all().contentType("application/json").header("Authorization", "Bearer some-valid-token").queryParam("active", true).queryParam("sort","desc").header("X-Request-ID",".+").header("User-Agent","RestAssured").when().get("/api/user/1")
			.then().statusCode(200).extract().response();
	
ExtentManager.getExtentTest().log(Status.INFO, res.asPrettyString());
	
System.out.println(res.asPrettyString());

	ExtentManager.getExtentTest().log(Status.INFO,"");
		
	}

@FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT},category = { CategoryType.SANITY,CategoryType.SMOKE,CategoryType.REGRESSION })

@Test(enabled =false, description= "Validate json schema with mock response",groups = {"SANITY","SMOKE","REGRESSION"})
void validatePostResponse() {
	RestAssured.baseURI="http://localhost:8081";

	Response res=given().log().all().auth().preemptive().basic("admin", "password").header("Content-Type", "application/json").body("{ \"name\": \"Rohit\" }") .when().post("/api/user").then().statusCode(201).log().all().extract().response();
System.out.println("     response     "+res.asPrettyString());

}

@FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT},category = { CategoryType.SANITY,CategoryType.SMOKE,CategoryType.REGRESSION })
@Test(enabled =true, description= "Validate json schema with mock response",groups = {"SANITY","SMOKE","REGRESSION"})
void validatePutResponse() {
	RestAssured.baseURI="http://localhost:8081";
	
Response res=	given().log().all().header("Authorization","Bearer valid_token").header("Content-Type", "application/json").body("{\"name\":\"Rohit\"}").when().put("/api/user/1").then().statusCode(200).log().all().extract().response();

System.out.println(res.asPrettyString());

}


}
