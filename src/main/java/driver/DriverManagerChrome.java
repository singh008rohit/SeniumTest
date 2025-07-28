

package driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import loggerUtil.LoggerUtils;
import utlity.ConfigLoader;

public class DriverManagerChrome implements DriverManager_OC {
	boolean useGrid = Boolean.parseBoolean(ConfigLoader.getInstance().getuseGrid());
	boolean isHeadless = Boolean.parseBoolean(ConfigLoader.getInstance().getIsheadless());

	@Override
	public WebDriver createDriver() {
		WebDriver webDriver = null;
		 ChromeOptions chromeOptions = new ChromeOptions();
         if (isHeadless) {
             chromeOptions.addArguments("--headless");
         }

         try {
			webDriver = useGrid
			         ? new RemoteWebDriver(new URL(ConfigLoader.getInstance().getGridUrl()), chromeOptions)
			         : createLocalChromeDriver(chromeOptions);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         webDriver.manage().window().maximize();
         webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
         webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
         webDriver.manage().deleteAllCookies();
         LoggerUtils.info("Launching browser:  with grid: " + useGrid + " headless: " + isHeadless);
return webDriver;
}
	 private static WebDriver createLocalChromeDriver(ChromeOptions options) {
	        WebDriverManager.chromedriver().setup();
	        return new ChromeDriver(options);
	    }
	
	
}
