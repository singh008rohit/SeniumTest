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
import listeners.AnnotationTransformer;
import listeners.MethodInterceptor;
import listeners.SeleniumListener;
import loggerUtil.LoggerUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import reportManager.ExtentManager;
import reportManager.ExtentReportManager;

import java.io.IOException;



public class Hooks extends BaseTest {

	  @Before(order = 0)
	    public void setupExtentReport() {
		  ExtentReportManager.getSetup();
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
            ExtentManager.getExtentTest().fail("Step failed",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        }
    }}

  