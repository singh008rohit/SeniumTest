import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import LoggerUtil.LoggerUtils;
import driver.SeleniumListnere;
import driver.DriverManager;
import pages.LoginPage;
import reportManager.ExtentReportManager;
@Listeners({SeleniumListnere.class})
public class DemoTest extends DriverManager {
	
private 	LoginPage loginPage;
private final	String userName ="rohit";
private final	String password ="12Killer@345";

@BeforeMethod
public

 void setup1() {
	//DriverManager.initializeDriver("chrome");
	DriverManager.getDriver().get("https://www.blazemeter.com/");
	 loginPage	= new LoginPage();
    
ExtentReportManager.getTest().log(Status.INFO, "Setup complated");
	
}

	
	@Test
	
	void loginToApplication() {
		 
		 loginPage.loginToApplication(userName, password);
		 ExtentReportManager.getTest().log(Status.INFO, "Login to application with username: "+userName+" and password:"+password);
		 
		 
	}

}
