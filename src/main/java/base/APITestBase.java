   package base;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import java.io.IOException;

import org.testng.annotations.AfterClass;
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
 
     
      
      
   }

   @BeforeClass
   public void setupMockServer() {
       // Use dynamic port to avoid "port in use" issues
       wireMockServer = new WireMockServer(options().port(8081));
       wireMockServer.start();

       // Optional: print or use this port in your baseURI
       System.out.println("WireMock running at: " + wireMockServer.baseUrl());

       // Set up your stubs here
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
      

      ExtentManager.unload();;
      
   }
   
   
   @AfterClass
   public void stopMockServer() {
       if (wireMockServer != null && wireMockServer.isRunning()) {
           wireMockServer.stop();
           System.out.println("WireMock stopped.");
       }
   }
   
}