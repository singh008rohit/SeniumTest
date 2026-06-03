package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import loggerUtil.LoggerUtils;
import utlity.ConfigLoader;

public class DriverManagerEdge implements DriverManager_OC {

    private final boolean isHeadless =
        Boolean.parseBoolean(ConfigLoader.getInstance().getIsheadless());

    @Override
    public WebDriver createDriver() {
        EdgeOptions options = new EdgeOptions();

        if (isHeadless) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            LoggerUtils.info("Edge running in headless mode");
        }

        // isGridEnabled() and createRemoteDriver() inherited from interface
        WebDriver driver = isGridEnabled()
            ? createRemoteDriver(options)
            : createLocalDriver(options);

        // configureDriver() inherited from interface — waits, timeouts, cookies
        return configureDriver(driver);
    }

    private WebDriver createLocalDriver(EdgeOptions options) {
        WebDriverManager.edgedriver().setup();
        LoggerUtils.info("Edge local driver created");
        return new EdgeDriver(options);
    }
}