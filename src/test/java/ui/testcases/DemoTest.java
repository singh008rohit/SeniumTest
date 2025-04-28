   package ui.testcases;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import listeners.SeleniumListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import reportManager.ExtentReportManager;

@Listeners({SeleniumListener.class})
public class DemoTest extends BaseTest {
   private final String userName = "rohit";
   private final String password = "12Killer@345";

   @Test(
      description = "Test login to application with valid credentials"
   )
   public void loginToApplication() {
      this.loginPage.loginToApplication("rohit", "12Killer@345");
      ExtentReportManager.getTest().log(Status.INFO, "Login successful with username: rohit");
   }

   @Test(
      description = "Test navigation to URL and login scenario 1",
      enabled = false
   )
   public void navigateToURL() {
      this.loginPage.loginToApplication("rohit", "12Killer@345");
      ExtentReportManager.getTest().log(Status.INFO, "Login successful with username: rohit");
   }

   @Test(
      description = "Test navigation to URL and login scenario 2",
      enabled = false
   )
   public void navigateToURL2() {
      this.loginPage.loginToApplication("rohit", "12Killer@345");
      ExtentReportManager.getTest().log(Status.INFO, "Login successful with username: rohit");
   }
}