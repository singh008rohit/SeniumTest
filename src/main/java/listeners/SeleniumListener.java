 package listeners;



import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import annotation.FrameworkAnnotation;
import commonConstant.ExtentReportConstant;
import driver.DriverManager;
import loggerUtil.LoggerUtils;
import reportManager.ExtentManager;
import reportManager.ExtentReportManager;
import utlity.BrowserOSInfoUtils;
import utlity.IconUtils;
import utlity.SeleniumCommonUtils;

public class SeleniumListener implements ITestListener, ISuiteListener, IInvokedMethodListener {
   ExtentReports extent;
   String testClassName = this.getClass().getSimpleName();  // Always returns actual class name
   
   static int count_passedTCs;
	static int count_skippedTCs;
	static int count_failedTCs;
	static int count_totalTCs;


   public void onStart(ITestContext context) {
      extent = ExtentReportManager.getSetup();
      LoggerUtils.info("Test Suite Started: " + context.getName());
   }

   public void onTestStart(ITestResult result) {
	   count_totalTCs = count_totalTCs + 1;
     ExtentReportManager.createTest(result.getMethod().getMethodName());
      LoggerUtils.info("Test Started: " + result.getName());

      ExtentReportManager.addAuthors(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrameworkAnnotation.class).author());
			

      ExtentReportManager.addCategories(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrameworkAnnotation.class).category());
			
      ExtentReportManager.addDevices();
      try {
          if (DriverManager.getDriver() != null) {
              ExtentReportManager.addDevices(); // will use getBrowserInfo()
              ExtentManager.getExtentTest().info("<b>" + IconUtils.getOSIcon() + "  &  " + IconUtils.getBrowserIcon()
                  + " --------- " + BrowserOSInfoUtils.getOS_Browser_BrowserVersionInfo() + "</b>");
          } else {
              ExtentManager.getExtentTest().info("<b>API Test Execution</b>");
          }
      } catch (Exception e) {
          ExtentManager.getExtentTest().info("<b>API Test Execution (No Browser Detected)</b>");
      }
   }

   public void onTestSuccess(ITestResult result) {
		count_passedTCs = count_passedTCs + 1;
		String logText = "<b>" + result.getMethod().getMethodName() + " is passed.</b>" + "  " + ExtentReportConstant.ICON_SMILEY_PASS;
		Markup markup_message = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		 ExtentManager.getExtentTest().pass( markup_message + result.getName());
      LoggerUtils.info("Test Passed: " + result.getName());
   }

   public void onTestFailure(ITestResult result) {
	   ExtentManager.getExtentTest().info("Test Failed: " + result.getName());
  	count_failedTCs = count_failedTCs + 1;
  	 ExtentManager.getExtentTest().log(Status.FAIL,ExtentReportConstant.ICON_BUG + "  " + "<b><i>" + result.getThrowable().toString() + "</i></b>");
	String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
	String message = "<details><summary><b><font color=red> Exception occured, click to see details: "
			+ ExtentReportConstant.ICON_SMILEY_FAIL + " </font></b>" + "</summary>" + exceptionMessage.replaceAll(",", "<br>")
			+ "</details> \n";
	 ExtentManager.getExtentTest().log(Status.FAIL, message);
      LoggerUtils.error("Test Failed: " + result.getName());
      LoggerUtils.errorThrowable(result.getThrowable());

      try {
    	  
         String screenshot = SeleniumCommonUtils.captureScreenshot(result.getName());
         File screenshotFile = new File(screenshot);
         String absolutePath = screenshotFile.getCanonicalPath();
         ExtentManager.getExtentTest().addScreenCaptureFromPath(absolutePath);
      } catch (IOException var5) {
    	  ExtentManager.getExtentTest().log(Status.FAIL, "Failed to attach screenshot: " + var5.getMessage());
         LoggerUtils.error("Failed to attach screenshot: " + var5.getMessage());
      }

   }

   public void onTestSkipped(ITestResult result) {
	   ExtentManager.getExtentTest().log(Status.SKIP, "Test Skipped: " + result.getName());
      LoggerUtils.info("Test Skipped: " + result.getName());
   }

   public void onTestFailedWithTimeout(ITestResult result) {
      this.onTestFailure(result);
   }

   public void onFinish(ITestContext context) {
      ExtentReportManager.flushReports();
      LoggerUtils.info("Test Suite Finished: " + context.getName());
   }

   public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	   ExtentManager.getExtentTest().log(Status.WARNING, "Test failed but within success percentage: " + result.getMethod().getMethodName());
   }

   public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
   }

   public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
   }

   public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
   }

   public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
   }
}
    