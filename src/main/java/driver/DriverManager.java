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

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initializeDriver(String browser) throws IOException {
        WebDriver webDriver;
        boolean useGrid = Boolean.parseBoolean(BaseTest.getValueFromPropFile(CommonConstant.USER_GRID));

        if (useGrid) {
            try {
                URL gridUrl = new URL(CommonConstant.GRIDURL);
                switch (browser.toLowerCase()) {
                    case "chrome":
                        webDriver = new RemoteWebDriver(gridUrl, new ChromeOptions());
                        break;
                    case "firefox":
                        webDriver = new RemoteWebDriver(gridUrl, new FirefoxOptions());
                        break;
                    case "edge":
                        webDriver = new RemoteWebDriver(gridUrl, new EdgeOptions());
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid browser: " + browser);
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid Selenium Grid URL", e);
            }
        } else {
            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    webDriver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    webDriver = new FirefoxDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    webDriver = new EdgeDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid browser: " + browser);
            }
        }

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        webDriver.manage().deleteAllCookies();

        driver.set(webDriver);
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