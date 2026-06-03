package listeners;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

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
        count_totalTCs.incrementAndGet();   // thread-safe — was count_totalTCs + 1

        // ── 1. determine test type from driver state ────────────
        // no try-catch — driver presence is a known condition, not exceptional
        TestType testType = DriverManager.getDriver() != null
            ? TestType.UI
            : TestType.API;

        // ── 2. create extent test node with correct icon ────────
        ExtentReportManager.createTest(
            result.getMethod().getMethodName(),
            testType    // was: no TestType — caused getBrowserIcon() NPE for API tests
        );

        LogUtils.info("Test Started: " + result.getName());

        // ── 3. add annotation metadata safely ──────────────────
        // was: direct .getAnnotation() — NPE if @FrameworkAnnotation missing
        FrameworkAnnotation annotation = result
            .getMethod()
            .getConstructorOrMethod()
            .getMethod()
            .getAnnotation(FrameworkAnnotation.class);

        if (annotation != null) {
            ExtentReportManager.addAuthors(annotation.author());
            ExtentReportManager.addCategories(annotation.category());
        } else {
        	LogUtils.warn("@FrameworkAnnotation missing on: "
                + result.getMethod().getMethodName()
                + " — author and category will not appear in report");
        }

        // ── 4. add device info once ─────────────────────────────
        // was: addDevices() called twice — duplicate entry in report
        ExtentReportManager.addDevices();

        // ── 5. log OS + browser info for UI, plain label for API
        // no try-catch — testType already determined above, no exception possible
        if (testType == TestType.UI) {
            ExtentManager.getExtentTest().info(
                "<b>"
                + IconUtils.getOSIcon()
                + "  &  "
                + IconUtils.getBrowserIcon()
                + " ——— "
                + BrowserOSInfoUtils.getOS_Browser_BrowserVersionInfo()
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
        count_passedTCs.incrementAndGet();  // was: count_passedTCs + 1

        String logText = "<b>"
            + result.getMethod().getMethodName()
            + " passed.</b>  "
            + ExtentReportConstant.ICON_SMILEY_PASS;

        Markup markup = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        ExtentManager.getExtentTest().pass(markup);
        LogUtils.info("Test Passed: " + result.getName());
    }

    // ─── TEST FAILURE ──────────────────────────────────────────

    public void onTestFailure(ITestResult result) {
        count_failedTCs.incrementAndGet();  // was: count_failedTCs + 1

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
        if (DriverManager.getDriver() != null) {
            try {
                String screenshotPath = SeleniumCommonUtils.captureScreenshot(
                    result.getName()
                );
                File screenshotFile  = new File(screenshotPath);
                String absolutePath  = screenshotFile.getCanonicalPath();
                ExtentManager.getExtentTest()
                    .addScreenCaptureFromPath(absolutePath);
            } catch (IOException e) {
                ExtentManager.getExtentTest().log(
                    Status.FAIL,
                    "Failed to attach screenshot: " + e.getMessage()
                );
                LogUtils.error(
                    "Failed to attach screenshot: " + e.getMessage()
                );
            }
        } else {
            // API test failure — log request/response details instead
            ExtentManager.getExtentTest().log(
                Status.FAIL,
                "<b>API test failed — no screenshot available. "
                + "Check request/response details above.</b>"
            );
        }
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
    }

    // ─── TEST FINISH ───────────────────────────────────────────

    public void onFinish(ITestContext context) {
        ExtentReportManager.flushReports();
        LogUtils.info("Test Suite Finished: " + context.getName());

        // summary log — counters are now accurate under parallel execution
        LogUtils.info(
            "Suite Summary — "
            + "Total: "   + count_totalTCs.get()
            + ", Passed: " + count_passedTCs.get()
            + ", Failed: " + count_failedTCs.get()
            + ", Skipped: " + count_skippedTCs.get()
        );
    }

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
