package hooks;

import base.PageObjectManager;
import com.aventstack.extentreports.MediaEntityBuilder;
import driver.DriverManager;
import driver.DriverManagerFactory;
import enums.DriverType;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import loggerUtils.LogUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import reportManager.ExtentManager;
import reportManager.ExtentReportManager;
import utlity.ConfigLoader;

// removed extends BaseTest — Hooks owns the BDD driver lifecycle independently
public class Hooks {

    @Before(order = 0)
    public void setupExtentReport() {
        ExtentReportManager.getSetup();
    }

    @Before(order = 1)
    public void initializeDriver() throws Exception {
        String browser = System.getProperty("browser") != null
            ? System.getProperty("browser").toUpperCase()
            : "CHROME";

        DriverManager.setDriver(
            DriverManagerFactory.getManager(DriverType.valueOf(browser)).createDriver()
        );

        // navigate once here — not in page object constructors
        DriverManager.getDriver().get(ConfigLoader.getInstance().getBaseUrl());
        LogUtils.info("BDD driver started: " + browser);
    }

    @Before(order = 2)
    public void initializeExtentTest(Scenario scenario) {
        ExtentReportManager.createTest(scenario.getName());
    }

    @AfterStep
    public void captureFailureScreenshot(Scenario scenario) {
        if (scenario.isFailed() && DriverManager.getDriver() != null) {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failed Step Screenshot");

            String base64Screenshot = ((TakesScreenshot) DriverManager.getDriver())
                .getScreenshotAs(OutputType.BASE64);
            ExtentManager.getExtentTest().fail("Step failed",
                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        }
    }

    @After(order = 0)  
    // order = 0 → runs LAST among @After hooks in Cucumber
    // driver is still alive here so AfterStep screenshot above works correctly
    public void tearDown(Scenario scenario) {
    	LogUtils.info("BDD scenario finished: " + scenario.getName()
            + " | Status: " + scenario.getStatus());

        if (DriverManager.getDriver() != null) {
            DriverManager.getDriver().quit();   // 1. close browser
            DriverManager.unload();             // 2. remove ThreadLocal
        }

        ExtentManager.unload();
        // only unload the ExtentTest node for this scenario
        // do NOT call flushReports() here — that would write the file after every scenario
    }

    // ─── AFTER ALL SCENARIOS ───────────────────────────────────

    @AfterAll
    public static void flushReport() {
        ExtentReportManager.flushReports();
        // called exactly once after all scenarios complete
        // this is where the HTML report file actually gets written
    }
}
