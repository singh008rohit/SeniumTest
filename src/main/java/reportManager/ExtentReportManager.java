// ExtentReportManager.java
package reportManager;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

import commonConstant.ExtentReportConstant;
import driver.DriverManager;
import enums.AuthorType;
import enums.CategoryType;
import enums.TestType;
import utlity.BrowserInfoUtils;
import utlity.ConfigLoader;
import utlity.DateUtils;
import utlity.IconUtils;

public class ExtentReportManager {

    // ── SINGLETON EXTENT INSTANCE ──────────────────────────────
    // Created eagerly — but reporter is NOT attached yet
    private static final ExtentReports extent = new ExtentReports();

    // ── LAZY REPORTER STATE ────────────────────────────────────
    // reporterAttached = false until first test class name is known
    private static final AtomicBoolean reporterAttached = new AtomicBoolean(false);

    // Stores the resolved file path once reporter is attached
    // volatile — read by any thread after AtomicBoolean cas
    private static volatile String currentReportFilePath = "";

    private ExtentReportManager() {}

    // ── LAZY REPORTER ATTACHMENT ───────────────────────────────
    // Called from onTestStart — first call attaches the reporter with the
    // correct class name in the filename. Subsequent calls are no-ops.
    // synchronized — only one thread does the attachment, all others skip
    public static synchronized void attachReporterIfNeeded(String testClassName) {
        if (reporterAttached.get()) {
            return; // already attached — nothing to do
        }

        // Build filename: ClassName_2026-06-04_18-01-58.html
        String timestamp = DateUtils.getReportTimestamp();
        String fileName  = testClassName + "_" + timestamp + ".html";
        currentReportFilePath = ExtentReportConstant.EXTENT_REPORT_FOLDER_PATH + fileName;

        // Create folder if it doesn't exist
        new java.io.File(ExtentReportConstant.EXTENT_REPORT_FOLDER_PATH).mkdirs();

        ExtentSparkReporter reporter = new ExtentSparkReporter(currentReportFilePath);
        configureReporter(reporter, testClassName);

        extent.attachReporter(reporter);

        // System info — set once after reporter is attached
        setSystemInfo(testClassName);

        reporterAttached.set(true);

        loggerUtils.LogUtils.info(
            "Extent report created: " + currentReportFilePath);
    }

    // ── REPORTER CONFIG ────────────────────────────────────────
    private static void configureReporter(ExtentSparkReporter reporter,
                                          String testClassName) {
        reporter.config().setTheme(Theme.STANDARD);
        reporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
        reporter.config().setEncoding("UTF-8");
        reporter.config().setProtocol(Protocol.HTTPS);
        reporter.config().setDocumentTitle(
            ExtentReportConstant.getProjectName() + " — " + testClassName);
        reporter.config().setReportName(
            ExtentReportConstant.getProjectName() + " — " + testClassName);
    }

    // ── SYSTEM INFO ────────────────────────────────────────────
    private static void setSystemInfo(String testClassName) {
        extent.setSystemInfo("Test Class",     testClassName);
        extent.setSystemInfo("Tester",         "Rohit Singh");
        extent.setSystemInfo("Environment",    System.getProperty("env", "STAGE"));
        extent.setSystemInfo("Organization",   "QATest");
        extent.setSystemInfo("Execution Time", DateUtils.getReportTimestamp());
        extent.setSystemInfo("Framework",
            "Selenium + RestAssured + Cucumber + TestNG");
        extent.setSystemInfo("Base URL",
            ConfigLoader.getInstance().getBaseUrl());
        extent.setSystemInfo("API Base URL",
            ConfigLoader.getInstance().getAPIBaseUrl());
        extent.setSystemInfo("Mock Enabled",
            ConfigLoader.getInstance().getUseMock());
        extent.setSystemInfo("Java Version",
            System.getProperty("java.version"));
        extent.setSystemInfo("OS",
            System.getProperty("os.name")
            + " (" + System.getProperty("os.arch") + ")");
        extent.setSystemInfo("Headless",
            ConfigLoader.getInstance().getIsheadless());
        extent.setSystemInfo("Grid Enabled",
            ConfigLoader.getInstance().getuseGrid());
        extent.setSystemInfo("Retry Enabled",
            ConfigLoader.getInstance().getRetryFailedTests());
        extent.setSystemInfo("Employee",
            "<b>Rohit Singh</b> "
            + ExtentReportConstant.ICON_SOCIAL_LINKEDIN + " "
            + ExtentReportConstant.ICON_SOCIAL_GITHUB);
        extent.setSystemInfo("Domain",
            "Engineering (IT - Software) "
            + ExtentReportConstant.ICON_LAPTOP);
        extent.setSystemInfo("Skill", "Test Automation Engineer");
    }

    // ── CREATE TEST ────────────────────────────────────────────
    public static synchronized void createTest(ITestResult result,
                                               TestType testType) {
        // Attach reporter on first call — uses this test's class name
        String className = result.getTestClass()
                                 .getRealClass()
                                 .getSimpleName();
        attachReporterIfNeeded(className);

        String methodName   = result.getMethod().getMethodName();
        String description  = result.getMethod().getDescription();
        String icon         = resolveIcon(testType);
        String title        = icon + " <b>" + className + "</b> › " + methodName;

        ExtentTest test = extent.createTest(title);

        if (description != null && !description.isEmpty()) {
            test.info("<i>" + description + "</i>");
        }

        ExtentManager.setExtentTest(test);
    }

    // Overload for BDD / string-based creation
    public static synchronized void createTest(String testName,
                                               TestType testType) {
        attachReporterIfNeeded(testName.replaceAll("[^a-zA-Z0-9]", "_"));
        String icon = resolveIcon(testType);
        ExtentManager.setExtentTest(extent.createTest(icon + " " + testName));
    }

    // ── ICON RESOLUTION ───────────────────────────────────────
    private static String resolveIcon(TestType testType) {
        switch (testType) {
            case API:
                return "<i class='fa fa-plug' style='color:#e67e22'></i> ";
            case BDD:
                return "<i class='fa fa-leaf' style='color:#27ae60'></i> ";
            case UI:
                return IconUtils.getBrowserIcon() + " ";
            default:
                throw new IllegalArgumentException(
                    "Unknown TestType: " + testType);
        }
    }

    // ── TEST METADATA ──────────────────────────────────────────
    public static synchronized void addAuthors(AuthorType[] authors) {
        for (AuthorType author : authors) {
            ExtentManager.getExtentTest().assignAuthor(author.toString());
        }
    }

    public static synchronized void addCategories(CategoryType[] categories) {
        for (CategoryType category : categories) {
            ExtentManager.getExtentTest().assignCategory(category.toString());
        }
    }

    public static void addDevices() {
        try {
            if (DriverManager.getDriver() != null) {
                ExtentManager.getExtentTest()
                    .assignDevice(BrowserInfoUtils.getBrowserInfo());
            } else {
                ExtentManager.getExtentTest()
                    .assignDevice("APITest-NoBrowser");
            }
        } catch (Exception e) {
            ExtentManager.getExtentTest().assignDevice("Unknown Device");
        }
    }

    // ── REPORT LIFECYCLE ───────────────────────────────────────
    public static ExtentReports getSetup() {
        return extent;
    }

    public static String getCurrentReportFilePath() {
        return currentReportFilePath;
    }

    public static void flushReports() {
        if (Objects.nonNull(extent) && reporterAttached.get()) {
            extent.flush();
            loggerUtils.LogUtils.info(
                "Extent report flushed: " + currentReportFilePath);
        }
        // Do NOT call ExtentManager.unload() here
    }
}