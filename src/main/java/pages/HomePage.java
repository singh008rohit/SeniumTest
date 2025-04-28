 package pages;

import com.aventstack.extentreports.Status;
import driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import reportManager.ExtentReportManager;
import utlity.SeleniumCommonUtils;

public class HomePage {
   private WebDriver driver = DriverManager.getDriver();
   private static final String homePageTitle = "Automation Exercise";
   private static final String signupPageTitle = "Automation Exercise - Signup / Login";
   private static final String deletemsg1 = "Account Deleted!";
   private static final String deletemsg2 = "Your account has been permanently deleted!";
   @FindBy(
      xpath = "//a[normalize-space()='Signup / Login']"
   )
   WebElement signup;
   @FindBy(
      xpath = "//b['demo@gmail.com']"
   )
   WebElement loginUser;
   @FindBy(
      xpath = "//a[normalize-space()='Delete Account']"
   )
   WebElement deleteAccountLink;
   @FindBy(
      xpath = "//b[text()'Account Deleted!']"
   )
   WebElement deleteAccountMsg;
   @FindBy(
      xpath = "//p[text()='Your account has been permanently deleted!']"
   )
   WebElement deleteAccountMsg1;
   @FindBy(
      xpath = "//a[text()='Continue']"
   )
   WebElement continueBtn;

   public HomePage() {
      if (this.driver == null) {
         throw new IllegalStateException("WebDriver is not initialized.");
      } else {
         PageFactory.initElements(this.driver, this);
      }
   }

   public void validatenavigatedToHomePage() {
      Assert.assertEquals(this.driver.getTitle(), "Automation Exercise", "Home page title is incorrect");
   }

   public void clickOnSignUpButton() {
      SeleniumCommonUtils.waitForVisibility(this.signup, 10);
      SeleniumCommonUtils.clickElement(this.signup);
   }

   public void validateNavigatedToSignupPage() {
      Assert.assertEquals(this.driver.getTitle(), "Automation Exercise - Signup / Login", "signup page title is incorrect");
   }

   public void loginWithUser(String email) {
      SeleniumCommonUtils.waitForVisibility(this.loginUser, 10);
      String userEmail = SeleniumCommonUtils.getText(this.loginUser);
      Assert.assertEquals(userEmail, email);
      Assert.assertTrue(SeleniumCommonUtils.isElementExist(this.driver, By.xpath("//a[normalize-space()='Delete Account']")));
      ExtentReportManager.getTest().log(Status.INFO, "Login with :" + email);
   }

   public void deleteAccount() {
      Assert.assertTrue(SeleniumCommonUtils.isElementExist(this.driver, By.xpath("//a[normalize-space()='Delete Account']")));
      SeleniumCommonUtils.clickElement(this.deleteAccountLink);
      String msg1 = SeleniumCommonUtils.getText(this.deleteAccountMsg);
      String msg2 = SeleniumCommonUtils.getText(this.deleteAccountMsg1);
      Assert.assertEquals(msg1, "Account Deleted!");
      Assert.assertEquals(msg2, "Your account has been permanently deleted!");
      SeleniumCommonUtils.clickElement(this.continueBtn);
   }

   public void verifyAccountdeleted() {
      Assert.assertTrue(SeleniumCommonUtils.isElementNotExist(this.driver, By.xpath("//a[normalize-space()='Delete Account']")));
      ExtentReportManager.getTest().log(Status.PASS, "Account is deleted");
   }
}