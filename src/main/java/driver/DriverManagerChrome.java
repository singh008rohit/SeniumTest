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

    @Override
    public WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();

        if (isHeadless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");          // add this
            options.addArguments("--window-size=1920,1080"); 
            options.setExperimentalOption("excludeSwitches", 
            	    java.util.Arrays.asList("enable-automation"));// add this — prevents layout issues
            LogUtils.info("Chrome running in headless mode");
        }

        // isGridEnabled() and createRemoteDriver() inherited from interface
        WebDriver driver = isGridEnabled()
            ? createRemoteDriver(options)
            : createLocalDriver(options);

        // configureDriver() inherited from interface — waits, timeouts, cookies
        return configureDriver(driver);
    }

    private WebDriver createLocalDriver(ChromeOptions options) {
        WebDriverManager.chromedriver().setup();
        LogUtils.info("Chrome local driver created");
        return new ChromeDriver(options);
    }
}
