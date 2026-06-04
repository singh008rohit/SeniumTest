package listeners;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.openqa.selenium.NoSuchSessionException;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import annotation.FrameworkAnnotation;
import commonConstant.ExtentReportConstant;
import driver.DriverManager;
import enums.TestType;
import loggerUtils.LogUtils;
import reportManager.ExtentManager;
import reportManager.ExtentReportManager;
import utlity.BrowserOSInfoUtils;
import utlity.IconUtils;
import utlity.SeleniumCommonUtils;

public class SeleniumListener implements ITestListener, ISuiteListener,
                                         IInvokedMethodListener {

    // ─── COUNTERS ──────────────────────────────────────────────
    // AtomicInteger — thread-safe increment in parallel runs
    // was: static int count_totalTCs — corrupted under parallel execution
    private static final AtomicInteger count_totalTCs   = new AtomicInteger(0);
    private static final AtomicInteger count_passedTCs  = new AtomicInteger(0);
    private static final AtomicInteger count_failedTCs  = new AtomicInteger(0);
    private static final AtomicInteger count_skippedTCs = new AtomicInteger(0);

    // ─── SUITE ─────────────────────────────────────────────────

    public void onStart(ITestContext context) {
        ExtentReportManager.getSetup();
        LogUtils.info("Test Suite Started: " + context.getName());
    }

    // ─── TEST START ────────────────────────────────────────────

    public void onTestStart(ITestResult result) {
        count_totalTCs.incrementAndGet();

        TestType testType = DriverManager.getDriver() != null
            ? TestType.UI
            : TestType.API;

        // USE THE NEW OVERLOAD — passes full result so class name is available
        ExtentReportManager.createTest(result, testType);

        LogUtils.info("Test Started: " + result.getName());

        FrameworkAnnotation annotation = result
            .getMethod()
            .getConstructorOrMethod()
            .getMethod()
            .getAnnotation(FrameworkAnnotation.class);

        if (annotation != null) {
            ExtentReportManager.addAuthors(annotation.author());
            ExtentReportManager.addCategories(annotation.category());
        }

        ExtentReportManager.addDevices();

        if (testType == TestType.UI) {
            ExtentManager.getExtentTest().info(
                "<b>" + IconUtils.getOSIcon()
                + "  &  " + IconUtils.getBrowserIcon()
                + " ——— " + BrowserOSInfoUtils.getOS_Browser_BrowserVersionInfo()
                + "</b>"
            );
        } else {
            ExtentManager.getExtentTest().info(
                "<b><i class='fa fa-plug' style='color:#e67e22'></i>"
                + "  API Test Execution</b>"
            );
        }
    }
    // ─── TEST SUCCESS ──────────────────────────────────────────

    public void onTestSuccess(ITestResult result) {
        count_passedTCs.incrementAndGet();

        // calculate and log duration
        long durationMs = result.getEndMillis() - result.getStartMillis();
        ExtentManager.getExtentTest().info(
            "<b>Duration:</b> " + durationMs + " ms"
        );

        String logText = "<b>"
            + result.getMethod().getMethodName()
            + " passed.</b>  "
            + ExtentReportConstant.ICON_SMILEY_PASS;

        Markup markup = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        ExtentManager.getExtentTest().pass(markup);
        LogUtils.info("Test Passed: " + result.getName());
        ExtentManager.unload(); 
    }

    // ─── TEST FAILURE ──────────────────────────────────────────

    public void onTestFailure(ITestResult result) {
        count_failedTCs.incrementAndGet();  
        
        long durationMs = result.getEndMillis() - result.getStartMillis();
        ExtentManager.getExtentTest().info(
            "<b>Duration:</b> " + durationMs + " ms"
        );// was: count_failedTCs + 1

        ExtentManager.getExtentTest().info(
            "Test Failed: " + result.getName()
        );
        ExtentManager.getExtentTest().log(
            Status.FAIL,
            ExtentReportConstant.ICON_BUG
            + "  <b><i>"
            + result.getThrowable().toString()
            + "</i></b>"
        );

        String exceptionMessage = Arrays.toString(
            result.getThrowable().getStackTrace()
        );
        String message = "<details><summary><b><font color=red>"
            + "Exception occurred, click to see details: "
            + ExtentReportConstant.ICON_SMILEY_FAIL
            + "</font></b></summary>"
            + exceptionMessage.replaceAll(",", "<br>")
            + "</details>\n";
        ExtentManager.getExtentTest().log(Status.FAIL, message);

        LogUtils.error("Test Failed: " + result.getName());
        LogUtils.errorThrowable(result.getThrowable());

        // ── screenshot only for UI tests ────────────────────────
        // was: always attempted — threw NPE for API tests with no driver
     // SeleniumListener.java — onTestFailure — guard the screenshot
        if (DriverManager.getDriver() != null) {
            try {
                // ADD: check session is alive before taking screenshot
                DriverManager.getDriver().getWindowHandles(); // throws if session dead
                
                String screenshotPath = SeleniumCommonUtils.captureScreenshot(result.getName());
                File screenshotFile = new File(screenshotPath);
                String absolutePath = screenshotFile.getCanonicalPath();
                ExtentManager.getExtentTest().addScreenCaptureFromPath(absolutePath);
            } catch (NoSuchSessionException se) {
                ExtentManager.getExtentTest().log(Status.FAIL,
                    "<b>Browser session was lost — screenshot unavailable.</b>");
                LogUtils.error("Browser session dead, cannot take screenshot: " + se.getMessage());
            } catch (IOException e) {
                ExtentManager.getExtentTest().log(Status.FAIL,
                    "Failed to attach screenshot: " + e.getMessage());
            }
        }
        ExtentManager.unload(); 
    }

    // ─── TEST SKIP ─────────────────────────────────────────────

    public void onTestSkipped(ITestResult result) {
        count_skippedTCs.incrementAndGet();  // was: no counter increment at all

        ExtentManager.getExtentTest().log(
            Status.SKIP,
            "<b>" + result.getName() + " skipped.</b>  "
            + ExtentReportConstant.ICON_SMILEY_SKIP
        );
        LogUtils.info("Test Skipped: " + result.getName());
        ExtentManager.unload(); 
    }

    // ─── TEST FINISH ───────────────────────────────────────────

    public void onFinish(ITestContext context) {
      //  ExtentReportManager.flushReports();
        LogUtils.info("Test Suite Finished: " + context.getName());}

        // summary log — counters are now accurate under parallel execution
   

    // ─── OTHER EVENTS ──────────────────────────────────────────

    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ExtentManager.getExtentTest().log(
            Status.WARNING,
            "Test failed but within success percentage: "
            + result.getMethod().getMethodName()
        );
    }

    public void beforeInvocation(IInvokedMethod method,
                                 ITestResult testResult) {}

    public void afterInvocation(IInvokedMethod method,
                                ITestResult testResult) {}

    public void beforeInvocation(IInvokedMethod method,
                                 ITestResult testResult,
                                 ITestContext context) {}

    public void afterInvocation(IInvokedMethod method,
                                ITestResult testResult,
                                ITestContext context) {}
}
