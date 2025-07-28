package hooks;

import base.BaseTest;
import base.PageObjectManager;
import commonConstant.CommonConstant;

import com.aventstack.extentreports.MediaEntityBuilder;
import driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import loggerUtil.LoggerUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import reportManager.ExtentReportManager;

import java.io.IOException;

public class Hooks extends BaseTest {

    @Before(order = 0)
    
    public void setupBrowser() throws IOException {
    	  // Priority 1: CLI `-Dbrowser=...`
    	String cliBrowser = System.getProperty("browser");
    	String xmlBrowser   = CommonConstant.CUCUMBERBROWSERTYPE;
    	String browser;

    	if (cliBrowser != null && !cliBrowser.isEmpty()) {
    	    browser = cliBrowser;
    	    LoggerUtils.info("Browser resolved from system property 'browser': " + browser);
    	} else {
    	    browser = getValueFromPropFile(CommonConstant.BROWSERTYPE);
    	    LoggerUtils.info("Browser resolved from properties file: " + browser);
    	}

    	   
        this.setup(browser);
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