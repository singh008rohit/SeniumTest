package driver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import base.BaseTest;
import commonConstant.CommonConstant;
import io.github.bonigarcia.wdm.WebDriverManager;
import loggerUtil.LoggerUtils;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initializeDriver(String browser) throws IOException {
        WebDriver webDriver;
        boolean useGrid = Boolean.parseBoolean(BaseTest.getValueFromPropFile(CommonConstant.USER_GRID));
        boolean isHeadless = Boolean.parseBoolean(BaseTest.getValueFromPropFile("headless")); // add this key in config

        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (isHeadless) {
                        chromeOptions.addArguments("--headless", "--window-size=1920,1080");
                    }

                    webDriver = useGrid
                            ? new RemoteWebDriver(new URL(BaseTest.getValueFromPropFile(CommonConstant.GRIDURL)), chromeOptions)
                            : createLocalChromeDriver(chromeOptions);
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (isHeadless) {
                        firefoxOptions.addArguments("-headless");;
                    }

                    webDriver = useGrid
                            ? new RemoteWebDriver(new URL(BaseTest.getValueFromPropFile(CommonConstant.GRIDURL)), firefoxOptions)
                            : createLocalFirefoxDriver(firefoxOptions);
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (isHeadless) {
                        edgeOptions.addArguments("--headless", "--window-size=1920,1080");
                    }

                    webDriver = useGrid
                            ? new RemoteWebDriver(new URL(BaseTest.getValueFromPropFile(CommonConstant.GRIDURL)), edgeOptions)
                            : createLocalEdgeDriver(edgeOptions);
                    break;

                default:
                    throw new IllegalArgumentException("Invalid browser: " + browser);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Selenium Grid URL", e);
        }

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        webDriver.manage().deleteAllCookies();
        LoggerUtils.info("Launching browser: " + browser + " with grid: " + useGrid + " headless: " + isHeadless);
        driver.set(webDriver);
    }

    private static WebDriver createLocalChromeDriver(ChromeOptions options) {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(options);
    }

    private static WebDriver createLocalFirefoxDriver(FirefoxOptions options) {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(options);
    }

    private static WebDriver createLocalEdgeDriver(EdgeOptions options) {
        WebDriverManager.edgedriver().setup();
        return new EdgeDriver(options);
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            throw new IllegalStateException("WebDriver not initialized for current thread!");
        }
        return driver.get();
    }

    public static void unload() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}