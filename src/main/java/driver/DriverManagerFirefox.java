package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import loggerUtils.LogUtils;
import utlity.ConfigLoader;

public class DriverManagerFirefox implements DriverManager_OC {

    private final boolean isHeadless =
        Boolean.parseBoolean(ConfigLoader.getInstance().getIsheadless());

    @Override
    public WebDriver createDriver() {
        FirefoxOptions options = new FirefoxOptions();

        if (isHeadless) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");          // add this
            options.addArguments("--window-size=1920,1080"); // add this — prevents layout issues
            LogUtils.info("Firefox running in headless mode");
        }

        // isGridEnabled() and createRemoteDriver() inherited from interface
        WebDriver driver = isGridEnabled()
            ? createRemoteDriver(options)
            : createLocalDriver(options);

        // configureDriver() inherited from interface — waits, timeouts, cookies
        return configureDriver(driver);
    }

    private WebDriver createLocalDriver(FirefoxOptions options) {
        WebDriverManager.firefoxdriver().setup();
        LogUtils.info("Firefox local driver created");
        return new FirefoxDriver(options);
    }
}
