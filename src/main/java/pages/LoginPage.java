    package pages;

import utlity.SeleniumCommonUtils;
import driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LoginPage {
   private WebDriver driver = DriverManager.getDriver();
   private static String message = "Invalid username or password.";
   @FindBy(
      xpath = "//a[@data-title='Login']"
   )
   WebElement loginLink;
   @FindBy(
      css = "#username"
   )
   WebElement username;
   @FindBy(
      css = "#kc-login"
   )
   WebElement signIN;
   @FindBy(
      css = "#password"
   )
   WebElement passwordWebelement;
   @FindBy(
      xpath = "//span[@class='kc-feedback-text']"
   )
   WebElement errorMessage;

   public LoginPage() {
      PageFactory.initElements(this.driver, this);
   }

   public void loginToApplication(String userName, String password) {
      SeleniumCommonUtils.clickElement(this.loginLink);
      SeleniumCommonUtils.typeText(this.username, userName);
      SeleniumCommonUtils.typeText(this.passwordWebelement, password);
      SeleniumCommonUtils.clickElement(this.signIN);
      Assert.assertEquals(SeleniumCommonUtils.getText(this.errorMessage), message, "able to login");
   }
}