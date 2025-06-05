   package base;

import driver.DriverManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import loggerUtil.LoggerUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import commonConstant.CommonConstant;
import pages.HomePage;
import pages.LoginPage;
import pages.NewUserSignUpPage;
import reportManager.ExtentReportManager;
import test.data.MapTestData;

public abstract class BaseTest {
   protected LoginPage loginPage;
   protected PageObjectManager pageManager = new PageObjectManager();
   protected NewUserSignUpPage newuserSingUpPage;
   protected HomePage homePage;
   protected MapTestData mapTestData;

   @BeforeMethod(alwaysRun = true)
   @Parameters("browser")
   public void setup(@Optional("") String xmlBrowser) throws IOException {
       // Priority 1: CLI `-Dbrowser=...`
       String cliBrowser = System.getProperty("browser");

       String browser;
       if (cliBrowser != null && !cliBrowser.isEmpty()) {
           browser = cliBrowser;
           LoggerUtils.info("Browser fetched from CLI: " + browser);
       } else if (xmlBrowser != null && !xmlBrowser.isEmpty()) {
           browser = xmlBrowser;
           LoggerUtils.info("Browser fetched from testng.xml: " + browser);
       } else {
           browser = getValueFromPropFile(CommonConstant.BROWSERTYPE);
           LoggerUtils.info("Browser fetched from properties file: " + browser);
       }
      ExtentReportManager.getSetup("UI_TestSuite");
      DriverManager.initializeDriver(browser);
      DriverManager.getDriver().get(getValueFromPropFile(CommonConstant.URL));
      LoggerUtils.info("Navigated to application and LoginPage initialized");
      this.loginPage = new LoginPage();
      this.homePage = new HomePage();
      this.mapTestData = new MapTestData();
      this.newuserSingUpPage = new NewUserSignUpPage();
   }

   public static String getValueFromPropFile(String key) {
	    Properties prop = new Properties();
	    String value = null;
	    
	    try (FileInputStream file = new FileInputStream(System.getProperty(CommonConstant.USER_DIRECTORY) + CommonConstant.FILE_PATH)) {
	        prop.load(file);
	        value = prop.getProperty(key);
	    } catch (IOException e) {
	        LoggerUtils.error("Error loading properties file: " + e.getMessage());
	        throw new RuntimeException("Could not load properties file", e);
	    }
	    
	    return value;
	}

   
  
   @AfterMethod(
      alwaysRun = true
   )
   public void tearDown() {
      DriverManager.unload();
   }
}
    
