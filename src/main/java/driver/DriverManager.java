package driver;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;


	public class DriverManager {

	    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	    public static WebDriver initializeDriver(String browser) {
	        WebDriver webDriver;
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
	                throw new IllegalArgumentException("Invalid browser name: " + browser);
	        }

	        webDriver.manage().window().maximize();
	        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));

	        driver.set(webDriver); // 
	        return webDriver;
	    }

	    public static WebDriver getDriver() {
	        return driver.get();
	    }

	    public static void unload() {
	        if (driver.get() != null) {
	            driver.get().quit();
	            driver.remove();
	        }
	    }
	    
	    @BeforeMethod(alwaysRun=true)
		
		 public void setup() throws IOException {
	      initializeDriver("chrome");
	    
	     
	    }
		
		@AfterMethod(alwaysRun=true)
		
		public void tearDown()
		{
			unload(); 
		}
	}






