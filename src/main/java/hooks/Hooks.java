package hooks;

import base.PageObjectManager;
import com.aventstack.extentreports.MediaEntityBuilder;
import driver.DriverManager;
import driver.DriverManagerFactory;
import enums.DriverType;
import enums.TestType;
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

 // Hooks.java — FIXED
    @Before(order = 2)
    public void initializeExtentTest(Scenario scenario) {
        // Create the Extent test node for this scenario
        // BDD uses TestType.BDD — gets the green leaf icon
        ExtentReportManager.createTest(scenario.getName(), TestType.BDD);
        
        // Add Cucumber tags as categories in the report
        scenario.getSourceTagNames().forEach(tag ->
            ExtentManager.getExtentTest().assignCategory(tag.replace("@", ""))
        );
        
        LogUtils.info("BDD scenario started: " + scenario.getName());
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

 // Hooks.java — FIXED tearDown
    @After(order = 0)
    public void tearDown(Scenario scenario) {
        LogUtils.info("BDD scenario finished: " + scenario.getName()
            + " | Status: " + scenario.getStatus());

        // Log final scenario status to Extent report
        if (ExtentManager.getExtentTest() != null) {
            switch (scenario.getStatus()) {
                case PASSED:
                    ExtentManager.getExtentTest().pass(
                        "<b>" + scenario.getName() + " passed.</b> "
                        + "<i class='fa fa-smile-o' style='font-size:20px'></i>");
                    break;
                case FAILED:
                    ExtentManager.getExtentTest().fail(
                        "<b>" + scenario.getName() + " failed.</b>");
                    break;
                case SKIPPED:
                    ExtentManager.getExtentTest().skip(
                        "<b>" + scenario.getName() + " skipped.</b>");
                    break;
                default:
                    ExtentManager.getExtentTest().warning(
                        scenario.getName() + " — status: " + scenario.getStatus());
            }
        }

        if (DriverManager.getDriver() != null) {
            DriverManager.getDriver().quit();
            DriverManager.unload();
        }
        ExtentManager.unload();
    }

    // ─── AFTER ALL SCENARIOS ───────────────────────────────────

    @AfterAll
    public static void flushReport() {
        ExtentReportManager.flushReports();
        // called exactly once after all scenarios complete
        // this is where the HTML report file actually gets written
    }
}
