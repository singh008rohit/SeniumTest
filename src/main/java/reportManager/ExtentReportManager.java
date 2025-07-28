package reportManager;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

import commonConstant.ExtentReportConstant;
import driver.DriverManager;
import enums.AuthorType;
import enums.CategoryType;
import utlity.BrowserInfoUtils;
import utlity.DateUtils;
import utlity.IconUtils;

public class ExtentReportManager  {

   

	private static volatile ExtentReports extent;    
    // 🔥 Method to Setup Extent Report
    public static ExtentReports getSetup() {
        if (extent == null) { // Prevent multiple initializations
            ExtentSparkReporter reporter = new ExtentSparkReporter(ExtentReportConstant.getExtentReportFilePath()+DateUtils.getCurrentDate());
            configureReporter(reporter);  // Move customization to a separate method
            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Tester", "Rohit Singh");
            extent.setSystemInfo("Environment", "QA");
         			extent.setSystemInfo("Organization", "QATest");
         			extent.setSystemInfo("Employee",
         					"<b> Rohit Singh </b>" + " " + ExtentReportConstant.ICON_SOCIAL_LINKEDIN + " " + ExtentReportConstant.ICON_SOCIAL_GITHUB);
         			extent.setSystemInfo("Domain", "Engineering (IT - Software)"+"  "+ExtentReportConstant.ICON_LAPTOP);
         			extent.setSystemInfo("Skill", "Test Automation Engineer");
        }
        return extent;
    }
    private static void configureReporter(ExtentSparkReporter reporter) {
        reporter.config().setReportName("Automation Test Report");
      //  reporter.config().setDocumentTitle("Test Execution Report");
        reporter.config().setTheme(Theme.STANDARD);
        reporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
        reporter.config().setEncoding("UTF-8");
        reporter.config().setProtocol(Protocol.HTTPS);
        reporter.config().setDocumentTitle(ExtentReportConstant.getProjectName() + " - ALL");
        reporter.config().setReportName(ExtentReportConstant.getProjectName() + " - ALL");

   
    }

    //  Create a Test Case in Extent Report
    public static synchronized void createTest(String testName) {
        String browserIcon;
        try {
            browserIcon = IconUtils.getBrowserIcon();
        } catch (Exception e) {
            browserIcon = "🧪"; // Generic test tube or any API icon
        }
        ExtentManager.setExtentTest(extent.createTest(browserIcon + " : " + testName));
    }

    
	synchronized public static void addAuthors(AuthorType[] authors) {
		for (AuthorType author : authors) {
			ExtentManager.getExtentTest().assignAuthor(author.toString());
		}
	}

	// public static void addCategories(String[] categories) {
	synchronized public static void addCategories(CategoryType[] categories) {
		// for (String category : categories) {
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
	
    //  Flush Extent Report After Execution
    public static void flushReports() {
		if (Objects.nonNull(extent)) {
			extent.flush();
		}
		ExtentManager.unload();
	//	try {
			//Desktop.getDesktop().browse(new File(ExtentReportConstant.getExtentReportFilePath()).toURI());
	//	} catch (IOException e) {
		//	e.printStackTrace();
		//}
	}

   
}
