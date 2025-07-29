   package base;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import listeners.AnnotationTransformer;
import listeners.MethodInterceptor;
import listeners.SeleniumListener;
import reportManager.ExtentManager;
import reportManager.ExtentReportManager;
import utlity.ConfigLoader;

@Listeners({
	AnnotationTransformer.class,
	SeleniumListener.class,
	MethodInterceptor.class
})

public class APITestBase {
	
    protected static WireMockServer wireMockServer;

   @BeforeMethod( alwaysRun = true )
   public void setupAPI() throws IOException {
      RestAssured.baseURI = ConfigLoader.getInstance().getBaseUrl();
      wireMockServer = new WireMockServer(options().port(8081)); // Default: 8080
      wireMockServer.start();
      MockServices.setupStubs(wireMockServer);
      MockServices.stubGetWithBasicAuth(wireMockServer);
      MockServices.stubGetWithBearerToken(wireMockServer);
      MockServices.stubOAuth2TokenEndpoint(wireMockServer);
      MockServices.stubProtectedResourceWithOAuthToken(wireMockServer);
     
      
      
   }
   
  

   @AfterSuite(
      alwaysRun = true
   )
   public void tearDownReport() {
       wireMockServer.stop();

      ExtentManager.unload();;
      
   }
}