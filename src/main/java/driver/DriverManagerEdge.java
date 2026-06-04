package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import loggerUtils.LogUtils;
import utlity.ConfigLoader;

public class DriverManagerEdge implements DriverManager_OC {

    private final boolean isHeadless =
        Boolean.parseBoolean(ConfigLoader.getInstance().getIsheadless());

    @Override
    public WebDriver createDriver() {
        EdgeOptions options = new EdgeOptions();

        if (isHeadless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");          // add this
            options.addArguments("--window-size=1920,1080"); // add this — prevents layout issues
            LogUtils.info("Edge running in headless mode");
        }

        // isGridEnabled() and createRemoteDriver() inherited from interface
        WebDriver driver = isGridEnabled()
            ? createRemoteDriver(options)
            : createLocalDriver(options);

        // configureDriver() inherited from interface — waits, timeouts, cookies
        return configureDriver(driver);
    }

 // DriverManagerEdge.java
    private WebDriver createLocalDriver(EdgeOptions options) {
        // Verify Edge binary exists before WebDriverManager tries to match a driver
        String edgePath = "/Applications/Microsoft Edge.app/Contents/MacOS/Microsoft Edge";
        if (!new java.io.File(edgePath).exists()) {
            throw new RuntimeException(
                "Microsoft Edge not found at: " + edgePath 
                + " — install Edge or remove Edge from testng-crossbrowser.xml");
        }
       
        WebDriverManager.edgedriver().setup();
        
        LogUtils.info("Edge local driver created");
        return new EdgeDriver(options);
    }
}
