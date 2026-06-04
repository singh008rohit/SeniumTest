package apitest;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.jsoup.select.Evaluator.ContainsData;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import annotation.FrameworkAnnotation;
import base.APITestBase;
import enums.AuthorType;
import enums.CategoryType;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import reportManager.ExtentManager;
import static org.hamcrest.Matchers.*;

public class MockServicesTest extends APITestBase {
	@FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT }, category = { CategoryType.SANITY,
			CategoryType.SMOKE, CategoryType.REGRESSION })

	@Test(enabled = true, description = "Validate json schema with mock response", groups = { "SANITY", "SMOKE",
			"REGRESSION" })
	void testGetRequest() {
		
		if (!isMockEnabled()) {
	        ExtentManager.getExtentTest().log(Status.INFO,
	            "MockServicesTest skipped — useMock=false in application.properties");
	        throw new org.testng.SkipException("Mock mode is disabled — skipping WireMock test");
	    }
	    
	    RestAssured.baseURI = getWireMockServer().baseUrl();
		ExtentManager.getExtentTest().log(Status.INFO, "Validate json schema with mock response");
		Response res = given().contentType("application/json").header("Authorization", "Bearer some-valid-token")
				.queryParam("active", true).queryParam("sort", "desc").header("X-Request-ID", ".+")
				.header("User-Agent", "RestAssured").when().get("/api/user/1").then().statusCode(200)
				.body("user.name", equalTo("Rohit")).header("Content-Type", equalTo("application/json")).extract()
				.response();

		ExtentManager.getExtentTest().log(Status.INFO, res.asPrettyString());

		System.out.println(res.asPrettyString());
//System.out.println(res.headers());

		String st = res.jsonPath().getString("user.projects.findAll{it.status=='active'}.name");
		System.out.println("data ===" + st);

		ExtentManager.getExtentTest().log(Status.INFO, "");

	}

	@FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT }, category = { CategoryType.SANITY,
			CategoryType.SMOKE, CategoryType.REGRESSION })

	@Test(enabled = false, description = "Validate json schema with mock response", groups = { "SANITY", "SMOKE",
			"REGRESSION" })
	void validatePostResponse() {
		if (!isMockEnabled()) {
	        ExtentManager.getExtentTest().log(Status.INFO,
	            "MockServicesTest skipped — useMock=false in application.properties");
	        throw new org.testng.SkipException("Mock mode is disabled — skipping WireMock test");
	    }
	    
	    RestAssured.baseURI = getWireMockServer().baseUrl();
		Response res = given().log().all().auth().preemptive().basic("admin", "password")
				.header("Content-Type", "application/json").body("{ \"name\": \"Rohit\" }").when().post("/api/user")
				.then().statusCode(201).log().all().extract().response();
		System.out.println("     response     " + res.asPrettyString());

	}

	@FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT }, category = { CategoryType.SANITY,
			CategoryType.SMOKE, CategoryType.REGRESSION })
	@Test(enabled = false, description = "Validate json schema with mock response", groups = { "SANITY", "SMOKE",
			"REGRESSION" })
	void validatePutResponse() {
		if (!isMockEnabled()) {
	        ExtentManager.getExtentTest().log(Status.INFO,
	            "MockServicesTest skipped — useMock=false in application.properties");
	        throw new org.testng.SkipException("Mock mode is disabled — skipping WireMock test");
	    }
	    
	    RestAssured.baseURI = getWireMockServer().baseUrl();
		Response res = given().log().all().header("Authorization", "Bearer valid_token")
				.header("Content-Type", "application/json").body("{\"name\":\"Rohit\"}").when().put("/api/user/1")
				.then().statusCode(200).log().all().extract().response();

		System.out.println(res.asPrettyString());

	}

}
