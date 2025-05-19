 package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import java.io.File;
import java.io.IOException;
import loggerUtil.LoggerUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reportManager.ExtentReportManager;
import utlity.SeleniumCommonUtils;

public class SeleniumListener implements ITestListener, ISuiteListener, IInvokedMethodListener {
   ExtentReports extent;

   public void onStart(ITestContext context) {
      extent = ExtentReportManager.getSetup(context.getName());
      LoggerUtils.info("Test Suite Started: " + context.getName());
   }

   public void onTestStart(ITestResult result) {
      ExtentReportManager.createTest(result.getMethod().getMethodName());
      LoggerUtils.info("Test Started: " + result.getName());
   }

   public void onTestSuccess(ITestResult result) {
      ExtentReportManager.getTest().log(Status.PASS, "Test Passed: " + result.getName());
      LoggerUtils.info("Test Passed: " + result.getName());
   }

   public void onTestFailure(ITestResult result) {
      ExtentReportManager.getTest().log(Status.FAIL, "Test Failed: " + result.getName());
      ExtentReportManager.getTest().log(Status.FAIL, result.getThrowable());
      LoggerUtils.error("Test Failed: " + result.getName());
      LoggerUtils.errorThrowable(result.getThrowable());

      try {
         String screenshot = SeleniumCommonUtils.captureScreenshot(result.getName());
         File screenshotFile = new File(screenshot);
         String absolutePath = screenshotFile.getCanonicalPath();
         ExtentReportManager.getTest().addScreenCaptureFromPath(absolutePath);
      } catch (IOException var5) {
         ExtentReportManager.getTest().log(Status.FAIL, "Failed to attach screenshot: " + var5.getMessage());
         LoggerUtils.error("Failed to attach screenshot: " + var5.getMessage());
      }

   }

   public void onTestSkipped(ITestResult result) {
      ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped: " + result.getName());
      LoggerUtils.info("Test Skipped: " + result.getName());
   }

   public void onTestFailedWithTimeout(ITestResult result) {
      this.onTestFailure(result);
   }

   public void onFinish(ITestContext context) {
      ExtentReportManager.flush();
      LoggerUtils.info("Test Suite Finished: " + context.getName());
   }

   public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
      ExtentReportManager.getTest().log(Status.WARNING, "Test failed but within success percentage: " + result.getMethod().getMethodName());
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
    