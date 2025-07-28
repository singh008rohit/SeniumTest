package driver;

import org.openqa.selenium.WebDriver;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    
    public static WebDriver getDriver() {
    	return driver.get();
    }
    
    public static void setDriver(WebDriver driverref) {
    	
    	driver.set(driverref);
    	
    }
    
    public static void unload() {
    	
    	driver.remove();
    	
    }

   
}