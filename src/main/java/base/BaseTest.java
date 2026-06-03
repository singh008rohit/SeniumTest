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

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public synchronized void startDriver(@Optional String browser) throws IOException {
        browser = setBrowserValue(browser).toUpperCase();
        WebDriver driver = DriverManagerFactory
            .getManager(DriverType.valueOf(browser))
            .createDriver();
        setDriver(driver);

        // navigate once here — not in BasePage constructor
        driver.get(ConfigLoader.getInstance().getBaseUrl());

        LogUtils.info("Thread: " + Thread.currentThread().getId()
            + ", Driver: " + getDriver());
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

    @Parameters("browser")
    @AfterMethod(alwaysRun = true)
    public void tearDown(@Optional String browser, ITestResult result) {
        takeScreenshotOnFailure(browser, result);
        if (getDriver() != null) {
            getDriver().quit();
        }
        DriverManager.unload(); // fix — was missing, causes ThreadLocal memory leak
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
