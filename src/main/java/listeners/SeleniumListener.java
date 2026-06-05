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
       // ExtentReportManager.getSetup();
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
 // SeleniumListener.java — onTestSuccess — add duration badge
    public void onTestSuccess(ITestResult result) {
        count_passedTCs.incrementAndGet();

        long durationMs = result.getEndMillis() - result.getStartMillis();
        
        // colour-coded duration badge
        String durationColor = durationMs < 3000 ? "green" : durationMs < 8000 ? "orange" : "red";
        ExtentManager.getExtentTest().info(
            "<span style='background:" + durationColor + ";color:white;"
            + "padding:2px 8px;border-radius:4px;font-size:12px'>"
            + "⏱ Duration: " + durationMs + " ms</span>"
        );

        String logText = "<b>" + result.getMethod().getMethodName()
            + " passed.</b>  " + ExtentReportConstant.ICON_SMILEY_PASS;
        ExtentManager.getExtentTest().pass(
            MarkupHelper.createLabel(logText, ExtentColor.GREEN));
        LogUtils.info("Test Passed: " + result.getName());
        ExtentManager.unload();
    }

    // SeleniumListener.java — onTestFailure — add collapsible stack trace
    public void onTestFailure(ITestResult result) {
        count_failedTCs.incrementAndGet();

        long durationMs = result.getEndMillis() - result.getStartMillis();
        ExtentManager.getExtentTest().info(
            "<span style='background:red;color:white;"
            + "padding:2px 8px;border-radius:4px;font-size:12px'>"
            + "⏱ Duration: " + durationMs + " ms</span>"
        );

        // Root cause — first line only, clean
        String rootCause = result.getThrowable().getClass().getSimpleName()
            + ": " + result.getThrowable().getMessage();
        ExtentManager.getExtentTest().log(Status.FAIL,
            ExtentReportConstant.ICON_BUG + " <b>" + rootCause + "</b>");

        // Collapsible full stack trace
        String stackTrace = Arrays.stream(result.getThrowable().getStackTrace())
            .map(StackTraceElement::toString)
            .collect(java.util.stream.Collectors.joining("<br>"));
        ExtentManager.getExtentTest().log(Status.FAIL,
            "<details><summary><b><font color='red'>Full stack trace (click to expand) "
            + ExtentReportConstant.ICON_SMILEY_FAIL + "</font></b></summary>"
            + "<pre style='font-size:11px'>" + stackTrace + "</pre></details>");

        LogUtils.error("Test Failed: " + result.getName());
        LogUtils.errorThrowable(result.getThrowable());

        // screenshot guard
        if (DriverManager.getDriver() != null) {
            try {
                DriverManager.getDriver().getWindowHandles();
                String screenshotPath = SeleniumCommonUtils.captureScreenshot(result.getName());
                ExtentManager.getExtentTest()
                    .addScreenCaptureFromPath(new File(screenshotPath).getCanonicalPath());
            } catch (NoSuchSessionException se) {
                ExtentManager.getExtentTest().log(Status.FAIL,
                    "<b>Browser session was lost — screenshot unavailable.</b>");
            } catch (IOException e) {
                ExtentManager.getExtentTest().log(Status.FAIL,
                    "Screenshot failed: " + e.getMessage());
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
        LogUtils.info(
                "\n╔══════════════════════════════════════╗\n"
                + "║         SUITE EXECUTION SUMMARY      ║\n"
                + "╠══════════════════════════════════════╣\n"
                + "║  Total  : " + count_totalTCs.get()   + "\n"
                + "║  Passed : " + count_passedTCs.get()  + "\n"
                + "║  Failed : " + count_failedTCs.get()  + "\n"
                + "║  Skipped: " + count_skippedTCs.get() + "\n"
                + "╚══════════════════════════════════════╝"
            );
        
    }

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
