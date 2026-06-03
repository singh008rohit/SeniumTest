package etihad.api.testcases;

import org.testng.annotations.Test;

import annotation.FrameworkAnnotation;
import base.APITestBase;
import enums.AuthorType;
import enums.CategoryType;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;


public class CheckEligibilityTest extends APITestBase {
	

	@FrameworkAnnotation(author = { AuthorType.ROHIT, AuthorType.ROHIT},category = { CategoryType.SANITY,CategoryType.SMOKE,CategoryType.REGRESSION })
	
	
	
	@Test(enabled =true, description= "Validate able to get all product list",groups = {"SANITY","SMOKE","REGRESSION"})
	
	void getEligibility() {
		//RestAssured.baseURI ="";
	
	given().header("was-secret","3rXypRXEsRDSUe7i2hWs").when().get("/ada-services/stopover/eligibility-rules/v1").then().log().all().contentType(ContentType.JSON).statusCode(200);
	}

}
