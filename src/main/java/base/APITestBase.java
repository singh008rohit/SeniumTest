   package base;

import java.io.IOException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.github.tomakehurst.wiremock.client.WireMock.*;



import commonConstant.CommonConstant;
import io.restassured.RestAssured;
import reportManager.ExtentReportManager;

public class APITestBase {
	
    protected static WireMockServer wireMockServer;

   @BeforeClass(
      alwaysRun = true
   )
   public void setupAPI() throws IOException {
      ExtentReportManager.getSetup("API_TestSuite");
      RestAssured.baseURI = BaseTest.getValueFromPropFile(CommonConstant.BASE_URI);
      wireMockServer = new WireMockServer(options().port(8080)); // Default: 8080
      wireMockServer.start();
      setupStub();
   }
   
   public void setupStub() {
	    configureFor("localhost", 8080);

	    stubFor(post(urlEqualTo("/users/1"))
	    		.willReturn(aResponse()
	    		        .withStatus(200)
	    		        .withHeader("Content-Type", "application/json")
	    		        .withBody("{ \"message\": \"User created successfully\" }")));
	}

   @AfterSuite(
      alwaysRun = true
   )
   public void tearDownReport() {
       wireMockServer.stop();

      ExtentReportManager.flush();
      
   }
}