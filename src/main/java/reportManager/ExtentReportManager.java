package reportManager;

import java.util.Objects;

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

	// ─── SINGLETON ─────────────────────────────────────────────

	private static final ExtentReports extent;

	static {
		ExtentSparkReporter reporter = new ExtentSparkReporter(
				ExtentReportConstant.getExtentReportFilePath() + DateUtils.getCurrentDate());
		configureReporter(reporter);

		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Rohit Singh");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Organization", "QATest");
	    extent.setSystemInfo("Execution Time", new java.util.Date().toString());
	    extent.setSystemInfo("Framework",      "Selenium + RestAssured + Cucumber + TestNG");
	    extent.setSystemInfo("Base URL",       ConfigLoader.getInstance().getBaseUrl());
	    extent.setSystemInfo("API Base URL",   ConfigLoader.getInstance().getAPIBaseUrl());
	    extent.setSystemInfo("Mock Enabled",   ConfigLoader.getInstance().getUseMock());
		extent.setSystemInfo("Employee", "<b>Rohit Singh</b> " + ExtentReportConstant.ICON_SOCIAL_LINKEDIN + " "
				+ ExtentReportConstant.ICON_SOCIAL_GITHUB);
		extent.setSystemInfo("Domain", "Engineering (IT - Software)  " + ExtentReportConstant.ICON_LAPTOP);
		extent.setSystemInfo("Skill", "Test Automation Engineer");
	}

	private ExtentReportManager() {
	}

	// ─── REPORTER CONFIG ───────────────────────────────────────

	private static void configureReporter(ExtentSparkReporter reporter) {
		reporter.config().setTheme(Theme.STANDARD);
		reporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
		reporter.config().setEncoding("UTF-8");
		reporter.config().setProtocol(Protocol.HTTPS);
		reporter.config().setDocumentTitle(ExtentReportConstant.getProjectName() + " - ALL");
		reporter.config().setReportName(ExtentReportConstant.getProjectName() + " - ALL");
	}

	// ─── CREATE TEST ───────────────────────────────────────────

	// original signature preserved — existing callers need no changes
	// defaults to UI since original behaviour assumed a browser was running
	// New overload — called from SeleniumListener with full result context
	public static synchronized void createTest(ITestResult result, TestType testType) {
	    String className  = result.getTestClass().getRealClass().getSimpleName();
	    String methodName = result.getMethod().getMethodName();
	    String description = result.getMethod().getDescription();

	    String icon = resolveIcon(testType);

	    // "ClassName > methodName" as the node title
	    String title = icon + " <b>" + className + "</b> › " + methodName;

	    ExtentTest test = extent.createTest(title);

	    // add description as a note under the title if present
	    if (description != null && !description.isEmpty()) {
	        test.info("<i>" + description + "</i>");
	    }

	    ExtentManager.setExtentTest(test);
	}

	// overload used by APITestBase, Hooks, SeleniumListener
	// no try-catch — each TestType has a deterministic icon, no exception possible
	public static synchronized void createTest(String testName, TestType testType) {
		String icon = resolveIcon(testType);
		ExtentManager.setExtentTest(extent.createTest(icon + " " + testName));
	}

	// icon resolution is pure logic — no driver access, no exception risk
	private static String resolveIcon(TestType testType) {
		switch (testType) {
		case API:
			return "<i class='fa fa-plug' style='color:#e67e22'></i> ";

		case BDD:
			return "<i class='fa fa-leaf' style='color:#27ae60'></i> ";

		case UI:
			// only UI tests have a live driver — safe to call getBrowserIcon()
			// if driver is null here it is a genuine bug, not expected flow
			return IconUtils.getBrowserIcon() + " ";

		default:
			throw new IllegalArgumentException("Unknown TestType: " + testType + " — add a case to resolveIcon()");
		}
	}

	// ─── TEST METADATA ─────────────────────────────────────────

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

	public static synchronized void addDevices() {
		try {
			if (DriverManager.getDriver() != null) {
				ExtentManager.getExtentTest().assignDevice(BrowserInfoUtils.getBrowserInfo());
			} else {
				ExtentManager.getExtentTest().assignDevice("API Test - No Browser");
			}
		} catch (Exception e) {
			ExtentManager.getExtentTest().assignDevice("Unknown Device");
		}
	}

	// ─── REPORT LIFECYCLE ──────────────────────────────────────

	public static ExtentReports getSetup() {
		return extent;
	}

	public static void flushReports() {
		if (Objects.nonNull(extent)) {
			extent.flush();
		}
		ExtentManager.unload();
	}
}