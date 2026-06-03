package base;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import java.io.IOException;
import org.testng.annotations.*;
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

    @BeforeMethod(alwaysRun = true)
    public void setupAPI() throws IOException {
        RestAssured.baseURI = ConfigLoader.getInstance().getAPIBaseUrl();
        wireMockServer = new WireMockServer(options().port(8081));
        wireMockServer.start();
        MockServices.setupStubs(wireMockServer);
        MockServices.stubGetWithBasicAuth(wireMockServer);
        MockServices.stubGetWithBearerToken(wireMockServer);
        MockServices.stubOAuth2TokenEndpoint(wireMockServer);
        MockServices.stubProtectedResourceWithOAuthToken(wireMockServer);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownAPI() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
        }
        // moved from @AfterSuite — each API test cleans up its own report node
        ExtentManager.unload();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownReport() {
        ExtentReportManager.flushReports();
    }
}