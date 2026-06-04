package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import loggerUtils.LogUtils;
import utlity.ConfigLoader;

public class DriverManagerChrome implements DriverManager_OC {

    private final boolean isHeadless = 
        Boolean.parseBoolean(ConfigLoader.getInstance().getIsheadless());

 // DriverManagerChrome.java
    @Override
    public WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();

        if (isHeadless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-extensions");
            options.addArguments("--no-first-run");
            options.addArguments("--disable-background-networking");
            options.addArguments("--disable-default-apps");
            options.addArguments("--disable-sync");
            options.addArguments("--remote-debugging-port=0");
            // ADD THIS — forces each ChromeDriver to use a fresh isolated user data dir
            options.addArguments("--user-data-dir=/tmp/chrome-" 
                + Thread.currentThread().getId() 
                + "-" + System.currentTimeMillis());
            LogUtils.info("Chrome running in headless mode");
        } else {
            options.addArguments("--disable-extensions");
            options.addArguments("--no-first-run");
            options.addArguments("--user-data-dir=/tmp/chrome-" 
                + Thread.currentThread().getId() 
                + "-" + System.currentTimeMillis());
        }

        WebDriver driver = isGridEnabled()
            ? createRemoteDriver(options)
            : createLocalDriver(options);

        return configureDriver(driver);
    }

    private WebDriver createLocalDriver(ChromeOptions options) {
        WebDriverManager.chromedriver().setup();
        LogUtils.info("Chrome local driver created");
        return new ChromeDriver(options);
    }
}
