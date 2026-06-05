package base;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import driver.DriverManager;
import driver.DriverManagerFactory;
import enums.DriverType;
import listeners.AnnotationTransformer;
import listeners.MethodInterceptor;
import listeners.SeleniumListener;
import loggerUtils.LogUtils;
import utlity.ConfigLoader;
import utlity.SeleniumCommonUtils;
import java.io.File;

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

 // BaseTest.java
    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public synchronized void startDriver(@Optional String browser) throws IOException {
        browser = setBrowserValue(browser).toUpperCase();
        LogUtils.info("Attempting to start driver for browser: " + browser);
        
        try {
            WebDriver driver = DriverManagerFactory
                .getManager(DriverType.valueOf(browser))
                .createDriver();
            setDriver(driver);
            driver.get(ConfigLoader.getInstance().getBaseUrl());
            LogUtils.info("Driver started successfully — Thread: " 
                + Thread.currentThread().getId()
                + ", Driver: " + getDriver());
            
            
        } catch (Exception e) {
            LogUtils.error("CRITICAL: @BeforeMethod failed for browser [" + browser 
                + "] on Thread: " + Thread.currentThread().getId() 
                + " — Reason: " + e.getMessage());
            throw e; // re-throw so TestNG marks it correctly as config failure, not silent skip
        }
    }

    public static String setBrowserValue(String browser) {
        String systemProp = System.getProperty("browser");
        if (systemProp != null) {
        	LogUtils.info("Browser from system property: " + systemProp.toUpperCase());
            return systemProp;
        }
        if (browser != null) {
        	LogUtils.info("Browser from TestNG xml: " + browser.toUpperCase());
            return browser;
        }
        LogUtils.info("No browser provided, defaulting to CHROME");
        return "CHROME";
    }

    
 // BaseTest.java
    @AfterMethod(alwaysRun = true)
    @Parameters("browser")
    public void tearDown(@Optional String browser, ITestResult result) {
        if (getDriver() != null) {
            takeScreenshotOnFailure(browser, result);
            getDriver().quit();
            DriverManager.unload();
        } else {
            LogUtils.warn("tearDown — driver was null for browser ["
                + setBrowserValue(browser) 
                + "] on Thread: " + Thread.currentThread().getId()
                + " (likely @BeforeMethod config failure)");
        }
    }

    private void takeScreenshotOnFailure(String browser, ITestResult result) {
        browser = setBrowserValue(browser);
        LogUtils.error("Thread: " + Thread.currentThread().getId()
            + ", Driver: " + getDriver());
        if (result.getStatus() == ITestResult.FAILURE && getDriver() != null) {
            File destFile = new File("Screenshots" + File.separator + browser
                + File.separator
                + result.getTestClass().getRealClass().getSimpleName()
                + "_" + result.getMethod().getMethodName() + ".png");
            SeleniumCommonUtils.screenshot(destFile);
        }
    }
}
