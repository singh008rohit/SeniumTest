package base;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.github.tomakehurst.wiremock.WireMockServer;

import io.restassured.RestAssured;
import listeners.AnnotationTransformer;
import listeners.MethodInterceptor;
import listeners.SeleniumListener;
import loggerUtils.LogUtils;
import reportManager.ExtentManager;
import reportManager.ExtentReportManager;
import utlity.ConfigLoader;

@Listeners({
    AnnotationTransformer.class,
    SeleniumListener.class,
    MethodInterceptor.class
})
public class APITestBase {

    // ThreadLocal — each thread gets its own WireMock instance
    // null when mock is disabled — no server running at all
    private static final ThreadLocal<WireMockServer> wireMockServer =
        new ThreadLocal<>();

    // read once — same value for entire test run
    private static final boolean USE_MOCK =
        Boolean.parseBoolean(ConfigLoader.getInstance().getUseMock());

    @BeforeMethod(alwaysRun = true)
    public void setupAPI() {

        if (USE_MOCK) {
            // ── mock mode — start WireMock, point RestAssured at it ──
            WireMockServer server = new WireMockServer(options().port(0));
            server.start();
            wireMockServer.set(server);

            RestAssured.baseURI = "http://localhost:" + server.port();

            MockServices.setupStubs(server);
            MockServices.stubGetWithBasicAuth(server);
            MockServices.stubGetWithBearerToken(server);
            MockServices.stubOAuth2TokenEndpoint(server);
            MockServices.stubProtectedResourceWithOAuthToken(server);

            LogUtils.info("Mock mode ON — WireMock started on port "
                + server.port()
                + " | Thread: " + Thread.currentThread().getId());

        } else {
            // ── real mode — point RestAssured at actual API server ──
            RestAssured.baseURI = ConfigLoader.getInstance().getAPIBaseUrl();

            LogUtils.info("Mock mode OFF — hitting real API: "
                + RestAssured.baseURI
                + " | Thread: " + Thread.currentThread().getId());
        }
    }

    
 // APITestBase.java — add stop timeout
    @AfterMethod(alwaysRun = true)
    public void tearDownAPI() {
        if (USE_MOCK) {
            WireMockServer server = wireMockServer.get();
            if (server != null && server.isRunning()) {
                server.stop();
                LogUtils.info("WireMock stopped | Thread: "
                    + Thread.currentThread().getId());
            }
            wireMockServer.remove();
        }
        ExtentManager.unload();
    }

  

    // ── accessors for subclasses ───────────────────────────────

    // returns the running WireMock server for this thread
    // only valid when useMock=true — null otherwise
    protected WireMockServer getWireMockServer() {
        return wireMockServer.get();
    }

    // true if running in mock mode
    protected boolean isMockEnabled() {
        return USE_MOCK;
    }
}