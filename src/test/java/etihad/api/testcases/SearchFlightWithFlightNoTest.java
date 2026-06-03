package etihad.api.testcases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import annotation.FrameworkAnnotation;
import base.APITestBase;
import commonConstant.ApiCommonContant;
import dataProvider.TestDataProvider;
import endpoints.Endpoint;
import enums.AuthorType;
import enums.CategoryType;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pageConstant.FlightSearchConstants;
import reportManager.ExtentManager;
import requestBody.FlightSearch;
import specs.RequestSpecBuilderFactory;
import utlity.SchemaValidator;
import static org.hamcrest.Matchers.lessThan;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;

public class SearchFlightWithFlightNoTest extends APITestBase {

	@FrameworkAnnotation(author = { AuthorType.ROHIT }, category = { CategoryType.SANITY, CategoryType.SMOKE,
			CategoryType.REGRESSION })

	@Test(enabled = true, description = "Validate flight search by flight number", groups = { "SANITY", "SMOKE",
			"REGRESSION" })

	public void searchFlight() {

	    ExtentManager.getExtentTest().log(
	            Status.INFO,
	            "Starting Flight Search API Validation");

	    String rootPath = "flightStatus";
	    String ondPath = rootPath + ".onds[0]";
	    String flightPath = ondPath + ".flights[0]";

	    Response response = given().log().all()
	            .spec(RequestSpecBuilderFactory.getSecretToken())
	            .body(FlightSearch.getFlightSearchdata())
	            .when()
	            .post(Endpoint.FLIGHT_SEARCH);

	    long responseTime = response.time();

	    ExtentManager.getExtentTest().log(
	            Status.INFO,
	            "Response Time : " + responseTime + " ms");

	    ExtentManager.getExtentTest().log(
	            Status.INFO,
	            "Status Code : " + response.getStatusCode());

	    ExtentManager.getExtentTest().log(
	            Status.INFO,
	            "Response Body : <pre>" + response.asPrettyString() + "</pre>");

	    response.then()
	            .statusCode(200)
	            .contentType(ContentType.JSON)
	            .time(lessThan(ApiCommonContant.MAX_RESPONSE_TIME_MS))
	            .log().ifValidationFails()
	            .body(rootPath + ".searchOrigin", equalTo(FlightSearchConstants.AUH))
	            .body(rootPath + ".searchDestination", equalTo(FlightSearchConstants.LHR))
	            .body(rootPath + ".searchDate", equalTo(FlightSearchConstants.DEPARTURE_DATE))
	            .body(rootPath + ".onds.size()", greaterThan(0))
	            .body(ondPath + ".flights.size()", greaterThan(0))
	            .body(flightPath + ".flightNumber", equalTo(FlightSearchConstants.FLIGHT_NUMBER))
	            .body(flightPath + ".carrier", equalTo(FlightSearchConstants.CARRIER))
	            .body(flightPath + ".departure.airportName", equalTo(FlightSearchConstants.DEPARTURE_AIRPORT))
	            .body(flightPath + ".arrival.airportName", equalTo(FlightSearchConstants.ARRIVAL_AIRPORT))
	            .body(flightPath + ".statusType", equalTo(FlightSearchConstants.STATUS_TYPE));

	    ExtentManager.getExtentTest().log(
	            Status.PASS,
	            "Response field validations completed successfully");

	    SchemaValidator.validateSchema(response, "schema/flightSchema.json");

	    ExtentManager.getExtentTest().log(
	            Status.PASS,
	            "Schema validation completed successfully");
	}

	@FrameworkAnnotation(author = { AuthorType.ROHIT }, category = { CategoryType.SANITY, CategoryType.SMOKE,
			CategoryType.REGRESSION })
	@Test(dataProvider = "getDataForLocalization", dataProviderClass = TestDataProvider.class, enabled = true, description = "Validate flight search locazition in english", groups = {
			"SANITY", "SMOKE", "REGRESSION" })

	public void validateLocalization(HashMap<String, Object> mapData) {

		String language = (String) mapData.get("language");
		ExtentManager.getExtentTest().log(
		        Status.INFO,
		        "Executing localization validation for language : " + language);

		Map<String, String> validations =

				(Map<String, String>) mapData.get("validations");

		RequestSpecification spec;

		switch (language.toLowerCase()) {

		case "en":

			spec = RequestSpecBuilderFactory.getSecretTokenEng();

			break;

		case "ar":

			spec = RequestSpecBuilderFactory.getSecretTokenAr();

			break;

		case "hi":

			spec = RequestSpecBuilderFactory.getSecretTokenHi();

			break;

		default:

			throw new IllegalArgumentException(

					"Unsupported language: " + language);

		}

		Response response = given()

				.spec(spec)

				.body(FlightSearch.getFlightSearchdata())

				.when()

				.post(Endpoint.FLIGHT_SEARCH);
		long responseTime = response.time();

		ExtentManager.getExtentTest().log(
		        Status.INFO,
		        "Response Time : " + responseTime + " ms");

		ExtentManager.getExtentTest().log(
		        Status.INFO,
		        "Response Status Code : " + response.getStatusCode());

		response.then()

				.statusCode(200)

				.contentType(ContentType.JSON)

				.time(lessThan(ApiCommonContant.MAX_RESPONSE_TIME_MS));

		JsonPath jsonPath = response.jsonPath();

		validations.forEach((path, expectedValue) -> {

		    String actualValue = jsonPath.getString(path);

		    try {

		        Assert.assertEquals(actualValue, expectedValue);

		        ExtentManager.getExtentTest().log(
		                Status.PASS,
		                "Language=" + language
		                + " | Path=" + path
		                + " | Expected=" + expectedValue
		                + " | Actual=" + actualValue);

		    } catch (AssertionError e) {

		        ExtentManager.getExtentTest().log(
		                Status.FAIL,
		                "Language=" + language
		                + " | Path=" + path
		                + " | Expected=" + expectedValue
		                + " | Actual=" + actualValue);

		        throw e;
		    }
		});
		ExtentManager.getExtentTest().log(
		        Status.PASS,
		        "Localization validation completed successfully for language : "
		                + language);

	}

}