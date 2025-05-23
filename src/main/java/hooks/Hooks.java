package hooks;

import base.BaseTest;
import base.PageObjectManager;
import com.aventstack.extentreports.MediaEntityBuilder;
import driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import reportManager.ExtentReportManager;

import java.io.IOException;

public class Hooks extends BaseTest {

    @Before(order = 0)
    public void setupBrowser() throws IOException {
        this.setup(BaseTest.getValueFromPropFile("browserType"));
        this.pageManager = new PageObjectManager();
    }

    @Before(order = 1)
    public void initializeExtentTest(Scenario scenario) {
        ExtentReportManager.createTest(scenario.getName());
    }

    @AfterStep
    public void captureFailureScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failed Step Screenshot");

            String base64Screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
            ExtentReportManager.getTest().fail("Step failed",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        }
    }

    @After(order = 0)
    public void tearDownBrowser() {
        this.tearDown();
    }

    // REMOVE flushExtent from here
}