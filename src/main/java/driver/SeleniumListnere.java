package driver;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import LoggerUtil.LoggerUtils;
import Utlity.SeleniumCommonUtils;
import reportManager.ExtentReportManager;

public class SeleniumListnere implements ITestListener {
	
WebDriver driver;
public static ExtentTest test;
 ExtentReports extent;

@Override
public void onStart(ITestContext context) {
	extent = ExtentReportManager.getSetup(context.getName()); // Initialize report
    LoggerUtils.info("Test Suite Started: " + context.getName());

    
}
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		  ExtentReportManager.createTest(result.getMethod().getMethodName()); // Initialize test
	        LoggerUtils.info("Test Started: " + result.getName());
        
        LoggerUtils.info("Test Started: " + result.getName());

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
        ExtentReportManager.getTest().log(Status.PASS, "Test Passed: " + result.getName());
        LoggerUtils.info("Test Passed: " + result.getName());

	}

	@Override
	public void onTestFailure(ITestResult result) {
	test.log(Status.FAIL," Test fail " +result.getName());
	ExtentReportManager.getTest().log(Status.FAIL, result.getThrowable());
		 LoggerUtils.error(" Test fail " +result.getName());
		 LoggerUtils.errorThrowable(result.getThrowable());
		
		
	    try {
	    	String screenshot=SeleniumCommonUtils.captureScreenshot(result.getName());
	        File screenshotFile = new File(screenshot);
	        String absolutePath = screenshotFile.getCanonicalPath();

	        // Attach screenshot to Extent Report
	        ExtentReportManager.getTest().addScreenCaptureFromPath(absolutePath);
	    } catch (IOException e) {
	    	ExtentReportManager.getTest().log(Status.FAIL, "Failed to attach screenshot: " + e.getMessage());
	        LoggerUtils.error( "Failed to attach screenshot: " + e.getMessage());
	    }

		

	
	
    

	
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ExtentReportManager.getTest().log(Status.SKIP, "Test cases skip"+result.getName());
		LoggerUtils.info("Test cases skip"+result.getName());

	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}



	

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ExtentReportManager.flush();
     //   System.out.println("Test Suite Finished: " + context.getName());
		LoggerUtils.info("Test Suite Finished: " + context.getName());

	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

}
