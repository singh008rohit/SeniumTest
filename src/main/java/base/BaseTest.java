   package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import commonConstant.CommonConstant;
import driver.DriverManager;
import driver.DriverManagerFactory;
import enums.DriverType;
import listeners.AnnotationTransformer;
import listeners.MethodInterceptor;
import listeners.SeleniumListener;
import loggerUtil.LoggerUtils;
import pages.HomePage;
import pages.LoginPage;
import pages.NewUserSignUpPage;
import reportManager.ExtentReportManager;
import test.data.MapTestData;
import utlity.SeleniumCommonUtils;

@Listeners({
	AnnotationTransformer.class,
	SeleniumListener.class,
	MethodInterceptor.class
})

public class BaseTest {

	protected WebDriver getDriver() {
		return DriverManager.getDriver();
	}
	
	protected void setDriver(WebDriver driver) {
		DriverManager.setDriver(driver);
		
	}

   @BeforeMethod(alwaysRun = true)
   @Parameters("browser")
   public  synchronized void  startDriver(@Optional String browser) throws IOException {   
 browser=setBrowserValue(browser).toUpperCase();
 setDriver(DriverManagerFactory.getManager(DriverType.valueOf(browser)).createDriver());
 LoggerUtils.info("Current Thread info = " + Thread.currentThread().getId() + ", Driver = " + getDriver());
 
   }

  
   public static String setBrowserValue(String browser) {
	    // Fetch system property first (highest priority)
	    String systemProp = System.getProperty("browser");

	    if (systemProp != null) {
	        LoggerUtils.info("Browser specified via system property: " + systemProp.toUpperCase());
	        return systemProp;
	    }

	    if (browser != null) {
	        LoggerUtils.info("Browser passed via code or TestNG.xml: " + browser.toUpperCase());
	        return browser;
	    }

	    LoggerUtils.info("No browser provided, defaulting to CHROME");
	    return "CHROME";
	}

   private void takeScreenShotOfTestFailure(String browser,ITestResult result ) {
	 browser=   setBrowserValue(browser);
	 LoggerUtils.error("Current Thread info = " + Thread.currentThread().getId() + ", Driver = "+getDriver());
	 if(result.getStatus()==ITestResult.FAILURE) {
		 File destFile = new File("Screenshots" + File.separator + browser + File.separator
					+ result.getTestClass().getRealClass().getSimpleName() + "_" + result.getMethod().getMethodName()
					+ ".png");
		 SeleniumCommonUtils.screenshot(destFile);
	 }
   	
   }
   @Parameters("browser")
   @AfterMethod(alwaysRun = true )
   public  void tearDown(@Optional String browser,ITestResult result) {
	   takeScreenShotOfTestFailure(browser,result);
	   getDriver().quit();
      
   }
}
    
